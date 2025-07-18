package com.ERI.demo.mappers;

import com.ERI.demo.vo.DlyPgmUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DlyPgmUseStsMapper {
    List<DlyPgmUseStsVO> selectDlyPgmUseStsList(DlyPgmUseStsVO dlyPgmUseStsVO);
    DlyPgmUseStsVO selectDlyPgmUseSts(@Param("baseDt") LocalDate baseDt, @Param("pgmTyCd") String pgmTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertDlyPgmUseSts(DlyPgmUseStsVO dlyPgmUseStsVO);
    int updateDlyPgmUseSts(DlyPgmUseStsVO dlyPgmUseStsVO);
    int deleteDlyPgmUseSts(@Param("baseDt") LocalDate baseDt, @Param("pgmTyCd") String pgmTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 