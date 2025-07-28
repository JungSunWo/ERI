package com.ERI.demo.mappers;

import com.ERI.demo.vo.FileAttachVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 게시판 파일 첨부 Mapper (TB_BOARD_FILE_ATTACH 전용)
 * @author ERI
 */
@Mapper
public interface BoardFileAttachMapper {
    
    /**
     * 파일 첨부 등록
     */
    int insertFileAttach(FileAttachVO fileAttach);
    
    /**
     * 게시글별 파일 첨부 목록 조회
     */
    List<FileAttachVO> selectFileAttachByBoardSeq(@Param("boardSeq") Long boardSeq);
    
    /**
     * 파일 첨부 상세 조회
     */
    FileAttachVO selectFileAttachBySeq(@Param("fileSeq") Long fileSeq);
    
    /**
     * 파일 첨부 삭제 (논리 삭제)
     */
    int deleteFileAttach(@Param("fileSeq") Long fileSeq, @Param("empId") String empId);
    
    /**
     * 게시글별 파일 첨부 전체 삭제 (논리 삭제)
     */
    int deleteFileAttachByBoardSeq(@Param("boardSeq") Long boardSeq, @Param("empId") String empId);
    
    /**
     * 다운로드 횟수 증가
     */
    int incrementDownloadCnt(@Param("fileSeq") Long fileSeq);
    
    /**
     * 다운로드 횟수 증가 (서비스에서 사용)
     */
    int updateDownloadCount(@Param("fileSeq") Long fileSeq);
    
    /**
     * 파일 링크 정보 업데이트
     */
    int updateFileLinks(@Param("fileSeq") Long fileSeq, 
                       @Param("imageLinks") String imageLinks);
    
    /**
     * 파일 링크 정보 조회
     */
    FileAttachVO selectFileLinks(@Param("fileSeq") Long fileSeq);
} 