package com.ERI.demo.service;

import com.ERI.demo.mappers.encryption.EmpEncryptMapper;
import com.ERI.demo.mappers.EmpLstMapper;
import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.vo.EmpEncryptVO;
import com.ERI.demo.vo.EmpLstVO;
import com.ERI.demo.vo.AdminLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원 암호화 정보 서비스 (암호화 데이터베이스 전용)
 * eri_enc_db 데이터베이스의 직원번호와 직원명 암호화 정보를 통합 관리
 */
@Service
@Transactional(transactionManager = "encryptionTransactionManager")
public class EmpEncryptService {
    
    @Autowired
    private EmpEncryptMapper empEncryptMapper;
    
    @Autowired
    private EmpLstMapper empLstMapper;
    
    @Autowired
    private AdminLstMapper adminLstMapper;
    
    /**
     * 전체 직원 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> getAllEmpEncrypt() {
        return empEncryptMapper.selectAllEmpEncrypt();
    }
    
    /**
     * 전체 직원 암호화 정보 조회 (삭제된 데이터 포함)
     */
    public List<EmpEncryptVO> getAllEmpEncryptWithDeleted() {
        return empEncryptMapper.selectAllEmpEncryptWithDeleted();
    }
    
    /**
     * 직원 일련번호로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public EmpEncryptVO getEmpEncryptBySeq(Long empSeq) {
        return empEncryptMapper.selectEmpEncryptBySeq(empSeq);
    }
    
    /**
     * 직원 일련번호로 암호화 정보 조회 (삭제된 데이터 포함)
     */
    public EmpEncryptVO getEmpEncryptBySeqWithDeleted(Long empSeq) {
        return empEncryptMapper.selectEmpEncryptBySeqWithDeleted(empSeq);
    }
    
    /**
     * 원본 직원번호로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public EmpEncryptVO getEmpEncryptByOrigEmpNo(String origEmpNo) {
        return empEncryptMapper.selectEmpEncryptByOrigEmpNo(origEmpNo);
    }
    
    /**
     * 해시된 직원번호로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public EmpEncryptVO getEmpEncryptByEncryptEmpNo(String encryptEmpNo) {
        return empEncryptMapper.selectByEncryptEmpNo(encryptEmpNo);
    }
    
    /**
     * 원본 직원명으로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> getEmpEncryptByOrigEmpNm(String origEmpNm) {
        return empEncryptMapper.selectEmpEncryptByOrigEmpNm(origEmpNm);
    }
    
    /**
     * 랜덤 변형 한글명으로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> getEmpEncryptByRandomEmpNm(String randomEmpNm) {
        return empEncryptMapper.selectEmpEncryptByRandomEmpNm(randomEmpNm);
    }
    
    /**
     * 직원 암호화 정보 등록
     */
    public int insertEmpEncrypt(EmpEncryptVO empEncrypt) {
        return empEncryptMapper.insertEmpEncrypt(empEncrypt);
    }
    
    /**
     * 직원 암호화 정보 수정
     */
    public int updateEmpEncrypt(EmpEncryptVO empEncrypt) {
        return empEncryptMapper.updateEmpEncrypt(empEncrypt);
    }
    
    /**
     * 직원 일련번호로 논리적 삭제
     */
    public int deleteEmpEncryptBySeq(Long empSeq) {
        return empEncryptMapper.deleteEmpEncryptBySeq(empSeq);
    }
    
    /**
     * 직원 일련번호로 복구
     */
    public int restoreEmpEncryptBySeq(Long empSeq) {
        return empEncryptMapper.restoreEmpEncryptBySeq(empSeq);
    }
    
    /**
     * 원본 직원번호로 논리적 삭제
     */
    public int deleteEmpEncryptByOrigEmpNo(String origEmpNo) {
        return empEncryptMapper.deleteEmpEncryptByOrigEmpNo(origEmpNo);
    }
    
