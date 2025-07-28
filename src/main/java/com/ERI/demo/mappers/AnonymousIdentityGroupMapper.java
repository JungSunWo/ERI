package com.ERI.demo.mappers;

import com.ERI.demo.vo.AnonymousIdentityGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 익명 사용자 동일성 그룹 Mapper
 */
@Mapper
public interface AnonymousIdentityGroupMapper {
    
    /**
     * 익명 사용자 동일성 그룹 목록 조회
     */
    List<AnonymousIdentityGroupVO> selectAnonymousIdentityGroupList(@Param("useYn") String useYn);
    
    /**
     * 익명 사용자 동일성 그룹 상세 조회
     */
    AnonymousIdentityGroupVO selectAnonymousIdentityGroupBySeq(@Param("groupSeq") Long groupSeq);
    
    /**
     * 익명 사용자 동일성 그룹 등록
     */
    int insertAnonymousIdentityGroup(AnonymousIdentityGroupVO anonymousIdentityGroupVO);
    
    /**
     * 익명 사용자 동일성 그룹 수정
     */
    int updateAnonymousIdentityGroup(AnonymousIdentityGroupVO anonymousIdentityGroupVO);
    
    /**
     * 익명 사용자 동일성 그룹 삭제 (논리 삭제)
     */
    int deleteAnonymousIdentityGroup(@Param("groupSeq") Long groupSeq, @Param("delEmpId") String delEmpId);
    
    /**
     * 익명 사용자 ID로 소속 그룹 조회
     */
    List<AnonymousIdentityGroupVO> selectGroupsByAnonymousId(@Param("anonymousId") Long anonymousId);
    
    /**
     * 그룹 멤버 수 조회
     */
    int selectMemberCountByGroupSeq(@Param("groupSeq") Long groupSeq);
} 