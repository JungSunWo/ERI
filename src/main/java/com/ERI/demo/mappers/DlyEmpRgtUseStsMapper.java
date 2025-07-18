package com.ERI.demo.mappers;

import com.ERI.demo.vo.DlyEmpRgtUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DlyEmpRgtUseStsMapper {
    List<DlyEmpRgtUseStsVO> selectDlyEmpRgtUseStsList(DlyEmpRgtUseStsVO dlyEmpRgtUseStsVO);
    DlyEmpRgtUseStsVO selectDlyEmpRgtUseSts(@Param("baseDt") LocalDate baseDt, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertDlyEmpRgtUseSts(DlyEmpRgtUseStsVO dlyEmpRgtUseStsVO);
    int updateDlyEmpRgtUseSts(DlyEmpRgtUseStsVO dlyEmpRgtUseStsVO);
    int deleteDlyEmpRgtUseSts(@Param("baseDt") LocalDate baseDt, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 