package com.ERI.demo.mappers;

import com.ERI.demo.vo.PgmAprvDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PgmAprvDtlMapper {
    List<PgmAprvDtlVO> selectPgmAprvDtlList(PgmAprvDtlVO pgmAprvDtlVO);
    PgmAprvDtlVO selectPgmAprvDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("trnsDt") LocalDate trnsDt, @Param("seq") Long seq);
    int insertPgmAprvDtl(PgmAprvDtlVO pgmAprvDtlVO);
    int updatePgmAprvDtl(PgmAprvDtlVO pgmAprvDtlVO);
    int deletePgmAprvDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("trnsDt") LocalDate trnsDt, @Param("seq") Long seq);
} 