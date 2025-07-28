package com.ERI.demo.service;

import com.ERI.demo.mappers.BoardFileAttachMapper;
import com.ERI.demo.vo.FileAttachVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 파일 업로드 서비스
 * - 게시글 첨부파일 관리
 * - 파일 업로드, 다운로드, 삭제 기능
 */
@Slf4j
@Service
public class FileUploadService {
    
    @Autowired
    private BoardFileAttachMapper boardFileAttachMapper;

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    @Value("${file.upload.max-size:52428800}") // 50MB
    private long maxFileSize;

    @Value("${file.upload.allowed-extensions:jpg,jpeg,png,gif,pdf,doc,docx,xls,xlsx,txt,mp4,avi,mov,wmv,flv,webm,mkv}")
    private String allowedExtensions;

    /**
     * 파일 업로드
     * @param files 업로드할 파일들
     * @param boardSeq 게시글 시퀀스
     * @param empId 직원 ID
     * @return 업로드된 파일 정보 목록
     */
    @Transactional
    public List<FileAttachVO> uploadFiles(List<MultipartFile> files, Long boardSeq, String empId) {
        List<FileAttachVO> uploadedFiles = new ArrayList<>();

        if (files == null || files.isEmpty()) {
            return uploadedFiles;
        }

        // 업로드 디렉토리 생성
        createUploadDirectory();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            try {
                // 파일 검증
                validateFile(file);

                // 파일 정보 생성
                FileAttachVO fileAttach = createFileAttach(file, boardSeq, empId);

                // 파일 저장
                saveFileToDisk(file, fileAttach);

                // 데이터베이스에 파일 정보 저장
                boardFileAttachMapper.insertFileAttach(fileAttach);

                uploadedFiles.add(fileAttach);
                log.info("파일 업로드 성공: {}", fileAttach.getOriginalFileName());

            } catch (Exception e) {
                log.error("파일 업로드 실패: {}", file.getOriginalFilename(), e);
                throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage());
            }
        }

        return uploadedFiles;
    }

    /**
     * 게시글별 파일 목록 조회
     * @param boardSeq 게시글 시퀀스
     * @return 파일 목록
     */
    public List<FileAttachVO> getFilesByBoardSeq(Long boardSeq) {
        return boardFileAttachMapper.selectFileAttachByBoardSeq(boardSeq);
    }

    /**
     * 파일 다운로드
     * @param fileSeq 파일 시퀀스
     * @return 파일 정보
     */
    @Transactional
    public FileAttachVO downloadFile(Long fileSeq) {
        FileAttachVO fileAttach = boardFileAttachMapper.selectFileAttachBySeq(fileSeq);
        if (fileAttach != null) {
            // 다운로드 횟수 증가
            boardFileAttachMapper.incrementDownloadCnt(fileSeq);
        }
        return fileAttach;
    }

    /**
     * 파일 삭제
     * @param fileSeq 파일 시퀀스
     * @param empId 직원 ID
     */
    @Transactional
    public void deleteFile(Long fileSeq, String empId) {
        FileAttachVO fileAttach = boardFileAttachMapper.selectFileAttachBySeq(fileSeq);
        if (fileAttach != null) {
            // 논리 삭제
            boardFileAttachMapper.deleteFileAttach(fileSeq, empId);
            
            // 물리 파일 삭제
            deletePhysicalFile(fileAttach);
        }
    }

    /**
     * 게시글별 파일 전체 삭제
     * @param boardSeq 게시글 시퀀스
     * @param empId 직원 ID
     */
    @Transactional
    public void deleteFilesByBoardSeq(Long boardSeq, String empId) {
        List<FileAttachVO> files = boardFileAttachMapper.selectFileAttachByBoardSeq(boardSeq);
        
        // 논리 삭제
        boardFileAttachMapper.deleteFileAttachByBoardSeq(boardSeq, empId);
        
        // 물리 파일 삭제
        for (FileAttachVO file : files) {
            deletePhysicalFile(file);
        }
    }

    /**
     * 파일 검증
     */
    private void validateFile(MultipartFile file) {
        // 파일 크기 검증
        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("파일 크기가 너무 큽니다. 최대 " + (maxFileSize / 1024 / 1024) + "MB까지 업로드 가능합니다.");
        }

        // 파일 확장자 검증
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("파일명이 없습니다.");
        }

        String extension = getFileExtension(originalFilename);
        if (!isAllowedExtension(extension)) {
            throw new RuntimeException("허용되지 않는 파일 형식입니다. 허용된 형식: " + allowedExtensions);
        }
    }

    /**
     * 파일 첨부 정보 생성
     */
    private FileAttachVO createFileAttach(MultipartFile file, Long boardSeq, String empId) {
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String storedFilename = generateStoredFilename(extension);

        FileAttachVO fileAttach = new FileAttachVO();
        fileAttach.setBoardSeq(boardSeq);
        fileAttach.setOriginalFileName(originalFilename);
        fileAttach.setStoredFileName(storedFilename);
        fileAttach.setFilePath(storedFilename);
        fileAttach.setFileSize(file.getSize());
        fileAttach.setFileExt(extension);
        fileAttach.setFileType(file.getContentType());
        fileAttach.setDownloadCnt(0);
        fileAttach.setRegId(empId);
        fileAttach.setUpdId(empId);

        return fileAttach;
    }

    /**
     * 파일을 디스크에 저장
     */
    private void saveFileToDisk(MultipartFile file, FileAttachVO fileAttach) throws IOException {
        Path filePath = Paths.get(uploadPath, fileAttach.getFilePath());
        Files.copy(file.getInputStream(), filePath);
    }

    /**
     * 물리 파일 삭제
     */
    private void deletePhysicalFile(FileAttachVO fileAttach) {
        Path filePath = Paths.get(uploadPath, fileAttach.getFilePath());
        try {
            Files.deleteIfExists(filePath);
            log.info("물리 파일 삭제 성공: {}", filePath);
        } catch (IOException e) {
            log.error("물리 파일 삭제 실패: {}", filePath, e);
        }
    }

    /**
     * 업로드 디렉토리 생성
     */
    private void createUploadDirectory() {
        try {
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            log.error("업로드 디렉토리 생성 실패", e);
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다.");
        }
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            String extension = filename.substring(lastDotIndex + 1).toLowerCase();
            // 확장자가 너무 길면 잘라내기 (최대 20자)
            if (extension.length() > 20) {
                extension = extension.substring(0, 20);
            }
            return extension;
        }
        return "";
    }

    /**
     * 허용된 확장자인지 확인
     */
    private boolean isAllowedExtension(String extension) {
        String[] allowed = allowedExtensions.split(",");
        for (String ext : allowed) {
            if (ext.trim().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 저장용 파일명 생성 (UUID 사용)
     */
    private String generateStoredFilename(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }
    
    /**
     * 파일 링크 정보 업데이트
     * @param fileSeq 파일 시퀀스
     * @param imageLinks 이미지 링크 정보 (JSON 배열)
     */
    @Transactional
    public void updateFileLinks(Long fileSeq, String imageLinks) {
        log.info("updateFileLinks 호출: fileSeq={}, imageLinks={}", fileSeq, imageLinks);
        log.info("imageLinks 타입: {}", imageLinks != null ? imageLinks.getClass().getName() : "null");
        log.info("imageLinks 길이: {}", imageLinks != null ? imageLinks.length() : "null");
        
        if (imageLinks == null) {
            log.error("imageLinks가 null입니다!");
            throw new RuntimeException("imageLinks가 null입니다.");
        }
        
        log.info("boardFileAttachMapper.updateFileLinks 호출 시작: fileSeq={}", fileSeq);
        int result = boardFileAttachMapper.updateFileLinks(fileSeq, imageLinks);
        log.info("boardFileAttachMapper.updateFileLinks 호출 완료");
        log.info("DB 업데이트 결과: result={}", result);
        
        if (result > 0) {
            log.info("파일 링크 정보 업데이트 성공: fileSeq={}, result={}", fileSeq, result);
        } else {
            log.warn("파일 링크 정보 업데이트 실패: fileSeq={}, result={}", fileSeq, result);
            throw new RuntimeException("파일 링크 정보 업데이트에 실패했습니다.");
        }
    }
    
    /**
     * 파일 링크 정보 조회
     * @param fileSeq 파일 시퀀스
     * @return 파일 링크 정보
     */
    public FileAttachVO getFileLinks(Long fileSeq) {
        return boardFileAttachMapper.selectFileLinks(fileSeq);
    }
} 