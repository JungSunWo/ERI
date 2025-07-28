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
     * 전체 관리자 목록 조회
     */
    List<AdminLstVO> selectAllAdminLst();
    
    /**
     * 페이징을 위한 관리자 목록 조회
     */
    List<AdminLstVO> selectAdminLstWithPaging(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size, @Param("sort") String sort);
    
    /**
     * 관리자 ID로 관리자 정보 조회
     */
    AdminLstVO selectByAdminId(@Param("adminId") String adminId);
    
    /**
     * 관리자 ID로 관리자 정보 조회 (삭제된 데이터 포함)
     */
    AdminLstVO selectByAdminIdWithDeleted(@Param("adminId") String adminId);
    
    /**
     * 관리자 상태 코드로 관리자 목록 조회
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
     * 관리자 ID로 실제 삭제
     */
    int deleteAdminLstByAdminId(@Param("adminId") String adminId);
    
    /**
     * 관리자 ID로 복구 (실제 삭제에서는 사용하지 않음)
     */
    int restoreAdminLstByAdminId(@Param("adminId") String adminId);
    
    /**
     * 전체 관리자 수 조회
     */
    int countAllAdminLst();
    
    /**
     * 검색 조건에 따른 관리자 수 조회
     */
    int countAdminLstWithSearch(@Param("keyword") String keyword);
    
    /**
     * 관리자 상태 코드별 관리자 수 조회
     */
    int countByAdminStsCd(@Param("adminStsCd") String adminStsCd);
    
    /**
     * 관리자 ID로 관리자 수 조회
     */
    int countByAdminId(@Param("adminId") String adminId);
} 