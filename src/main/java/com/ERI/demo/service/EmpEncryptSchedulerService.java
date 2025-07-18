package com.ERI.demo.service;

import com.ERI.demo.mappers.encryption.EmpEncryptMapper;
import com.ERI.demo.mappers.EmpLstMapper;
import com.ERI.demo.vo.EmpEncryptVO;
import com.ERI.demo.vo.EmpLstVO;
import com.ERI.demo.vo.EncryptHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 직원 정보 암호화 스케줄링 서비스 (암호화 데이터베이스 전용)
 * Cron 표현식을 사용하여 정기적으로 실행
 * eri_enc_db 데이터베이스에 암호화 정보 저장
 */
@Service
public class EmpEncryptSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(EmpEncryptSchedulerService.class);

    @Autowired
    private EmpEncryptMapper empEncryptMapper;

    @Autowired
    private EmpLstService empLstService;

    @Autowired
    private EmpLstMapper empLstMapper;

    @Autowired
    private EncryptHistoryService encryptHistoryService;

    @Value("${eri.hash.salt}")
    private String hashSalt;

    // 해시 키 (실제 운영에서는 외부 설정으로 관리)
    private static final String ALGORITHM = "SHA-256";
    private static final String KEY_ID = "SCHEDULER_KEY_001";
    private static final String OPERATOR_ID = "SCHEDULER_SYSTEM";

    /**
     * 일일 직원 정보 SHA256 해시 배치 (매일 새벽 2시 실행)
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void dailyEmpEncryptBatch() {
        logger.info("=== 일일 직원 정보 SHA256 해시 배치 시작 ===");
        logger.info("현재 솔트키: {}", hashSalt);
        
        try {
            long startTime = System.currentTimeMillis();
            
            // empInfo.txt 파일 읽기
            List<EmpLstVO> empList = readEmpInfoFile();
            logger.info("읽어온 직원 수: {}", empList.size());
            
            if (empList.isEmpty()) {
                logger.warn("empInfo.txt 파일에서 읽어온 직원 데이터가 없습니다.");
                return;
            }
            
            int successCount = 0;
            int errorCount = 0;
            int newEncryptCount = 0;
            int reEncryptCount = 0;
            int updateCount = 0;
            int emailUpdateCount = 0;
            int noChangeCount = 0;
            
            // 각 직원 정보 SHA256 해시 처리
            for (EmpLstVO emp : empList) {
                try {
                    String processType = processEmpEncryption(emp);
                    successCount++;
                    
                    // 처리 타입별 카운트
                    switch (processType) {
                        case "NEW":
                            newEncryptCount++;
                            logger.debug("신규 암호화 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "RE_ENCRYPT":
                            reEncryptCount++;
                            logger.info("솔트키 변경 재암호화 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "UPDATE":
                            updateCount++;
                            logger.debug("암호화 업데이트 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "EMAIL_UPDATE":
                            emailUpdateCount++;
                            logger.info("이메일 업데이트 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "NO_CHANGE":
                            noChangeCount++;
                            logger.debug("변경 없음: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        default:
                            logger.warn("알 수 없는 처리 타입: {} - 직원번호: {}", processType, emp.getEmpId());
                            break;
                    }
                    
                } catch (Exception e) {
                    errorCount++;
                    logger.error("직원 정보 SHA256 해시 실패 - 직원번호: {}, 오류: {}", emp.getEmpId(), e.getMessage(), e);
                }
            }
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            logger.info("=== 일일 직원 정보 SHA256 해시 배치 완료 ===");
            logger.info("처리 시간: {}ms", processingTime);
            logger.info("성공: {}건, 실패: {}건", successCount, errorCount);
            logger.info("신규 암호화: {}건, 솔트키 변경 재암호화: {}건, 업데이트: {}건, 이메일 업데이트: {}건, 변경 없음: {}건", 
                       newEncryptCount, reEncryptCount, updateCount, emailUpdateCount, noChangeCount);
            logger.info("TB_EMP_ENCRYPT 및 TB_EMP_LST 테이블에 직원정보 저장 완료");
            
            // 솔트키 변경 이력 통계 출력
            if (reEncryptCount > 0) {
                logger.info("=== 솔트키 변경 이력 통계 ===");
                logger.info("솔트키 변경으로 인한 재암호화된 직원 수: {}건", reEncryptCount);
                logger.info("이력은 tb_encrypt_history 테이블에 'RE_ENCRYPT' 타입으로 저장되었습니다.");
            }
            
        } catch (Exception e) {
            logger.error("일일 직원 정보 SHA256 해시 배치 실행 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * 매주 일요일 오전 3시에 실행되는 주간 정리 배치
     */
    @Scheduled(cron = "0 0 3 ? * SUN")
    public void weeklyCleanupBatch() {
        logger.info("=== 주간 정리 배치 시작 ===");
        
        try {
            // 주간 정리 작업 (현재는 로그만 출력)
            logger.info("주간 정리 작업 완료");
            
            logger.info("=== 주간 정리 배치 완료 ===");
            
        } catch (Exception e) {
            logger.error("주간 정리 배치 실행 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * 매월 1일 오전 4시에 실행되는 월간 통계 배치
     */
    @Scheduled(cron = "0 0 4 1 * ?")
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    public void monthlyStatisticsBatch() {
        logger.info("=== 월간 통계 배치 시작 ===");
        
        try {
            // 전체 암호화된 직원 수 조회
            int totalEncryptedCount = empEncryptMapper.selectTotalCount();
            
            // 이번 달 새로 암호화된 직원 수 조회
            int monthlyNewCount = empEncryptMapper.selectMonthlyNewCount();
            
            logger.info("전체 암호화된 직원 수: {}", totalEncryptedCount);
            logger.info("이번 달 새로 암호화된 직원 수: {}", monthlyNewCount);
            
            logger.info("=== 월간 통계 배치 완료 ===");
            
        } catch (Exception e) {
            logger.error("월간 통계 배치 실행 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    /**
     * 데이터베이스 연결 테스트
     */
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    public void testDatabaseConnection() {
        try {
            logger.info("=== 암호화 데이터베이스 연결 테스트 시작 ===");
            
            // 1. 기본 연결 테스트
            logger.info("1. 기본 연결 테스트 시작...");
            int count = empEncryptMapper.selectTotalCount();
            logger.info("TB_EMP_ENCRYPT 테이블 레코드 수: {}", count);
            
            // 2. 테스트 데이터 삽입 테스트
            logger.info("2. 테스트 데이터 삽입 테스트 시작...");
            EmpEncryptVO testData = new EmpEncryptVO();
            testData.setOrigEmpNo("TEST_001");
            testData.setEncryptEmpNo("TEST_HASH_001");
            testData.setOrigEmpNm("테스트직원");
            testData.setRandomEmpNm("김테스트");
            testData.setOrigEmpEmail("test@company.com");
            testData.setEncryptAlgorithm("SHA-256");
            testData.setEncryptKeyId("TEST_KEY");
            testData.setEncryptIv("TEST_SALT");
            
            int insertResult = empEncryptMapper.insert(testData);
            logger.info("테스트 데이터 삽입 결과: {}", insertResult);
            
            // 3. 삽입된 데이터 조회 테스트
            logger.info("3. 삽입된 데이터 조회 테스트 시작...");
            EmpEncryptVO retrievedData = empEncryptMapper.selectByEmpNo("TEST_001");
            if (retrievedData != null) {
                logger.info("테스트 데이터 조회 성공: empSeq={}, origEmpNo={}", 
                           retrievedData.getEmpSeq(), retrievedData.getOrigEmpNo());
            } else {
                logger.warn("테스트 데이터 조회 실패");
            }
            
            // 4. 테스트 데이터 삭제
            logger.info("4. 테스트 데이터 정리 시작...");
            if (retrievedData != null) {
                int deleteResult = empEncryptMapper.deleteEmpEncryptBySeq(retrievedData.getEmpSeq());
                logger.info("테스트 데이터 삭제 결과: {}", deleteResult);
            }
            
            logger.info("=== 암호화 데이터베이스 연결 테스트 완료 ===");
            
        } catch (Exception e) {
            logger.error("암호화 데이터베이스 연결 테스트 실패: {}", e.getMessage(), e);
            throw new RuntimeException("데이터베이스 연결 테스트 실패", e);
        }
    }

    /**
     * 수동 실행용 메서드 (테스트용)
     */
    public void manualEmpEncryptBatch() {
        logger.info("=== 수동 직원 정보 SHA256 해시 배치 시작 ===");
        logger.info("현재 솔트키: {}", hashSalt);
        
        try {
            long startTime = System.currentTimeMillis();
            
            // empInfo.txt 파일 읽기
            List<EmpLstVO> empList = readEmpInfoFile();
            logger.info("읽어온 직원 수: {}", empList.size());
            
            if (empList.isEmpty()) {
                logger.warn("empInfo.txt 파일에서 읽어온 직원 데이터가 없습니다.");
                return;
            }
            
            int successCount = 0;
            int errorCount = 0;
            int newEncryptCount = 0;
            int reEncryptCount = 0;
            int updateCount = 0;
            int emailUpdateCount = 0;
            int noChangeCount = 0;
            
            // 각 직원 정보 SHA256 해시 처리
            for (EmpLstVO emp : empList) {
                try {
                    String processType = processEmpEncryption(emp);
                    successCount++;
                    
                    // 처리 타입별 카운트
                    switch (processType) {
                        case "NEW":
                            newEncryptCount++;
                            logger.info("신규 암호화 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "RE_ENCRYPT":
                            reEncryptCount++;
                            logger.info("솔트키 변경 재암호화 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "UPDATE":
                            updateCount++;
                            logger.info("암호화 업데이트 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "EMAIL_UPDATE":
                            emailUpdateCount++;
                            logger.info("이메일 업데이트 완료: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        case "NO_CHANGE":
                            noChangeCount++;
                            logger.info("변경 없음: {} ({})", emp.getEmpId(), emp.getEmpNm());
                            break;
                        default:
                            logger.warn("알 수 없는 처리 타입: {} - 직원번호: {}", processType, emp.getEmpId());
                            break;
                    }
                    
                } catch (Exception e) {
                    errorCount++;
                    logger.error("직원 정보 SHA256 해시 실패 - 직원번호: {}, 오류: {}", emp.getEmpId(), e.getMessage(), e);
                }
            }
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            logger.info("=== 수동 직원 정보 SHA256 해시 배치 완료 ===");
            logger.info("처리 시간: {}ms", processingTime);
            logger.info("성공: {}건, 실패: {}건", successCount, errorCount);
            logger.info("신규 암호화: {}건, 솔트키 변경 재암호화: {}건, 업데이트: {}건, 이메일 업데이트: {}건, 변경 없음: {}건", 
                       newEncryptCount, reEncryptCount, updateCount, emailUpdateCount, noChangeCount);
            
            // 솔트키 변경 이력 통계 출력
            if (reEncryptCount > 0) {
                logger.info("=== 솔트키 변경 이력 통계 ===");
                logger.info("솔트키 변경으로 인한 재암호화된 직원 수: {}건", reEncryptCount);
                logger.info("이력은 tb_encrypt_history 테이블에 'RE_ENCRYPT' 타입으로 저장되었습니다.");
            }
            
            // 배치 완료 후 데이터베이스 상태 확인
            logger.info("=== 배치 완료 후 데이터베이스 상태 확인 ===");
            checkDatabaseStatus();
            
        } catch (Exception e) {
            logger.error("수동 직원 정보 SHA256 해시 배치 실행 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("수동 배치 실행 실패", e);
        }
    }

    /**
     * 배치 완료 후 데이터베이스 상태 확인
     */
    private void checkDatabaseStatus() {
        try {
            // 암호화 데이터베이스 상태 확인
            checkEncryptionDatabaseStatus();
            
            // 메인 데이터베이스 상태 확인
            checkMainDatabaseStatus();
            
        } catch (Exception e) {
            logger.error("데이터베이스 상태 확인 실패: {}", e.getMessage(), e);
        }
    }

    /**
     * 암호화 데이터베이스 상태 확인
     */
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    private void checkEncryptionDatabaseStatus() {
        try {
            int totalCount = empEncryptMapper.selectTotalCount();
            logger.info("TB_EMP_ENCRYPT 테이블 총 레코드 수: {}", totalCount);
            
            // 최근 처리된 데이터 확인
            List<EmpEncryptVO> recentData = empEncryptMapper.selectAllEmpEncrypt();
            if (!recentData.isEmpty()) {
                logger.info("최근 처리된 데이터 (최대 5개):");
                recentData.stream().limit(5).forEach(data -> {
                    logger.info("  - empSeq: {}, origEmpNo: {}, encryptEmpNo: {}, origEmpNm: {}, randomEmpNm: {}", 
                               data.getEmpSeq(), data.getOrigEmpNo(), data.getEncryptEmpNo(), 
                               data.getOrigEmpNm(), data.getRandomEmpNm());
                });
            }
        } catch (Exception e) {
            logger.error("암호화 데이터베이스 상태 확인 실패: {}", e.getMessage(), e);
        }
    }

    /**
     * 메인 데이터베이스 상태 확인
     */
    @Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class)
    private void checkMainDatabaseStatus() {
        try {
            int empLstCount = empLstMapper.selectTotalCount();
            logger.info("TB_EMP_LST 테이블 총 레코드 수: {}", empLstCount);
        } catch (Exception e) {
            logger.warn("TB_EMP_LST 테이블 상태 확인 실패: {}", e.getMessage());
        }
    }

    /**
     * eri_db의 tb_emp_lst 테이블 조회 테스트
     */
    @Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class)
    public void testEmpLstTableConnection() {
        try {
            logger.info("=== eri_db TB_EMP_LST 테이블 연결 테스트 시작 ===");
            
            // 1. 전체 레코드 수 조회
            logger.info("1. 전체 레코드 수 조회 시작...");
            try {
                int totalCount = empLstMapper.selectTotalCount();
                logger.info("TB_EMP_LST 테이블 총 레코드 수: {}", totalCount);
            } catch (Exception e) {
                logger.error("TB_EMP_LST 테이블 조회 실패: {}", e.getMessage(), e);
                throw new RuntimeException("TB_EMP_LST 테이블 조회 실패: " + e.getMessage(), e);
            }
            
            // 2. 테스트 데이터 삽입
            logger.info("2. 테스트 데이터 삽입 시작...");
            EmpLstVO testData = new EmpLstVO();
            testData.setEmpId("TEST_EMP_001");
            testData.setEmpNm("테스트직원");
            testData.setEmpDeptCd("DEPT001");
            testData.setEmpPosCd("POS001");
            testData.setEmpStsCd("ACTIVE");
            testData.setRgstDt(java.time.LocalDate.now());
            testData.setRgstTm(java.time.LocalTime.now());
            testData.setRgstEmpId("TEST_SYSTEM");
            testData.setUpdtDt(java.time.LocalDate.now());
            testData.setUpdtTm(java.time.LocalTime.now());
            testData.setUpdtEmpId("TEST_SYSTEM");
            testData.setDelYn("N");
            
            try {
                int insertResult = empLstMapper.insert(testData);
                logger.info("테스트 데이터 삽입 결과: {}", insertResult);
            } catch (Exception e) {
                logger.error("테스트 데이터 삽입 실패: {}", e.getMessage(), e);
                throw new RuntimeException("테스트 데이터 삽입 실패: " + e.getMessage(), e);
            }
            
            // 3. 삽입된 데이터 조회
            logger.info("3. 삽입된 데이터 조회 시작...");
            EmpLstVO retrievedData = null;
            try {
                retrievedData = empLstMapper.selectByEmpId("TEST_EMP_001");
                if (retrievedData != null) {
                    logger.info("테스트 데이터 조회 성공: empId={}, empNm={}", 
                               retrievedData.getEmpId(), retrievedData.getEmpNm());
                } else {
                    logger.warn("테스트 데이터 조회 실패 - 데이터가 null입니다");
                }
            } catch (Exception e) {
                logger.error("테스트 데이터 조회 실패: {}", e.getMessage(), e);
                throw new RuntimeException("테스트 데이터 조회 실패: " + e.getMessage(), e);
            }
            
            // 4. 테스트 데이터 삭제
            logger.info("4. 테스트 데이터 정리 시작...");
            if (retrievedData != null) {
                try {
                    int deleteResult = empLstMapper.deleteEmpLst("TEST_EMP_001");
                    logger.info("테스트 데이터 삭제 결과: {}", deleteResult);
                } catch (Exception e) {
                    logger.error("테스트 데이터 삭제 실패: {}", e.getMessage(), e);
                    // 삭제 실패는 전체 테스트를 실패시키지 않음
                }
            }
            
            // 5. 최종 레코드 수 확인
            try {
                int finalCount = empLstMapper.selectTotalCount();
                logger.info("최종 TB_EMP_LST 테이블 레코드 수: {}", finalCount);
            } catch (Exception e) {
                logger.error("최종 레코드 수 조회 실패: {}", e.getMessage(), e);
                // 최종 조회 실패는 전체 테스트를 실패시키지 않음
            }
            
            logger.info("=== eri_db TB_EMP_LST 테이블 연결 테스트 완료 ===");
            
        } catch (Exception e) {
            logger.error("eri_db TB_EMP_LST 테이블 연결 테스트 실패: {}", e.getMessage(), e);
            throw new RuntimeException("eri_db TB_EMP_LST 테이블 연결 테스트 실패: " + e.getMessage(), e);
        }
    }

    /**
     * empInfo.txt 파일 읽기
     */
    private List<EmpLstVO> readEmpInfoFile() {
        List<EmpLstVO> empList = new ArrayList<>();

        try {
            logger.info("empInfo.txt 파일 읽기 시작");
            
            ClassPathResource resource = new ClassPathResource("templates/empInfo.txt");
            logger.info("파일 경로: {}", resource.getPath());
            logger.info("파일 존재 여부: {}", resource.exists());
            
            if (!resource.exists()) {
                throw new RuntimeException("empInfo.txt 파일을 찾을 수 없습니다: " + resource.getPath());
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                boolean isFirstLine = true;
                int lineCount = 0;
                int parsedCount = 0;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    logger.debug("라인 {} 읽기: {}", lineCount, line);
                    
                    // 헤더 라인 스킵
                    if (isFirstLine) {
                        logger.info("헤더 라인 스킵: {}", line);
                        isFirstLine = false;
                        continue;
                    }

                    // 빈 라인 스킵
                    if (line.trim().isEmpty()) {
                        logger.debug("빈 라인 스킵");
                        continue;
                    }

                    // 데이터 파싱
                    EmpLstVO emp = parseEmpInfoLine(line);
                    if (emp != null) {
                        empList.add(emp);
                        parsedCount++;
                        logger.debug("직원 정보 파싱 성공: {} ({})", emp.getEmpId(), emp.getEmpNm());
                    } else {
                        logger.warn("라인 {} 파싱 실패: {}", lineCount, line);
                    }
                }
                
                logger.info("파일 읽기 완료 - 총 라인: {}, 파싱 성공: {}, 직원 수: {}", lineCount, parsedCount, empList.size());
            }

        } catch (IOException e) {
            logger.error("empInfo.txt 파일 읽기 실패: {}", e.getMessage(), e);
            throw new RuntimeException("empInfo.txt 파일 읽기 실패", e);
        }

        return empList;
    }

    /**
     * 직원 정보 라인 파싱
     */
    private EmpLstVO parseEmpInfoLine(String line) {
        try {
            logger.debug("라인 파싱 시작: {}", line);
            
            String[] parts = line.split("\\|");
            logger.debug("분할된 필드 수: {}", parts.length);
            
            if (parts.length >= 8) {
                EmpLstVO emp = new EmpLstVO();
                emp.setEmpId(parts[0].trim());        // 직원번호 (원본)
                emp.setEmpNm(parts[1].trim());        // 직원명
                emp.setEmpDeptCd(parts[2].trim());    // 부서코드
                emp.setEmpPosCd(parts[4].trim());     // 직급코드
                emp.setEmpStsCd(parts[6].trim());     // 직원상태
                emp.setEmpEmail(parts[7].trim());     // 이메일
                emp.setRgstDt(LocalDate.now());
                emp.setRgstTm(LocalTime.now());
                emp.setRgstEmpId("SCHEDULER_SYSTEM");
                emp.setUpdtDt(LocalDate.now());
                emp.setUpdtTm(LocalTime.now());
                emp.setUpdtEmpId("SCHEDULER_SYSTEM");
                emp.setDelYn("N");
                
                logger.debug("직원 정보 파싱 완료: ID={}, 이름={}, 부서={}, 직급={}, 상태={}, 이메일={}", 
                    emp.getEmpId(), emp.getEmpNm(), emp.getEmpDeptCd(), emp.getEmpPosCd(), emp.getEmpStsCd(), emp.getEmpEmail());
                
                return emp;
            } else {
                logger.warn("필드 수 부족: {} (필요: 8, 실제: {})", line, parts.length);
                for (int i = 0; i < parts.length; i++) {
                    logger.debug("필드 {}: '{}'", i, parts[i]);
                }
            }
        } catch (Exception e) {
            logger.error("라인 파싱 오류: {}, 오류: {}", line, e.getMessage(), e);
        }
        return null;
    }

    /**
     * 직원 정보 SHA256 해시 처리
     * @return 처리 타입: "NEW", "RE_ENCRYPT", "UPDATE", "EMAIL_UPDATE"
     */
    private String processEmpEncryption(EmpLstVO emp) throws Exception {
        // 원본 데이터 저장
        String originalEmpId = emp.getEmpId();
        String originalEmpNm = emp.getEmpNm();
        
        // 직원번호 SHA256 해시
        String hashedEmpId = encryptData(originalEmpId);
        
        // 직원명 SHA256 해시
        String hashedEmpNm = encryptData(originalEmpNm);
        
        // 랜덤 변형 한글명 생성 (직원명 대신 사용)
        String randomEmpNm = generateRandomKoreanName();
        
        // TB_EMP_ENCRYPT 테이블에 해시 정보 저장 (이메일은 empInfo.txt에서 읽어옴)
        String originalEmpEmail = emp.getEmpEmail(); // empInfo.txt에서 읽어온 이메일 사용
        String processType = saveEncryptInfo(originalEmpId, hashedEmpId, originalEmpNm, randomEmpNm, originalEmpEmail);
        
        // TB_EMP_LST 테이블에 직원정보 저장 (eri_db 메인 데이터베이스) - 별도 트랜잭션으로 분리
        saveEmpLstInfoWithMainTransaction(emp, hashedEmpId, randomEmpNm);
        
        logger.info("SHA256 해시 완료: {} -> {}", originalEmpId, hashedEmpId);
        logger.info("직원명 변형: {} -> {}", originalEmpNm, randomEmpNm);
        
        return processType;
    }

    /**
     * 데이터 SHA256 해시 처리
     */
    private String encryptData(String data) throws Exception {
        try {
            // SHA-256 해시 생성
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance(ALGORITHM);
            
            // 솔트와 데이터를 결합하여 해시 생성
            String saltedData = hashSalt + data;
            byte[] hashBytes = digest.digest(saltedData.getBytes(StandardCharsets.UTF_8));
            
            // 해시 결과를 Base64로 인코딩
            String hashResult = Base64.getEncoder().encodeToString(hashBytes);
            
            logger.debug("SHA256 해시 완료 - 원본: {}, 해시: {}", data, hashResult);
            
            return hashResult;
            
        } catch (Exception e) {
            logger.error("SHA256 해시 처리 실패: {}, 오류: {}", data, e.getMessage(), e);
            throw new RuntimeException("SHA256 해시 처리 실패: " + data, e);
        }
    }

    /**
     * 랜덤 한글명 생성 (실제 사용하지 않는 이름)
     */
    private String generateRandomKoreanName() {
        String[] surnames = {"공", "구", "국", "군", "궁", "금", "기", "길", "나", "남", "노", "단", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "마", "만", "명", "문", "반", "방", "배", "백", "범", "법", "벽", "변", "복", "봉", "부", "분", "비", "빈", "사", "삼", "상", "새", "석", "선", "설", "성", "세", "소", "손", "신", "심", "양", "어", "엄", "여", "연", "염", "옥", "완", "왕", "요", "용", "우", "원", "위", "유", "육", "은", "음", "의", "인", "자", "제", "종", "차", "창", "채", "천", "초", "추", "탁", "태", "판", "편", "평", "함", "해", "허", "형", "화", "환", "효", "후", "휘", "흥", "희"};
        String[] givenNames = {"가람", "가을", "강산", "강철", "거북", "건우", "경수", "고은", "공주", "관수", "광수", "구름", "국수", "군수", "금수", "기수", "길수", "나무", "남수", "노수", "단수", "담수", "대수", "덕수", "도수", "동수", "두수", "라수", "래수", "로수", "루수", "마수", "만수", "명수", "문수", "반수", "방수", "배수", "백수", "범수", "법수", "벽수", "변수", "복수", "봉수", "부수", "분수", "비수", "빈수", "사수", "삼수", "상수", "새수", "석수", "선수", "설수", "성수", "세수", "소수", "손수", "송수", "신수", "심수", "안수", "양수", "어수", "엄수", "여수", "연수", "염수", "오수", "옥수", "완수", "왕수", "요수", "용수", "우수", "원수", "위수", "유수", "육수", "은수", "음수", "의수", "인수", "자수", "장수", "전수", "정수", "제수", "종수", "주수", "지수", "진수", "차수", "창수", "채수", "천수", "초수", "추수", "탁수", "태수", "판수", "편수", "평수", "하수", "함수", "해수", "허수", "현수", "형수", "호수", "화수", "환수", "효수", "후수", "훈수", "휘수", "흥수", "희수"};
        
        String surname = surnames[(int) (Math.random() * surnames.length)];
        String givenName = givenNames[(int) (Math.random() * givenNames.length)];
        
        return surname + givenName;
    }

    /**
     * TB_EMP_ENCRYPT 테이블에 SHA256 해시 정보 저장
     * @return 처리 타입: "NEW", "RE_ENCRYPT", "UPDATE", "EMAIL_UPDATE"
     */
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    private String saveEncryptInfo(String origEmpNo, String encryptEmpNo, String origEmpNm, String randomEmpNm, String origEmpEmail) {
        try {
            logger.info("=== SHA256 해시 정보 저장 시작 ===");
            logger.info("원본번호: {}, 원본이름: {}, 원본이메일: {}", origEmpNo, origEmpNm, origEmpEmail);
            logger.info("해시번호: {}, 랜덤이름: {}", encryptEmpNo, randomEmpNm);
            
            // 기존 데이터 확인
            EmpEncryptVO existingEncrypt = empEncryptMapper.selectByEmpNo(origEmpNo);
            logger.info("기존 데이터 조회 결과: {}", existingEncrypt != null ? "존재" : "없음");
            
            if (existingEncrypt != null) {
                // 기존 데이터가 있는 경우 - 직원번호와 암호화된 직원번호가 동일한지 확인
                boolean isSameEncryptValue = encryptEmpNo.equals(existingEncrypt.getEncryptEmpNo());
                boolean isSaltChanged = !hashSalt.equals(existingEncrypt.getEncryptIv());
                
                logger.info("기존 데이터 존재 - 암호화값 동일: {}, 솔트키 변경: {}", isSameEncryptValue, isSaltChanged);
                logger.info("기존 암호화값: {}, 새 암호화값: {}", existingEncrypt.getEncryptEmpNo(), encryptEmpNo);
                logger.info("기존 솔트: {}, 새 솔트: {}", existingEncrypt.getEncryptIv(), hashSalt);
                
                if (isSameEncryptValue) {
                    // [중요] 암호화값이 동일하면 이메일만 변경, randomEmpNm은 절대 set/변경하지 않음
                    logger.info("직원번호와 암호화된 직원번호가 동일 - 이메일만 업데이트 (랜덤이름은 변경하지 않음)");
                    updateEmailOnly(existingEncrypt.getEmpSeq(), origEmpEmail);
                    
                    // 이메일 변경 여부만 확인하고 별도 이력 기록하지 않음 (ENCRYPT 처리 시 이메일도 함께 처리됨)
                    if (!origEmpEmail.equals(existingEncrypt.getOrigEmpEmail())) {
                        logger.info("이메일 변경 감지: {} -> {}", existingEncrypt.getOrigEmpEmail(), origEmpEmail);
                        return "EMAIL_UPDATE";
                    } else {
                        return "NO_CHANGE";
                    }
                } else {
                    // [중요] 암호화값이 다를 때만 randomEmpNm을 set하여 전체 업데이트
                    logger.info("암호화값이 다름 - 전체 정보 업데이트 (랜덤이름도 반영)");
                    EmpEncryptVO encryptInfo = new EmpEncryptVO();
                    encryptInfo.setEmpSeq(existingEncrypt.getEmpSeq());
                    encryptInfo.setOrigEmpNo(origEmpNo);
                    encryptInfo.setEncryptEmpNo(encryptEmpNo);
                    encryptInfo.setOrigEmpNm(origEmpNm);
                    encryptInfo.setRandomEmpNm(randomEmpNm); // 이 경우에만 set
                    encryptInfo.setOrigEmpEmail(origEmpEmail);
                    encryptInfo.setEncryptAlgorithm(ALGORITHM);
                    encryptInfo.setEncryptKeyId(KEY_ID);
                    encryptInfo.setEncryptIv(hashSalt);
                    
                    logger.info("전체 업데이트 쿼리 실행 시작...");
                    int updateResult = empEncryptMapper.update(encryptInfo);
                    logger.info("SHA256 해시 정보 전체 업데이트 완료: {} (결과: {})", origEmpNo, updateResult);
                    
                    // 솔트키 변경으로 인한 재암호화인 경우 이력 기록
                    if (isSaltChanged) {
                        logger.info("솔트키 변경으로 인한 재암호화 감지: 직원번호={}", origEmpNo);
                        saveEncryptHistory(origEmpNo, existingEncrypt.getEncryptEmpNo(), encryptEmpNo, 
                                         origEmpNm, existingEncrypt.getRandomEmpNm(), randomEmpNm, 
                                         origEmpEmail, "RE_ENCRYPT", "솔트키 변경으로 인한 재암호화");
                        return "RE_ENCRYPT";
                    } else {
                        logger.info("암호화값 변경 감지: 직원번호={}", origEmpNo);
                        saveEncryptHistory(origEmpNo, existingEncrypt.getEncryptEmpNo(), encryptEmpNo, 
                                         origEmpNm, existingEncrypt.getRandomEmpNm(), randomEmpNm, 
                                         origEmpEmail, "UPDATE", "암호화값 업데이트");
                        return "UPDATE";
                    }
                }
            } else {
                // 새 데이터 삽입
                logger.info("새 데이터 삽입 시작...");
                EmpEncryptVO encryptInfo = new EmpEncryptVO();
                encryptInfo.setOrigEmpNo(origEmpNo);
                encryptInfo.setEncryptEmpNo(encryptEmpNo);
                encryptInfo.setOrigEmpNm(origEmpNm);
                encryptInfo.setRandomEmpNm(randomEmpNm);
                encryptInfo.setOrigEmpEmail(origEmpEmail);
                encryptInfo.setEncryptAlgorithm(ALGORITHM);
                encryptInfo.setEncryptKeyId(KEY_ID);
                encryptInfo.setEncryptIv(hashSalt);
                
                int insertResult = empEncryptMapper.insert(encryptInfo);
                logger.info("SHA256 해시 정보 삽입 완료: {} (결과: {})", origEmpNo, insertResult);
                
                // 신규 암호화 이력 기록
                saveEncryptHistory(origEmpNo, null, encryptEmpNo, 
                                 origEmpNm, null, randomEmpNm, 
                                 origEmpEmail, "ENCRYPT", "신규 직원 정보 암호화");
                
                return "NEW";
            }
            
        } catch (Exception e) {
            logger.error("SHA256 해시 정보 저장 실패: {}, 오류: {}", origEmpNo, e.getMessage(), e);
            throw new RuntimeException("SHA256 해시 정보 저장 실패: " + origEmpNo, e);
        }
    }

    /**
     * TB_EMP_LST 테이블에 직원정보 저장 (eri_db 메인 데이터베이스) - mainTransactionManager 사용
     */
    @Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveEmpLstInfoWithMainTransaction(EmpLstVO emp, String hashedEmpId, String randomEmpNm) {
        try {
            logger.info("=== TB_EMP_LST 직원정보 저장 시작 (mainTransactionManager) ===");
            logger.info("직원번호: {} -> {}", emp.getEmpId(), hashedEmpId);
            logger.info("직원명: {} -> {}", emp.getEmpNm(), randomEmpNm);
            
            // TB_EMP_LST 테이블 존재 여부 확인
            try {
                int tableExists = empLstMapper.selectTotalCount();
                logger.info("TB_EMP_LST 테이블 확인 완료 - 레코드 수: {}", tableExists);
            } catch (Exception e) {
                logger.error("TB_EMP_LST 테이블이 존재하지 않거나 접근할 수 없습니다: {}", e.getMessage());
                logger.info("TB_EMP_LST 테이블 생성이 필요합니다. DDL 스크립트를 실행해주세요.");
                return; // 테이블이 없으면 조기 종료
            }
            
            // 기존 직원 정보 확인
            EmpLstVO existingEmp = null;
            try {
                existingEmp = empLstMapper.selectByEmpId(hashedEmpId);
                logger.info("기존 직원 정보 조회 결과: {}", existingEmp != null ? "존재" : "없음");
            } catch (Exception e) {
                logger.error("기존 직원 정보 조회 실패: {}", e.getMessage());
                // 조회 실패해도 계속 진행
            }
            
            // 새로운 직원 정보 객체 생성
            EmpLstVO newEmpInfo = new EmpLstVO();
            newEmpInfo.setEmpId(hashedEmpId);  // 해시된 직원번호 사용
            newEmpInfo.setEmpNm(randomEmpNm);  // 랜덤 변형 한글명 사용
            newEmpInfo.setEmpDeptCd(emp.getEmpDeptCd());
            newEmpInfo.setEmpPosCd(emp.getEmpPosCd());
            newEmpInfo.setEmpStsCd(emp.getEmpStsCd());
            newEmpInfo.setRgstDt(java.time.LocalDate.now());
            newEmpInfo.setRgstTm(java.time.LocalTime.now());
            newEmpInfo.setRgstEmpId(OPERATOR_ID);
            newEmpInfo.setUpdtDt(java.time.LocalDate.now());
            newEmpInfo.setUpdtTm(java.time.LocalTime.now());
            newEmpInfo.setUpdtEmpId(OPERATOR_ID);
            newEmpInfo.setDelYn("N");
            
            if (existingEmp != null) {
                // 기존 데이터가 있는 경우 업데이트 (랜덤 이름은 제외)
                logger.info("기존 직원 정보 업데이트 시작... (랜덤 이름 제외)");
                
                // 업데이트용 객체 생성 (랜덤 이름은 기존 값 유지)
                EmpLstVO updateEmpInfo = new EmpLstVO();
                updateEmpInfo.setEmpId(hashedEmpId);  // 해시된 직원번호 사용
                updateEmpInfo.setEmpNm(existingEmp.getEmpNm());  // 기존 랜덤 이름 유지 (변경하지 않음)
                updateEmpInfo.setEmpDeptCd(emp.getEmpDeptCd());
                updateEmpInfo.setEmpPosCd(emp.getEmpPosCd());
                updateEmpInfo.setEmpStsCd(emp.getEmpStsCd());
                updateEmpInfo.setUpdtDt(java.time.LocalDate.now());
                updateEmpInfo.setUpdtTm(java.time.LocalTime.now());
                updateEmpInfo.setUpdtEmpId(OPERATOR_ID);
                
                int updateResult = empLstMapper.update(updateEmpInfo);
                logger.info("TB_EMP_LST 직원정보 업데이트 완료: {} (결과: {}, 랜덤 이름 유지: {})", 
                           hashedEmpId, updateResult, existingEmp.getEmpNm());
            } else {
                // 새 데이터 삽입 (랜덤 이름 포함)
                logger.info("새 직원 정보 삽입 시작... (랜덤 이름 포함)");
                int insertResult = empLstMapper.insert(newEmpInfo);
                logger.info("TB_EMP_LST 직원정보 삽입 완료: {} (결과: {}, 랜덤 이름: {})", 
                           hashedEmpId, insertResult, randomEmpNm);
            }
            
            logger.info("=== TB_EMP_LST 직원정보 저장 완료 (mainTransactionManager) ===");
            
        } catch (Exception e) {
            logger.error("TB_EMP_LST 직원정보 저장 실패 (mainTransactionManager): {}, 오류: {}", emp.getEmpId(), e.getMessage(), e);
            // TB_EMP_LST 저장 실패는 전체 프로세스를 중단하지 않도록 예외를 던지지 않음
            // 단, 로그는 남겨서 추적 가능하도록 함
        }
    }

    /**
     * TB_EMP_LST 테이블에 직원정보 저장 (eri_db 메인 데이터베이스) - 기존 메서드 (하위 호환성 유지)
     */
    private void saveEmpLstInfo(EmpLstVO emp, String hashedEmpId, String randomEmpNm) {
        // mainTransactionManager를 사용하는 새로운 메서드 호출
        saveEmpLstInfoWithMainTransaction(emp, hashedEmpId, randomEmpNm);
    }

    /**
     * 이메일만 업데이트 (직원번호와 암호화된 직원번호가 동일한 경우)
     */
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    private void updateEmailOnly(Long empSeq, String origEmpEmail) {
        try {
            logger.info("이메일만 업데이트 시작: empSeq={}, 이메일={}", empSeq, origEmpEmail);
            
            int updateResult = empEncryptMapper.updateEmailOnly(empSeq, origEmpEmail);
            logger.info("이메일 업데이트 완료: empSeq={}, 결과={}", empSeq, updateResult);
            
        } catch (Exception e) {
            logger.error("이메일 업데이트 실패: empSeq={}, 오류: {}", empSeq, e.getMessage(), e);
            throw new RuntimeException("이메일 업데이트 실패: " + empSeq, e);
        }
    }

    /**
     * 암호화 이력 저장
     */
    @Transactional(transactionManager = "encryptionTransactionManager", rollbackFor = Exception.class)
    private void saveEncryptHistory(String origEmpNo, String oldEncryptEmpNo, String newEncryptEmpNo,
                                   String origEmpNm, String oldRandomEmpNm, String newRandomEmpNm,
                                   String origEmpEmail, String operationType, String description) {
        try {
            logger.debug("암호화 이력 저장 시작: 직원번호={}, 작업타입={}", origEmpNo, operationType);
            
            // tb_emp_encrypt에서 실제 emp_seq 조회
            EmpEncryptVO currentEncrypt = empEncryptMapper.selectByEmpNo(origEmpNo);
            Long empSeq = null;
            
            if (currentEncrypt != null) {
                empSeq = currentEncrypt.getEmpSeq();
                logger.debug("tb_emp_encrypt에서 emp_seq 조회: {}", empSeq);
            } else {
                // tb_emp_encrypt에 없는 경우, 새로 생성된 emp_seq 사용
                // 실제로는 시퀀스에서 다음 값을 가져와야 함
                empSeq = 1L;
                logger.warn("tb_emp_encrypt에 데이터가 없어 임시 emp_seq 사용: {}", empSeq);
            }
            
            // 암호화 이력 생성
            EncryptHistoryVO history = encryptHistoryService.createEncryptHistory(
                empSeq,                    // 직원 일련번호
                origEmpNo,                 // 원본 직원번호
                newEncryptEmpNo,           // 암호화된 직원번호
                origEmpNm,                 // 원본 직원명
                newRandomEmpNm,            // 랜덤 변형 한글명
                origEmpEmail,              // 원본 이메일
                ALGORITHM,                 // 암호화 알고리즘
                KEY_ID,                    // 암호화 키 ID
                hashSalt,                  // 암호화 초기화 벡터 (솔트)
                operationType,             // 작업 타입
                1L                         // 작업자 일련번호 (시스템)
            );
            
            int result = encryptHistoryService.saveEncryptHistory(history);
            logger.info("암호화 이력 저장 완료: 직원번호={}, 작업타입={}, 설명={}, 결과={}, emp_seq={}", 
                       origEmpNo, operationType, description, result, empSeq);
            
        } catch (Exception e) {
            logger.error("암호화 이력 저장 실패: {}, 오류: {}", origEmpNo, e.getMessage(), e);
            // 이력 저장 실패는 전체 프로세스를 중단하지 않도록 예외를 던지지 않음
        }
    }

} 