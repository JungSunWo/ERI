package com.ERI.demo.mappers;

import com.ERI.demo.vo.CnslAppDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CnslAppDtlMapper {
    List<CnslAppDtlVO> selectCnslAppDtlList(CnslAppDtlVO cnslAppDtlVO);
    CnslAppDtlVO selectCnslAppDtl(@Param("appDt") LocalDate appDt, @Param("seq") Long seq, @Param("empId") String empId);
    int insertCnslAppDtl(CnslAppDtlVO cnslAppDtlVO);
    int updateCnslAppDtl(CnslAppDtlVO cnslAppDtlVO);
    int deleteCnslAppDtl(@Param("appDt") LocalDate appDt, @Param("seq") Long seq, @Param("empId") String empId);
} 