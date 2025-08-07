package com.ibkcloud.eoc.dao;

import com.ibkcloud.eoc.dao.dto.AdminLstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일명 : AdminLstDao
 * @논리명 : 관리자 목록 DAO
 * @작성자 : 시스템
 * @설명   : 관리자 정보를 처리하는 DAO 인터페이스
 */
@Mapper
public interface AdminLstDao {
    
    /**
     * 전체 관리자 목록 조회
     */
    List<AdminLstDto> selectAllAdmins();
    
    /**
     * 관리자 ID로 관리자 정보 조회
     */
    AdminLstDto selectAdminById(String adminId);
    
    /**
     * 관리자 정보 등록
     */
    int insertAdmin(AdminLstDto admin);
    
    /**
     * 관리자 정보 수정
     */
    int updateAdmin(AdminLstDto admin);
    
    /**
     * 관리자 정보 삭제
     */
    int deleteAdmin(String adminId);
} 