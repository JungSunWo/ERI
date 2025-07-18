package com.ERI.demo.mappers;

import com.ERI.demo.vo.GnrlCnslVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface GnrlCnslMapper {
    List<GnrlCnslVO> selectGnrlCnslList(GnrlCnslVO gnrlCnslVO);
    GnrlCnslVO selectGnrlCnsl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq);
    int insertGnrlCnsl(GnrlCnslVO gnrlCnslVO);
    int updateGnrlCnsl(GnrlCnslVO gnrlCnslVO);
    int deleteGnrlCnsl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cnslSeq") Long cnslSeq);
} 