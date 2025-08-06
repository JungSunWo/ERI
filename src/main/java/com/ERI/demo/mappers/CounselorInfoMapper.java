package com.ERI.demo.mappers;

import com.ERI.demo.vo.CounselorInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상담사 정보 Mapper
 */
@Mapper
public interface CounselorInfoMapper {

    /**
     * 상담사 정보 목록 조회
     */
    List<CounselorInfoVO> selectCounselorInfoList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") CounselorInfoVO searchCondition
    );

    /**
     * 상담사 정보 총 개수 조회
     */
    int selectCounselorInfoCount(@Param("searchCondition") CounselorInfoVO searchCondition);

    /**
     * 상담사 정보 상세 조회
     */
    CounselorInfoVO selectCounselorInfoByEmpId(@Param("cnslrEmpId") String cnslrEmpId);

    /**
     * 상담사 정보 등록
     */
    int insertCounselorInfo(CounselorInfoVO counselorInfo);

    /**
     * 상담사 정보 수정
     */
    int updateCounselorInfo(CounselorInfoVO counselorInfo);

    /**
     * 상담사 정보 삭제 (논리 삭제)
     */
    int deleteCounselorInfo(@Param("cnslrEmpId") String cnslrEmpId, @Param("empId") String empId);

    /**
     * 상담 가능한 상담사 목록 조회
     */
    List<CounselorInfoVO> selectAvailableCounselorList();
} 