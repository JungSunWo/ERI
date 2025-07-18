package com.ERI.demo.config;

import com.ERI.demo.dto.ErrorResponseDto;
import com.ERI.demo.dto.UserAuthDto;
import com.ERI.demo.exception.AccessDeniedException;
import com.ERI.demo.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        log.debug("인증 인터셉터 실행: {} {}", method, requestURI);
        
        // OPTIONS 요청 (CORS preflight)은 통과
        if ("OPTIONS".equals(method)) {
            log.debug("OPTIONS 요청 통과: {}", requestURI);
            return true;
        }

        // DELETE 요청 (CORS preflight)은 통과
        if ("DELETE".equals(method)) {
            log.debug("DELETE 요청 통과: {}", requestURI);
            return true;
        }
        
        // 인증이 필요하지 않은 경로는 통과
        if (isPublicPath(requestURI)) {
            log.debug("공개 경로 통과: {}", requestURI);
            return true;
        }
        
        // 세션에서 사용자 정보 확인
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.warn("세션이 없음: {} {}", method, requestURI);
            handleUnauthorized(request, response, "로그인이 필요합니다.");
            return false;
        }
        
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            log.warn("인증 정보가 없음: {} {}", method, requestURI);
            handleUnauthorized(request, response, "인증 정보가 없습니다.");
            return false;
        }
        
        log.debug("인증 성공: {} {} (사용자: {})", method, requestURI, userAuth.getUserId());

        String uri = request.getRequestURI();

        // 공통코드 관리 예시 (메뉴코드: CMN_CODE)
        if (uri.startsWith("/api/cmn-code/group-codes") || uri.startsWith("/api/cmn-code/detail-codes")) {
            if ("GET".equals(method)) {
                if (!userAuth.canRead("CMN_CODE")) {
                    sendErrorResponse(response, new ErrorResponseDto(
                        "ACCESS_DENIED_READ",
                        "공통 코드 조회 권한이 없습니다.",
                        403,
                        request.getRequestURI()
                    ));
                    return false;
                }
            } else if ("POST".equals(method)) {
                if (!userAuth.canCreate("CMN_CODE")) {
                    sendErrorResponse(response, new ErrorResponseDto(
                        "ACCESS_DENIED_CREATE",
                        "공통 코드 등록 권한이 없습니다.",
                        403,
                        request.getRequestURI()
                    ));
                    return false;
                }
            } else if ("PUT".equals(method)) {
                if (!userAuth.canUpdate("CMN_CODE")) {
                    sendErrorResponse(response, new ErrorResponseDto(
                        "ACCESS_DENIED_UPDATE",
                        "공통 코드 수정 권한이 없습니다.",
                        403,
                        request.getRequestURI()
                    ));
                    return false;
                }
            } else if ("DELETE".equals(method)) {
                if (!userAuth.canDelete("CMN_CODE")) {
                    sendErrorResponse(response, new ErrorResponseDto(
                        "ACCESS_DENIED_DELETE",
                        "공통 코드 삭제 권한이 없습니다.",
                        403,
                        request.getRequestURI()
                    ));
                    return false;
                }
            }
        }
        // 필요시 다른 메뉴코드/URI도 추가

        return true;
    }
    
    /**
     * 공개 경로인지 확인 (인증이 필요하지 않은 경로)
     */
    private boolean isPublicPath(String requestURI) {
        // 로그인 관련 경로
        if (requestURI.startsWith("/api/auth/login") || 
            requestURI.startsWith("/api/auth/logout") ||
            requestURI.startsWith("/login") ||
            requestURI.startsWith("/logout")) {
            return true;
        }
        
       
        
        // 정적 리소스
        if (requestURI.startsWith("/static/") || 
            requestURI.startsWith("/css/") ||
            requestURI.startsWith("/js/") ||
            requestURI.startsWith("/images/") ||
            requestURI.startsWith("/favicon.ico")) {
            return true;
        }
        
        // API 문서
        if (requestURI.startsWith("/swagger-ui/") || 
            requestURI.startsWith("/v3/api-docs/")) {
            return true;
        }
        
        // 헬스 체크
        if (requestURI.equals("/health") || requestURI.equals("/actuator/health")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 인증되지 않은 요청 처리
     * API 요청인 경우 JSON 에러 응답, 웹 페이지 요청인 경우 로그인 페이지로 리다이렉트
     */
    private void handleUnauthorized(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        // API 요청인지 확인 (URI 패턴 또는 Accept 헤더로 판단)
        if (isApiRequest(request)) {
            sendErrorResponse(response, ErrorResponseDto.unauthorized(request.getRequestURI()));
        } else {
            // 웹 페이지 요청인 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
        }
    }
    
    /**
     * API 요청인지 확인
     */
    private boolean isApiRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String accept = request.getHeader("Accept");
        String contentType = request.getHeader("Content-Type");
        
        // URI가 /api/로 시작하는 경우
        if (uri.startsWith("/api/")) {
            return true;
        }
        
        // Accept 헤더에 application/json이 포함된 경우
        if (accept != null && accept.contains("application/json")) {
            return true;
        }
        
        // Content-Type이 application/json인 경우
        if (contentType != null && contentType.contains("application/json")) {
            return true;
        }
        
        // X-Requested-With 헤더가 XMLHttpRequest인 경우 (AJAX 요청)
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && "XMLHttpRequest".equals(xRequestedWith)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 에러 응답 전송
     */
    private void sendErrorResponse(HttpServletResponse response, ErrorResponseDto errorResponse) throws IOException {
        response.setStatus(errorResponse.getStatus());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
} 