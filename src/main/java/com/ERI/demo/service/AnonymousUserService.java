package com.ERI.demo.service;

import com.ERI.demo.mappers.AnonymousUserMapper;
import com.ERI.demo.vo.AnonymousUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnonymousUserService {

    @Autowired
    private AnonymousUserMapper anonymousUserMapper;

    /**
     * 익명 사용자 생성
     * @param nickname 익명 닉네임
     * @return 생성된 익명 사용자 ID
     */
    @Transactional
    public Long createAnonymousUser(String nickname) {
        AnonymousUserVO user = new AnonymousUserVO();
        user.setNickname(nickname);
        
        anonymousUserMapper.insertAnonymousUser(user);
        return user.getAnonymousId();
    }

    /**
     * 익명 사용자 조회
     * @param anonymousId 익명 사용자 ID
     * @return 익명 사용자 정보
     */
    public AnonymousUserVO getAnonymousUser(Long anonymousId) {
        return anonymousUserMapper.selectAnonymousUser(anonymousId);
    }

    /**
     * 닉네임으로 익명 사용자 조회
     * @param nickname 익명 닉네임
     * @return 익명 사용자 정보
     */
    public AnonymousUserVO getAnonymousUserByNickname(String nickname) {
        return anonymousUserMapper.selectAnonymousUserByNickname(nickname);
    }

    /**
     * 익명 사용자 목록 조회
     * @return 익명 사용자 목록
     */
    public List<AnonymousUserVO> getAnonymousUserList() {
        return anonymousUserMapper.selectAnonymousUserList();
    }

    /**
     * 익명 사용자 삭제 (논리 삭제)
     * @param anonymousId 익명 사용자 ID
     */
    @Transactional
    public void deleteAnonymousUser(Long anonymousId) {
        anonymousUserMapper.deleteAnonymousUser(anonymousId);
    }
} 