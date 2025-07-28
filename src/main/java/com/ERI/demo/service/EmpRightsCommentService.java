package com.ERI.demo.service;

import com.ERI.demo.mappers.EmpRightsCommentMapper;
import com.ERI.demo.vo.EmpRightsCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원권익게시판 댓글 서비스
 */
@Service
@Transactional
public class EmpRightsCommentService {

    @Autowired
    private EmpRightsCommentMapper empRightsCommentMapper;

    /**
     * 댓글 목록 조회
     */
    public List<EmpRightsCommentVO> getCommentList(Long boardSeq) {
        return empRightsCommentMapper.selectCommentList(boardSeq);
    }

    /**
     * 댓글 상세 조회
     */
    public EmpRightsCommentVO getCommentBySeq(Long seq) {
        return empRightsCommentMapper.selectCommentBySeq(seq);
    }

    /**
     * 댓글 등록
     */
    public EmpRightsCommentVO createComment(EmpRightsCommentVO comment) {
        // 기본값 설정
        if (comment.getDepth() == null) {
            comment.setDepth(0);
        }
        if (comment.getLikeCnt() == null) {
            comment.setLikeCnt(0);
        }
        if (comment.getDislikeCnt() == null) {
            comment.setDislikeCnt(0);
        }
        if (comment.getSecretYn() == null) {
            comment.setSecretYn("N");
        }
        if (comment.getDelYn() == null) {
            comment.setDelYn("N");
        }

        // 세션에서 가져온 직원번호 설정
        if (comment.getRegEmpId() != null) {
            comment.setRegEmpId(comment.getRegEmpId());
        }
        if (comment.getUpdEmpId() != null) {
            comment.setUpdEmpId(comment.getUpdEmpId());
        }

        // 부모 댓글이 있는 경우 깊이 계산
        if (comment.getParentSeq() != null) {
            EmpRightsCommentVO parentComment = empRightsCommentMapper.selectCommentBySeq(comment.getParentSeq());
            if (parentComment != null) {
                comment.setDepth(parentComment.getDepth() + 1);
            }
        }

        // 댓글 등록
        empRightsCommentMapper.insertComment(comment);
        
        // 등록된 댓글 반환
        return empRightsCommentMapper.selectCommentBySeq(comment.getSeq());
    }

    /**
     * 댓글 수정
     */
    public EmpRightsCommentVO updateComment(EmpRightsCommentVO comment) {
        // 기존 댓글 조회
        EmpRightsCommentVO existingComment = empRightsCommentMapper.selectCommentBySeq(comment.getSeq());
        if (existingComment == null) {
            return null;
        }

        // 세션에서 가져온 직원번호 설정
        if (comment.getUpdEmpId() != null) {
            comment.setUpdEmpId(comment.getUpdEmpId());
        }

        // 댓글 수정
        empRightsCommentMapper.updateComment(comment);
        
        // 수정된 댓글 반환
        return empRightsCommentMapper.selectCommentBySeq(comment.getSeq());
    }

    /**
     * 댓글 삭제 (논리 삭제) - 하위 답글도 함께 삭제
     */
    public boolean deleteComment(Long seq, String empId) {
        // 기존 댓글 조회
        EmpRightsCommentVO existingComment = empRightsCommentMapper.selectCommentBySeq(seq);
        if (existingComment == null) {
            return false;
        }

        // 하위 답글들 삭제 (재귀적으로 모든 하위 답글 삭제)
        deleteChildCommentsRecursively(seq, empId);

        // 댓글 삭제 (세션에서 가져온 직원번호 사용)
        empRightsCommentMapper.deleteComment(seq, empId);
        return true;
    }

    /**
     * 하위 답글들을 재귀적으로 삭제
     */
    private void deleteChildCommentsRecursively(Long parentSeq, String empId) {
        // 직접적인 하위 답글들 조회
        List<EmpRightsCommentVO> childComments = empRightsCommentMapper.selectChildComments(parentSeq);
        
        for (EmpRightsCommentVO childComment : childComments) {
            // 각 하위 답글의 하위 답글들도 재귀적으로 삭제
            deleteChildCommentsRecursively(childComment.getSeq(), empId);
            
            // 하위 답글 삭제
            empRightsCommentMapper.deleteComment(childComment.getSeq(), empId);
        }
    }

    /**
     * 댓글 좋아요 수 증가
     */
    public void incrementCommentLikeCount(Long seq) {
        empRightsCommentMapper.incrementCommentLikeCount(seq);
    }

    /**
     * 댓글 좋아요 수 감소
     */
    public void decrementCommentLikeCount(Long seq) {
        empRightsCommentMapper.decrementCommentLikeCount(seq);
    }

    /**
     * 댓글 싫어요 수 증가
     */
    public void incrementCommentDislikeCount(Long seq) {
        empRightsCommentMapper.incrementCommentDislikeCount(seq);
    }

    /**
     * 댓글 싫어요 수 감소
     */
    public void decrementCommentDislikeCount(Long seq) {
        empRightsCommentMapper.decrementCommentDislikeCount(seq);
    }

    /**
     * 하위 답글이 있는지 확인
     */
    public boolean hasChildComments(Long parentSeq) {
        List<EmpRightsCommentVO> childComments = empRightsCommentMapper.selectChildComments(parentSeq);
        return childComments != null && !childComments.isEmpty();
    }
} 