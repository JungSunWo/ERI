package com.ERI.demo.service;

import com.ERI.demo.mappers.employee.EmpLstMapper;
import com.ERI.demo.vo.employee.EmpLstVO;
import com.ERI.demo.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 직원 정보 DB의 TB_EMP_LST 테이블 Service
 * eri_employee_db 연결을 사용
 */
@Service
public class EmpLstService {

    @Autowired
    private EmpLstMapper empLstMapper;

    @Autowired
    @Qualifier("employeeJdbcTemplate")
    private JdbcTemplate employeeJdbcTemplate;

    @Autowired
    private com.ERI.demo.mappers.EmpRefMapper empRefMapper;

    /**
     * 전체 직원 목록 조회 (복호화된 데이터)
     */
    public List<EmpLstVO> getAllEmployees() {
        List<EmpLstVO> employees = empLstMapper.selectAllEmployees();
        // 이메일과 전화번호 복호화
        for (EmpLstVO employee : employees) {
            if (employee.getEad() != null && EncryptionUtil.isEncrypted(employee.getEad())) {
                employee.setEad(EncryptionUtil.decryptEmail(employee.getEad()));
            }
            if (employee.getEmpCpn() != null && EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
                employee.setEmpCpn(EncryptionUtil.decryptPhone(employee.getEmpCpn()));
            }
        }
        return employees;
    }

    /**
     * ERI_EMP_ID로 직원 정보 조회 (복호화된 데이터)
     */
    public EmpLstVO getEmployeeByEriId(String eriEmpId) {
        EmpLstVO employee = empLstMapper.selectEmployeeByEriId(eriEmpId);
        if (employee != null) {
            decryptEmployeeData(employee);
        }
        return employee;
    }

    /**
     * EMP_ID로 직원 정보 조회 (복호화된 데이터)
     */
    public EmpLstVO getEmployeeByEmpId(String empId) {
        EmpLstVO employee = empLstMapper.selectEmployeeByEmpId(empId);
        if (employee != null) {
            decryptEmployeeData(employee);
        }
        return employee;
    }

    /**
     * 직원번호로 직원 정보 조회 (AuthController용) (복호화된 데이터)
     */
    public EmpLstVO getEmployeeById(String empId) {
        EmpLstVO employee = empLstMapper.selectEmployeeByEmpId(empId);
        if (employee != null) {
            decryptEmployeeData(employee);
        }
        return employee;
    }

    /**
     * 직원 데이터 복호화 헬퍼 메서드
     */
    private void decryptEmployeeData(EmpLstVO employee) {
        if (employee.getEad() != null && EncryptionUtil.isEncrypted(employee.getEad())) {
            employee.setEad(EncryptionUtil.decryptEmail(employee.getEad()));
        }
        if (employee.getEmpCpn() != null && EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
            employee.setEmpCpn(EncryptionUtil.decryptPhone(employee.getEmpCpn()));
        }
    }

    /**
     * 전체 직원 목록 조회 (캐싱용)
     */
    public List<EmpLstVO> getAllEmployeesForCache() {
        return empLstMapper.selectAllEmployees();
    }

    /**
     * 부점별 직원 목록 조회 (복호화된 데이터)
     */
    public List<EmpLstVO> getEmployeesByBranch(String branchCd) {
        List<EmpLstVO> employees = empLstMapper.selectEmployeesByBranch(branchCd);
        for (EmpLstVO employee : employees) {
            decryptEmployeeData(employee);
        }
        return employees;
    }

    /**
     * 직원 정보 등록 (이메일과 전화번호 암호화)
     */
    @Transactional(transactionManager = "employeeTransactionManager")
    public int insertEmployee(EmpLstVO employee) {
        // 이메일과 전화번호 암호화
        if (employee.getEad() != null && !EncryptionUtil.isEncrypted(employee.getEad())) {
            employee.setEad(EncryptionUtil.encryptEmail(employee.getEad()));
        }
        if (employee.getEmpCpn() != null && !EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
            employee.setEmpCpn(EncryptionUtil.encryptPhone(employee.getEmpCpn()));
        }
        return empLstMapper.insertEmployee(employee);
    }

