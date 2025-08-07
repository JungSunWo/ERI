package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.NoticeService;
import com.ibkcloud.eoc.controller.vo.NtiLstInVo;
import com.ibkcloud.eoc.controller.vo.NtiLstOutVo;
import com.ibkcloud.eoc.controller.vo.NtiLstSearchInVo;
import com.ibkcloud.eoc.controller.vo.NtiLstSearchOutVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<NtiLstSearchOutVo> inqNoticeList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status) {
        
        try {
            NtiLstSearchInVo searchVo = new NtiLstSearchInVo();
            searchVo.setTitle(title);
            searchVo.setStatus(status);
            searchVo.setPageNo(pageNo);
            searchVo.setPageSize(pageSize);
            
            List<NtiLstOutVo> notices = noticeService.inqNoticeList(searchVo);
            
            NtiLstSearchOutVo response = new NtiLstSearchOutVo();
            response.setSuccess(true);
            response.setData(notices);
            response.setCount(notices.size());
            response.setPageNo(pageNo);
            response.setPageSize(pageSize);
            response.setTitle(title);
            response.setStatus(status);
            response.setMessage("공지사항 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("공지사항 목록 조회 실패", e);
            throw new BizException("N001", "공지사항 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공지사항 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<NtiLstOutVo> inqNotice(@PathVariable Long seq) {
        try {
            NtiLstOutVo notice = noticeService.inqNoticeBySeq(seq);
            
            if (notice == null) {
                throw new BizException("N002", "공지사항을 찾을 수 없습니다.");
            }
            
            notice.setSuccess(true);
            notice.setMessage("공지사항을 조회했습니다.");
            
            return ResponseEntity.ok(notice);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("공지사항 조회 실패", e);
            throw new BizException("N003", "공지사항 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 세션에서 사용자 정보를 가져오는 헬퍼 메서드
     */
    private String getCurrentUserId(HttpServletRequest request) {
        String empId = (String) request.getAttribute("EMP_ID");
        if (empId == null) {
            throw new BizException("N004", "로그인이 필요합니다.");
        }
        return empId;
    }

    /**
     * 공지사항 등록
     */
    @PostMapping
    public ResponseEntity<NtiLstOutVo> rgsnNotice(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "status", defaultValue = "STS001") String status,
            @RequestParam(value = "empId", required = false) String empId,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(request);
            
            NtiLstInVo notice = new NtiLstInVo();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setStatus(status);
            notice.setRegEmpId(currentEmpId);
            
            NtiLstOutVo createdNotice = noticeService.rgsnNotice(notice, files, currentEmpId);
            
            createdNotice.setSuccess(true);
            createdNotice.setMessage("공지사항이 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(createdNotice);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("공지사항 등록 실패", e);
            throw new BizException("N005", "공지사항 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공지사항 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<NtiLstOutVo> mdfcNotice(
            @PathVariable Long seq,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "status", defaultValue = "STS001") String status,
            @RequestParam(value = "empId", required = false) String empId,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(request);
            
            NtiLstInVo notice = new NtiLstInVo();
            notice.setSeq(seq);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setStatus(status);
            notice.setUpdEmpId(currentEmpId);
            
            NtiLstOutVo updatedNotice = noticeService.mdfcNotice(notice, files, currentEmpId);
            
            if (updatedNotice == null) {
                throw new BizException("N002", "공지사항을 찾을 수 없습니다.");
            }
            
            updatedNotice.setSuccess(true);
            updatedNotice.setMessage("공지사항이 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(updatedNotice);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("공지사항 수정 실패", e);
            throw new BizException("N006", "공지사항 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공지사항 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<NtiLstOutVo> delNotice(
            @PathVariable Long seq,
            @RequestParam(value = "empId", required = false) String empId,
            HttpServletRequest request) {
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(request);
            
            NtiLstOutVo result = noticeService.delNotice(seq, currentEmpId);
            
            result.setSuccess(true);
            result.setMessage("공지사항이 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("공지사항 삭제 실패", e);
            throw new BizException("N007", "공지사항 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공지사항 상태 변경
     */
    @PatchMapping("/{seq}/status")
    public ResponseEntity<NtiLstOutVo> mdfcNoticeStatus(
            @PathVariable Long seq,
            @RequestParam("status") String status,
            @RequestParam(value = "empId", required = false) String empId,
            HttpServletRequest request) {
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(request);
            
            NtiLstOutVo result = noticeService.mdfcNoticeStatus(seq, status, currentEmpId);
            
            result.setSuccess(true);
            result.setMessage("공지사항 상태가 변경되었습니다.");
            
            return ResponseEntity.ok(result);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("공지사항 상태 변경 실패", e);
            throw new BizException("N008", "공지사항 상태 변경에 실패했습니다: " + e.getMessage());
        }
    }
} 