    /**
     * 암호화 알고리즘으로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> getEmpEncryptByAlgorithm(String encryptAlgorithm) {
        return empEncryptMapper.selectEmpEncryptByAlgorithm(encryptAlgorithm);
    }
    
    /**
     * 암호화 일시 범위로 암호화 정보 조회 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> getEmpEncryptByDateRange(String startDate, String endDate) {
        return empEncryptMapper.selectEmpEncryptByDateRange(startDate, endDate);
    }
    
    /**
     * 직원번호와 직원명으로 암호화 정보 검색 (삭제되지 않은 데이터만)
     */
    public List<EmpEncryptVO> searchEmpEncrypt(String origEmpNo, String origEmpNm) {
        return empEncryptMapper.searchEmpEncrypt(origEmpNo, origEmpNm);
    }
    
    /**
     * 삭제된 직원 암호화 정보 조회
     */
    public List<EmpEncryptVO> getDeletedEmpEncrypt() {
        return empEncryptMapper.selectDeletedEmpEncrypt();
    }
    
    /**
     * 전체 암호화 정보 개수 조회 (삭제되지 않은 데이터만)
     */
    public int countAllEmpEncrypt() {
        return empEncryptMapper.countAllEmpEncrypt();
    }
    
    /**
     * 전체 암호화 정보 개수 조회 (삭제된 데이터 포함)
     */
    public int countAllEmpEncryptWithDeleted() {
        return empEncryptMapper.countAllEmpEncryptWithDeleted();
    }
    
    /**
     * 삭제된 암호화 정보 개수 조회
     */
    public int countDeletedEmpEncrypt() {
        return empEncryptMapper.countDeletedEmpEncrypt();
    }
    
    /**
     * 암호화 알고리즘별 암호화 정보 개수 조회 (삭제되지 않은 데이터만)
     */
    public int countEmpEncryptByAlgorithm(String encryptAlgorithm) {
        return empEncryptMapper.countEmpEncryptByAlgorithm(encryptAlgorithm);
    }
    
