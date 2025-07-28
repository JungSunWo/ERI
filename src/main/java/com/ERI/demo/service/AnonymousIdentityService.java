package com.ERI.demo.service;

import com.ERI.demo.mappers.AnonymousIdentityGroupMapper;
import com.ERI.demo.mappers.AnonymousIdentityMemberMapper;
import com.ERI.demo.vo.AnonymousIdentityGroupVO;
import com.ERI.demo.vo.AnonymousIdentityMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 익명 사용자 동일성 판단 Service
 */
@Service
public class AnonymousIdentityService {
    
    @Autowired
    private AnonymousIdentityGroupMapper anonymousIdentityGroupMapper;
    
    @Autowired
    private AnonymousIdentityMemberMapper anonymousIdentityMemberMapper;
    
    /**
     * 익명 사용자 동일성 그룹 목록 조회
     */
    public List<AnonymousIdentityGroupVO> getAnonymousIdentityGroupList(String useYn) {
        List<AnonymousIdentityGroupVO> groupList = anonymousIdentityGroupMapper.selectAnonymousIdentityGroupList(useYn);
        
        // 각 그룹의 멤버 수 조회
        for (AnonymousIdentityGroupVO group : groupList) {
            int memberCount = anonymousIdentityGroupMapper.selectMemberCountByGroupSeq(group.getGroupSeq());
            group.setMemberCount(memberCount);
        }
        
        return groupList;
    }
    
    /**
     * 익명 사용자 동일성 그룹 상세 조회
     */
    public AnonymousIdentityGroupVO getAnonymousIdentityGroupBySeq(Long groupSeq) {
        return anonymousIdentityGroupMapper.selectAnonymousIdentityGroupBySeq(groupSeq);
    }
    
    /**
     * 익명 사용자 동일성 그룹 등록
     */
    @Transactional
    public boolean createAnonymousIdentityGroup(AnonymousIdentityGroupVO groupVO) {
        try {
            // 기본값 설정
            if (groupVO.getUseYn() == null) groupVO.setUseYn("Y");
            if (groupVO.getDelYn() == null) groupVO.setDelYn("N");
            if (groupVO.getConfidenceLevel() == null) groupVO.setConfidenceLevel("M");
            
            int result = anonymousIdentityGroupMapper.insertAnonymousIdentityGroup(groupVO);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("익명 사용자 동일성 그룹 등록 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 수정
     */
    @Transactional
    public boolean updateAnonymousIdentityGroup(AnonymousIdentityGroupVO groupVO) {
        try {
            groupVO.setUpdDate(LocalDateTime.now());
            int result = anonymousIdentityGroupMapper.updateAnonymousIdentityGroup(groupVO);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("익명 사용자 동일성 그룹 수정 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 삭제 (논리 삭제)
     */
    @Transactional
    public boolean deleteAnonymousIdentityGroup(Long groupSeq, String delEmpId) {
        try {
            int result = anonymousIdentityGroupMapper.deleteAnonymousIdentityGroup(groupSeq, delEmpId);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("익명 사용자 동일성 그룹 삭제 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 그룹 멤버 목록 조회
     */
    public List<AnonymousIdentityMemberVO> getMemberListByGroupSeq(Long groupSeq, String useYn) {
        return anonymousIdentityMemberMapper.selectMemberListByGroupSeq(groupSeq, useYn);
    }
    
    /**
     * 그룹 멤버 상세 목록 조회 (익명 사용자 정보 포함)
     */
    public List<AnonymousIdentityMemberVO> getMemberDetailListByGroupSeq(Long groupSeq) {
        return anonymousIdentityMemberMapper.selectMemberDetailListByGroupSeq(groupSeq);
    }
    
    /**
     * 익명 사용자를 그룹에 추가
     */
    @Transactional
    public boolean addMemberToGroup(AnonymousIdentityMemberVO memberVO) {
        try {
            // 이미 그룹에 속해있는지 확인
            int exists = anonymousIdentityMemberMapper.checkMemberExists(memberVO.getGroupSeq(), memberVO.getAnonymousId());
            if (exists > 0) {
                throw new RuntimeException("이미 해당 그룹에 속해있는 익명 사용자입니다.");
            }
            
            // 기본값 설정
            if (memberVO.getUseYn() == null) memberVO.setUseYn("Y");
            if (memberVO.getDelYn() == null) memberVO.setDelYn("N");
            
            int result = anonymousIdentityMemberMapper.insertAnonymousIdentityMember(memberVO);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("그룹 멤버 추가 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 그룹에서 멤버 제거 (논리 삭제)
     */
    @Transactional
    public boolean removeMemberFromGroup(Long groupSeq, Long anonymousId, String delEmpId) {
        try {
            int result = anonymousIdentityMemberMapper.removeMemberFromGroup(groupSeq, anonymousId, delEmpId);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("그룹 멤버 제거 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 익명 사용자 ID로 소속 그룹 조회
     */
    public List<AnonymousIdentityGroupVO> getGroupsByAnonymousId(Long anonymousId) {
        return anonymousIdentityGroupMapper.selectGroupsByAnonymousId(anonymousId);
    }
    
    /**
     * 익명 사용자 ID로 소속 그룹 멤버 조회
     */
    public List<AnonymousIdentityMemberVO> getMemberListByAnonymousId(Long anonymousId, String useYn) {
        return anonymousIdentityMemberMapper.selectMemberListByAnonymousId(anonymousId, useYn);
    }
} 