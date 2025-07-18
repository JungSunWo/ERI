package com.ERI.demo.dto;

import lombok.Data;
import java.util.List;
import java.util.Set;

/**
 * 사용자 권한 정보 DTO
 */
@Data
public class UserAuthDto {
    
    /**
     * 사용자 ID
     */
    private String userId;
    
    /**
     * 사용자 이름
     */
    private String userName;
    
    /**
     * 부서 코드
     */
    private String deptCd;
    
    /**
     * 직급 코드
     */
    private String rankCd;
    
    /**
     * 권한 그룹 코드 목록
     */
    private List<String> authGroupCodes;
    
    /**
     * 권한 코드 목록
     */
    private List<String> authCodes;
    
    /**
     * 메뉴 권한 목록 (메뉴코드:권한코드)
     */
    private Set<String> menuAuths;
    
    /**
     * 관리자 여부
     */
    private boolean isAdmin;
    
    /**
     * 슈퍼 관리자 여부
     */
    private boolean isSuperAdmin;
    
    /**
     * 특정 메뉴에 대한 권한 확인
     */
    public boolean hasMenuAuth(String menuCode, String authCode) {
        if (isSuperAdmin) return true;
        if (isAdmin && !"SUPER_ADMIN".equals(menuCode)) return true;
        
        String menuAuth = menuCode + ":" + authCode;
        return menuAuths != null && menuAuths.contains(menuAuth);
    }
    
    /**
     * 읽기 권한 확인
     */
    public boolean canRead(String menuCode) {
        return hasMenuAuth(menuCode, "READ");
    }
    
    /**
     * 생성 권한 확인
     */
    public boolean canCreate(String menuCode) {
        return hasMenuAuth(menuCode, "CREATE");
    }
    
    /**
     * 수정 권한 확인
     */
    public boolean canUpdate(String menuCode) {
        return hasMenuAuth(menuCode, "UPDATE");
    }
    
    /**
     * 삭제 권한 확인
     */
    public boolean canDelete(String menuCode) {
        return hasMenuAuth(menuCode, "DELETE");
    }
    
    /**
     * 모든 권한 확인
     */
    public boolean hasAllAuth(String menuCode) {
        return hasMenuAuth(menuCode, "ALL");
    }
    
    // Lombok이 제대로 작동하지 않는 경우를 대비한 수동 getter 메서드들
    public String getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getDeptCd() {
        return deptCd;
    }
    
    public String getRankCd() {
        return rankCd;
    }
    
    public List<String> getAuthGroupCodes() {
        return authGroupCodes;
    }
    
    public List<String> getAuthCodes() {
        return authCodes;
    }
    
    public Set<String> getMenuAuths() {
        return menuAuths;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }
    
    // Lombok이 제대로 작동하지 않는 경우를 대비한 수동 setter 메서드들
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }
    
    public void setRankCd(String rankCd) {
        this.rankCd = rankCd;
    }
    
    public void setAuthGroupCodes(List<String> authGroupCodes) {
        this.authGroupCodes = authGroupCodes;
    }
    
    public void setAuthCodes(List<String> authCodes) {
        this.authCodes = authCodes;
    }
    
    public void setMenuAuths(Set<String> menuAuths) {
        this.menuAuths = menuAuths;
    }
    
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    
    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }
} 