package com.ERI.demo.service;

import com.ERI.demo.dto.UserAuthDto;
import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.mappers.AuthLstMapper;
import com.ERI.demo.mappers.EmpLstMapper;
import com.ERI.demo.vo.AdminLstVO;
import com.ERI.demo.vo.AuthLstVO;
import com.ERI.demo.vo.EmpLstVO;
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
     * 사용자 권한 정보 조회
     */
    public UserAuthDto getUserAuth(String userId) {
        // 직원 정보 조회
        EmpLstVO empInfo = empLstMapper.selectEmpLst(userId);
        if (empInfo == null) {
            return null;
        }
        
        UserAuthDto userAuth = new UserAuthDto();
        userAuth.setUserId(userId);
        userAuth.setUserName(empInfo.getEmpNm());
        
        // 관리자 정보 조회
        AdminLstVO adminInfo = adminLstMapper.selectByAdminId(userId);
        
        List<String> authGroupCodes = new ArrayList<>();
        List<String> authCodes = new ArrayList<>();
        Set<String> menuAuths = new HashSet<>();
        
        if (adminInfo != null && "STS001".equals(adminInfo.getAdminStsCd())) {
            // 관리자인 경우
            authGroupCodes.add("ADMIN");
            authCodes.add("ADMIN");
            
            // 관리자는 모든 메뉴에 대한 모든 권한을 가짐
            // 기본 메뉴 코드들을 하드코딩 (실제로는 메뉴 테이블에서 조회해야 함)
            String[] allMenus = {"MNU_001", "MNU_002", "MNU_003", "MNU_004", "MNU_005", "MNU_006", "MNU_007", "MNU_008", "MNU_009", "MNU_010"};
            for (String menuCode : allMenus) {
                menuAuths.add(menuCode + ":READ");
                menuAuths.add(menuCode + ":CREATE");
                menuAuths.add(menuCode + ":UPDATE");
                menuAuths.add(menuCode + ":DELETE");
            }
            
            // 슈퍼 관리자 여부 확인 (관리자 ID가 SUPER_ADMIN으로 시작하는 경우)
            if (userId.startsWith("SUPER_ADMIN")) {
                authGroupCodes.add("SUPER_ADMIN");
                authCodes.add("SUPER_ADMIN");
                userAuth.setSuperAdmin(true);
            }
            
            userAuth.setAdmin(true);
        } else {
            // 일반 사용자인 경우 - 기본 읽기 권한만 부여
            authGroupCodes.add("USER");
            authCodes.add("USER");
            
            // 기본 메뉴에 대한 읽기 권한만 부여
            // USER 권한이 없을 수 있으므로 기본 메뉴 코드들을 하드코딩
            String[] basicMenus = {"MNU_001", "MNU_002", "MNU_003", "MNU_004", "MNU_005"};
            for (String menuCode : basicMenus) {
                menuAuths.add(menuCode + ":READ");
            }
            
            userAuth.setAdmin(false);
            userAuth.setSuperAdmin(false);
        }
        
        userAuth.setAuthGroupCodes(authGroupCodes);
        userAuth.setAuthCodes(authCodes);
        userAuth.setMenuAuths(menuAuths);
        
        return userAuth;
    }
    
    /**
     * 권한 체크
     */
    public boolean checkAuth(String userId, String menuCode, String authCode) {
        UserAuthDto userAuth = getUserAuth(userId);
        if (userAuth == null) {
            return false;
        }
        
        return userAuth.hasMenuAuth(menuCode, authCode);
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
        UserAuthDto userAuth = getUserAuth(userId);
        return userAuth != null && userAuth.isAdmin();
    }
    
    /**
     * 슈퍼 관리자 권한 체크
     */
    public boolean checkSuperAdminAuth(String userId) {
        UserAuthDto userAuth = getUserAuth(userId);
        return userAuth != null && userAuth.isSuperAdmin();
    }
} 