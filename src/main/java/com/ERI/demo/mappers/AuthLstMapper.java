package com.ERI.demo.mappers;

import com.ERI.demo.vo.AuthLstVO;
import com.ERI.demo.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthLstMapper {
    
    /**
     * 전체 권한 목록 조회 (페이징)
     */
    List<AuthLstVO> selectAllWithPaging(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 권한 수 조회
     */
    long selectAllCount(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 권한 목록 조회 (페이징 없음)
     */
    List<AuthLstVO> selectAll();
    
    /**
     * 전체 권한 목록 조회 (관리자용)
     */
    List<AuthLstVO> selectAllAuths();
    
    /**
     * 권한 코드로 권한 조회
     */
    AuthLstVO selectByAuthCd(@Param("authCd") String authCd);
    
    /**
     * 권한 등록
     */
    int insert(AuthLstVO authLstVO);
    
    /**
     * 권한 수정
     */
    int update(AuthLstVO authLstVO);
    
    /**
     * 권한 삭제
     */
    int deleteByAuthCd(@Param("authCd") String authCd);
    
    /**
     * 권한 코드 중복 확인
     */
    int countByAuthCd(@Param("authCd") String authCd);
    
    /**
     * 권한레벨별 권한 목록 조회
     */
    List<AuthLstVO> selectByAuthLvl(@Param("authLvl") Integer authLvl);
    
    /**
     * 권한레벨 이상의 권한 목록 조회
     */
    List<AuthLstVO> selectByAuthLvlGreaterThan(@Param("authLvl") Integer authLvl);
} 