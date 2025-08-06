package com.ERI.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HTML 페이지 호출 통합 컨트롤러
 * /templates 폴더의 모든 HTML 페이지들을 관리
 */
@Controller
public class PageController {
    
    // ==================== 관리자 페이지 ====================
    
    /**
     * 메뉴 관리 화면
     */
    @GetMapping("/admin/menuMng")
    public String menuManagement() {
        return "menu-management";
    }
    
    /**
     * 권한 관리 화면
     */
    @GetMapping("/admin/authMng")
    public String authManagement() {
        return "auth-management";
    }
    
    /**
     * 공통코드 관리 화면
     */
    @GetMapping("/admin/comCdMng")
    public String commonCodeManagement() {
        return "common-code-management";
    }
    
    /**
     * 공지사항 관리 화면
     */
    @GetMapping("/admin/noticeMng")
    public String noticeManagement() {
        return "notice-management";
    }
    
    /**
     * 프로그램 관리 화면
     */
    @GetMapping("/admin/programMng")
    public String programManagement() {
        return "program-management";
    }
    
    /**
     * 상담 관리 화면
     */
    @GetMapping("/admin/consultationMng")
    public String consultationManagement() {
        return "consultation-management";
    }
    
    /**
     * 직원 관리 화면
     */
    @GetMapping("/admin/empMng")
    public String employeeManagement() {
        return "employee-management";
    }
    
    /**
     * 부서 관리 화면
     */
    @GetMapping("/admin/deptMng")
    public String departmentManagement() {
        return "department-management";
    }
    
    /**
     * 사용자 관리 화면
     */
    @GetMapping("/admin/userMng")
    public String userManagement() {
        return "user-management";
    }
    
    /**
     * 통계 관리 화면
     */
    @GetMapping("/admin/statisticsMng")
    public String statisticsManagement() {
        return "statistics-management";
    }
    
    /**
     * 시스템 설정 화면
     */
    @GetMapping("/admin/systemConfig")
    public String systemConfig() {
        return "system-config";
    }
    
    /**
     * 로그 관리 화면
     */
    @GetMapping("/admin/logMng")
    public String logManagement() {
        return "log-management";
    }
    
    /**
     * 백업 관리 화면
     */
    @GetMapping("/admin/backupMng")
    public String backupManagement() {
        return "backup-management";
    }
    
    // ==================== 첨부파일 관련 페이지 ====================
    
    /**
     * 파일 첨부 테스트 페이지
     */
    @GetMapping("/file-attach-test")
    public String fileAttachTestPage() {
        return "file-attach-test";
    }
    
    /**
     * 파일 관리 페이지
     */
    @GetMapping("/admin/fileMng")
    public String fileManagement() {
        return "file-management";
    }
    
    /**
     * 파일 업로드 데모 페이지
     */
    @GetMapping("/file-upload-demo")
    public String fileUploadDemo() {
        return "file-upload-demo";
    }
    
    // ==================== 테스트 페이지 ====================
    
    /**
     * 데이터베이스 테스트 페이지
     */
    @GetMapping("/db-test")
    public String dbTestPage() {
        return "db-test";
    }
    
    /**
     * 직원정보 조회 테스트 페이지
     */
    @GetMapping("/emp-info-test")
    public String empInfoTestPage() {
        return "emp-info-test";
    }
    
    /**
     * 스케줄링 테스트 페이지
     */
    @GetMapping("/scheduler/test")
    public String schedulerTestPage() {
        return "scheduler-test";
    }
    
    /**
     * 테스트 파일 페이지
     */
    @GetMapping("/testFile")
    public String testFilePage() {
        return "testFile";
    }
    
   
    
    /**
     * 직원권익게시판 페이지
     */
    @GetMapping("/emp-rights-board")
    public String empRightsBoardPage() {
        return "emp-rights-board";
    }
    
    /**
     * 암호화 테스트 페이지
     */
    @GetMapping("/encryption-test")
    public String encryptionTestPage() {
        return "encryption-test";
    }
} 