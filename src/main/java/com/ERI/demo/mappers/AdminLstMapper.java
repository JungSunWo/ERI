package com.ERI.demo.mappers;

import com.ERI.demo.vo.AdminLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 관리자 목록 Mapper
 * TB_ADMIN_LST 테이블 관련 데이터베이스 작업
 */
@Mapper
public interface AdminLstMapper {
    
    /**
     * 전체 관리자 목록 조회 (삭제되지 않은 데이터만)
     */
    List<AdminLstVO> selectAllAdminLst();
    
    /**
     * 관리자 ID로 관리자 정보 조회 (삭제되지 않은 데이터만)
     */
    AdminLstVO selectByAdminId(@Param("adminId") String adminId);
    
    /**
     * 관리자 ID로 관리자 정보 조회 (삭제된 데이터 포함)
     */
    AdminLstVO selectByAdminIdWithDeleted(@Param("adminId") String adminId);
    
    /**
     * 관리자 상태 코드로 관리자 목록 조회 (삭제되지 않은 데이터만)
     */
    List<AdminLstVO> selectByAdminStsCd(@Param("adminStsCd") String adminStsCd);
    
    /**
     * 관리자 정보 등록
     */
    int insertAdminLst(AdminLstVO adminLst);
    
    /**
     * 관리자 정보 수정
     */
    int updateAdminLst(AdminLstVO adminLst);
    
    /**
     * 관리자 ID로 논리적 삭제
     */
    int deleteAdminLstByAdminId(@Param("adminId") String adminId);
    
    /**
     * 관리자 ID로 복구
     */
    int restoreAdminLstByAdminId(@Param("adminId") String adminId);
    
    /**
     * 전체 관리자 수 조회 (삭제되지 않은 데이터만)
     */
    int countAllAdminLst();
    
    /**
     * 관리자 상태 코드별 관리자 수 조회 (삭제되지 않은 데이터만)
     */
    int countByAdminStsCd(@Param("adminStsCd") String adminStsCd);
} 