package com.ERI.demo.mappers;

import com.ERI.demo.vo.FileAttachVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileAttachMapper {
    
    /**
     * 첨부파일 목록 조회
     */
    List<FileAttachVO> selectFileAttachList(@Param("refTblCd") String refTblCd, 
                                           @Param("refPkVal") String refPkVal);
    
    /**
     * 첨부파일 상세 조회
     */
    FileAttachVO selectFileAttachBySeq(@Param("fileSeq") Long fileSeq);
    
    /**
     * 첨부파일 등록
     */
    int insertFileAttach(FileAttachVO fileAttachVO);
    
    /**
     * 첨부파일 수정
     */
    int updateFileAttach(FileAttachVO fileAttachVO);
    
    /**
     * 첨부파일 삭제 (논리 삭제)
     */
    int deleteFileAttach(@Param("fileSeq") Long fileSeq, 
                        @Param("updEmpId") String updEmpId);
    
    /**
     * 첨부파일 물리 삭제
     */
    int deleteFileAttachPhysical(@Param("fileSeq") Long fileSeq);
    
    /**
     * 참조 테이블의 모든 첨부파일 삭제 (논리 삭제)
     */
    int deleteFileAttachByRef(@Param("refTblCd") String refTblCd, 
                             @Param("refPkVal") String refPkVal, 
                             @Param("updEmpId") String updEmpId);
    
    /**
     * 다운로드 횟수 증가
     */
    int updateDownloadCount(@Param("fileSeq") Long fileSeq);
    
    /**
     * 파일명으로 첨부파일 조회
     */
    FileAttachVO selectFileAttachBySaveName(@Param("fileSaveNm") String fileSaveNm);
} 