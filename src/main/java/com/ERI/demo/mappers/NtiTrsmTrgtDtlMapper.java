package com.ERI.demo.mappers;

import com.ERI.demo.vo.NtiTrsmTrgtDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface NtiTrsmTrgtDtlMapper {
    List<NtiTrsmTrgtDtlVO> selectNtiTrsmTrgtDtlList(NtiTrsmTrgtDtlVO ntiTrsmTrgtDtlVO);
    NtiTrsmTrgtDtlVO selectNtiTrsmTrgtDtl(@Param("rgstDt") LocalDate rgstDt, @Param("seq") Long seq, @Param("empId") String empId);
    int insertNtiTrsmTrgtDtl(NtiTrsmTrgtDtlVO ntiTrsmTrgtDtlVO);
    int updateNtiTrsmTrgtDtl(NtiTrsmTrgtDtlVO ntiTrsmTrgtDtlVO);
    int deleteNtiTrsmTrgtDtl(@Param("rgstDt") LocalDate rgstDt, @Param("seq") Long seq, @Param("empId") String empId);
} 