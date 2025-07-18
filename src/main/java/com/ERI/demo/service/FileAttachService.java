package com.ERI.demo.service;

import com.ERI.demo.mappers.FileAttachMapper;
import com.ERI.demo.vo.FileAttachVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileAttachService {

    private final FileAttachMapper fileAttachMapper;

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    @Value("${file.upload.max-size:10485760}") // 10MB
    private long maxFileSize;

    @Value("${file.upload.allowed-extensions:pdf,doc,docx,xls,xlsx,ppt,pptx,txt,jpg,jpeg,png,gif}")
    private String allowedExtensions;

    @Value("${file.upload.max-files:10}") // 최대 파일 개수
    private int maxFiles;

    /**
     * 첨부파일 목록 조회
     */
    public List<FileAttachVO> getFileAttachList(String refTblCd, String refPkVal) {
        List<FileAttachVO> fileList = fileAttachMapper.selectFileAttachList(refTblCd, refPkVal);
        
        // 파일 크기 표시용 포맷팅 및 URL 설정
        for (FileAttachVO file : fileList) {
            file.setFileSizeDisplay(formatFileSize(file.getFileSize()));
            file.setDownloadUrl("/api/file/download/" + file.getFileSeq());
            if (isImageFile(file.getFileExt())) {
                file.setPreviewUrl("/api/file/preview/" + file.getFileSeq());
            }
        }
        
        return fileList;
    }

    /**
     * 첨부파일 상세 조회
     */
    public FileAttachVO getFileAttachBySeq(Long fileSeq) {
        FileAttachVO file = fileAttachMapper.selectFileAttachBySeq(fileSeq);
        if (file != null) {
            file.setFileSizeDisplay(formatFileSize(file.getFileSize()));
            file.setDownloadUrl("/api/file/download/" + file.getFileSeq());
            if (isImageFile(file.getFileExt())) {
                file.setPreviewUrl("/api/file/preview/" + file.getFileSeq());
            }
        }
        return file;
    }

    /**
     * 파일 업로드 및 등록 (단일/다중 모두 지원)
     */
    @Transactional
    public List<FileAttachVO> uploadMultipleFiles(MultipartFile[] files, String refTblCd, String refPkVal, 
                                                 String[] fileDescs, String empId) throws IOException {
        
        // 다중 파일 유효성 검사
        validateMultipleFiles(files);
        
        List<FileAttachVO> uploadedFiles = new ArrayList<>();
        
        // 파일 저장 경로 생성
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String relativePath = refTblCd.toLowerCase() + "/" + today;
        String fullPath = uploadPath + "/" + relativePath;
        
        // 디렉토리 생성
        Path directory = Paths.get(fullPath);
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                log.info("디렉토리 생성 완료: {}", directory.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("디렉토리 생성 실패: {}", directory.toAbsolutePath(), e);
            throw new IOException("파일 저장 디렉토리를 생성할 수 없습니다: " + e.getMessage());
        }
        
        // 현재 파일 순서 시작점 계산
        Integer startFileOrd = getNextFileOrd(refTblCd, refPkVal);
        
        for (int i = 0; i < files.length; i++) {
            MultipartFile multipartFile = files[i];
            
            // 개별 파일 유효성 검사
            validateFile(multipartFile);
            
            // 파일명 생성 (UUID + 원본 확장자)
            String originalFilename = multipartFile.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String savedFilename = UUID.randomUUID().toString() + "." + fileExtension;
            
            // 파일 저장
            Path filePath = directory.resolve(savedFilename);
            try {
                multipartFile.transferTo(filePath.toFile());
                log.info("파일 저장 완료: {}", filePath.toAbsolutePath());
            } catch (IOException e) {
                log.error("파일 저장 실패: {}", filePath.toAbsolutePath(), e);
                throw new IOException("파일을 저장할 수 없습니다: " + e.getMessage());
            }
            
            // 파일 설명 가져오기 (배열 인덱스에 맞게)
            String fileDesc = (fileDescs != null && i < fileDescs.length) ? fileDescs[i] : null;
            
            // DB에 파일 정보 저장
            FileAttachVO fileAttachVO = new FileAttachVO();
            fileAttachVO.setRefTblCd(refTblCd);
            fileAttachVO.setRefPkVal(refPkVal);
            fileAttachVO.setFileNm(originalFilename);
            fileAttachVO.setFileSaveNm(savedFilename);
            fileAttachVO.setFilePath("/" + relativePath + "/");
            fileAttachVO.setFileSize(multipartFile.getSize());
            fileAttachVO.setFileExt(fileExtension);
            fileAttachVO.setFileMimeType(multipartFile.getContentType());
            fileAttachVO.setFileDesc(fileDesc);
            fileAttachVO.setFileOrd(startFileOrd + i);
            fileAttachVO.setDownloadCnt(0);
            fileAttachVO.setRgstEmpId(empId);
            fileAttachVO.setUpdtEmpId(empId);
            fileAttachVO.setRegEmpId(empId);
            fileAttachVO.setUpdEmpId(empId);
            
            fileAttachMapper.insertFileAttach(fileAttachVO);
            uploadedFiles.add(fileAttachVO);
        }
        
        log.info("파일 업로드 완료: {}개 파일", uploadedFiles.size());
        return uploadedFiles;
    }

    /**
     * 첨부파일 삭제
     */
    @Transactional
    public boolean deleteFile(Long fileSeq, String empId) {
        FileAttachVO file = fileAttachMapper.selectFileAttachBySeq(fileSeq);
        if (file == null) {
            return false;
        }
        
        // 물리 파일 삭제
        String fullPath = uploadPath + file.getFilePath() + file.getFileSaveNm();
        try {
            Files.deleteIfExists(Paths.get(fullPath));
        } catch (IOException e) {
            log.error("파일 삭제 실패: {}", fullPath, e);
        }
        
        // DB에서 논리 삭제
        boolean deleted = fileAttachMapper.deleteFileAttach(fileSeq, empId) > 0;
        
        // 삭제 후 파일 순서 재정렬
        if (deleted) {
            reorderFiles(file.getRefTblCd(), file.getRefPkVal());
        }
        
        return deleted;
    }

    /**
     * 참조 테이블의 모든 첨부파일 삭제
     */
    @Transactional
    public boolean deleteFilesByRef(String refTblCd, String refPkVal, String empId) {
        List<FileAttachVO> files = fileAttachMapper.selectFileAttachList(refTblCd, refPkVal);
        
        // 물리 파일 삭제
        for (FileAttachVO file : files) {
            String fullPath = uploadPath + file.getFilePath() + file.getFileSaveNm();
            try {
                Files.deleteIfExists(Paths.get(fullPath));
            } catch (IOException e) {
                log.error("파일 삭제 실패: {}", fullPath, e);
            }
        }
        
        // DB에서 논리 삭제
        return fileAttachMapper.deleteFileAttachByRef(refTblCd, refPkVal, empId) > 0;
    }

    /**
     * 다운로드 횟수 증가
     */
    public void incrementDownloadCount(Long fileSeq) {
        fileAttachMapper.updateDownloadCount(fileSeq);
    }

    /**
     * 다음 파일 순서 계산
     */
    private Integer getNextFileOrd(String refTblCd, String refPkVal) {
        List<FileAttachVO> existingFiles = fileAttachMapper.selectFileAttachList(refTblCd, refPkVal);
        if (existingFiles.isEmpty()) {
            return 1;
        }
        
        // 기존 파일들의 최대 순서 + 1
        return existingFiles.stream()
                .mapToInt(FileAttachVO::getFileOrd)
                .max()
                .orElse(0) + 1;
    }

    /**
     * 파일 순서 재정렬
     */
    private void reorderFiles(String refTblCd, String refPkVal) {
        List<FileAttachVO> files = fileAttachMapper.selectFileAttachList(refTblCd, refPkVal);
        
        for (int i = 0; i < files.size(); i++) {
            FileAttachVO file = files.get(i);
            if (file.getFileOrd() != (i + 1)) {
                file.setFileOrd(i + 1);
                fileAttachMapper.updateFileAttach(file);
            }
        }
    }

    /**
     * 다중 파일 유효성 검사
     */
    private void validateMultipleFiles(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }
        
        if (files.length > maxFiles) {
            throw new IllegalArgumentException("최대 " + maxFiles + "개까지 파일을 업로드할 수 있습니다.");
        }
        
        // 각 파일 개별 검사
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("빈 파일이 포함되어 있습니다.");
            }
        }
    }

    /**
     * 파일 유효성 검사
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드된 파일이 비어있습니다.");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("파일 크기가 최대 허용 크기를 초과했습니다. (최대: " + formatFileSize(maxFileSize) + ")");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }
        
        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!isAllowedExtension(extension)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. 허용 형식: " + allowedExtensions);
        }
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex + 1) : "";
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
     * 이미지 파일인지 확인
     */
    private boolean isImageFile(String extension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        for (String ext : imageExtensions) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 파일 크기 포맷팅
     */
    private String formatFileSize(Long bytes) {
        if (bytes == null) return "0 B";
        
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.1f %s", size, units[unitIndex]);
    }
} 