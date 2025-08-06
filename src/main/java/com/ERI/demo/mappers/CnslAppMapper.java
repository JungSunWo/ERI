package com.ERI.demo.mappers;

import com.ERI.demo.vo.CnslAppVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담 신청 Mapper
 */
@Mapper
public interface CnslAppMapper {

    /**
     * 상담 신청 목록 조회
     */
    List<CnslAppVO> selectCnslAppList(CnslAppVO cnslAppVO);

    /**
     * 상담 신청 상세 조회
     */
    CnslAppVO selectCnslApp(@Param("appSeq") Long appSeq);

    /**
     * 상담 신청 등록
     */
    int insertCnslApp(CnslAppVO cnslAppVO);

    /**
     * 상담 신청 수정
     */
    int updateCnslApp(CnslAppVO cnslAppVO);

    /**
     * 상담 신청 삭제 (논리 삭제)
     */
    int deleteCnslApp(@Param("appSeq") Long appSeq, @Param("empId") String empId);
} 