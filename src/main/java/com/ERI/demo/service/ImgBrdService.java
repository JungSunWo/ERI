package com.ERI.demo.service;

import com.ERI.demo.mappers.ImgFileLstMapper;
import com.ERI.demo.mappers.ImgSelLstMapper;
import com.ERI.demo.vo.ImgFileLstVO;
import com.ERI.demo.vo.ImgSelLstVO;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 이미지 게시판 Service
 */
@Service
public class ImgBrdService {
    
    @Autowired
    private ImgFileLstMapper imgFileLstMapper;
    
    @Autowired
    private ImgSelLstMapper imgSelLstMapper;
    
    // 파일 업로드 경로
    private static final String UPLOAD_PATH = "uploads/tb_img_file_lst";
    

    
    /**
     * 이미지 파일 목록 조회 (게시판별)
     */
    public List<ImgFileLstVO> getImgFileListByBrdSeq(Long imgBrdSeq) {
        return imgFileLstMapper.selectImgFileListByBrdSeq(imgBrdSeq);
    }
    
    /**
     * 이미지 파일 목록 조회 (사용자 선택 여부 포함)
     */
    public List<ImgFileLstVO> getImgFileListWithSelection(Long imgBrdSeq, String selEmpId) {
        return imgFileLstMapper.selectImgFileListWithSelection(imgBrdSeq, selEmpId);
    }
    
    /**
     * 이미지 파일 업로드 (텍스트 포함)
     */
    @Transactional
    public boolean uploadImageFiles(Long imgBrdSeq, List<MultipartFile> files, List<String> texts, String eriEmpId) {
        try {
            // 업로드 디렉토리 생성
            createUploadDirectory();
            
            int order = 1;
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String text = (texts != null && i < texts.size()) ? texts.get(i) : "";
                
                if (!file.isEmpty()) {
                    // 파일 정보 추출
                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = getFileExtension(originalFilename);
                    String savedFilename = generateUniqueFilename(fileExtension);
                    
                    // 파일 저장
                    String filePath = saveFile(file, savedFilename);
                    
                    // DB에 파일 정보 저장
                    ImgFileLstVO imgFileLstVO = new ImgFileLstVO(
                        imgBrdSeq,
                        originalFilename,
                        filePath,
                        file.getSize(),
                        fileExtension,
                        text,
                        order++,
                        eriEmpId
                    );
                    // MyBatis 매핑을 위해 imgFileNm도 설정
                    imgFileLstVO.setImgFileNm(originalFilename);
                    // 등록자와 수정자를 동일하게 설정
                    imgFileLstVO.setEriEmpId(eriEmpId);
                    
                    imgFileLstMapper.insertImgFile(imgFileLstVO);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 이미지 파일 삭제
     */
    @Transactional
    public boolean deleteImgFile(Long imgFileSeq, String eriEmpId) {
        System.out.println("=== ImgBrdService.deleteImgFile ===");
        System.out.println("imgFileSeq: " + imgFileSeq);
        System.out.println("eriEmpId: " + eriEmpId);
        
        try {
            // 파일 정보 조회
            ImgFileLstVO imgFile = imgFileLstMapper.selectImgFileBySeq(imgFileSeq);
            System.out.println("조회된 이미지 파일: " + (imgFile != null ? imgFile.getImgFileNm() : "null"));
            
            if (imgFile != null) {
                // 실제 파일 삭제
                deletePhysicalFile(imgFile.getImgFilePath());
                System.out.println("물리적 파일 삭제 완료: " + imgFile.getImgFilePath());
                
                // DB에서 논리 삭제
                int result = imgFileLstMapper.deleteImgFile(imgFileSeq, eriEmpId);
                System.out.println("DB 논리 삭제 결과: " + result + " rows affected");
                
                return result > 0;
            } else {
                System.out.println("이미지 파일을 찾을 수 없음: " + imgFileSeq);
                return false;
            }
        } catch (Exception e) {
            System.out.println("이미지 파일 삭제 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 이미지 선택/해제
     */
    @Transactional
    public boolean toggleImageSelection(Long imgBrdSeq, Long imgFileSeq, String selEmpId) {
        // 토글 처리 (INSERT 또는 DELETE)
        return imgSelLstMapper.toggleImageSelection(imgBrdSeq, imgFileSeq, selEmpId) > 0;
    }
    
    /**
     * 사용자가 선택한 이미지 목록 조회
     */
    public List<ImgSelLstVO> getSelectedImageList(Long imgBrdSeq, String selEmpId) {
        return imgSelLstMapper.selectSelectedImageList(imgBrdSeq, selEmpId);
    }
    
    /**
     * 사용자의 모든 이미지 선택 삭제
     */
    @Transactional
    public boolean clearAllSelections(Long imgBrdSeq, String selEmpId) {
        return imgSelLstMapper.clearAllSelections(imgBrdSeq, selEmpId) > 0;
    }
    
    /**
     * 이미지 게시판별 선택 통계 조회
     */
    public List<ImgSelLstVO> getImageSelectionStats(Long imgBrdSeq) {
        return imgSelLstMapper.selectImageSelectionStats(imgBrdSeq);
    }
    
    /**
     * 이미지 파일 조회 (시퀀스로)
     */
    public ImgFileLstVO getImgFileBySeq(Long imgFileSeq) {
        return imgFileLstMapper.selectImgFileBySeq(imgFileSeq);
    }
    
    /**
     * 이미지 설명 수정
     */
    @Transactional
    public boolean updateImageText(Long imgFileSeq, String imgText, String eriEmpId) {
        try {
            return imgFileLstMapper.updateImageText(imgFileSeq, imgText, eriEmpId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 업로드 디렉토리 생성
     */
    private void createUploadDirectory() throws IOException {
        Path uploadPath = Paths.get(UPLOAD_PATH);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }
    
    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }
    
    /**
     * 고유한 파일명 생성
     */
    private String generateUniqueFilename(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return timestamp + "_" + uuid + "." + extension;
    }
    
    /**
     * 파일 저장
     */
    private String saveFile(MultipartFile file, String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_PATH, filename);
        Files.copy(file.getInputStream(), filePath);
        return filename; // 파일명만 반환
    }
    
    /**
     * 실제 파일 삭제
     */
    private void deletePhysicalFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 