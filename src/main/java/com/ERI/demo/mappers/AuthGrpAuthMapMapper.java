package com.ERI.demo.mappers;

import com.ERI.demo.vo.AuthGrpAuthMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 권한그룹-권한 매핑 Mapper
 */
@Mapper
public interface AuthGrpAuthMapMapper {
    
    /**
     * 권한그룹-권한 매핑 목록 조회
     */
    List<AuthGrpAuthMapVO> selectAuthGrpAuthMapList();
    
    /**
     * 권한그룹별 권한 목록 조회
     */
    List<AuthGrpAuthMapVO> selectAuthListByAuthGrp(@Param("authGrpCd") String authGrpCd);
    
    /**
     * 권한별 권한그룹 목록 조회
     */
    List<AuthGrpAuthMapVO> selectAuthGrpListByAuth(@Param("authCd") String authCd);
    
    /**
     * 권한그룹-권한 매핑 등록
     */
    int insertAuthGrpAuthMap(AuthGrpAuthMapVO authGrpAuthMap);
    
    /**
     * 권한그룹-권한 매핑 수정
     */
    int updateAuthGrpAuthMap(AuthGrpAuthMapVO authGrpAuthMap);
    
    /**
     * 권한그룹-권한 매핑 논리적 삭제
     */
    int deleteAuthGrpAuthMap(@Param("authGrpCd") String authGrpCd, @Param("authCd") String authCd);
    
    /**
     * 권한그룹-권한 매핑 복구
     */
    int restoreAuthGrpAuthMap(@Param("authGrpCd") String authGrpCd, @Param("authCd") String authCd);
    
    /**
     * 삭제된 권한그룹-권한 매핑 목록 조회
     */
    List<AuthGrpAuthMapVO> selectDeletedAuthGrpAuthMapList();
    
    /**
     * 권한그룹-권한 매핑 존재 여부 확인
     */
    int countAuthGrpAuthMap(@Param("authGrpCd") String authGrpCd, @Param("authCd") String authCd);
} 