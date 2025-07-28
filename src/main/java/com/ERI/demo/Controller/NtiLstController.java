package com.ERI.demo.Controller;

import com.ERI.demo.dto.ErrorResponseDto;
import com.ERI.demo.dto.NtiLstPageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.NtiLstService;
import com.ERI.demo.vo.NtiLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nti")
public class NtiLstController {

    @Autowired
    private NtiLstService ntiLstService;

    /**
     * 공지사항 목록 조회 (전체)
     * @param ttl 제목 검색어 (선택)
     * @param stsCd 상태코드 (선택)
     * @param regEmpId 등록자ID (선택)
     * @return 공지사항 목록
     */
    @GetMapping("/list")
    public ResponseEntity<?> getNtiLstList(
            @RequestParam(required = false) String ttl,
            @RequestParam(required = false) String stsCd,
            @RequestParam(required = false) String regEmpId) {
        
        try {
            NtiLstVO searchVO = new NtiLstVO();
            searchVO.setTtl(ttl);
            searchVO.setStsCd(stsCd);
            searchVO.setRegEmpId(regEmpId);
            
            List<NtiLstVO> noticeList = ntiLstService.getNtiLstList(searchVO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", noticeList);
            response.put("count", noticeList.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/list");
            errorResponse.setErrorMessage("공지사항 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 공지사항 페이징 목록 조회
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지 크기 (기본값: 10)
     * @param sortKey 정렬 필드 (선택, 기본값: seq)
     * @param sortOrder 정렬 방향 (선택, asc/desc, 기본값: desc)
     * @param ttl 제목 검색어 (선택)
     * @param stsCd 상태코드 (선택)
     * @param regEmpId 등록자ID (선택)
     * @param startDate 시작일자 (선택, YYYY-MM-DD 형식)
     * @param endDate 종료일자 (선택, YYYY-MM-DD 형식)
     * @return 페이징된 공지사항 목록
     */
    @GetMapping("/page")
    public ResponseEntity<?> getNtiLstListWithPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortKey,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String ttl,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String searchField,
            @RequestParam(required = false) String stsCd,
            @RequestParam(required = false) String regEmpId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        try {
            // 날짜 파라미터 로깅 추가
            System.out.println("=== 날짜 파라미터 디버깅 ===");
            System.out.println("startDate: " + startDate);
            System.out.println("endDate: " + endDate);
            System.out.println("==========================");
            
            // 페이징 요청 DTO 생성
            NtiLstPageRequestDto pageRequest = new NtiLstPageRequestDto();
            pageRequest.setPage(page);
            pageRequest.setSize(size);
            pageRequest.setSortBy(sortKey != null ? sortKey : "seq");
            pageRequest.setSortDirection(sortOrder != null ? sortOrder.toUpperCase() : "ASC");
            
            // 검색 조건 설정 (기존 ttl 파라미터와 새로운 searchKeyword/searchField 파라미터 모두 지원)
            if (searchKeyword != null && searchField != null) {
                // 새로운 검색 파라미터 사용
                if ("ttl".equals(searchField)) {
                    pageRequest.setTtl(searchKeyword);
                } else if ("cntn".equals(searchField)) {
                    pageRequest.setCntn(searchKeyword);
                } else if ("both".equals(searchField)) {
                    // 제목+내용 검색
                    pageRequest.setTtl(searchKeyword);
                    pageRequest.setCntn(searchKeyword);
                }
            } else if (ttl != null) {
                // 기존 ttl 파라미터 사용 (하위 호환성)
                pageRequest.setTtl(ttl);
            }
            
            pageRequest.setStsCd(stsCd);
            pageRequest.setRegEmpId(regEmpId);
            pageRequest.setStartDate(startDate);
            pageRequest.setEndDate(endDate);
            
            // 페이징된 목록 조회
            PageResponseDto<NtiLstVO> pageResponse = ntiLstService.getNtiLstListWithPaging(pageRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", pageResponse);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/page");
            errorResponse.setErrorMessage("공지사항 페이징 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 공지사항 상세 조회
     * @param id 공지사항 일련번호
     * @return 공지사항 상세 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNtiLst(@PathVariable Long id) {
        try {
            NtiLstVO notice = ntiLstService.getNtiLst(id);
            
            if (notice == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.notFound("해당 공지사항을 찾을 수 없습니다.", "/api/nti/" + id);
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", notice);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/" + id);
            errorResponse.setErrorMessage("공지사항 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 공지사항 등록
     * @param ntiLstVO 공지사항 정보
     * @param session 세션 정보
     * @return 등록 결과
     */
    @PostMapping("/register")
    public ResponseEntity<?> insertNtiLst(@RequestBody NtiLstVO ntiLstVO, HttpSession session) {
        try {
            // 세션에서 사용자 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.unauthorized("/api/nti/register");
                return ResponseEntity.status(401).body(errorResponse);
            }
            
            String empId = empInfo.getEmpId();
            
            // 필수 필드 검증
            if (ntiLstVO.getTtl() == null || ntiLstVO.getTtl().trim().isEmpty()) {
                ErrorResponseDto errorResponse = ErrorResponseDto.badRequest("제목은 필수 입력 항목입니다.", "/api/nti/register");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            if (ntiLstVO.getCntn() == null || ntiLstVO.getCntn().trim().isEmpty()) {
                ErrorResponseDto errorResponse = ErrorResponseDto.badRequest("내용은 필수 입력 항목입니다.", "/api/nti/register");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 등록자 정보 설정
            ntiLstVO.setRegEmpId(empId);
            
            int result = ntiLstService.insertNtiLst(ntiLstVO);
            
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "공지사항이 성공적으로 등록되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/register");
                errorResponse.setErrorMessage("공지사항 등록에 실패했습니다.");
                return ResponseEntity.internalServerError().body(errorResponse);
            }
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/register");
            errorResponse.setErrorMessage("공지사항 등록 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 공지사항 수정
     * @param seq 공지사항 일련번호
     * @param ntiLstVO 공지사항 정보
     * @param session 세션 정보
     * @return 수정 결과
     */
    @PutMapping("/{seq}")
    public ResponseEntity<?> updateNtiLst(@PathVariable Long seq, @RequestBody NtiLstVO ntiLstVO, HttpSession session) {
        try {
            // 세션에서 사용자 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.unauthorized("/api/nti/" + seq);
                return ResponseEntity.status(401).body(errorResponse);
            }
            
            String empId = empInfo.getEmpId();
            
            // 기존 공지사항 확인
            NtiLstVO existingNotice = ntiLstService.getNtiLst(seq);
            if (existingNotice == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.notFound("해당 공지사항을 찾을 수 없습니다.", "/api/nti/" + seq);
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            // 필수 필드 검증
            if (ntiLstVO.getTtl() == null || ntiLstVO.getTtl().trim().isEmpty()) {
                ErrorResponseDto errorResponse = ErrorResponseDto.badRequest("제목은 필수 입력 항목입니다.", "/api/nti/" + seq);
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            if (ntiLstVO.getCntn() == null || ntiLstVO.getCntn().trim().isEmpty()) {
                ErrorResponseDto errorResponse = ErrorResponseDto.badRequest("내용은 필수 입력 항목입니다.", "/api/nti/" + seq);
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 수정자 정보 설정
            ntiLstVO.setSeq(seq);
            ntiLstVO.setUpdEmpId(empId);
            
            int result = ntiLstService.updateNtiLst(ntiLstVO);
            
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "공지사항이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/" + seq);
                errorResponse.setErrorMessage("공지사항 수정에 실패했습니다.");
                return ResponseEntity.internalServerError().body(errorResponse);
            }
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/" + seq);
            errorResponse.setErrorMessage("공지사항 수정 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 공지사항 삭제
     * @param seq 공지사항 일련번호
     * @param session 세션 정보
     * @return 삭제 결과
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<?> deleteNtiLst(@PathVariable Long seq, HttpSession session) {
        try {
            // 세션에서 사용자 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.unauthorized("/api/nti/" + seq);
                return ResponseEntity.status(401).body(errorResponse);
            }
            
            String empId = empInfo.getEmpId();
            
            // 기존 공지사항 확인
            NtiLstVO existingNotice = ntiLstService.getNtiLst(seq);
            if (existingNotice == null) {
                ErrorResponseDto errorResponse = ErrorResponseDto.notFound("해당 공지사항을 찾을 수 없습니다.", "/api/nti/" + seq);
                return ResponseEntity.status(404).body(errorResponse);
            }
            
            int result = ntiLstService.deleteNtiLst(seq);
            
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "공지사항이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/" + seq);
                errorResponse.setErrorMessage("공지사항 삭제에 실패했습니다.");
                return ResponseEntity.internalServerError().body(errorResponse);
            }
            
        } catch (Exception e) {
            ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError("/api/nti/" + seq);
            errorResponse.setErrorMessage("공지사항 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 