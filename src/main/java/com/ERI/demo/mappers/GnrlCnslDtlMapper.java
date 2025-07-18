package com.ERI.demo.mappers;

import com.ERI.demo.vo.GnrlCnslDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface GnrlCnslDtlMapper {
    List<GnrlCnslDtlVO> selectGnrlCnslDtlList(GnrlCnslDtlVO gnrlCnslDtlVO);
    GnrlCnslDtlVO selectGnrlCnslDtl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq, @Param("dtlSeq") Long dtlSeq);
    int insertGnrlCnslDtl(GnrlCnslDtlVO gnrlCnslDtlVO);
    int updateGnrlCnslDtl(GnrlCnslDtlVO gnrlCnslDtlVO);
    int deleteGnrlCnslDtl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq, @Param("dtlSeq") Long dtlSeq);
} 