    /**
     * 직원 정보 수정 (이메일과 전화번호 암호화)
     */
    @Transactional(transactionManager = "employeeTransactionManager")
    public int updateEmployee(EmpLstVO employee) {
        // 이메일과 전화번호 암호화
        if (employee.getEad() != null && !EncryptionUtil.isEncrypted(employee.getEad())) {
            employee.setEad(EncryptionUtil.encryptEmail(employee.getEad()));
        }
        if (employee.getEmpCpn() != null && !EncryptionUtil.isEncrypted(employee.getEmpCpn())) {
            employee.setEmpCpn(EncryptionUtil.encryptPhone(employee.getEmpCpn()));
        }
        return empLstMapper.updateEmployee(employee);
    }

    /**
     * 직원 정보 삭제 (논리 삭제)
     */
    @Transactional(transactionManager = "employeeTransactionManager")
    public int deleteEmployee(String eriEmpId) {
        return empLstMapper.deleteEmployee(eriEmpId);
    }

    /**
     * 직원 수 조회
     */
    public int getEmployeeCount() {
        return empLstMapper.countAllEmployees();
    }

    /**
     * 조건별 직원 검색 (복호화된 데이터)
     */
    public List<EmpLstVO> searchEmployees(String searchKeyword, String branchCd, String jbclCd, String jbttCd, String hlofYn) {
        List<EmpLstVO> employees = empLstMapper.searchEmployees(searchKeyword, branchCd, jbclCd, jbttCd, hlofYn);
        for (EmpLstVO employee : employees) {
            decryptEmployeeData(employee);
        }
        return employees;
    }

    /**
     * 직원 정보 존재 여부 확인
     */
    public boolean isEmployeeExists(String empId) {
        EmpLstVO employee = empLstMapper.selectEmployeeByEmpId(empId);
        return employee != null;
    }

    /**
     * ERI_EMP_ID 존재 여부 확인
     */
    public boolean isEriEmployeeExists(String eriEmpId) {
        EmpLstVO employee = empLstMapper.selectEmployeeByEriId(eriEmpId);
        return employee != null;
    }

