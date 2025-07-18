package com.ERI.demo.mappers;

import com.ERI.demo.vo.RmtCnslVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface RmtCnslMapper {
    List<RmtCnslVO> selectRmtCnslList(RmtCnslVO rmtCnslVO);
    RmtCnslVO selectRmtCnsl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cmtSeq") Long cmtSeq);
    int insertRmtCnsl(RmtCnslVO rmtCnslVO);
    int updateRmtCnsl(RmtCnslVO rmtCnslVO);
    int deleteRmtCnsl(@Param("appDt") LocalDate appDt, @Param("cnslAppSeq") Long cnslAppSeq, @Param("cmtSeq") Long cmtSeq);
} 