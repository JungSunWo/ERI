package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.EmpRightsBoardMapper;
import com.ERI.demo.vo.EmpRightsBoardVO;
import com.ERI.demo.vo.employee.EmpLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원권익게시판 서비스
 */
@Service
@Transactional
public class EmpRightsBoardService {

    @Autowired
    private EmpRightsBoardMapper empRightsBoardMapper;
    
    @Autowired
    private EmpLstService empLstService;

    /**
     * 게시글 목록 조회 (페이징)
     */
    public PageResponseDto<EmpRightsBoardVO> getBoardList(PageRequestDto pageRequest, EmpRightsBoardVO searchCondition) {
        // 검색 조건 설정
        searchCondition.setOffset((pageRequest.getPage() - 1) * pageRequest.getSize());
        searchCondition.setSize(pageRequest.getSize());
        searchCondition.setSortBy(pageRequest.getSortBy());
        searchCondition.setSortDirection(pageRequest.getSortDirection());

        // 게시글 목록 조회
        List<EmpRightsBoardVO> boards = empRightsBoardMapper.selectBoardList(searchCondition);
        
        // 직원 정보 조회하여 설정
        for (EmpRightsBoardVO board : boards) {
            if (board.getRegEmpId() != null) {
                EmpLstVO regEmp = empLstService.getEmployeeByEmpId(board.getRegEmpId());
                if (regEmp != null) {
                    board.setRegEmpNm(regEmp.getEmpNm());
                }
            }
            if (board.getUpdEmpId() != null) {
                EmpLstVO updEmp = empLstService.getEmployeeByEmpId(board.getUpdEmpId());
                if (updEmp != null) {
                    board.setUpdEmpNm(updEmp.getEmpNm());
                }
            }
        }
        
        // 전체 개수 조회
        int totalCount = empRightsBoardMapper.selectBoardCount(searchCondition);
        
        // 페이지 정보 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getSize());
        
        return new PageResponseDto<EmpRightsBoardVO>(boards, pageRequest.getPage(), pageRequest.getSize(), totalCount, totalPages);
    }

    /**
     * 게시글 상세 조회
     */
    public EmpRightsBoardVO getBoardBySeq(Long seq) {
        EmpRightsBoardVO board = empRightsBoardMapper.selectBoardBySeq(seq);
        
        if (board != null) {
            // 직원 정보 조회하여 설정
            if (board.getRegEmpId() != null) {
                EmpLstVO regEmp = empLstService.getEmployeeByEmpId(board.getRegEmpId());
                if (regEmp != null) {
                    board.setRegEmpNm(regEmp.getEmpNm());
                }
            }
            if (board.getUpdEmpId() != null) {
                EmpLstVO updEmp = empLstService.getEmployeeByEmpId(board.getUpdEmpId());
                if (updEmp != null) {
                    board.setUpdEmpNm(updEmp.getEmpNm());
                }
            }
        }
        
        return board;
    }

    /**
     * 게시글 등록
     */
    public EmpRightsBoardVO createBoard(EmpRightsBoardVO board) {
        // 기본값 설정
        if (board.getStsCd() == null) {
            board.setStsCd("ACTIVE");
        }
        if (board.getFileAttachYn() == null) {
            board.setFileAttachYn("N");
        }
        if (board.getViewCnt() == null) {
            board.setViewCnt(0);
        }
        if (board.getLikeCnt() == null) {
            board.setLikeCnt(0);
        }
        if (board.getDislikeCnt() == null) {
            board.setDislikeCnt(0);
        }
        if (board.getSecretYn() == null) {
            board.setSecretYn("N");
        }
        if (board.getNoticeYn() == null) {
            board.setNoticeYn("N");
        }
        if (board.getDelYn() == null) {
            board.setDelYn("N");
        }

        // 세션에서 가져온 직원번호 설정
        if (board.getRegEmpId() != null) {
            board.setRegEmpId(board.getRegEmpId());
        }
        if (board.getUpdEmpId() != null) {
            board.setUpdEmpId(board.getUpdEmpId());
        }

        // 게시글 등록
        empRightsBoardMapper.insertBoard(board);
        
        // 등록된 게시글 반환
        return getBoardBySeq(board.getSeq());
    }

    /**
     * 게시글 수정
     */
    public EmpRightsBoardVO updateBoard(EmpRightsBoardVO board) {
        // 기존 게시글 조회
        EmpRightsBoardVO existingBoard = getBoardBySeq(board.getSeq());
        if (existingBoard == null) {
            return null;
        }

        // 세션에서 가져온 직원번호 설정
        if (board.getUpdEmpId() != null) {
            board.setUpdEmpId(board.getUpdEmpId());
        }

        // 게시글 수정
        empRightsBoardMapper.updateBoard(board);
        
        // 수정된 게시글 반환
        return getBoardBySeq(board.getSeq());
    }

    /**
     * 게시글 삭제 (논리 삭제)
     */
    public boolean deleteBoard(Long seq, String empId) {
        // 기존 게시글 조회
        EmpRightsBoardVO existingBoard = getBoardBySeq(seq);
        if (existingBoard == null) {
            return false;
        }

        // 게시글 삭제 (세션에서 가져온 직원번호 사용)
        empRightsBoardMapper.deleteBoard(seq, empId);
        return true;
    }

    /**
     * 조회수 증가
     */
    public void incrementViewCount(Long seq) {
        empRightsBoardMapper.incrementViewCount(seq);
    }

    /**
     * 좋아요 수 증가
     */
    public void incrementLikeCount(Long seq) {
        empRightsBoardMapper.incrementLikeCount(seq);
    }

    /**
     * 좋아요 수 감소
     */
    public void decrementLikeCount(Long seq) {
        empRightsBoardMapper.decrementLikeCount(seq);
    }

    /**
     * 싫어요 수 증가
     */
    public void incrementDislikeCount(Long seq) {
        empRightsBoardMapper.incrementDislikeCount(seq);
    }

    /**
     * 싫어요 수 감소
     */
    public void decrementDislikeCount(Long seq) {
        empRightsBoardMapper.decrementDislikeCount(seq);
    }

    /**
     * 공지글 목록 조회
     */
    public List<EmpRightsBoardVO> getNoticeList() {
        return empRightsBoardMapper.selectNoticeList();
    }

    /**
     * 내가 작성한 게시글 목록 조회
     */
    public PageResponseDto<EmpRightsBoardVO> getMyBoardList(String empId, PageRequestDto pageRequest) {
        // 검색 조건 설정
        EmpRightsBoardVO searchCondition = new EmpRightsBoardVO();
        searchCondition.setRegEmpId(empId);
        searchCondition.setOffset((pageRequest.getPage() - 1) * pageRequest.getSize());
        searchCondition.setSize(pageRequest.getSize());

        // 게시글 목록 조회
        List<EmpRightsBoardVO> boards = empRightsBoardMapper.selectMyBoardList(searchCondition);
        
        // 전체 개수 조회
        int totalCount = empRightsBoardMapper.selectMyBoardCount(searchCondition);
        
        // 페이지 정보 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getSize());
        
        return new PageResponseDto<EmpRightsBoardVO>(boards, pageRequest.getPage(), pageRequest.getSize(), totalCount, totalPages);
    }
} 