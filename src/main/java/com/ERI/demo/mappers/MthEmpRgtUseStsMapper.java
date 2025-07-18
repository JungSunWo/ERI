package com.ERI.demo.mappers;

import com.ERI.demo.vo.MthEmpRgtUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MthEmpRgtUseStsMapper {
    List<MthEmpRgtUseStsVO> selectMthEmpRgtUseStsList(MthEmpRgtUseStsVO mthEmpRgtUseStsVO);
    MthEmpRgtUseStsVO selectMthEmpRgtUseSts(@Param("baseYm") String baseYm, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertMthEmpRgtUseSts(MthEmpRgtUseStsVO mthEmpRgtUseStsVO);
    int updateMthEmpRgtUseSts(MthEmpRgtUseStsVO mthEmpRgtUseStsVO);
    int deleteMthEmpRgtUseSts(@Param("baseYm") String baseYm, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 