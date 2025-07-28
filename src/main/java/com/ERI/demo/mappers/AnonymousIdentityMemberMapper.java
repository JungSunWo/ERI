package com.ERI.demo.mappers;

import com.ERI.demo.vo.AnonymousIdentityMemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 익명 사용자 동일성 그룹 멤버 Mapper
 */
@Mapper
public interface AnonymousIdentityMemberMapper {
    
    /**
     * 그룹 멤버 목록 조회
     */
    List<AnonymousIdentityMemberVO> selectMemberListByGroupSeq(@Param("groupSeq") Long groupSeq, @Param("useYn") String useYn);
    
    /**
     * 익명 사용자 ID로 소속 그룹 멤버 조회
     */
    List<AnonymousIdentityMemberVO> selectMemberListByAnonymousId(@Param("anonymousId") Long anonymousId, @Param("useYn") String useYn);
    
    /**
     * 그룹 멤버 등록
     */
    int insertAnonymousIdentityMember(AnonymousIdentityMemberVO anonymousIdentityMemberVO);
    
    /**
     * 그룹 멤버 삭제 (논리 삭제)
     */
    int deleteAnonymousIdentityMember(@Param("memberSeq") Long memberSeq, @Param("delEmpId") String delEmpId);
    
    /**
     * 그룹에서 특정 익명 사용자 제거
     */
    int removeMemberFromGroup(@Param("groupSeq") Long groupSeq, @Param("anonymousId") Long anonymousId, @Param("delEmpId") String delEmpId);
    
    /**
     * 그룹의 모든 멤버 조회 (상세 정보 포함)
     */
    List<AnonymousIdentityMemberVO> selectMemberDetailListByGroupSeq(@Param("groupSeq") Long groupSeq);
    
    /**
     * 익명 사용자가 이미 그룹에 속해있는지 확인
     */
    int checkMemberExists(@Param("groupSeq") Long groupSeq, @Param("anonymousId") Long anonymousId);
} 