    /**
     * 직원 정보 DB 연결 테스트
     */
    public boolean testEmployeeDbConnection() {
        try {
            String result = employeeJdbcTemplate.queryForObject("SELECT 1", String.class);
            return "1".equals(result);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * empInfo.txt 파일 배치 적재 수동 실행 (새로운 데이터 구조)
     */
    @Transactional(transactionManager = "employeeTransactionManager")
    public void manualBatchLoadEmpInfo() throws IOException {
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("empInfo.txt 배치 처리 시작: " + startTime);
        
        String filePath = "src/main/resources/templates/empInfo.txt";
        
        // 파일 존재 확인
        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("empInfo.txt 파일을 찾을 수 없습니다: " + filePath);
        }
        
        List<EmpLstVO> employees = new ArrayList<>();
        int lineNumber = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // 첫 번째 줄은 헤더이므로 건너뛰기
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // 빈 줄 건너뛰기
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // 파이프(|) 구분자로 데이터 파싱
                String[] fields = line.split("\\|");
                if (fields.length < 20) {
                    throw new IOException("라인 " + lineNumber + "의 데이터 형식이 올바르지 않습니다: " + line);
                }
                
                EmpLstVO employee = new EmpLstVO();
                employee.setEmpId(fields[0].trim());                    // 직원번호
                employee.setEmpNm(fields[1].trim());                    // 직원명
                employee.setGndrDcd(fields[2].trim());                  // 성별구분코드
                employee.setHlofYn(fields[3].trim());                   // 재직여부
                employee.setEtbnYmd(parseDate(fields[4].trim()));       // 입행년월일
                employee.setBlngBrcd(fields[5].trim());                 // 소속부점코드
                employee.setBeteamCd(fields[6].trim());                 // 소속팀코드
                employee.setExigBlngYmd(parseDate(fields[7].trim()));   // 현소속년월일
                employee.setTrthWorkBrcd(fields[8].trim());             // 실제근무부점코드
                employee.setRswrBrcd(fields[9].trim());                 // 주재근무부점코드
                employee.setJbttCd(fields[10].trim());                  // 직위코드
                employee.setJbttYmd(parseDate(fields[11].trim()));      // 직위년월일
                employee.setNameNm(fields[12].trim());                  // 호칭명
                employee.setJbclCd(fields[13].trim());                  // 직급코드
                employee.setTrthBirtYmd(parseDate(fields[14].trim()));  // 실제생년월일
                employee.setSlcnUncgBirtYmd(parseDate(fields[15].trim())); // 양력환산생년월일
                employee.setInslDcd(fields[16].trim());                 // 음양구분코드
                // 전화번호와 이메일 암호화
                String phoneNumber = fields[17].trim();
                String email = fields[19].trim();
                
                employee.setEmpCpn(EncryptionUtil.encryptPhone(phoneNumber));  // 직원휴대폰번호 (암호화)
                employee.setEmpExtiNo(fields[18].trim());                       // 직원내선번호
                employee.setEad(EncryptionUtil.encryptEmail(email));            // 이메일주소 (암호화)
                
                // 기본값 설정
                employee.setDelYn("N");
                
                // NULL 값 처리
                if (employee.getEmpNm() == null || employee.getEmpNm().isEmpty()) {
                    employee.setEmpNm("미지정");
                }
                if (employee.getBlngBrcd() == null || employee.getBlngBrcd().isEmpty()) {
                    employee.setBlngBrcd("0000");
                }
                if (employee.getJbclCd() == null || employee.getJbclCd().isEmpty()) {
                    employee.setJbclCd("1");
                }
                if (employee.getHlofYn() == null || employee.getHlofYn().isEmpty()) {
                    employee.setHlofYn("Y");
                }
                if (employee.getEad() == null || employee.getEad().isEmpty()) {
                    employee.setEad(EncryptionUtil.encryptEmail("no-email@company.com"));
                }
                
                employees.add(employee);
            }
        }
        
        System.out.println("파일 읽기 완료: " + employees.size() + "명의 직원 데이터 파싱됨");
        
        int insertCount = 0;
        int updateCount = 0;
        
        // 데이터베이스에 일괄 삽입
        for (EmpLstVO employee : employees) {
            try {
                // 기존 데이터가 있는지 확인
                if (!isEmployeeExists(employee.getEmpId())) {
                    // 새로운 직원인 경우 ERI_EMP_ID 생성
                    String eriEmpId = generateUniqueEriEmpId();
                    employee.setEriEmpId(eriEmpId);
                    
                    System.out.println("새 직원 삽입: " + employee.getEmpId() + " -> " + eriEmpId);
                    int result = empLstMapper.insertEmployee(employee);
                    System.out.println("삽입 결과: " + result + " 행이 영향받음");
                    insertCount++;
                } else {
                    // 기존 데이터가 있으면 업데이트 (ERI_EMP_ID는 유지)
                    EmpLstVO existingEmployee = getEmployeeByEmpId(employee.getEmpId());
                    if (existingEmployee != null) {
                        employee.setEriEmpId(existingEmployee.getEriEmpId());
                        System.out.println("기존 직원 업데이트: " + employee.getEmpId() + " -> " + existingEmployee.getEriEmpId());
                        int result = empLstMapper.updateEmployee(employee);
                        System.out.println("업데이트 결과: " + result + " 행이 영향받음");
                        updateCount++;
                    }
                }
            } catch (Exception e) {
                System.err.println("직원 처리 중 오류 발생: " + employee.getEmpId());
                System.err.println("오류 내용: " + e.getMessage());
                e.printStackTrace();
                throw e; // 오류를 다시 던져서 전체 트랜잭션 롤백
            }
        }
        
        LocalDateTime endTime = LocalDateTime.now();
        long duration = java.time.Duration.between(startTime, endTime).toMillis();
        
