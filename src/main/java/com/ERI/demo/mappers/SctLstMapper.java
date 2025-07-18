package com.ERI.demo.mappers;

import com.ERI.demo.vo.SctLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SctLstMapper {
    List<SctLstVO> selectSctLstList(SctLstVO sctLstVO);
    SctLstVO selectSctLst(@Param("empId") String empId, @Param("sctCd") String sctCd);
    int insertSctLst(SctLstVO sctLstVO);
    int updateSctLst(SctLstVO sctLstVO);
    int deleteSctLst(@Param("empId") String empId, @Param("sctCd") String sctCd);
} 