    /**
     * 직원 암호화 정보 등록/수정 (통합 처리)
     */
    public boolean saveEmpEncrypt(EmpEncryptVO empEncrypt) {
        try {
            // 기존 데이터 확인
            EmpEncryptVO existing = empEncryptMapper.selectEmpEncryptBySeq(empEncrypt.getEmpSeq());
            
            if (existing != null) {
                // 기존 데이터가 있으면 업데이트
                empEncrypt.setEmpSeq(existing.getEmpSeq());
                return empEncryptMapper.updateEmpEncrypt(empEncrypt) > 0;
            } else {
                // 새 데이터면 삽입
                return empEncryptMapper.insertEmpEncrypt(empEncrypt) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 랜덤 변형 한글명 생성 (간단한 예시)
     */
    public String generateRandomEmpNm(String origEmpNm) {
        if (origEmpNm == null || origEmpNm.trim().isEmpty()) {
            return "알수없음";
        }
        
        // 간단한 랜덤 변형 로직 (실제로는 더 복잡한 알고리즘 사용)
        String[] randomNames = {"김철수", "이영희", "박민수", "최지영", "정현우", "한소영", "강동원", "윤서연"};
        int randomIndex = (origEmpNm.hashCode() % randomNames.length + randomNames.length) % randomNames.length;
        
        return randomNames[randomIndex];
    }
    
    /**
     * 직원번호로 암호화된 직원번호를 조회한 후 tb_emp_lst 테이블의 직원정보를 조회
     * @param origEmpNo 원본 직원번호
     * @return 직원정보 (EmpLstVO) - 조회 실패시 null
     */
    public EmpLstVO getEmpInfoByOrigEmpNo(String origEmpNo) {
        try {
            // 1. 원본 직원번호로 tb_emp_encrypt 테이블에서 암호화된 직원번호 조회
            EmpEncryptVO empEncrypt = empEncryptMapper.selectEmpEncryptByOrigEmpNo(origEmpNo);
            
            if (empEncrypt == null) {
                System.out.println("암호화 정보를 찾을 수 없습니다: " + origEmpNo);
                return null;
            }
            
            String encryptEmpNo = empEncrypt.getEncryptEmpNo();
            System.out.println("암호화된 직원번호 조회 성공: " + origEmpNo + " -> " + encryptEmpNo);
            
            // 2. 암호화된 직원번호로 tb_emp_lst 테이블에서 직원정보 조회
            EmpLstVO empInfo = empLstMapper.selectByEmpId(encryptEmpNo);
            
            if (empInfo == null) {
                System.out.println("직원정보를 찾을 수 없습니다: " + encryptEmpNo);
                return null;
            }
            
            System.out.println("직원정보 조회 성공: " + encryptEmpNo);
            return empInfo;
            
        } catch (Exception e) {
            System.err.println("직원정보 조회 중 오류 발생: " + origEmpNo + ", 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 직원번호로 암호화된 직원번호를 조회한 후 tb_emp_lst 테이블의 직원정보를 조회 (상세 정보 포함)
     * @param origEmpNo 원본 직원번호
     * @return 직원정보와 암호화 정보를 포함한 Map - 조회 실패시 null
     */
    public java.util.Map<String, Object> getEmpInfoWithEncryptByOrigEmpNo(String origEmpNo) {
        try {
            // 1. 원본 직원번호로 tb_emp_encrypt 테이블에서 암호화된 직원번호 조회
            EmpEncryptVO empEncrypt = empEncryptMapper.selectEmpEncryptByOrigEmpNo(origEmpNo);
            
            if (empEncrypt == null) {
                System.out.println("암호화 정보를 찾을 수 없습니다: " + origEmpNo);
                return null;
            }
            
            String encryptEmpNo = empEncrypt.getEncryptEmpNo();
            System.out.println("암호화된 직원번호 조회 성공: " + origEmpNo + " -> " + encryptEmpNo);
            
            // 2. 암호화된 직원번호로 tb_emp_lst 테이블에서 직원정보 조회
            EmpLstVO empInfo = empLstMapper.selectByEmpId(encryptEmpNo);
            
            if (empInfo == null) {
                System.out.println("직원정보를 찾을 수 없습니다: " + encryptEmpNo);
                return null;
            }
            
            // 3. 결과를 Map으로 구성하여 반환
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            result.put("empInfo", empInfo);           // tb_emp_lst 정보
            result.put("empEncrypt", empEncrypt);     // tb_emp_encrypt 정보
            result.put("origEmpNo", origEmpNo);       // 원본 직원번호
            result.put("encryptEmpNo", encryptEmpNo); // 암호화된 직원번호
            
            System.out.println("직원정보 조회 성공 (상세): " + origEmpNo);
            return result;
            
        } catch (Exception e) {
            System.err.println("직원정보 조회 중 오류 발생: " + origEmpNo + ", 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 직원번호로 관리자 여부 확인
     * @param empId 직원번호 (암호화된 직원번호)
     * @return 관리자 여부 (true: 관리자, false: 일반 사용자)
     */
    public boolean isAdmin(String empId) {
        try {
            AdminLstVO adminInfo = adminLstMapper.selectByAdminId(empId);
            return adminInfo != null;
        } catch (Exception e) {
            System.err.println("관리자 확인 중 오류 발생: " + empId + ", 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 원본 직원번호로 관리자 여부 확인
     * @param origEmpNo 원본 직원번호
     * @return 관리자 여부 (true: 관리자, false: 일반 사용자)
     */
    public boolean isAdminByOrigEmpNo(String origEmpNo) {
        try {
            // 1. 원본 직원번호로 암호화된 직원번호 조회
            EmpEncryptVO empEncrypt = empEncryptMapper.selectEmpEncryptByOrigEmpNo(origEmpNo);
            
            if (empEncrypt == null) {
                return false;
            }
            
            String encryptEmpNo = empEncrypt.getEncryptEmpNo();
            
            // 2. 암호화된 직원번호로 관리자 여부 확인
            return isAdmin(encryptEmpNo);
            
        } catch (Exception e) {
            System.err.println("원본 직원번호로 관리자 확인 중 오류 발생: " + origEmpNo + ", 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 