package com.ERI.demo.service;

import com.ERI.demo.mapper.ImageFileMapper;
import com.ERI.demo.vo.ImageFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * 이미지 파일 서비스
 */
@Service
@Transactional
public class ImageFileService {

    @Autowired
    private ImageFileMapper imageFileMapper;

    /**
     * 이미지 파일 목록 조회 (페이징/검색)
     * @param imageFile 조회 조건
     * @return 이미지 파일 목록
     */
    public List<ImageFileVO> getImageFileList(ImageFileVO imageFile) {
        return imageFileMapper.selectImageFileList(imageFile);
    }

    /**
     * 이미지 파일 목록 개수 조회
     * @param imageFile 조회 조건
     * @return 이미지 파일 목록 개수
     */
    public int getImageFileCount(ImageFileVO imageFile) {
        return imageFileMapper.selectImageFileCount(imageFile);
    }

    /**
     * 이미지 파일 상세 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 파일 정보
     */
    public ImageFileVO getImageFileBySeq(Long imgFileSeq) {
        return imageFileMapper.selectImageFileBySeq(imgFileSeq);
    }

    /**
     * 이미지 게시판별 이미지 파일 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 파일 목록
     */
    public List<ImageFileVO> getImageFileByBrdSeq(Long imgBrdSeq) {
        return imageFileMapper.selectImageFileByBrdSeq(imgBrdSeq);
    }

    /**
     * 이미지 파일 등록
     * @param imageFile 이미지 파일 정보
     * @return 등록된 이미지 파일 정보
     */
    public ImageFileVO createImageFile(ImageFileVO imageFile) {
        // 순서가 지정되지 않은 경우 최대 순서 + 1로 설정
        if (imageFile.getImgOrd() == null) {
            Integer maxOrder = imageFileMapper.selectMaxOrderByBrdSeq(imageFile.getImgBrdSeq());
            imageFile.setImgOrd(maxOrder != null ? maxOrder + 1 : 1);
        }
        imageFileMapper.insertImageFile(imageFile);
        return imageFile;
    }

    /**
     * 이미지 파일 업로드 및 등록
     * @param file 업로드된 파일
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgText 이미지 텍스트
     * @param regEmpId 등록자 ID
     * @param uploadPath 업로드 경로
     * @return 등록된 이미지 파일 정보
     */
    public ImageFileVO uploadImageFile(MultipartFile file, Long imgBrdSeq, String imgText, String regEmpId, String uploadPath) throws IOException {
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
        ImageFileVO imageFile = new ImageFileVO();
        imageFile.setImgBrdSeq(imgBrdSeq);
        imageFile.setImgFileNm(storedFileName);
        imageFile.setImgFilePath(uploadPath);
        imageFile.setImgFileSize(file.getSize());
        imageFile.setImgFileExt(fileExtension);
        imageFile.setImgText(imgText);
        imageFile.setRegEmpId(regEmpId);
        
        // 데이터베이스에 저장
        return createImageFile(imageFile);
    }

    /**
     * 이미지 파일 수정
     * @param imageFile 이미지 파일 정보
     * @return 수정된 이미지 파일 정보
     */
    public ImageFileVO updateImageFile(ImageFileVO imageFile) {
        imageFileMapper.updateImageFile(imageFile);
        return imageFile;
    }

    /**
     * 이미지 파일 삭제 (논리 삭제)
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageFile(Long imgFileSeq) {
        return imageFileMapper.deleteImageFile(imgFileSeq);
    }

    /**
     * 이미지 게시판별 이미지 파일 삭제 (논리 삭제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageFileByBrdSeq(Long imgBrdSeq) {
        return imageFileMapper.deleteImageFileByBrdSeq(imgBrdSeq);
    }

    /**
     * 이미지 순서 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgOrd 이미지 순서
     * @return 업데이트된 행 수
     */
    public int updateImageOrder(Long imgFileSeq, Integer imgOrd) {
        return imageFileMapper.updateImageOrder(imgFileSeq, imgOrd);
    }

    /**
     * 이미지 텍스트 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgText 이미지 텍스트
     * @return 업데이트된 행 수
     */
    public int updateImageText(Long imgFileSeq, String imgText) {
        return imageFileMapper.updateImageText(imgFileSeq, imgText);
    }

    /**
     * 이미지 파일명으로 조회
     * @param imgFileNm 이미지 파일명
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 파일 정보
     */
    public ImageFileVO getImageFileByName(String imgFileNm, Long imgBrdSeq) {
        return imageFileMapper.selectImageFileByName(imgFileNm, imgBrdSeq);
    }

    /**
     * 최대 순서 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 최대 순서
     */
    public Integer getMaxOrderByBrdSeq(Long imgBrdSeq) {
        return imageFileMapper.selectMaxOrderByBrdSeq(imgBrdSeq);
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
     * 이미지 파일 중복 확인
     * @param imgFileNm 이미지 파일명
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 중복 여부
     */
    public boolean isImageFileNameDuplicate(String imgFileNm, Long imgBrdSeq) {
        ImageFileVO existingFile = imageFileMapper.selectImageFileByName(imgFileNm, imgBrdSeq);
        return existingFile != null;
    }
} 