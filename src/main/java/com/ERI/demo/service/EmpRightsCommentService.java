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
        if (comment.getRgstEmpId() != null) {
            comment.setRegEmpId(comment.getRgstEmpId());
        }
        if (comment.getUpdtEmpId() != null) {
            comment.setUpdEmpId(comment.getUpdtEmpId());
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
        if (comment.getUpdtEmpId() != null) {
            comment.setUpdEmpId(comment.getUpdtEmpId());
        }

        // 댓글 수정
        empRightsCommentMapper.updateComment(comment);
        
        // 수정된 댓글 반환
        return empRightsCommentMapper.selectCommentBySeq(comment.getSeq());
    }

    /**
     * 댓글 삭제 (논리 삭제)
     */
    public boolean deleteComment(Long seq, String empId) {
        // 기존 댓글 조회
        EmpRightsCommentVO existingComment = empRightsCommentMapper.selectCommentBySeq(seq);
        if (existingComment == null) {
            return false;
        }

        // 댓글 삭제 (세션에서 가져온 직원번호 사용)
        empRightsCommentMapper.deleteComment(seq, empId);
        return true;
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
} 