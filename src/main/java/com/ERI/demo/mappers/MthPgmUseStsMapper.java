package com.ERI.demo.mappers;

import com.ERI.demo.vo.MthPgmUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MthPgmUseStsMapper {
    List<MthPgmUseStsVO> selectMthPgmUseStsList(MthPgmUseStsVO mthPgmUseStsVO);
    MthPgmUseStsVO selectMthPgmUseSts(@Param("baseYm") String baseYm, @Param("pgmTyCd") String pgmTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertMthPgmUseSts(MthPgmUseStsVO mthPgmUseStsVO);
    int updateMthPgmUseSts(MthPgmUseStsVO mthPgmUseStsVO);
    int deleteMthPgmUseSts(@Param("baseYm") String baseYm, @Param("pgmTyCd") String pgmTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 