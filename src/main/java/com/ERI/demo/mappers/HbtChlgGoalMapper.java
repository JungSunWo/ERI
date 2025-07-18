package com.ERI.demo.mappers;

import com.ERI.demo.vo.HbtChlgGoalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HbtChlgGoalMapper {
    List<HbtChlgGoalVO> selectHbtChlgGoalList(HbtChlgGoalVO hbtChlgGoalVO);
    HbtChlgGoalVO selectHbtChlgGoal(@Param("empId") String empId, @Param("seq") Long seq);
    int insertHbtChlgGoal(HbtChlgGoalVO hbtChlgGoalVO);
    int updateHbtChlgGoal(HbtChlgGoalVO hbtChlgGoalVO);
    int deleteHbtChlgGoal(@Param("empId") String empId, @Param("seq") Long seq);
} 