package com.ERI.demo.mapper;

import com.ERI.demo.vo.ImageFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 이미지 파일 Mapper
 * TB_IMG_FILE_LST 테이블과 매핑
 */
@Mapper
public interface ImageFileMapper {
    
    /**
     * 이미지 파일 목록 조회 (페이징/검색)
     * @param imageFile 조회 조건
     * @return 이미지 파일 목록
     */
    List<ImageFileVO> selectImageFileList(ImageFileVO imageFile);
    
    /**
     * 이미지 파일 목록 개수 조회
     * @param imageFile 조회 조건
     * @return 이미지 파일 목록 개수
     */
    int selectImageFileCount(ImageFileVO imageFile);
    
    /**
     * 이미지 파일 상세 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 파일 정보
     */
    ImageFileVO selectImageFileBySeq(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 이미지 게시판별 이미지 파일 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 파일 목록
     */
    List<ImageFileVO> selectImageFileByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 파일 등록
     * @param imageFile 이미지 파일 정보
     * @return 등록된 행 수
     */
    int insertImageFile(ImageFileVO imageFile);
    
    /**
     * 이미지 파일 수정
     * @param imageFile 이미지 파일 정보
     * @return 수정된 행 수
     */
    int updateImageFile(ImageFileVO imageFile);
    
    /**
     * 이미지 파일 삭제 (논리 삭제)
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageFile(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 이미지 게시판별 이미지 파일 삭제 (논리 삭제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageFileByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 순서 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgOrd 이미지 순서
     * @return 업데이트된 행 수
     */
    int updateImageOrder(@Param("imgFileSeq") Long imgFileSeq, @Param("imgOrd") Integer imgOrd);
    
    /**
     * 이미지 텍스트 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgText 이미지 텍스트
     * @return 업데이트된 행 수
     */
    int updateImageText(@Param("imgFileSeq") Long imgFileSeq, @Param("imgText") String imgText);
    
    /**
     * 이미지 파일명으로 조회
     * @param imgFileNm 이미지 파일명
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 파일 정보
     */
    ImageFileVO selectImageFileByName(@Param("imgFileNm") String imgFileNm, @Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 최대 순서 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 최대 순서
     */
    Integer selectMaxOrderByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
} 