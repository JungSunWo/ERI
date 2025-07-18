package com.ERI.demo.mappers;

import com.ERI.demo.vo.CnslrLstVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CnslrLstMapper {
    List<CnslrLstVO> selectCnslrLstList(CnslrLstVO cnslrLstVO);
    CnslrLstVO selectCnslrLst(Long seq);
    int insertCnslrLst(CnslrLstVO cnslrLstVO);
    int updateCnslrLst(CnslrLstVO cnslrLstVO);
    int deleteCnslrLst(Long seq);
} 