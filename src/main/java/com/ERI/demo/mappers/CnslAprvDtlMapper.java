package com.ERI.demo.mappers;

import com.ERI.demo.vo.CnslAprvDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CnslAprvDtlMapper {
    List<CnslAprvDtlVO> selectCnslAprvDtlList(CnslAprvDtlVO cnslAprvDtlVO);
    CnslAprvDtlVO selectCnslAprvDtl(@Param("empId") String empId, @Param("cnslDt") LocalDate cnslDt, @Param("seq") Long seq);
    int insertCnslAprvDtl(CnslAprvDtlVO cnslAprvDtlVO);
    int updateCnslAprvDtl(CnslAprvDtlVO cnslAprvDtlVO);
    int deleteCnslAprvDtl(@Param("empId") String empId, @Param("cnslDt") LocalDate cnslDt, @Param("seq") Long seq);
} 