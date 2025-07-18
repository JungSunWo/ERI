package com.ERI.demo.mappers;

import com.ERI.demo.vo.ChlgGoalAchvDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ChlgGoalAchvDtlMapper {
    List<ChlgGoalAchvDtlVO> selectChlgGoalAchvDtlList(ChlgGoalAchvDtlVO chlgGoalAchvDtlVO);
    ChlgGoalAchvDtlVO selectChlgGoalAchvDtl(@Param("empId") String empId, @Param("seq") Long seq, @Param("baseDt") LocalDate baseDt);
    int insertChlgGoalAchvDtl(ChlgGoalAchvDtlVO chlgGoalAchvDtlVO);
    int updateChlgGoalAchvDtl(ChlgGoalAchvDtlVO chlgGoalAchvDtlVO);
    int deleteChlgGoalAchvDtl(@Param("empId") String empId, @Param("seq") Long seq, @Param("baseDt") LocalDate baseDt);
} 