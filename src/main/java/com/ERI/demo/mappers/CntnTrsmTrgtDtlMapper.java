package com.ERI.demo.mappers;

import com.ERI.demo.vo.CntnTrsmTrgtDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CntnTrsmTrgtDtlMapper {
    List<CntnTrsmTrgtDtlVO> selectCntnTrsmTrgtDtlList(CntnTrsmTrgtDtlVO cntnTrsmTrgtDtlVO);
    CntnTrsmTrgtDtlVO selectCntnTrsmTrgtDtl(@Param("pgmId") String pgmId, @Param("cntnSeq") Long cntnSeq, @Param("empId") String empId);
    int insertCntnTrsmTrgtDtl(CntnTrsmTrgtDtlVO cntnTrsmTrgtDtlVO);
    int updateCntnTrsmTrgtDtl(CntnTrsmTrgtDtlVO cntnTrsmTrgtDtlVO);
    int deleteCntnTrsmTrgtDtl(@Param("pgmId") String pgmId, @Param("cntnSeq") Long cntnSeq, @Param("empId") String empId);
} 