        System.out.println("empInfo.txt 배치 처리 완료: " + endTime);
        System.out.println("처리 시간: " + duration + "ms");
        System.out.println("삽입된 직원 수: " + insertCount);
        System.out.println("업데이트된 직원 수: " + updateCount);
        System.out.println("총 처리된 직원 수: " + (insertCount + updateCount));
        
        // empInfo.txt 처리 완료 후 TB_EMP_REF 동기화 실행
        System.out.println("TB_EMP_REF 동기화 시작...");
        syncEmpRefFromEmpLst();
        System.out.println("TB_EMP_REF 동기화 완료");
    }
    
    /**
     * TB_EMP_LST에서 TB_EMP_REF로 데이터 동기화
     * empInfo.txt 배치 처리 후 자동 실행
     */
    @Transactional(transactionManager = "employeeTransactionManager")
    public void syncEmpRefFromEmpLst() {
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("TB_EMP_REF 동기화 시작: " + startTime);
        
        try {
            // TB_EMP_LST에서 모든 직원 정보 조회
            List<EmpLstVO> empLstEmployees = getAllEmployees();
            System.out.println("TB_EMP_LST에서 조회된 직원 수: " + empLstEmployees.size());
            
            int insertCount = 0;
            int updateCount = 0;
            int deleteCount = 0;
            
            // TB_EMP_REF에 있는 모든 ERI_EMP_ID 조회
            List<com.ERI.demo.vo.EmpRefVO> existingEmpRefs = empRefMapper.selectAllEmpRef();
            Set<String> existingEriEmpIds = existingEmpRefs.stream()
                .map(com.ERI.demo.vo.EmpRefVO::getEriEmpId)
                .collect(java.util.stream.Collectors.toSet());
            
            // TB_EMP_LST의 각 직원에 대해 TB_EMP_REF 동기화
            for (EmpLstVO empLstEmployee : empLstEmployees) {
                com.ERI.demo.vo.EmpRefVO empRefVO = convertToEmpRefVO(empLstEmployee);
                
                if (existingEriEmpIds.contains(empRefVO.getEriEmpId())) {
                    // 기존 데이터가 있으면 업데이트
                    int result = empRefMapper.updateEmpRef(empRefVO);
                    if (result > 0) {
                        updateCount++;
                        System.out.println("업데이트: " + empRefVO.getEriEmpId() + " - " + empRefVO.getEmpNm());
                    }
                } else {
                    // 새로운 데이터면 삽입
                    int result = empRefMapper.insertEmpRef(empRefVO);
                    if (result > 0) {
                        insertCount++;
                        System.out.println("삽입: " + empRefVO.getEriEmpId() + " - " + empRefVO.getEmpNm());
                    }
                }
            }
            
            // TB_EMP_LST에 없는 TB_EMP_REF 데이터 삭제 (실제 삭제)
            Set<String> empLstEriEmpIds = empLstEmployees.stream()
                .map(EmpLstVO::getEriEmpId)
                .collect(java.util.stream.Collectors.toSet());
            
            for (String existingEriEmpId : existingEriEmpIds) {
                if (!empLstEriEmpIds.contains(existingEriEmpId)) {
                    int result = empRefMapper.deleteEmpRefByEriEmpId(existingEriEmpId);
                    if (result > 0) {
                        deleteCount++;
                        System.out.println("삭제: " + existingEriEmpId);
                    }
                }
            }
            
            LocalDateTime endTime = LocalDateTime.now();
            long duration = java.time.Duration.between(startTime, endTime).toMillis();
            
            System.out.println("TB_EMP_REF 동기화 완료: " + endTime);
            System.out.println("처리 시간: " + duration + "ms");
            System.out.println("삽입된 직원 수: " + insertCount);
            System.out.println("업데이트된 직원 수: " + updateCount);
            System.out.println("삭제된 직원 수: " + deleteCount);
            System.out.println("총 처리된 직원 수: " + (insertCount + updateCount + deleteCount));
            
        } catch (Exception e) {
            System.err.println("TB_EMP_REF 동기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * EmpLstVO를 EmpRefVO로 변환
     */
    private com.ERI.demo.vo.EmpRefVO convertToEmpRefVO(EmpLstVO empLstVO) {
        com.ERI.demo.vo.EmpRefVO empRefVO = new com.ERI.demo.vo.EmpRefVO();
        
        empRefVO.setEriEmpId(empLstVO.getEriEmpId());
        empRefVO.setEmpNm(empLstVO.getEmpNm());
        empRefVO.setGndrDcd(empLstVO.getGndrDcd());
        empRefVO.setHlofYn(empLstVO.getHlofYn());
        empRefVO.setEtbnYmd(empLstVO.getEtbnYmd());
        empRefVO.setBlngBrcd(empLstVO.getBlngBrcd());
        empRefVO.setBeteamCd(empLstVO.getBeteamCd());
        empRefVO.setExigBlngYmd(empLstVO.getExigBlngYmd());
        empRefVO.setTrthWorkBrcd(empLstVO.getTrthWorkBrcd());
        empRefVO.setRswrBrcd(empLstVO.getRswrBrcd());
        empRefVO.setJbttCd(empLstVO.getJbttCd());
        empRefVO.setJbttYmd(empLstVO.getJbttYmd());
        empRefVO.setNameNm(empLstVO.getNameNm());
        empRefVO.setJbclCd(empLstVO.getJbclCd());
        empRefVO.setTrthBirtYmd(empLstVO.getTrthBirtYmd());
        empRefVO.setSlcnUncgBirtYmd(empLstVO.getSlcnUncgBirtYmd());
        empRefVO.setInslDcd(empLstVO.getInslDcd());
        empRefVO.setEmpCpn(empLstVO.getEmpCpn());
        empRefVO.setEmpExtiNo(empLstVO.getEmpExtiNo());
        empRefVO.setEad(empLstVO.getEad());
        empRefVO.setDelYn("N"); // 기본값
        empRefVO.setRegDt(empLstVO.getRegDt());
        empRefVO.setUpdDt(empLstVO.getUpdDt());
        
        return empRefVO;
    }
    
    /**
     * 날짜 문자열을 LocalDate로 파싱
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty() || "null".equalsIgnoreCase(dateStr)) {
            return null;
        }
        
        try {
            // yyyy-MM-dd 형식으로 파싱
            return LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            System.err.println("날짜 파싱 오류: " + dateStr + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 고유한 ERI_EMP_ID 생성 (E00000001 형식)
     */
    private String generateUniqueEriEmpId() {
        int maxAttempts = 100;
        int attempt = 0;
        
        while (attempt < maxAttempts) {
            // 현재 시간을 기반으로 고유한 번호 생성
            String timestamp = String.valueOf(System.currentTimeMillis());
            String sequence = timestamp.substring(timestamp.length() - 8); // 마지막 8자리 사용
            
            // E00000001 형식으로 포맷팅
            String eriEmpId = String.format("E%08d", Integer.parseInt(sequence) % 99999999);
            
            // 중복 확인
            if (!isEriEmployeeExists(eriEmpId)) {
                return eriEmpId;
            }
            
            attempt++;
            try {
                Thread.sleep(1); // 1ms 대기하여 다른 타임스탬프 생성
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        throw new RuntimeException("고유한 ERI_EMP_ID를 생성할 수 없습니다. 최대 시도 횟수를 초과했습니다.");
    }

    /**
     * 스케줄링된 empInfo.txt 파일 배치 적재 (매일 오전 6시 실행)
     */
    // @Scheduled(cron = "0 0 6 * * ?") // 매일 오전 6시
    // @Transactional(transactionManager = "employeeTransactionManager")
    // public void scheduledBatchLoadEmpInfo() {
    //     try {
    //         System.out.println("스케줄링된 empInfo.txt 배치 적재 시작: " + LocalDateTime.now());
    //         manualBatchLoadEmpInfo();
    //         System.out.println("스케줄링된 empInfo.txt 배치 적재 완료: " + LocalDateTime.now());
    //     } catch (Exception e) {
    //         System.err.println("스케줄링된 empInfo.txt 배치 적재 실패: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

     

    

   
} 