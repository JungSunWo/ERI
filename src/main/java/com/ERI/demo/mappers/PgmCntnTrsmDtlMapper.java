package com.ERI.demo.mappers;

import com.ERI.demo.vo.PgmCntnTrsmDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PgmCntnTrsmDtlMapper {
    List<PgmCntnTrsmDtlVO> selectPgmCntnTrsmDtlList(PgmCntnTrsmDtlVO pgmCntnTrsmDtlVO);
    PgmCntnTrsmDtlVO selectPgmCntnTrsmDtl(@Param("pgmId") String pgmId, @Param("cntnSeq") Long cntnSeq);
    int insertPgmCntnTrsmDtl(PgmCntnTrsmDtlVO pgmCntnTrsmDtlVO);
    int updatePgmCntnTrsmDtl(PgmCntnTrsmDtlVO pgmCntnTrsmDtlVO);
    int deletePgmCntnTrsmDtl(@Param("pgmId") String pgmId, @Param("cntnSeq") Long cntnSeq);
} 