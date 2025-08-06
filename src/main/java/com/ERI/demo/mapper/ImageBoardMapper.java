package com.ERI.demo.mapper;

import com.ERI.demo.vo.ImageBoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 이미지 게시판 Mapper
 * TB_IMG_BRD_LST 테이블과 매핑
 */
@Mapper
public interface ImageBoardMapper {
    
    /**
     * 이미지 게시판 목록 조회 (페이징/검색)
     * @param imageBoard 조회 조건
     * @return 이미지 게시판 목록
     */
    List<ImageBoardVO> selectImageBoardList(ImageBoardVO imageBoard);
    
    /**
     * 이미지 게시판 목록 개수 조회
     * @param imageBoard 조회 조건
     * @return 이미지 게시판 목록 개수
     */
    int selectImageBoardCount(ImageBoardVO imageBoard);
    
    /**
     * 이미지 게시판 상세 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 게시판 정보
     */
    ImageBoardVO selectImageBoardBySeq(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 게시판 등록
     * @param imageBoard 이미지 게시판 정보
     * @return 등록된 행 수
     */
    int insertImageBoard(ImageBoardVO imageBoard);
    
    /**
     * 이미지 게시판 수정
     * @param imageBoard 이미지 게시판 정보
     * @return 수정된 행 수
     */
    int updateImageBoard(ImageBoardVO imageBoard);
    
    /**
     * 이미지 게시판 삭제 (논리 삭제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    int deleteImageBoard(@Param("imgBrdSeq") Long imgBrdSeq);
    
    /**
     * 이미지 게시판 제목으로 조회
     * @param imgBrdTitl 이미지 게시판 제목
     * @return 이미지 게시판 정보
     */
    ImageBoardVO selectImageBoardByTitle(@Param("imgBrdTitl") String imgBrdTitl);
    
    /**
     * 최근 등록된 이미지 게시판 목록 조회
     * @param limit 조회 개수 제한
     * @return 이미지 게시판 목록
     */
    List<ImageBoardVO> selectRecentImageBoardList(@Param("limit") int limit);
} 