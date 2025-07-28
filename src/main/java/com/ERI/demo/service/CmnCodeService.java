package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.CmnGrpCdMapper;
import com.ERI.demo.mappers.CmnDtlCdMapper;
import com.ERI.demo.vo.CmnGrpCdVO;
import com.ERI.demo.vo.CmnDtlCdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공통 코드 관리 서비스
 */
@Service
@Transactional
public class CmnCodeService {
    
    @Autowired
    private CmnGrpCdMapper cmnGrpCdMapper;
    
    @Autowired
    private CmnDtlCdMapper cmnDtlCdMapper;
    
    // ==================== 공통 그룹 코드 관리 ====================
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징)
     */
    public PageResponseDto<CmnGrpCdVO> getAllGroupCodesWithPaging(PageRequestDto pageRequest) {
        List<CmnGrpCdVO> content = cmnGrpCdMapper.selectAllWithPaging(pageRequest);
        long totalElements = cmnGrpCdMapper.selectAllCount(pageRequest);
        return new PageResponseDto<CmnGrpCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징 없음)
     */
    public List<CmnGrpCdVO> getAllGroupCodes() {
        return cmnGrpCdMapper.selectAll();
    }
    
    /**
     * 그룹 코드로 공통 그룹 코드 조회
     */
    public CmnGrpCdVO getGroupCodeByGrpCd(String grpCd) {
        return cmnGrpCdMapper.selectByGrpCd(grpCd);
    }
    
    /**
     * 공통 그룹 코드 등록
     */
    public int createGroupCode(CmnGrpCdVO cmnGrpCdVO) {
        return cmnGrpCdMapper.insert(cmnGrpCdVO);
    }
    
    /**
     * 공통 그룹 코드 수정
     */
    public int updateGroupCode(CmnGrpCdVO cmnGrpCdVO) {
        return cmnGrpCdMapper.update(cmnGrpCdVO);
    }
    
    /**
     * 공통 그룹 코드 삭제
     */
    public int deleteGroupCode(String grpCd) {
        // 그룹 코드 삭제 시 관련된 상세 코드들도 함께 삭제
        cmnDtlCdMapper.deleteByGrpCd(grpCd);
        return cmnGrpCdMapper.deleteByGrpCd(grpCd);
    }
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징)
     */
    public PageResponseDto<CmnGrpCdVO> getGroupCodesByUseYnWithPaging(String useYn, PageRequestDto pageRequest) {
        List<CmnGrpCdVO> content = cmnGrpCdMapper.selectByUseYnWithPaging(useYn, pageRequest);
        long totalElements = cmnGrpCdMapper.selectByUseYnCount(useYn, pageRequest);
        return new PageResponseDto<CmnGrpCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징 없음)
     */
    public List<CmnGrpCdVO> getGroupCodesByUseYn(String useYn) {
        return cmnGrpCdMapper.selectByUseYn(useYn);
    }
    
    // ==================== 공통 상세 코드 관리 ====================
    
    /**
     * 전체 공통 상세 코드 조회 (페이징)
     */
    public PageResponseDto<CmnDtlCdVO> getAllDetailCodesWithPaging(PageRequestDto pageRequest) {
        List<CmnDtlCdVO> content = cmnDtlCdMapper.selectAllWithPaging(pageRequest);
        long totalElements = cmnDtlCdMapper.selectAllCount(pageRequest);
        return new PageResponseDto<CmnDtlCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 전체 공통 상세 코드 조회 (페이징 없음)
     */
    public List<CmnDtlCdVO> getAllDetailCodes() {
        return cmnDtlCdMapper.selectAll();
    }
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징)
     */
    public PageResponseDto<CmnDtlCdVO> getDetailCodesByGrpCdWithPaging(String grpCd, PageRequestDto pageRequest) {
        List<CmnDtlCdVO> content = cmnDtlCdMapper.selectByGrpCdWithPaging(grpCd, pageRequest);
        long totalElements = cmnDtlCdMapper.selectByGrpCdCount(grpCd, pageRequest);
        return new PageResponseDto<CmnDtlCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징 없음)
     */
    public List<CmnDtlCdVO> getDetailCodesByGrpCd(String grpCd) {
        return cmnDtlCdMapper.selectByGrpCd(grpCd);
    }
    
    /**
     * 그룹 코드와 상세 코드로 공통 상세 코드 조회
     */
    public CmnDtlCdVO getDetailCodeByGrpCdAndDtlCd(String grpCd, String dtlCd) {
        return cmnDtlCdMapper.selectByGrpCdAndDtlCd(grpCd, dtlCd);
    }
    
    /**
     * 공통 상세 코드 등록
     */
    public int createDetailCode(CmnDtlCdVO cmnDtlCdVO) {
        return cmnDtlCdMapper.insert(cmnDtlCdVO);
    }
    
    /**
     * 공통 상세 코드 수정
     */
    public int updateDetailCode(CmnDtlCdVO cmnDtlCdVO) {
        return cmnDtlCdMapper.update(cmnDtlCdVO);
    }
    
    /**
     * 공통 상세 코드 삭제
     */
    public int deleteDetailCode(String grpCd, String dtlCd) {
        return cmnDtlCdMapper.deleteByGrpCdAndDtlCd(grpCd, dtlCd);
    }
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징)
     */
    public PageResponseDto<CmnDtlCdVO> getDetailCodesByUseYnWithPaging(String useYn, PageRequestDto pageRequest) {
        List<CmnDtlCdVO> content = cmnDtlCdMapper.selectByUseYnWithPaging(useYn, pageRequest);
        long totalElements = cmnDtlCdMapper.selectByUseYnCount(useYn, pageRequest);
        return new PageResponseDto<CmnDtlCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징 없음)
     */
    public List<CmnDtlCdVO> getDetailCodesByUseYn(String useYn) {
        return cmnDtlCdMapper.selectByUseYn(useYn);
    }
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징)
     */
    public PageResponseDto<CmnDtlCdVO> getDetailCodesByGrpCdAndUseYnWithPaging(String grpCd, String useYn, PageRequestDto pageRequest) {
        List<CmnDtlCdVO> content = cmnDtlCdMapper.selectByGrpCdAndUseYnWithPaging(grpCd, useYn, pageRequest);
        long totalElements = cmnDtlCdMapper.selectByGrpCdAndUseYnCount(grpCd, useYn, pageRequest);
        return new PageResponseDto<CmnDtlCdVO>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징 없음)
     */
    public List<CmnDtlCdVO> getDetailCodesByGrpCdAndUseYn(String grpCd, String useYn) {
        return cmnDtlCdMapper.selectByGrpCdAndUseYn(grpCd, useYn);
    }
    

} 