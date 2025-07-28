package com.ERI.demo.service;

import com.ERI.demo.mappers.FileAttachMapper;
import com.ERI.demo.vo.FileAttachVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileAttachService {

    @Autowired
    private FileAttachMapper fileAttachMapper;

    /**
     * 특정 참조 테이블의 첨부파일 목록 조회
     * @param refTblCd 참조 테이블 코드
     * @param refPkVal 참조 테이블의 PK 값
     * @return 첨부파일 목록
     */
    public List<FileAttachVO> getFileAttachList(String refTblCd, String refPkVal) {
        return fileAttachMapper.selectFileAttachList(refTblCd, refPkVal);
    }

    /**
     * 첨부파일 다운로드 횟수 증가
     * @param fileSeq 파일 시퀀스
     * @return 업데이트된 다운로드 횟수
     */
    @Transactional
    public Integer incrementDownloadCount(Long fileSeq) {
        // 다운로드 횟수 증가
        fileAttachMapper.incrementDownloadCount(fileSeq);
        
        // 업데이트된 다운로드 횟수 조회
        FileAttachVO fileAttach = fileAttachMapper.selectFileAttach(fileSeq);
        return fileAttach != null ? fileAttach.getDownloadCnt() : 0;
    }

    /**
     * 첨부파일 상세 조회
     * @param fileSeq 파일 시퀀스
     * @return 첨부파일 정보
     */
    public FileAttachVO getFileAttach(Long fileSeq) {
        return fileAttachMapper.selectFileAttach(fileSeq);
    }

    /**
     * 첨부파일 등록
     * @param fileAttach 첨부파일 정보
     * @return 등록된 파일 시퀀스
     */
    @Transactional
    public Long createFileAttach(FileAttachVO fileAttach) {
        fileAttachMapper.insertFileAttach(fileAttach);
        return fileAttach.getFileSeq();
    }

    /**
     * 첨부파일 삭제 (논리 삭제)
     * @param fileSeq 파일 시퀀스
     * @param empId 삭제자 직원 ID
     */
    @Transactional
    public void deleteFileAttach(Long fileSeq, String empId) {
        fileAttachMapper.deleteFileAttach(fileSeq, empId);
    }

    /**
     * 참조 테이블의 모든 첨부파일 삭제
     * @param refTblCd 참조 테이블 코드
     * @param refPkVal 참조 테이블의 PK 값
     * @param empId 삭제자 직원 ID
     */
    @Transactional
    public void deleteFileAttachByRef(String refTblCd, String refPkVal, String empId) {
        fileAttachMapper.deleteFileAttachByRef(refTblCd, refPkVal, empId);
    }
} 