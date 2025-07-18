package com.ERI.demo.mappers;

import com.ERI.demo.vo.PgmPreAsgnDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PgmPreAsgnDtlMapper {
    List<PgmPreAsgnDtlVO> selectPgmPreAsgnDtlList(PgmPreAsgnDtlVO pgmPreAsgnDtlVO);
    PgmPreAsgnDtlVO selectPgmPreAsgnDtl(@Param("pgmId") String pgmId, @Param("preAsgnSeq") Long preAsgnSeq);
    int insertPgmPreAsgnDtl(PgmPreAsgnDtlVO pgmPreAsgnDtlVO);
    int updatePgmPreAsgnDtl(PgmPreAsgnDtlVO pgmPreAsgnDtlVO);
    int deletePgmPreAsgnDtl(@Param("pgmId") String pgmId, @Param("preAsgnSeq") Long preAsgnSeq);
} 