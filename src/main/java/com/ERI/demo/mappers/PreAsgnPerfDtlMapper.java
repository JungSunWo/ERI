package com.ERI.demo.mappers;

import com.ERI.demo.vo.PreAsgnPerfDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PreAsgnPerfDtlMapper {
    List<PreAsgnPerfDtlVO> selectPreAsgnPerfDtlList(PreAsgnPerfDtlVO preAsgnPerfDtlVO);
    PreAsgnPerfDtlVO selectPreAsgnPerfDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("preAsgnSeq") Long preAsgnSeq);
    int insertPreAsgnPerfDtl(PreAsgnPerfDtlVO preAsgnPerfDtlVO);
    int updatePreAsgnPerfDtl(PreAsgnPerfDtlVO preAsgnPerfDtlVO);
    int deletePreAsgnPerfDtl(@Param("empId") String empId, @Param("pgmId") String pgmId, @Param("preAsgnSeq") Long preAsgnSeq);
} 