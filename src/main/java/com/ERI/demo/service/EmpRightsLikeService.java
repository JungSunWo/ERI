package com.ERI.demo.service;

import com.ERI.demo.mappers.EmpRightsLikeMapper;
import com.ERI.demo.vo.EmpRightsLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 직원권익게시판 좋아요/싫어요 서비스
 */
@Service
@Transactional
public class EmpRightsLikeService {

    @Autowired
    private EmpRightsLikeMapper empRightsLikeMapper;

    @Autowired
    private EmpRightsBoardService empRightsBoardService;

    @Autowired
    private EmpRightsCommentService empRightsCommentService;

    /**
     * 좋아요/싫어요 토글 처리
     */
    public boolean toggleLike(EmpRightsLikeVO like) {
        // 세션에서 가져온 직원번호 설정
        if (like.getRgstEmpId() != null) {
            like.setRegEmpId(like.getRgstEmpId());
            like.setUpdEmpId(like.getRgstEmpId());
        }
        
        // 기존 좋아요 조회
        EmpRightsLikeVO existingLike = empRightsLikeMapper.selectLike(like.getBoardSeq(), like.getCommentSeq(), like.getRgstEmpId());
        
        if (existingLike == null) {
            // 새로운 좋아요 등록
            empRightsLikeMapper.insertLike(like);
            
            // 카운트 증가
            updateCount(like.getBoardSeq(), like.getCommentSeq(), like.getLikeType(), true);
        } else {
            if (existingLike.getLikeType().equals(like.getLikeType())) {
                // 같은 타입이면 삭제
                empRightsLikeMapper.deleteLike(existingLike.getSeq(), like.getRgstEmpId());
                
                // 카운트 감소
                updateCount(like.getBoardSeq(), like.getCommentSeq(), like.getLikeType(), false);
            } else {
                // 다른 타입이면 수정
                existingLike.setLikeType(like.getLikeType());
                empRightsLikeMapper.updateLike(existingLike);
                
                // 카운트 조정 (기존 타입 감소, 새 타입 증가)
                updateCount(like.getBoardSeq(), like.getCommentSeq(), existingLike.getLikeType(), false);
                updateCount(like.getBoardSeq(), like.getCommentSeq(), like.getLikeType(), true);
            }
        }
        
        return true;
    }

    /**
     * 좋아요 상태 조회
     */
    public String getLikeStatus(Long boardSeq, Long commentSeq, String empId) {
        EmpRightsLikeVO like = empRightsLikeMapper.selectLike(boardSeq, commentSeq, empId);
        return like != null ? like.getLikeType() : null;
    }

    /**
     * 카운트 업데이트
     */
    private void updateCount(Long boardSeq, Long commentSeq, String likeType, boolean increment) {
        if (boardSeq != null) {
            // 게시글 좋아요/싫어요
            if ("L".equals(likeType)) {
                if (increment) {
                    empRightsBoardService.incrementLikeCount(boardSeq);
                } else {
                    empRightsBoardService.decrementLikeCount(boardSeq);
                }
            } else {
                if (increment) {
                    empRightsBoardService.incrementDislikeCount(boardSeq);
                } else {
                    empRightsBoardService.decrementDislikeCount(boardSeq);
                }
            }
        } else if (commentSeq != null) {
            // 댓글 좋아요/싫어요
            if ("L".equals(likeType)) {
                if (increment) {
                    empRightsCommentService.incrementCommentLikeCount(commentSeq);
                } else {
                    empRightsCommentService.decrementCommentLikeCount(commentSeq);
                }
            } else {
                if (increment) {
                    empRightsCommentService.incrementCommentDislikeCount(commentSeq);
                } else {
                    empRightsCommentService.decrementCommentDislikeCount(commentSeq);
                }
            }
        }
    }
} 