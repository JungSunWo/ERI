package com.ERI.demo.mappers;

import com.ERI.demo.vo.EmpRefVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원 참조 정보 Mapper
 * TB_EMP_REF 테이블 관련 데이터베이스 작업
 */
@Mapper
public interface EmpRefMapper {
    
    /**
     * 전체 직원 참조 목록 조회
     */
    List<EmpRefVO> selectAllEmpRef();
    
    /**
     * 페이징을 위한 직원 참조 목록 조회
     */
    List<EmpRefVO> selectEmpRefWithPaging(@Param("offset") int offset, @Param("size") int size, @Param("sort") String sort);
    
    /**
     * ERI_EMP_ID로 직원 참조 정보 조회
     */
    EmpRefVO selectByEriEmpId(@Param("eriEmpId") String eriEmpId);
    
    /**
     * 직원명으로 직원 참조 정보 검색
     */
    List<EmpRefVO> selectByEmpNm(@Param("empNm") String empNm);
    
    /**
     * 소속부점코드로 직원 참조 목록 조회
     */
    List<EmpRefVO> selectByBlngBrcd(@Param("blngBrcd") String blngBrcd);
    
    /**
     * 재직여부로 직원 참조 목록 조회
     */
    List<EmpRefVO> selectByHlofYn(@Param("hlofYn") String hlofYn);
    
    /**
     * 직원 참조 정보 등록
     */
    int insertEmpRef(EmpRefVO empRef);
    
    /**
     * 직원 참조 정보 수정
     */
    int updateEmpRef(EmpRefVO empRef);
    
    /**
     * ERI_EMP_ID로 실제 삭제
     */
    int deleteEmpRefByEriEmpId(@Param("eriEmpId") String eriEmpId);
    
    /**
     * 전체 직원 참조 수 조회
     */
    int countAllEmpRef();
    
    /**
     * 조건별 직원 참조 수 조회
     */
    int countEmpRefByCondition(@Param("hlofYn") String hlofYn, @Param("blngBrcd") String blngBrcd);
    
    /**
     * ERI_EMP_ID로 직원 참조 수 조회
     */
    int countByEriEmpId(@Param("eriEmpId") String eriEmpId);
} 