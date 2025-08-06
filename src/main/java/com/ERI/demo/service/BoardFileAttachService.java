package com.ERI.demo.service;

import com.ERI.demo.mapper.BoardFileAttachMapper;
import com.ERI.demo.vo.BoardFileAttachVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 게시판 파일 첨부 서비스
 */
@Service
@Transactional
public class BoardFileAttachService {

    @Autowired
    private BoardFileAttachMapper boardFileAttachMapper;

    /**
     * 파일 첨부 목록 조회 (페이징/검색)
     * @param boardFileAttach 조회 조건
     * @return 파일 첨부 목록
     */
    public List<BoardFileAttachVO> getBoardFileAttachList(BoardFileAttachVO boardFileAttach) {
        return boardFileAttachMapper.selectBoardFileAttachList(boardFileAttach);
    }

    /**
     * 파일 첨부 목록 개수 조회
     * @param boardFileAttach 조회 조건
     * @return 파일 첨부 목록 개수
     */
    public int getBoardFileAttachCount(BoardFileAttachVO boardFileAttach) {
        return boardFileAttachMapper.selectBoardFileAttachCount(boardFileAttach);
    }

    /**
     * 파일 첨부 상세 조회
     * @param fileSeq 파일 시퀀스
     * @return 파일 첨부 정보
     */
    public BoardFileAttachVO getBoardFileAttachBySeq(Long fileSeq) {
        return boardFileAttachMapper.selectBoardFileAttachBySeq(fileSeq);
    }

    /**
     * 게시글별 파일 첨부 목록 조회
     * @param brdSeq 게시글 시퀀스
     * @return 파일 첨부 목록
     */
    public List<BoardFileAttachVO> getBoardFileAttachByBrdSeq(Long brdSeq) {
        return boardFileAttachMapper.selectBoardFileAttachByBrdSeq(brdSeq);
    }

    /**
     * 파일 첨부 등록
     * @param boardFileAttach 파일 첨부 정보
     * @return 등록된 파일 첨부 정보
     */
    public BoardFileAttachVO createBoardFileAttach(BoardFileAttachVO boardFileAttach) {
        boardFileAttachMapper.insertBoardFileAttach(boardFileAttach);
        return boardFileAttach;
    }

    /**
     * 파일 업로드 및 등록
     * @param file 업로드된 파일
     * @param brdSeq 게시글 시퀀스
     * @param regId 등록자 ID
     * @param uploadPath 업로드 경로
     * @return 등록된 파일 첨부 정보
     */
    public BoardFileAttachVO uploadFile(MultipartFile file, Long brdSeq, String regId, String uploadPath) throws IOException {
        // 파일 정보 설정
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String storedFileName = generateStoredFileName(fileExtension);
        
        // 파일 저장 경로 생성
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        // 파일 저장
        Path filePath = uploadDir.resolve(storedFileName);
        Files.copy(file.getInputStream(), filePath);
        
        // VO 생성
        BoardFileAttachVO boardFileAttach = new BoardFileAttachVO();
        boardFileAttach.setBrdSeq(brdSeq);
        boardFileAttach.setOrigFileNm(originalFileName);
        boardFileAttach.setStorFileNm(storedFileName);
        boardFileAttach.setFilePath(uploadPath);
        boardFileAttach.setFileSize(file.getSize());
        boardFileAttach.setFileExt(fileExtension);
        boardFileAttach.setFileTyp(file.getContentType());
        boardFileAttach.setRegId(regId);
        
        // 데이터베이스에 저장
        return createBoardFileAttach(boardFileAttach);
    }

    /**
     * 파일 첨부 수정
     * @param boardFileAttach 파일 첨부 정보
     * @return 수정된 파일 첨부 정보
     */
    public BoardFileAttachVO updateBoardFileAttach(BoardFileAttachVO boardFileAttach) {
        boardFileAttachMapper.updateBoardFileAttach(boardFileAttach);
        return boardFileAttach;
    }

    /**
     * 파일 첨부 삭제 (논리 삭제)
     * @param fileSeq 파일 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteBoardFileAttach(Long fileSeq) {
        return boardFileAttachMapper.deleteBoardFileAttach(fileSeq);
    }

    /**
     * 게시글별 파일 첨부 삭제 (논리 삭제)
     * @param brdSeq 게시글 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteBoardFileAttachByBrdSeq(Long brdSeq) {
        return boardFileAttachMapper.deleteBoardFileAttachByBrdSeq(brdSeq);
    }

    /**
     * 다운로드 횟수 증가
     * @param fileSeq 파일 시퀀스
     * @return 업데이트된 행 수
     */
    public int incrementDownloadCount(Long fileSeq) {
        return boardFileAttachMapper.incrementDownloadCount(fileSeq);
    }

    /**
     * 이미지 링크 정보 업데이트
     * @param fileSeq 파일 시퀀스
     * @param imgLinks 이미지 링크 정보
     * @return 업데이트된 행 수
     */
    public int updateImgLinks(Long fileSeq, String imgLinks) {
        return boardFileAttachMapper.updateImgLinks(fileSeq, imgLinks);
    }

    /**
     * 파일 확장자 추출
     * @param fileName 파일명
     * @return 파일 확장자
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 저장 파일명 생성
     * @param fileExtension 파일 확장자
     * @return 저장 파일명
     */
    private String generateStoredFileName(String fileExtension) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + "." + fileExtension;
    }

    /**
     * 파일 삭제 (물리적 삭제)
     * @param filePath 파일 경로
     * @param storedFileName 저장 파일명
     * @return 삭제 성공 여부
     */
    public boolean deletePhysicalFile(String filePath, String storedFileName) {
        try {
            Path path = Paths.get(filePath, storedFileName);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 파일 존재 여부 확인
     * @param filePath 파일 경로
     * @param storedFileName 저장 파일명
     * @return 파일 존재 여부
     */
    public boolean fileExists(String filePath, String storedFileName) {
        Path path = Paths.get(filePath, storedFileName);
        return Files.exists(path);
    }

    /**
     * 파일 크기 포맷팅
     * @param fileSize 파일 크기 (bytes)
     * @return 포맷된 파일 크기
     */
    public String formatFileSize(Long fileSize) {
        if (fileSize == null) return "0 B";
        
        long size = fileSize;
        String[] units = {"B", "KB", "MB", "GB"};
        int unitIndex = 0;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.1f %s", (double) size, units[unitIndex]);
    }
} 