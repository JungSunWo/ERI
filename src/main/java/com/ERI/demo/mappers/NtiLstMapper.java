package com.ERI.demo.mappers;

import com.ERI.demo.vo.NtiLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NtiLstMapper {

    /**
     * 공지사항 목록 조회 (페이징)
     */
    List<NtiLstVO> selectNoticeList(
            @Param("title") String title,
            @Param("status") String status,
            @Param("offset") int offset,
            @Param("size") int size);

    /**
     * 공지사항 전체 개수 조회
     */
    int selectNoticeCount(
            @Param("title") String title,
            @Param("status") String status);

    /**
     * 공지사항 상세 조회
     */
    NtiLstVO selectNoticeBySeq(@Param("seq") Long seq);

    /**
     * 공지사항 등록
     */
    int insertNotice(NtiLstVO notice);

    /**
     * 공지사항 수정
     */
    int updateNotice(NtiLstVO notice);

    /**
     * 공지사항 삭제 (논리 삭제)
     */
    int deleteNotice(@Param("seq") Long seq, @Param("empId") String empId);

    /**
     * 공지사항 목록 조회 (전체)
     */
    List<NtiLstVO> selectAllNotices();

    /**
     * 활성 공지사항 목록 조회
     */
    List<NtiLstVO> selectActiveNotices();

    // NtiLstService에서 호출하는 메서드들
    
    /**
     * 공지사항 목록 조회 (검색 조건 포함)
     */
    List<NtiLstVO> selectNtiLstList(NtiLstVO ntiLstVO);

    /**
     * 공지사항 상세 조회
     */
    NtiLstVO selectNtiLst(@Param("seq") Long seq);

    /**
     * 공지사항 등록
     */
    int insertNtiLst(NtiLstVO ntiLstVO);

    /**
     * 공지사항 수정
     */
    int updateNtiLst(NtiLstVO ntiLstVO);

    /**
     * 공지사항 삭제
     */
    int deleteNtiLst(@Param("seq") Long seq);

    /**
     * 공지사항 페이징 목록 조회
     */
    List<NtiLstVO> selectNtiLstListWithPaging(NtiLstVO ntiLstVO);

    /**
     * 공지사항 총 개수 조회
     */
    int selectNtiLstCount(NtiLstVO ntiLstVO);
} 