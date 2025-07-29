package com.ERI.demo.mappers;

import com.ERI.demo.vo.SystemLogVo;
import com.ERI.demo.vo.SystemLogSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 시스템 로그 매퍼
 */
@Mapper
public interface SystemLogMapper {
    
    /**
     * 시스템 로그 삽입
     */
    int insertSystemLog(SystemLogVo systemLogVo);
    
    /**
     * 시스템 로그 조회 (페이징)
     */
    List<SystemLogVo> selectSystemLogs(SystemLogSearchVo searchVo);
    
    /**
     * 시스템 로그 총 개수 조회
     */
    int countSystemLogs(SystemLogSearchVo searchVo);
    
    /**
     * 에러 로그 통계 조회
     */
    List<Map<String, Object>> selectErrorLogStats(@Param("startDate") String startDate, 
                                                  @Param("endDate") String endDate);
    
    /**
     * 오래된 로그 삭제 (자동 정리)
     */
    int deleteOldLogs(@Param("days") int days);
    
    /**
     * 특정 로그 삭제
     */
    int deleteSystemLog(@Param("logSeq") Long logSeq);
    
    /**
     * 로그 레벨별 통계
     */
    List<Map<String, Object>> selectLogLevelStats();
    
    /**
     * 로그 타입별 통계
     */
    List<Map<String, Object>> selectLogTypeStats();
    
    /**
     * 시스템 로그 통계 뷰 조회
     */
    List<Map<String, Object>> selectSystemLogStatsView();
    
    /**
     * 특정 기간 로그 통계
     */
    List<Map<String, Object>> selectLogStatsByPeriod(@Param("startDate") String startDate, 
                                                     @Param("endDate") String endDate);
}