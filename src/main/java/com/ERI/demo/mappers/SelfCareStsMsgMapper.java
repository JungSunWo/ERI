package com.ERI.demo.mappers;

import com.ERI.demo.vo.SelfCareStsMsgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelfCareStsMsgMapper {
    List<SelfCareStsMsgVO> selectSelfCareStsMsgList(SelfCareStsMsgVO selfCareStsMsgVO);
    SelfCareStsMsgVO selectSelfCareStsMsg(String sctStsCd);
    int insertSelfCareStsMsg(SelfCareStsMsgVO selfCareStsMsgVO);
    int updateSelfCareStsMsg(SelfCareStsMsgVO selfCareStsMsgVO);
    int deleteSelfCareStsMsg(String sctStsCd);
} 