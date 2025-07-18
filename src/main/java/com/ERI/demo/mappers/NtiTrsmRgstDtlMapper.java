package com.ERI.demo.mappers;

import com.ERI.demo.vo.NtiTrsmRgstDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface NtiTrsmRgstDtlMapper {
    List<NtiTrsmRgstDtlVO> selectNtiTrsmRgstDtlList(NtiTrsmRgstDtlVO ntiTrsmRgstDtlVO);
    NtiTrsmRgstDtlVO selectNtiTrsmRgstDtl(@Param("rgstDt") LocalDate rgstDt, @Param("seq") Long seq);
    int insertNtiTrsmRgstDtl(NtiTrsmRgstDtlVO ntiTrsmRgstDtlVO);
    int updateNtiTrsmRgstDtl(NtiTrsmRgstDtlVO ntiTrsmRgstDtlVO);
    int deleteNtiTrsmRgstDtl(@Param("rgstDt") LocalDate rgstDt, @Param("seq") Long seq);
} 