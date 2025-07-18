package com.ERI.demo.mappers;

import com.ERI.demo.vo.DlyMchrUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DlyMchrUseStsMapper {
    List<DlyMchrUseStsVO> selectDlyMchrUseStsList(DlyMchrUseStsVO dlyMchrUseStsVO);
    DlyMchrUseStsVO selectDlyMchrUseSts(@Param("baseDt") LocalDate baseDt, @Param("mchrTyCd") String mchrTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertDlyMchrUseSts(DlyMchrUseStsVO dlyMchrUseStsVO);
    int updateDlyMchrUseSts(DlyMchrUseStsVO dlyMchrUseStsVO);
    int deleteDlyMchrUseSts(@Param("baseDt") LocalDate baseDt, @Param("mchrTyCd") String mchrTyCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 