package com.ERI.demo.mappers;

import com.ERI.demo.vo.SelfCareBgImgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelfCareBgImgMapper {
    List<SelfCareBgImgVO> selectSelfCareBgImgList(SelfCareBgImgVO selfCareBgImgVO);
    SelfCareBgImgVO selectSelfCareBgImg(String sctStsCd);
    int insertSelfCareBgImg(SelfCareBgImgVO selfCareBgImgVO);
    int updateSelfCareBgImg(SelfCareBgImgVO selfCareBgImgVO);
    int deleteSelfCareBgImg(String sctStsCd);
} 