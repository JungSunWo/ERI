package com.ERI.demo.mappers;

import com.ERI.demo.vo.FileAttachVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileAttachMapper {

    /**
     * 특정 참조 테이블의 첨부파일 목록 조회
     * @param refTblCd 참조 테이블 코드
     * @param refPkVal 참조 테이블의 PK 값
     * @return 첨부파일 목록
     */
    List<FileAttachVO> selectFileAttachList(@Param("refTblCd") String refTblCd, 
                                           @Param("refPkVal") String refPkVal);

    /**
     * 첨부파일 상세 조회
     * @param fileSeq 파일 시퀀스
     * @return 첨부파일 정보
     */
    FileAttachVO selectFileAttach(@Param("fileSeq") Long fileSeq);

    /**
     * 첨부파일 등록
     * @param fileAttach 첨부파일 정보
     */
    void insertFileAttach(FileAttachVO fileAttach);

    /**
     * 첨부파일 다운로드 횟수 증가
     * @param fileSeq 파일 시퀀스
     */
    void incrementDownloadCount(@Param("fileSeq") Long fileSeq);

    /**
     * 첨부파일 삭제 (논리 삭제)
     * @param fileSeq 파일 시퀀스
     * @param empId 삭제자 직원 ID
     */
    void deleteFileAttach(@Param("fileSeq") Long fileSeq, @Param("empId") String empId);

    /**
     * 참조 테이블의 모든 첨부파일 삭제
     * @param refTblCd 참조 테이블 코드
     * @param refPkVal 참조 테이블의 PK 값
     * @param empId 삭제자 직원 ID
     */
    void deleteFileAttachByRef(@Param("refTblCd") String refTblCd, 
                               @Param("refPkVal") String refPkVal, 
                               @Param("empId") String empId);
} 