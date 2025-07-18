package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpRightsLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 직원권익게시판 좋아요/싫어요 Mapper
 */
@Mapper
public interface EmpRightsLikeMapper {

    /**
     * 좋아요 조회
     */
    EmpRightsLikeVO selectLike(@Param("boardSeq") Long boardSeq, 
                               @Param("commentSeq") Long commentSeq, 
                               @Param("empId") String empId);

    /**
     * 좋아요 등록
     */
    int insertLike(EmpRightsLikeVO like);

    /**
     * 좋아요 수정
     */
    int updateLike(EmpRightsLikeVO like);

    /**
     * 좋아요 삭제
     */
    int deleteLike(@Param("seq") Long seq, @Param("empId") String empId);
} 