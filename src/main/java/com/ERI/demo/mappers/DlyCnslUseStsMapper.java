package com.ERI.demo.mappers;

import com.ERI.demo.vo.DlyCnslUseStsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DlyCnslUseStsMapper {
    List<DlyCnslUseStsVO> selectDlyCnslUseStsList(DlyCnslUseStsVO dlyCnslUseStsVO);
    DlyCnslUseStsVO selectDlyCnslUseSts(@Param("baseDt") LocalDate baseDt, @Param("cnslTyCd") String cnslTyCd, @Param("grvncTpcCd") String grvncTpcCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
    int insertDlyCnslUseSts(DlyCnslUseStsVO dlyCnslUseStsVO);
    int updateDlyCnslUseSts(DlyCnslUseStsVO dlyCnslUseStsVO);
    int deleteDlyCnslUseSts(@Param("baseDt") LocalDate baseDt, @Param("cnslTyCd") String cnslTyCd, @Param("grvncTpcCd") String grvncTpcCd, @Param("deptCd") String deptCd, @Param("posCd") String posCd, @Param("genderCd") String genderCd);
} 