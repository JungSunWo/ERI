package com.ERI.demo.mappers;

import com.ERI.demo.vo.AnonymousUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnonymousUserMapper {

    /**
     * 익명 사용자 등록
     * @param user 익명 사용자 정보
     */
    void insertAnonymousUser(AnonymousUserVO user);

    /**
     * 익명 사용자 조회
     * @param anonymousId 익명 사용자 ID
     * @return 익명 사용자 정보
     */
    AnonymousUserVO selectAnonymousUser(@Param("anonymousId") Long anonymousId);

    /**
     * 닉네임으로 익명 사용자 조회
     * @param nickname 익명 닉네임
     * @return 익명 사용자 정보
     */
    AnonymousUserVO selectAnonymousUserByNickname(@Param("nickname") String nickname);

    /**
     * 익명 사용자 목록 조회
     * @return 익명 사용자 목록
     */
    List<AnonymousUserVO> selectAnonymousUserList();

    /**
     * 익명 사용자 삭제 (논리 삭제)
     * @param anonymousId 익명 사용자 ID
     */
    void deleteAnonymousUser(@Param("anonymousId") Long anonymousId);
} 