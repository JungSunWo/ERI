package com.ERI.demo.mappers;

import com.ERI.demo.vo.CounselorScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 상담사 스케줄 Mapper
 */
@Mapper
public interface CounselorScheduleMapper {

    /**
     * 상담사 스케줄 목록 조회
     */
    List<CounselorScheduleVO> selectCounselorScheduleList(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("searchCondition") CounselorScheduleVO searchCondition
    );

    /**
     * 상담사 스케줄 총 개수 조회
     */
    int selectCounselorScheduleCount(@Param("searchCondition") CounselorScheduleVO searchCondition);

    /**
     * 상담사 스케줄 상세 조회
     */
    CounselorScheduleVO selectCounselorScheduleBySeq(@Param("schSeq") Long schSeq);

    /**
     * 상담사별 스케줄 조회
     */
    List<CounselorScheduleVO> selectCounselorScheduleByEmpId(
        @Param("cnslrEmpId") String cnslrEmpId,
        @Param("schDt") LocalDate schDt
    );

    /**
     * 상담사 스케줄 등록
     */
    int insertCounselorSchedule(CounselorScheduleVO counselorSchedule);

    /**
     * 상담사 스케줄 수정
     */
    int updateCounselorSchedule(CounselorScheduleVO counselorSchedule);

    /**
     * 상담사 스케줄 삭제 (논리 삭제)
     */
    int deleteCounselorSchedule(@Param("schSeq") Long schSeq, @Param("empId") String empId);

    /**
     * 상담사별 스케줄 일괄 삭제
     */
    int deleteCounselorScheduleByEmpId(@Param("cnslrEmpId") String cnslrEmpId, @Param("empId") String empId);
} 