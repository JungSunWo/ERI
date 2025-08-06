package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.ExpertConsultationAppMapper;
import com.ERI.demo.vo.ExpertConsultationAppVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 전문가 상담 Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExpertConsultationService {

    private final ExpertConsultationAppMapper expertConsultationAppMapper;

    /**
     * 전문가 상담 신청 목록 조회 (페이징)
     */
    public PageResponseDto<ExpertConsultationAppVO> getExpertConsultationAppList(PageRequestDto pageRequest, ExpertConsultationAppVO searchCondition) {
        int offset = (pageRequest.getPage() - 1) * pageRequest.getSize();
        
        List<ExpertConsultationAppVO> content = expertConsultationAppMapper.selectExpertConsultationAppList(
            offset, pageRequest.getSize(), searchCondition, pageRequest.getSortBy(), pageRequest.getSortDirection()
        );
        
        int totalElements = expertConsultationAppMapper.selectExpertConsultationAppCount(searchCondition);
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        
        return PageResponseDto.<ExpertConsultationAppVO>builder()
            .content(content)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(pageRequest.getPage())
            .size(pageRequest.getSize())
            .hasNext(pageRequest.getPage() < totalPages)
            .hasPrevious(pageRequest.getPage() > 1)
            .startPage(Math.max(1, pageRequest.getPage() - 2))
            .endPage(Math.min(totalPages, pageRequest.getPage() + 2))
            .build();
    }

    /**
     * 전문가 상담 신청 상세 조회
     */
    public ExpertConsultationAppVO getExpertConsultationAppBySeq(Long appSeq) {
        return expertConsultationAppMapper.selectExpertConsultationAppBySeq(appSeq);
    }

    /**
     * 전문가 상담 신청 등록
     */
    @Transactional
    public ExpertConsultationAppVO createExpertConsultationApp(ExpertConsultationAppVO expertConsultationApp, String empId) {
        // 유효성 검사
        validateExpertConsultationApp(expertConsultationApp);
        
        // 기본값 설정
        expertConsultationApp.setAppDt(LocalDate.now());
        expertConsultationApp.setAppTm(LocalTime.now());
        expertConsultationApp.setEmpId(empId);
        expertConsultationApp.setRegEmpId(empId);
        
        // 전문가 상담 신청 등록
        expertConsultationAppMapper.insertExpertConsultationApp(expertConsultationApp);
        
        return expertConsultationApp;
    }

    /**
     * 전문가 상담 신청 수정
     */
    @Transactional
    public ExpertConsultationAppVO updateExpertConsultationApp(ExpertConsultationAppVO expertConsultationApp, String empId) {
        // 기존 전문가 상담 신청 조회
        ExpertConsultationAppVO existingApp = expertConsultationAppMapper.selectExpertConsultationAppBySeq(expertConsultationApp.getAppSeq());
        if (existingApp == null) {
            return null;
        }
        
        // 유효성 검사
        validateExpertConsultationApp(expertConsultationApp);
        
        // 수정자 정보 설정
        expertConsultationApp.setUpdEmpId(empId);
        
        // 전문가 상담 신청 수정
        expertConsultationAppMapper.updateExpertConsultationApp(expertConsultationApp);
        
        return expertConsultationApp;
    }

    /**
     * 전문가 상담 신청 삭제
     */
    @Transactional
    public boolean deleteExpertConsultationApp(Long appSeq, String empId) {
        return expertConsultationAppMapper.deleteExpertConsultationApp(appSeq, empId) > 0;
    }

    /**
     * 내 전문가 상담 신청 목록 조회
     */
    public PageResponseDto<ExpertConsultationAppVO> getMyExpertConsultationAppList(int page, int size, String empId) {
        int offset = (page - 1) * size;
        
        List<ExpertConsultationAppVO> content = expertConsultationAppMapper.selectMyExpertConsultationAppList(empId, offset, size);
        int totalElements = expertConsultationAppMapper.selectMyExpertConsultationAppCount(empId);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return PageResponseDto.<ExpertConsultationAppVO>builder()
            .content(content)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(page)
            .size(size)
            .hasNext(page < totalPages)
            .hasPrevious(page > 1)
            .startPage(Math.max(1, page - 2))
            .endPage(Math.min(totalPages, page + 2))
            .build();
    }

    /**
     * 전문가 상담 신청 승인/반려
     */
    @Transactional
    public ExpertConsultationAppVO updateApprovalStatus(Long appSeq, String aprvStsCd, String aprvEmpId, String rejRsn) {
        ExpertConsultationAppVO expertConsultationApp = new ExpertConsultationAppVO();
        expertConsultationApp.setAppSeq(appSeq);
        expertConsultationApp.setAprvStsCd(aprvStsCd);
        expertConsultationApp.setAprvEmpId(aprvEmpId);
        expertConsultationApp.setAprvDt(LocalDate.now());
        expertConsultationApp.setAprvTm(LocalTime.now());
        
        if ("REJECTED".equals(aprvStsCd)) {
            expertConsultationApp.setRejRsn(rejRsn);
        }
        
        expertConsultationAppMapper.updateExpertConsultationApp(expertConsultationApp);
        
        return expertConsultationApp;
    }

    /**
     * 전문가 상담 신청 유효성 검사
     */
    private void validateExpertConsultationApp(ExpertConsultationAppVO expertConsultationApp) {
        if (expertConsultationApp.getCnslDt() == null) {
            throw new IllegalArgumentException("상담일자는 필수입니다.");
        }
        
        if (expertConsultationApp.getCnslTm() == null) {
            throw new IllegalArgumentException("상담시간은 필수입니다.");
        }
        
        if (expertConsultationApp.getCnslrEmpId() == null || expertConsultationApp.getCnslrEmpId().trim().isEmpty()) {
            throw new IllegalArgumentException("상담사는 필수입니다.");
        }
        
        if (expertConsultationApp.getCnslTyCd() == null || expertConsultationApp.getCnslTyCd().trim().isEmpty()) {
            throw new IllegalArgumentException("상담유형은 필수입니다.");
        }
        
        if (expertConsultationApp.getCnslCntn() == null || expertConsultationApp.getCnslCntn().trim().isEmpty()) {
            throw new IllegalArgumentException("상담내용은 필수입니다.");
        }
        
        if (expertConsultationApp.getAnonymousYn() == null || expertConsultationApp.getAnonymousYn().trim().isEmpty()) {
            expertConsultationApp.setAnonymousYn("N");
        }
    }
} 