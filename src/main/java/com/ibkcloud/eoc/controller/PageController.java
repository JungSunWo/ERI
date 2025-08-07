package com.ibkcloud.eoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HTML 페이지 호출 통합 컨트롤러
 * /templates 폴더의 모든 HTML 페이지들을 관리
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PageController {
    
    // ==================== 관리자 페이지 ====================
    
    /**
     * 메뉴 관리 화면
     */
    @GetMapping("/admin/menuMng")
    public String inqMenuManagementPage() {
        log.info("메뉴 관리 화면 호출");
        return "menu-management";
    }
    
    /**
     * 권한 관리 화면
     */
    @GetMapping("/admin/authMng")
    public String inqAuthManagementPage() {
        log.info("권한 관리 화면 호출");
        return "auth-management";
    }
    
    /**
     * 공통코드 관리 화면
     */
    @GetMapping("/admin/comCdMng")
    public String inqCommonCodeManagementPage() {
        log.info("공통코드 관리 화면 호출");
        return "common-code-management";
    }
    
    /**
     * 공지사항 관리 화면
     */
    @GetMapping("/admin/noticeMng")
    public String inqNoticeManagementPage() {
        log.info("공지사항 관리 화면 호출");
        return "notice-management";
    }
    
    /**
     * 프로그램 관리 화면
     */
    @GetMapping("/admin/programMng")
    public String inqProgramManagementPage() {
        log.info("프로그램 관리 화면 호출");
        return "program-management";
    }
    
    /**
     * 상담 관리 화면
     */
    @GetMapping("/admin/consultationMng")
    public String inqConsultationManagementPage() {
        log.info("상담 관리 화면 호출");
        return "consultation-management";
    }
    
    /**
     * 직원 관리 화면
     */
    @GetMapping("/admin/empMng")
    public String inqEmployeeManagementPage() {
        log.info("직원 관리 화면 호출");
        return "employee-management";
    }
    
    /**
     * 부서 관리 화면
     */
    @GetMapping("/admin/deptMng")
    public String inqDepartmentManagementPage() {
        log.info("부서 관리 화면 호출");
        return "department-management";
    }
    
    /**
     * 사용자 관리 화면
     */
    @GetMapping("/admin/userMng")
    public String inqUserManagementPage() {
        log.info("사용자 관리 화면 호출");
        return "user-management";
    }
    
    /**
     * 통계 관리 화면
     */
    @GetMapping("/admin/statisticsMng")
    public String inqStatisticsManagementPage() {
        log.info("통계 관리 화면 호출");
        return "statistics-management";
    }
    
    /**
     * 시스템 설정 화면
     */
    @GetMapping("/admin/systemConfig")
    public String inqSystemConfigPage() {
        log.info("시스템 설정 화면 호출");
        return "system-config";
    }
    
    /**
     * 로그 관리 화면
     */
    @GetMapping("/admin/logMng")
    public String inqLogManagementPage() {
        log.info("로그 관리 화면 호출");
        return "log-management";
    }
    
    /**
     * 백업 관리 화면
     */
    @GetMapping("/admin/backupMng")
    public String inqBackupManagementPage() {
        log.info("백업 관리 화면 호출");
        return "backup-management";
    }
    
    /**
     * 파일 첨부 테스트 화면
     */
    @GetMapping("/file-attach-test")
    public String inqFileAttachTestPage() {
        log.info("파일 첨부 테스트 화면 호출");
        return "file-attach-test";
    }
    
    /**
     * 파일 관리 화면
     */
    @GetMapping("/admin/fileMng")
    public String inqFileManagementPage() {
        log.info("파일 관리 화면 호출");
        return "file-management";
    }
    
    /**
     * 파일 업로드 데모 화면
     */
    @GetMapping("/file-upload-demo")
    public String inqFileUploadDemoPage() {
        log.info("파일 업로드 데모 화면 호출");
        return "file-upload-demo";
    }
    
    /**
     * 데이터베이스 테스트 화면
     */
    @GetMapping("/db-test")
    public String inqDbTestPage() {
        log.info("데이터베이스 테스트 화면 호출");
        return "db-test";
    }
    
    /**
     * 직원 정보 테스트 화면
     */
    @GetMapping("/emp-info-test")
    public String inqEmpInfoTestPage() {
        log.info("직원 정보 테스트 화면 호출");
        return "emp-info-test";
    }
    
    /**
     * 스케줄러 테스트 화면
     */
    @GetMapping("/scheduler/test")
    public String inqSchedulerTestPage() {
        log.info("스케줄러 테스트 화면 호출");
        return "scheduler-test";
    }
    
    /**
     * 파일 테스트 화면
     */
    @GetMapping("/testFile")
    public String inqTestFilePage() {
        log.info("파일 테스트 화면 호출");
        return "test-file";
    }
    
    /**
     * 직원권익 게시판 화면
     */
    @GetMapping("/emp-rights-board")
    public String inqEmpRightsBoardPage() {
        log.info("직원권익 게시판 화면 호출");
        return "emp-rights-board";
    }
    
    /**
     * 암호화 테스트 화면
     */
    @GetMapping("/encryption-test")
    public String inqEncryptionTestPage() {
        log.info("암호화 테스트 화면 호출");
        return "encryption-test";
    }
} 