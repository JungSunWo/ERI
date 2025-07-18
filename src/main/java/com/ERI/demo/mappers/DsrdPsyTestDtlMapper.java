package com.ERI.demo.mappers;

import com.ERI.demo.vo.DsrdPsyTestDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DsrdPsyTestDtlMapper {
    List<DsrdPsyTestDtlVO> selectDsrdPsyTestDtlList(DsrdPsyTestDtlVO dsrdPsyTestDtlVO);
    DsrdPsyTestDtlVO selectDsrdPsyTestDtl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq, @Param("psyTestCd") String psyTestCd);
    int insertDsrdPsyTestDtl(DsrdPsyTestDtlVO dsrdPsyTestDtlVO);
    int updateDsrdPsyTestDtl(DsrdPsyTestDtlVO dsrdPsyTestDtlVO);
    int deleteDsrdPsyTestDtl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq, @Param("psyTestCd") String psyTestCd);
} 