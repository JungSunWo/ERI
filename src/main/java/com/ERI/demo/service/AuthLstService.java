package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.AuthLstMapper;
import com.ERI.demo.vo.AuthLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 권한 관리 서비스
 */
@Service
@Transactional
public class AuthLstService {
    
    @Autowired
    private AuthLstMapper authLstMapper;
    
    /**
     * 전체 권한 목록 조회 (페이징)
     */
    public PageResponseDto<AuthLstVO> getAllAuthsWithPaging(PageRequestDto pageRequest) {
        List<AuthLstVO> content = authLstMapper.selectAllWithPaging(pageRequest);
        long totalElements = authLstMapper.selectAllCount(pageRequest);
        return new PageResponseDto<>(content, totalElements, pageRequest.getPage(), pageRequest.getSize());
    }
    
    /**
     * 전체 권한 목록 조회 (페이징 없음)
     */
    public List<AuthLstVO> getAllAuths() {
        return authLstMapper.selectAll();
    }
    
    /**
     * 권한 코드로 권한 조회
     */
    public AuthLstVO getAuthByAuthCd(String authCd) {
        return authLstMapper.selectByAuthCd(authCd);
    }
    
    /**
     * 권한 등록
     */
    public int createAuth(AuthLstVO authLstVO) {
        return authLstMapper.insert(authLstVO);
    }
    
    /**
     * 권한 수정
     */
    public int updateAuth(AuthLstVO authLstVO) {
        return authLstMapper.update(authLstVO);
    }
    
    /**
     * 권한 삭제
     */
    public int deleteAuth(String authCd) {
        return authLstMapper.deleteByAuthCd(authCd);
    }
    
    /**
     * 권한레벨별 권한 목록 조회
     */
    public List<AuthLstVO> getAuthsByAuthLvl(Integer authLvl) {
        return authLstMapper.selectByAuthLvl(authLvl);
    }
    
    /**
     * 권한레벨 이상의 권한 목록 조회
     */
    public List<AuthLstVO> getAuthsByAuthLvlGreaterThan(Integer authLvl) {
        return authLstMapper.selectByAuthLvlGreaterThan(authLvl);
    }
    
    /**
     * 권한 코드 중복 확인
     */
    public boolean isAuthCdExists(String authCd) {
        return authLstMapper.countByAuthCd(authCd) > 0;
    }
    
    /**
     * 권한레벨 유효성 검사
     */
    public boolean isValidAuthLvl(Integer authLvl) {
        return authLvl != null && authLvl >= 1 && authLvl <= 10;
    }
} 