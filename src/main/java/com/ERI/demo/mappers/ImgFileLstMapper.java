package com.ERI.demo.mappers;

import com.ERI.demo.vo.ImgFileLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgFileLstMapper {
    
    /**
     * 이미지 파일 목록 조회 (게시판별)
     */
    List<ImgFileLstVO> selectImgFileListByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 파일 목록 조회 (사용자 선택 여부 포함)
     */
    List<ImgFileLstVO> selectImgFileListWithSelection(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 파일 등록
     */
    int insertImgFile(ImgFileLstVO imgFileLstVO);
    
    /**
     * 이미지 파일 삭제 (논리 삭제)
     */
    int deleteImgFile(@Param("imgFileSeq") Long imgFileSeq, @Param("updEmpId") String updEmpId);
    
    /**
     * 이미지 파일 조회 (시퀀스로)
     */
    ImgFileLstVO selectImgFileBySeq(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 이미지 설명 수정
     */
    int updateImageText(@Param("imgFileSeq") Long imgFileSeq, 
                       @Param("imgText") String imgText, 
                       @Param("eriEmpId") String eriEmpId);
} 