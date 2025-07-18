package com.ERI.demo.mappers;

import com.ERI.demo.vo.MthCnslUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MthCnslUseStsMapper {
    List<MthCnslUseStsVO> selectMthCnslUseStsList(MthCnslUseStsVO mthCnslUseStsVO);
    MthCnslUseStsVO selectMthCnslUseSts(@Param("baseYm") String baseYm, @Param("cnslTyCd") String cnslTyCd, @Param("grvncTpcCd") String grvncTpcCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertMthCnslUseSts(MthCnslUseStsVO mthCnslUseStsVO);
    int updateMthCnslUseSts(MthCnslUseStsVO mthCnslUseStsVO);
    int deleteMthCnslUseSts(@Param("baseYm") String baseYm, @Param("cnslTyCd") String cnslTyCd, @Param("grvncTpcCd") String grvncTpcCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 