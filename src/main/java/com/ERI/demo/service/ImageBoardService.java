package com.ERI.demo.service;

import com.ERI.demo.mapper.ImageBoardMapper;
import com.ERI.demo.mapper.ImageFileMapper;
import com.ERI.demo.mapper.ImageSelectionMapper;
import com.ERI.demo.vo.ImageBoardVO;
import com.ERI.demo.vo.ImageFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 이미지 게시판 서비스
 */
@Service
@Transactional
public class ImageBoardService {

    @Autowired
    private ImageBoardMapper imageBoardMapper;

    @Autowired
    private ImageFileMapper imageFileMapper;

    @Autowired
    private ImageSelectionMapper imageSelectionMapper;

    /**
     * 이미지 게시판 목록 조회 (페이징/검색)
     * @param imageBoard 조회 조건
     * @return 이미지 게시판 목록
     */
    public List<ImageBoardVO> getImageBoardList(ImageBoardVO imageBoard) {
        return imageBoardMapper.selectImageBoardList(imageBoard);
    }

    /**
     * 이미지 게시판 목록 개수 조회
     * @param imageBoard 조회 조건
     * @return 이미지 게시판 목록 개수
     */
    public int getImageBoardCount(ImageBoardVO imageBoard) {
        return imageBoardMapper.selectImageBoardCount(imageBoard);
    }

    /**
     * 이미지 게시판 상세 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 게시판 정보
     */
    public ImageBoardVO getImageBoardBySeq(Long imgBrdSeq) {
        ImageBoardVO imageBoard = imageBoardMapper.selectImageBoardBySeq(imgBrdSeq);
        if (imageBoard != null) {
            // 이미지 파일 목록 조회
            List<ImageFileVO> imageFiles = imageFileMapper.selectImageFileByBrdSeq(imgBrdSeq);
            imageBoard.setImageFiles(imageFiles);
            imageBoard.setImageFileCount(imageFiles.size());
        }
        return imageBoard;
    }

    /**
     * 이미지 게시판 등록
     * @param imageBoard 이미지 게시판 정보
     * @return 등록된 이미지 게시판 정보
     */
    public ImageBoardVO createImageBoard(ImageBoardVO imageBoard) {
        imageBoardMapper.insertImageBoard(imageBoard);
        return imageBoard;
    }

    /**
     * 이미지 게시판 수정
     * @param imageBoard 이미지 게시판 정보
     * @return 수정된 이미지 게시판 정보
     */
    public ImageBoardVO updateImageBoard(ImageBoardVO imageBoard) {
        imageBoardMapper.updateImageBoard(imageBoard);
        return imageBoard;
    }

    /**
     * 이미지 게시판 삭제 (논리 삭제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제된 행 수
     */
    public int deleteImageBoard(Long imgBrdSeq) {
        // 이미지 파일들도 논리 삭제
        imageFileMapper.deleteImageFileByBrdSeq(imgBrdSeq);
        // 이미지 선택들도 삭제
        imageSelectionMapper.deleteImageSelectionByBrdSeq(imgBrdSeq);
        // 이미지 게시판 삭제
        return imageBoardMapper.deleteImageBoard(imgBrdSeq);
    }

    /**
     * 이미지 게시판 제목으로 조회
     * @param imgBrdTitl 이미지 게시판 제목
     * @return 이미지 게시판 정보
     */
    public ImageBoardVO getImageBoardByTitle(String imgBrdTitl) {
        return imageBoardMapper.selectImageBoardByTitle(imgBrdTitl);
    }

    /**
     * 최근 등록된 이미지 게시판 목록 조회
     * @param limit 조회 개수 제한
     * @return 이미지 게시판 목록
     */
    public List<ImageBoardVO> getRecentImageBoardList(int limit) {
        return imageBoardMapper.selectRecentImageBoardList(limit);
    }

    /**
     * 이미지 게시판과 이미지 파일들을 함께 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 게시판 정보 (이미지 파일 목록 포함)
     */
    public ImageBoardVO getImageBoardWithFiles(Long imgBrdSeq) {
        ImageBoardVO imageBoard = getImageBoardBySeq(imgBrdSeq);
        if (imageBoard != null) {
            // 각 이미지 파일의 선택 개수 조회
            for (ImageFileVO imageFile : imageBoard.getImageFiles()) {
                int selectionCount = imageSelectionMapper.countImageSelectionByFileSeq(imageFile.getImgFileSeq());
                imageFile.setSelectionCount(selectionCount);
            }
        }
        return imageBoard;
    }

    /**
     * 이미지 게시판의 선택 통계 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 선택 통계 정보
     */
    public int getImageBoardSelectionCount(Long imgBrdSeq) {
        return imageSelectionMapper.countImageSelectionByBrdSeq(imgBrdSeq);
    }

    /**
     * 이미지 게시판 제목 중복 확인
     * @param imgBrdTitl 이미지 게시판 제목
     * @return 중복 여부
     */
    public boolean isImageBoardTitleDuplicate(String imgBrdTitl) {
        ImageBoardVO existingBoard = imageBoardMapper.selectImageBoardByTitle(imgBrdTitl);
        return existingBoard != null;
    }
} 