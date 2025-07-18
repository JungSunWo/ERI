package com.ERI.demo.mappers;

import com.ERI.demo.vo.AuthGrpLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthGrpLstMapper {
    /**
     * 권한 그룹 목록 조회
     */
    List<AuthGrpLstVO> selectAuthGrpLstList(AuthGrpLstVO authGrpLstVO);
    
    /**
     * 권한 그룹 상세 조회
     */
    AuthGrpLstVO selectAuthGrpLst(@Param("authGrpCd") String authGrpCd, @Param("authCd") String authCd);
    
    /**
     * 권한 그룹 등록
     */
    int insertAuthGrpLst(AuthGrpLstVO authGrpLstVO);
    
    /**
     * 권한 그룹 수정
     */
    int updateAuthGrpLst(AuthGrpLstVO authGrpLstVO);
    
    /**
     * 권한 그룹 삭제
     */
    int deleteAuthGrpLst(@Param("authGrpCd") String authGrpCd, @Param("authCd") String authCd);
    
    /**
     * 직원별 권한 그룹 조회
     */
    List<AuthGrpLstVO> selectByEmpId(@Param("empId") String empId);
} 