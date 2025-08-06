package com.ERI.demo.mapper;

import com.ERI.demo.vo.ImageSelectionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 이미지 선택 Mapper
 * TB_IMG_SEL_LST 테이블과 매핑
 */
@Mapper
public interface ImageSelectionMapper {
    
    /**
     * 이미지 선택 목록 조회 (페이징/검색)
     * @param imageSelection 조회 조건
     * @return 이미지 선택 목록
     */
    List<ImageSelectionVO> selectImageSelectionList(ImageSelectionVO imageSelection);
    
    /**
     * 이미지 선택 목록 개수 조회
     * @param imageSelection 조회 조건
     * @return 이미지 선택 목록 개수
     */
    int selectImageSelectionCount(ImageSelectionVO imageSelection);
    
    /**
     * 이미지 선택 상세 조회
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 이미지 선택 정보
     */
    ImageSelectionVO selectImageSelectionBySeq(@Param("imgSelSeq") Long imgSelSeq);
    
    /**
     * 이미지 게시판별 선택 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 선택 목록
     */
    List<ImageSelectionVO> selectImageSelectionByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 직원별 선택 목록 조회
     * @param selEmpId 선택한 직원 ID
     * @return 이미지 선택 목록
     */
    List<ImageSelectionVO> selectImageSelectionByEmpId(@Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 파일별 선택 목록 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 선택 목록
     */
    List<ImageSelectionVO> selectImageSelectionByFileSeq(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 이미지 선택 등록
     * @param imageSelection 이미지 선택 정보
     * @return 등록된 행 수
     */
    int insertImageSelection(ImageSelectionVO imageSelection);
    
    /**
     * 이미지 선택 삭제
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageSelection(@Param("imgSelSeq") Long imgSelSeq);
    
    /**
     * 직원별 이미지 선택 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 삭제된 행 수
     */
    int deleteImageSelectionByEmpId(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 게시판별 선택 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageSelectionByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 파일별 선택 삭제
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageSelectionByFileSeq(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 직원의 이미지 선택 여부 확인
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 여부 (1: 선택됨, 0: 선택되지 않음)
     */
    int checkImageSelection(@Param("imgBrdSeq") Long imgBrdSeq, @Param("imgFileSeq") Long imgFileSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 직원의 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 개수
     */
    int countImageSelectionByEmpId(@Param("imgBrdSeq") Long imgBrdSeq, @Param("selEmpId") String selEmpId);
    
    /**
     * 이미지 파일의 선택 개수 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 선택 개수
     */
    int countImageSelectionByFileSeq(@Param("imgFileSeq") Long imgFileSeq);
    
    /**
     * 이미지 게시판의 전체 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 전체 선택 개수
     */
    int countImageSelectionByBrdSeq(@Param("imgBrdSeq") Long imgBrdSeq);
} 