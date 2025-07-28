package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.ConsultationAnswerMapper;
import com.ERI.demo.mappers.ConsultationBoardMapper;
import com.ERI.demo.mappers.ConsultationFileAttachMapper;
import com.ERI.demo.vo.ConsultationAnswerVO;
import com.ERI.demo.vo.ConsultationBoardVO;
import com.ERI.demo.vo.ConsultationFileAttachVO;
import com.ERI.demo.vo.AnonymousUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 상담 게시판 Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultationBoardService {

    private final ConsultationBoardMapper consultationBoardMapper;
    private final ConsultationAnswerMapper consultationAnswerMapper;
    private final ConsultationFileAttachMapper consultationFileAttachMapper;
    private final FileAttachService fileAttachService;
    private final AnonymousUserService anonymousUserService;

    /**
     * 상담 게시글 목록 조회 (페이징)
     */
    public PageResponseDto<ConsultationBoardVO> getConsultationBoardList(PageRequestDto pageRequest, ConsultationBoardVO searchCondition) {
        int offset = (pageRequest.getPage() - 1) * pageRequest.getSize();
        
        List<ConsultationBoardVO> content = consultationBoardMapper.selectConsultationBoardList(
            offset, pageRequest.getSize(), searchCondition, pageRequest.getSortBy(), pageRequest.getSortDirection()
        );
        
        int totalElements = consultationBoardMapper.selectConsultationBoardCount(searchCondition);
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        
        return PageResponseDto.<ConsultationBoardVO>builder()
            .content(content)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(pageRequest.getPage())
            .size(pageRequest.getSize())
            .hasNext(pageRequest.getPage() < totalPages)
            .hasPrevious(pageRequest.getPage() > 1)
            .startPage(Math.max(1, pageRequest.getPage() - 2))
            .endPage(Math.min(totalPages, pageRequest.getPage() + 2))
            .build();
    }

    /**
     * 상담 게시글 상세 조회
     */
    @Transactional
    public ConsultationBoardVO getConsultationBoardBySeq(Long seq) {
        // 조회수 증가
        consultationBoardMapper.incrementViewCount(seq);
        
        // 상담 게시글 조회
        ConsultationBoardVO consultationBoard = consultationBoardMapper.selectConsultationBoardBySeq(seq);
        if (consultationBoard == null) {
            return null;
        }
        
        // 답변 조회
        ConsultationAnswerVO answer = consultationAnswerMapper.selectConsultationAnswerByBoardSeq(seq);
        consultationBoard.setAnswer(answer);
        
        // 첨부파일 조회 (게시글)
        List<ConsultationFileAttachVO> boardFiles = consultationFileAttachMapper.selectConsultationFileAttachByBoardSeq(seq);
        consultationBoard.setFiles(boardFiles);
        
        // 첨부파일 조회 (답변)
        if (answer != null) {
            List<ConsultationFileAttachVO> answerFiles = consultationFileAttachMapper.selectConsultationFileAttachByAnswerSeq(answer.getSeq());
            answer.setFiles(answerFiles);
        }
        
        return consultationBoard;
    }

    /**
     * 상담 게시글 등록
     */
    @Transactional
    public ConsultationBoardVO createConsultationBoard(ConsultationBoardVO consultationBoard, MultipartFile[] files, String empId) throws Exception {
        // 유효성 검사
        validateConsultationBoard(consultationBoard);
        
        // 익명 상담인 경우 익명 사용자 처리
        if ("Y".equals(consultationBoard.getAnonymousYn())) {
            handleAnonymousUser(consultationBoard);
        }
        
        // 기본값 설정
        consultationBoard.setStsCd("STS001"); // 대기
        consultationBoard.setFileAttachYn(files != null && files.length > 0 ? "Y" : "N");
        consultationBoard.setRegEmpId(empId);
        
        // 상담 게시글 등록
        consultationBoardMapper.insertConsultationBoard(consultationBoard);
        
        // 첨부파일 처리
        if (files != null && files.length > 0) {
            try {
                fileAttachService.uploadMultipleFilesForConsultation(files, consultationBoard.getSeq(), null, empId);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        }
        
        return consultationBoard;
    }

    /**
     * 상담 게시글 수정
     */
    @Transactional
    public ConsultationBoardVO updateConsultationBoard(ConsultationBoardVO consultationBoard, MultipartFile[] files, String empId) throws Exception {
        // 기존 상담 게시글 조회
        ConsultationBoardVO existingBoard = consultationBoardMapper.selectConsultationBoardBySeq(consultationBoard.getSeq());
        if (existingBoard == null) {
            return null;
        }
        
        // 유효성 검사
        validateConsultationBoard(consultationBoard);
        
        // 익명 상담인 경우 익명 사용자 처리
        if ("Y".equals(consultationBoard.getAnonymousYn())) {
            handleAnonymousUser(consultationBoard);
        }
        
        // 수정자 정보 설정
        consultationBoard.setUpdEmpId(empId);
        consultationBoard.setFileAttachYn(files != null && files.length > 0 ? "Y" : existingBoard.getFileAttachYn());
        
        // 상담 게시글 수정
        consultationBoardMapper.updateConsultationBoard(consultationBoard);
        
        // 첨부파일 처리
        if (files != null && files.length > 0) {
            try {
                fileAttachService.uploadMultipleFilesForConsultation(files, consultationBoard.getSeq(), null, empId);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        }
        
        return consultationBoard;
    }

    /**
     * 상담 게시글 삭제
     */
    @Transactional
    public boolean deleteConsultationBoard(Long seq, String empId) {
        // 기존 상담 게시글 조회
        ConsultationBoardVO existingBoard = consultationBoardMapper.selectConsultationBoardBySeq(seq);
        if (existingBoard == null) {
            return false;
        }
        
        // 첨부파일 삭제
        consultationFileAttachMapper.deleteConsultationFileAttachByBoardSeq(seq, empId);
        
        // 답변 삭제
        consultationAnswerMapper.deleteConsultationAnswerByBoardSeq(seq, empId);
        
        // 상담 게시글 삭제
        return consultationBoardMapper.deleteConsultationBoard(seq, empId) > 0;
    }

    /**
     * 상담 답변 등록/수정
     */
    @Transactional
    public ConsultationAnswerVO saveConsultationAnswer(ConsultationAnswerVO consultationAnswer, MultipartFile[] files, String empId) throws Exception {
        // 유효성 검사
        if (consultationAnswer.getCntn() == null || consultationAnswer.getCntn().trim().isEmpty()) {
            throw new IllegalArgumentException("답변 내용은 필수입니다.");
        }
        
        // 기존 답변 조회
        ConsultationAnswerVO existingAnswer = consultationAnswerMapper.selectConsultationAnswerByBoardSeq(consultationAnswer.getBoardSeq());
        
        if (existingAnswer == null) {
            // 답변 등록
            consultationAnswer.setStsCd("STS002"); // 등록완료
            consultationAnswer.setRegEmpId(empId);
            consultationAnswerMapper.insertConsultationAnswer(consultationAnswer);
            
            // 상담 게시글 답변 상태 업데이트
            consultationBoardMapper.updateAnswerStatus(consultationAnswer.getBoardSeq(), "Y", empId);
        } else {
            // 답변 수정
            consultationAnswer.setSeq(existingAnswer.getSeq());
            consultationAnswer.setStsCd("STS002"); // 등록완료
            consultationAnswer.setUpdEmpId(empId);
            consultationAnswerMapper.updateConsultationAnswer(consultationAnswer);
        }
        
        // 첨부파일 처리
        if (files != null && files.length > 0) {
            try {
                fileAttachService.uploadMultipleFilesForConsultation(files, null, consultationAnswer.getSeq(), empId);
            } catch (Exception e) {
                log.error("첨부파일 업로드 실패", e);
                throw new Exception("첨부파일 업로드에 실패했습니다: " + e.getMessage());
            }
        }
        
        return consultationAnswer;
    }

    /**
     * 내가 작성한 상담 게시글 목록 조회
     */
    public PageResponseDto<ConsultationBoardVO> getMyConsultationBoardList(int page, int size, String empId) {
        int offset = (page - 1) * size;
        
        List<ConsultationBoardVO> content = consultationBoardMapper.selectMyConsultationBoardList(empId, offset, size);
        int totalElements = consultationBoardMapper.selectMyConsultationBoardCount(empId);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return PageResponseDto.<ConsultationBoardVO>builder()
            .content(content)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(page)
            .size(size)
            .hasNext(page < totalPages)
            .hasPrevious(page > 1)
            .startPage(Math.max(1, page - 2))
            .endPage(Math.min(totalPages, page + 2))
            .build();
    }

    /**
     * 익명 사용자 처리
     */
    private void handleAnonymousUser(ConsultationBoardVO consultationBoard) {
        String nickname = consultationBoard.getNickname();
        
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("익명 상담의 경우 닉네임은 필수입니다.");
        }
        
        // 기존 익명 사용자 조회
        AnonymousUserVO existingUser = anonymousUserService.getAnonymousUserByNickname(nickname);
        
        if (existingUser != null) {
            // 기존 익명 사용자 사용
            consultationBoard.setAnonymousId(existingUser.getAnonymousId());
            consultationBoard.setNickname(existingUser.getNickname());
        } else {
            // 새로운 익명 사용자 생성
            AnonymousUserVO newUser = new AnonymousUserVO();
            newUser.setNickname(nickname);
            newUser.setUseYn("Y");
            
            AnonymousUserVO createdUser = anonymousUserService.createAnonymousUser(newUser);
            consultationBoard.setAnonymousId(createdUser.getAnonymousId());
            consultationBoard.setNickname(createdUser.getNickname());
        }
    }

    /**
     * 상담 게시글 유효성 검사
     */
    private void validateConsultationBoard(ConsultationBoardVO consultationBoard) {
        if (consultationBoard.getTtl() == null || consultationBoard.getTtl().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        
        if (consultationBoard.getTtl().length() > 255) {
            throw new IllegalArgumentException("제목은 255자를 초과할 수 없습니다.");
        }
        
        if (consultationBoard.getCntn() == null || consultationBoard.getCntn().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        
        if (consultationBoard.getCategoryCd() == null || consultationBoard.getCategoryCd().trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }
        
        if (consultationBoard.getPriorityCd() == null || consultationBoard.getPriorityCd().trim().isEmpty()) {
            throw new IllegalArgumentException("우선순위는 필수입니다.");
        }
        
        // 익명 상담인 경우 닉네임 필수
        if ("Y".equals(consultationBoard.getAnonymousYn())) {
            if (consultationBoard.getNickname() == null || consultationBoard.getNickname().trim().isEmpty()) {
                throw new IllegalArgumentException("익명 상담의 경우 닉네임은 필수입니다.");
            }
        }
    }
} 