package com.ERI.demo.mappers;

import com.ERI.demo.vo.MthMchrUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MthMchrUseStsMapper {
    List<MthMchrUseStsVO> selectMthMchrUseStsList(MthMchrUseStsVO mthMchrUseStsVO);
    MthMchrUseStsVO selectMthMchrUseSts(@Param("baseYm") String baseYm, @Param("mchrTyCd") String mchrTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertMthMchrUseSts(MthMchrUseStsVO mthMchrUseStsVO);
    int updateMthMchrUseSts(MthMchrUseStsVO mthMchrUseStsVO);
    int deleteMthMchrUseSts(@Param("baseYm") String baseYm, @Param("mchrTyCd") String mchrTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 