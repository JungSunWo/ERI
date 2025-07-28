package com.ERI.demo.mappers;

import com.ERI.demo.vo.ImgSelLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgSelLstMapper {
    
    /**
     * 사용자가 선택한 이미지 목록 조회
     */
    List<ImgSelLstVO> selectSelectedImageList(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 선택/해제 토글
     */
    int toggleImageSelection(@Param("imgBrdSeq") Long imgBrdSeq, @Param("imgFileSeq") Long imgFileSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 모든 선택 해제
     */
    int clearAllSelections(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 선택 통계 조회
     */
    List<ImgSelLstVO> selectImageSelectionStats(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 선택된 이미지 개수 조회
     */
    int countSelectedImages(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
} 