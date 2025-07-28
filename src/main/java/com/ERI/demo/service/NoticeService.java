package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.NtiLstMapper;
import com.ERI.demo.vo.NtiLstVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NtiLstMapper ntiLstMapper;
    private final FileAttachService fileAttachService;

    /**
     * 공지사항 목록 조회 (페이징) - 기존 메서드
     */
    public PageResponseDto<NtiLstVO> getNoticeList(PageRequestDto pageRequest) {
        // 전체 개수 조회
        int totalCount = ntiLstMapper.selectNoticeCount(pageRequest.getSearchKeyword(), pageRequest.getStsCd());
        
        // 페이징 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getSize());
        int offset = (pageRequest.getPage() - 1) * pageRequest.getSize();
        
        // 목록 조회
        List<NtiLstVO> notices = ntiLstMapper.selectNoticeList(pageRequest.getSearchKeyword(), pageRequest.getStsCd(), offset, pageRequest.getSize());
        
        // 첨부파일 정보 추가
        for (NtiLstVO notice : notices) {
            if ("Y".equals(notice.getFileAttachYn())) {
                List<?> files = fileAttachService.getFileAttachList("NTI", String.valueOf(notice.getSeq()));
                notice.setFileCount(files.size());
            }
        }
        
        return new PageResponseDto<NtiLstVO>(notices, totalCount, pageRequest.getPage(), pageRequest.getSize());
    }

    /**
     * 공지사항 목록 조회 (새로운 메서드) - 컨트롤러용
     */
    public Map<String, Object> getNoticeList(int page, int size, String title, String status) {
        // 전체 개수 조회
        int totalCount = ntiLstMapper.selectNoticeCount(title, status);
        
        // 페이징 계산
        int totalPages = (int) Math.ceil((double) totalCount / size);
        int offset = (page - 1) * size;
        
        // 목록 조회
        List<NtiLstVO> notices = ntiLstMapper.selectNoticeList(title, status, offset, size);
        
        // 첨부파일 정보 추가
        for (NtiLstVO notice : notices) {
            if ("Y".equals(notice.getFileAttachYn())) {
                List<?> files = fileAttachService.getFileAttachList("NTI", String.valueOf(notice.getSeq()));
                notice.setFileCount(files.size());
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("content", notices);
        result.put("totalElements", totalCount);
        result.put("totalPages", totalPages);
        result.put("currentPage", page);
        result.put("size", size);
        
        return result;
    }

    /**
     * 공지사항 상세 조회
     */
    public NtiLstVO getNoticeBySeq(Long seq) {
        NtiLstVO notice = ntiLstMapper.selectNoticeBySeq(seq);
        
        if (notice != null && "Y".equals(notice.getFileAttachYn())) {
            List<?> files = fileAttachService.getFileAttachList("NTI", String.valueOf(notice.getSeq()));
            notice.setFileCount(files.size());
        }
        
        return notice;
    }

    /**
     * 공지사항 등록
     */
    @Transactional
    public NtiLstVO createNotice(NtiLstVO notice, MultipartFile[] files, String empId) throws Exception {
        // 유효성 검사
        validateNotice(notice);
        
        // 공지사항 저장
        ntiLstMapper.insertNotice(notice);
        
        // 첨부파일이 있는 경우 처리
        if (files != null && files.length > 0) {
            try {
                fileAttachService.uploadMultipleFilesForNotice(files, "NTI", String.valueOf(notice.getSeq()), null, empId);
                notice.setFileAttachYn("Y");
                ntiLstMapper.updateNotice(notice);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        }
        
        return notice;
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public NtiLstVO updateNotice(NtiLstVO notice, MultipartFile[] files, String empId) throws Exception {
        // 기존 공지사항 조회
        NtiLstVO existingNotice = ntiLstMapper.selectNoticeBySeq(notice.getSeq());
        if (existingNotice == null) {
            return null;
        }
        
        // 유효성 검사
        validateNotice(notice);
        
        // 공지사항 수정 (파일첨부여부는 별도 처리)
        ntiLstMapper.updateNotice(notice);
        
        // 첨부파일 처리
        if (files != null && files.length > 0) {
            // 새 파일이 업로드된 경우 - 기존 파일 삭제 후 새 파일 업로드
            try {
                // 기존 파일 삭제
                if ("Y".equals(existingNotice.getFileAttachYn())) {
                    fileAttachService.deleteFilesByRef("NTI", String.valueOf(notice.getSeq()), empId);
                }
                
                // 새 파일 업로드
                fileAttachService.uploadMultipleFilesForNotice(files, "NTI", String.valueOf(notice.getSeq()), null, empId);
                notice.setFileAttachYn("Y");
                ntiLstMapper.updateNotice(notice);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        } else {
            // 파일이 업로드되지 않은 경우 - 기존 파일 유지
            // 기존에 파일이 있었으면 파일첨부여부를 'Y'로 유지
            if ("Y".equals(existingNotice.getFileAttachYn())) {
                notice.setFileAttachYn("Y");
                ntiLstMapper.updateNotice(notice);
            }
        }
        
        return notice;
    }

    /**
     * 공지사항 수정 (파일 삭제 옵션 포함)
     */
    @Transactional
    public NtiLstVO updateNoticeWithFileOption(NtiLstVO notice, MultipartFile[] files, boolean deleteExistingFiles, String empId) throws Exception {
        // 기존 공지사항 조회
        NtiLstVO existingNotice = ntiLstMapper.selectNoticeBySeq(notice.getSeq());
        if (existingNotice == null) {
            return null;
        }
        
        // 유효성 검사
        validateNotice(notice);
        
        // 공지사항 수정 (파일첨부여부는 별도 처리)
        ntiLstMapper.updateNotice(notice);
        
        // 기존 파일 삭제 요청이 있는 경우
        if (deleteExistingFiles && "Y".equals(existingNotice.getFileAttachYn())) {
            try {
                fileAttachService.deleteFilesByRef("NTI", String.valueOf(notice.getSeq()), empId);
                notice.setFileAttachYn("N");
                ntiLstMapper.updateNotice(notice);
            } catch (Exception e) {
                log.error("기존 첨부파일 삭제 실패", e);
                throw new Exception("기존 첨부파일 삭제에 실패했습니다: " + e.getMessage());
            }
        }
        
        // 새 파일 업로드
        if (files != null && files.length > 0) {
            try {
                // 기존 파일이 있고 삭제하지 않았다면 기존 파일 삭제 후 새 파일 업로드
                if ("Y".equals(existingNotice.getFileAttachYn()) && !deleteExistingFiles) {
                    fileAttachService.deleteFilesByRef("NTI", String.valueOf(notice.getSeq()), empId);
                }
                
                fileAttachService.uploadMultipleFilesForNotice(files, "NTI", String.valueOf(notice.getSeq()), null, empId);
                notice.setFileAttachYn("Y");
                ntiLstMapper.updateNotice(notice);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        } else if (!deleteExistingFiles && "Y".equals(existingNotice.getFileAttachYn())) {
            // 새 파일이 없고 기존 파일을 삭제하지 않았다면 기존 파일 유지
            notice.setFileAttachYn("Y");
            ntiLstMapper.updateNotice(notice);
        }
        
        return notice;
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public boolean deleteNotice(Long seq, String empId) {
        // 기존 공지사항 조회
        NtiLstVO existingNotice = ntiLstMapper.selectNoticeBySeq(seq);
        if (existingNotice == null) {
            return false;
        }
        
        // 첨부파일이 있는 경우 삭제
        if ("Y".equals(existingNotice.getFileAttachYn())) {
            try {
                fileAttachService.deleteFilesByRef("NTI", String.valueOf(seq), empId);
            } catch (Exception e) {
                log.error("첨부파일 삭제 실패", e);
            }
        }
        
        // 공지사항 논리 삭제
        return ntiLstMapper.deleteNotice(seq, empId) > 0;
    }

    /**
     * 공지사항 상태 변경
     */
    public boolean updateNoticeStatus(Long seq, String status, String empId) {
        NtiLstVO notice = new NtiLstVO();
        notice.setSeq(seq);
        notice.setStsCd(status);
        notice.setUpdtEmpId(empId);
        
        return ntiLstMapper.updateNotice(notice) > 0;
    }

    /**
     * 공지사항 유효성 검사
     */
    private void validateNotice(NtiLstVO notice) {
        if (notice.getTtl() == null || notice.getTtl().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        
        if (notice.getTtl().length() > 255) {
            throw new IllegalArgumentException("제목은 255자를 초과할 수 없습니다.");
        }
        
        if (notice.getCntn() == null || notice.getCntn().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        
        if (notice.getStsCd() == null || notice.getStsCd().trim().isEmpty()) {
            throw new IllegalArgumentException("상태는 필수입니다.");
        }
    }
} 