package com.ERI.demo.service;

import com.ERI.demo.mapper.ImageSelectionMapper;
import com.ERI.demo.vo.ImageSelectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 이미지 선택 서비스
 */
@Service
@Transactional
public class ImageSelectionService {

    @Autowired
    private ImageSelectionMapper imageSelectionMapper;

    /**
     * 이미지 선택 목록 조회 (페이징/검색)
     * @param imageSelection 조회 조건
     * @return 이미지 선택 목록
     */
    public List<ImageSelectionVO> getImageSelectionList(ImageSelectionVO imageSelection) {
        return imageSelectionMapper.selectImageSelectionList(imageSelection);
    }

    /**
     * 이미지 선택 목록 개수 조회
     * @param imageSelection 조회 조건
     * @return 이미지 선택 목록 개수
     */
    public int getImageSelectionCount(ImageSelectionVO imageSelection) {
        return imageSelectionMapper.selectImageSelectionCount(imageSelection);
    }

    /**
     * 이미지 선택 상세 조회
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 이미지 선택 정보
     */
    public ImageSelectionVO getImageSelectionBySeq(Long imgSelSeq) {
        return imageSelectionMapper.selectImageSelectionBySeq(imgSelSeq);
    }

    /**
     * 이미지 게시판별 선택 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 선택 목록
     */
    public List<ImageSelectionVO> getImageSelectionByBrdSeq(Long imgBrdSeq) {
        return imageSelectionMapper.selectImageSelectionByBrdSeq(imgBrdSeq);
    }

    /**
     * 직원별 선택 목록 조회
     * @param selEmpId 선택한 직원 ID
     * @return 이미지 선택 목록
     */
    public List<ImageSelectionVO> getImageSelectionByEmpId(String selEmpId) {
        return imageSelectionMapper.selectImageSelectionByEmpId(selEmpId);
    }

    /**
     * 이미지 파일별 선택 목록 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 선택 목록
     */
    public List<ImageSelectionVO> getImageSelectionByFileSeq(Long imgFileSeq) {
        return imageSelectionMapper.selectImageSelectionByFileSeq(imgFileSeq);
    }

    /**
     * 이미지 선택 등록
     * @param imageSelection 이미지 선택 정보
     * @return 등록된 이미지 선택 정보
     */
    public ImageSelectionVO createImageSelection(ImageSelectionVO imageSelection) {
        imageSelectionMapper.insertImageSelection(imageSelection);
        return imageSelection;
    }

    /**
     * 이미지 선택 삭제
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageSelection(Long imgSelSeq) {
        return imageSelectionMapper.deleteImageSelection(imgSelSeq);
    }

    /**
     * 직원별 이미지 선택 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 삭제된 행 수
     */
    public int deleteImageSelectionByEmpId(Long imgBrdSeq, String selEmpId) {
        return imageSelectionMapper.deleteImageSelectionByEmpId(imgBrdSeq, selEmpId);
    }

    /**
     * 이미지 게시판별 선택 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageSelectionByBrdSeq(Long imgBrdSeq) {
        return imageSelectionMapper.deleteImageSelectionByBrdSeq(imgBrdSeq);
    }

    /**
     * 이미지 파일별 선택 삭제
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageSelectionByFileSeq(Long imgFileSeq) {
        return imageSelectionMapper.deleteImageSelectionByFileSeq(imgFileSeq);
    }

    /**
     * 직원의 이미지 선택 여부 확인
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 여부
     */
    public boolean isImageSelected(Long imgBrdSeq, Long imgFileSeq, String selEmpId) {
        int count = imageSelectionMapper.checkImageSelection(imgBrdSeq, imgFileSeq, selEmpId);
        return count > 0;
    }

    /**
     * 직원의 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 개수
     */
    public int getImageSelectionCountByEmpId(Long imgBrdSeq, String selEmpId) {
        return imageSelectionMapper.countImageSelectionByEmpId(imgBrdSeq, selEmpId);
    }

    /**
     * 이미지 파일의 선택 개수 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 선택 개수
     */
    public int getImageSelectionCountByFileSeq(Long imgFileSeq) {
        return imageSelectionMapper.countImageSelectionByFileSeq(imgFileSeq);
    }

    /**
     * 이미지 게시판의 전체 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 전체 선택 개수
     */
    public int getImageSelectionCountByBrdSeq(Long imgBrdSeq) {
        return imageSelectionMapper.countImageSelectionByBrdSeq(imgBrdSeq);
    }

    /**
     * 이미지 선택 토글 (선택/해제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 토글 결과 (true: 선택됨, false: 해제됨)
     */
    public boolean toggleImageSelection(Long imgBrdSeq, Long imgFileSeq, String selEmpId) {
        boolean isSelected = isImageSelected(imgBrdSeq, imgFileSeq, selEmpId);
        
        if (isSelected) {
            // 이미 선택된 경우 해제
            List<ImageSelectionVO> selections = imageSelectionMapper.selectImageSelectionList(
                new ImageSelectionVO(imgBrdSeq, imgFileSeq, selEmpId)
            );
            if (!selections.isEmpty()) {
                imageSelectionMapper.deleteImageSelection(selections.get(0).getImgSelSeq());
            }
            return false;
        } else {
            // 선택되지 않은 경우 선택
            ImageSelectionVO imageSelection = new ImageSelectionVO(imgBrdSeq, imgFileSeq, selEmpId);
            imageSelectionMapper.insertImageSelection(imageSelection);
            return true;
        }
    }

    /**
     * 이미지 선택 가능 여부 확인 (최대 선택 개수 체크)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @param maxSelCnt 최대 선택 개수
     * @return 선택 가능 여부
     */
    public boolean canSelectImage(Long imgBrdSeq, String selEmpId, int maxSelCnt) {
        int currentSelectionCount = getImageSelectionCountByEmpId(imgBrdSeq, selEmpId);
        return currentSelectionCount < maxSelCnt;
    }

    /**
     * 이미지 선택 통계 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 선택 통계 정보
     */
    public int getImageSelectionStatistics(Long imgBrdSeq) {
        return imageSelectionMapper.countImageSelectionByBrdSeq(imgBrdSeq);
    }
} 