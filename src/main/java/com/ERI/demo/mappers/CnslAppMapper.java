package com.ERI.demo.mappers;

import com.ERI.demo.vo.CnslAppVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CnslAppMapper {
    List<CnslAppVO> selectCnslAppList(CnslAppVO cnslAppVO);
    CnslAppVO selectCnslApp(@Param("appDt") LocalDate appDt, @Param("seq") Long seq);
    int insertCnslApp(CnslAppVO cnslAppVO);
    int updateCnslApp(CnslAppVO cnslAppVO);
    int deleteCnslApp(@Param("appDt") LocalDate appDt, @Param("seq") Long seq);
} 