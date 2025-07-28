package com.ERI.demo.config;

import com.ERI.demo.dto.ErrorResponseDto;
import com.ERI.demo.vo.employee.EmpLstVO;
import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.mappers.CounselorLstMapper;
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
    
    @Autowired
    private AdminLstMapper adminLstMapper;
    
    @Autowired
    private CounselorLstMapper counselorLstMapper;
    
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
        
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            log.warn("인증 정보가 없음: {} {}", method, requestURI);
            handleUnauthorized(request, response, "인증 정보가 없습니다.");
            return false;
        }
        
        // 세션 정보를 요청 속성으로 전달 (컨트롤러에서 재사용)
        request.setAttribute("EMP_INFO", empInfo);
        request.setAttribute("EMP_ID", empInfo.getEriEmpId());
        
        // 관리자 여부도 요청 속성으로 전달
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        request.setAttribute("isAdmin", isAdmin);
        
        // 상담사 여부 확인 및 요청 속성으로 전달
        Boolean isCounselor = (Boolean) session.getAttribute("isCounselor");
        if (isCounselor == null) {
            // 세션에 없으면 DB에서 확인
            isCounselor = checkCounselorStatus(empInfo.getEriEmpId());
            session.setAttribute("isCounselor", isCounselor);
        }
        request.setAttribute("isCounselor", isCounselor);
        
        log.debug("인증 성공: {} {} (사용자: {}, 관리자: {}, 상담사: {})", 
                method, requestURI, empInfo.getEriEmpId(), isAdmin, isCounselor);

        String uri = request.getRequestURI();

        // 공지사항 관리 권한 체크 (POST, PUT, DELETE 요청)
        if (uri.startsWith("/api/notice/") && 
            (("POST".equals(method) && uri.equals("/api/notice")) ||  // 등록
             ("PUT".equals(method) || "DELETE".equals(method)) && uri.matches("/api/notice/\\d+"))) {  // 수정/삭제
            if (isAdmin == null || !isAdmin) {
                log.warn("관리자 권한이 없는 사용자의 공지사항 관리 접근 시도: {} {} (사용자: {})", method, uri, empInfo.getEriEmpId());
                sendErrorResponse(response, new ErrorResponseDto(
                    "ACCESS_DENIED",
                    "공지사항 등록/수정/삭제는 관리자 권한이 필요합니다.",
                    403,
                    request.getRequestURI()
                ));
                return false;
            }
            log.debug("공지사항 관리 권한 체크 통과: {} {} (사용자: {})", method, uri, empInfo.getEriEmpId());
        }
        
        // 관리자 권한이 필요한 경로 체크
        if (isAdminRequiredPath(uri)) {
            if (isAdmin == null || !isAdmin) {
                log.warn("관리자 권한이 없는 사용자의 관리자 기능 접근 시도: {} {} (사용자: {})", method, uri, empInfo.getEriEmpId());
                sendErrorResponse(response, new ErrorResponseDto(
                    "ACCESS_DENIED",
                    "관리자 권한이 필요합니다.",
                    403,
                    request.getRequestURI()
                ));
                return false;
            }
            log.debug("관리자 권한 체크 통과: {} {} (사용자: {})", method, uri, empInfo.getEriEmpId());
        }

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
        
        // 공지사항 조회 (공개)
        if (requestURI.startsWith("/api/notice/list") || 
            requestURI.matches("/api/notice/\\d+")) {
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
     * 관리자 권한이 필요한 경로인지 확인
     */
    private boolean isAdminRequiredPath(String requestURI) {
        // 관리자 기능 경로들
        if (requestURI.startsWith("/api/admin/") ||
            requestURI.startsWith("/api/cmn-code/") ||
            requestURI.startsWith("/api/auth/") ||
            requestURI.startsWith("/admin/")) {
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

    /**
     * 직원의 관리자 여부 확인
     * @param eriEmpId ERI 직원ID
     * @return 관리자 여부
     */
    private boolean checkAdminStatus(String eriEmpId) {
        try {
            // TB_ADMIN_LST 테이블에서 관리자 정보 조회
            int adminCount = adminLstMapper.countByAdminId(eriEmpId);
            boolean isAdmin = adminCount > 0;
            
            log.debug("관리자 여부 확인: {} -> {}", eriEmpId, isAdmin);
            return isAdmin;
            
        } catch (Exception e) {
            log.error("관리자 여부 확인 중 오류 발생: {}, 오류: {}", eriEmpId, e.getMessage(), e);
            return false; // 오류 발생 시 관리자가 아닌 것으로 처리
        }
    }
    
    /**
     * 직원의 상담사 여부 확인
     * @param eriEmpId ERI 직원ID
     * @return 상담사 여부
     */
    private boolean checkCounselorStatus(String eriEmpId) {
        try {
            // TB_COUNSELOR_LST 테이블에서 상담사 정보 조회
            int counselorCount = counselorLstMapper.countByCounselorEmpId(eriEmpId);
            boolean isCounselor = counselorCount > 0;
            
            log.debug("상담사 여부 확인: {} -> {}", eriEmpId, isCounselor);
            return isCounselor;
            
        } catch (Exception e) {
            log.error("상담사 여부 확인 중 오류 발생: {}, 오류: {}", eriEmpId, e.getMessage(), e);
            return false; // 오류 발생 시 상담사가 아닌 것으로 처리
        }
    }
} 