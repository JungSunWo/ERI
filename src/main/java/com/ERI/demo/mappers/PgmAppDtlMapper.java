package com.ERI.demo.mappers;

import com.ERI.demo.vo.PgmAppDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PgmAppDtlMapper {
    List<PgmAppDtlVO> selectPgmAppDtlList(PgmAppDtlVO pgmAppDtlVO);
    PgmAppDtlVO selectPgmAppDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("appDt") LocalDate appDt);
    int insertPgmAppDtl(PgmAppDtlVO pgmAppDtlVO);
    int updatePgmAppDtl(PgmAppDtlVO pgmAppDtlVO);
    int deletePgmAppDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("appDt") LocalDate appDt);
} 