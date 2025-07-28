package com.ERI.demo.service;

import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.mappers.AuthLstMapper;
import com.ERI.demo.mappers.employee.EmpLstMapper;
import com.ERI.demo.vo.AdminLstVO;
import com.ERI.demo.vo.AuthLstVO;
import com.ERI.demo.vo.employee.EmpLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 권한 관리 서비스
 */
@Service
public class AuthService {
    
    @Autowired
    private EmpLstMapper empLstMapper;
    
    @Autowired
    private AdminLstMapper adminLstMapper;
    
    @Autowired
    private AuthLstMapper authLstMapper;
    
    /**
     * 직원 정보 조회
     */
    public EmpLstVO getEmployeeInfo(String userId) {
        return empLstMapper.findByUserId(userId);
    }
    
    /**
     * 관리자 여부 확인
     */
    public boolean isAdmin(String userId) {
        AdminLstVO adminInfo = adminLstMapper.selectByAdminId(userId);
        return adminInfo != null && "STS001".equals(adminInfo.getAdminStsCd());
    }
    
    /**
     * 슈퍼 관리자 여부 확인
     */
    public boolean isSuperAdmin(String userId) {
        return userId.startsWith("SUPER_ADMIN") && isAdmin(userId);
    }
    
    /**
     * 권한 체크 (관리자는 모든 권한, 일반 사용자는 읽기만)
     */
    public boolean checkAuth(String userId, String menuCode, String authCode) {
        // 관리자는 모든 권한
        if (isAdmin(userId)) {
            return true;
        }
        
        // 일반 사용자는 읽기 권한만
        return "READ".equals(authCode);
    }
    
    /**
     * 읽기 권한 체크
     */
    public boolean checkReadAuth(String userId, String menuCode) {
        return checkAuth(userId, menuCode, "READ");
    }
    
    /**
     * 생성 권한 체크
     */
    public boolean checkCreateAuth(String userId, String menuCode) {
        return checkAuth(userId, menuCode, "CREATE");
    }
    
    /**
     * 수정 권한 체크
     */
    public boolean checkUpdateAuth(String userId, String menuCode) {
        return checkAuth(userId, menuCode, "UPDATE");
    }
    
    /**
     * 삭제 권한 체크
     */
    public boolean checkDeleteAuth(String userId, String menuCode) {
        return checkAuth(userId, menuCode, "DELETE");
    }
    
    /**
     * 관리자 권한 체크
     */
    public boolean checkAdminAuth(String userId) {
        return isAdmin(userId);
    }
    
    /**
     * 슈퍼 관리자 권한 체크
     */
    public boolean checkSuperAdminAuth(String userId) {
        return isSuperAdmin(userId);
    }
} 