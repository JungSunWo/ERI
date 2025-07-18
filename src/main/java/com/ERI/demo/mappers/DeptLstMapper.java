package com.ERI.demo.mappers;

import com.ERI.demo.vo.DeptLstVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptLstMapper {
    List<DeptLstVO> selectDeptLstList(DeptLstVO deptLstVO);
    DeptLstVO selectDeptLst(String deptCd);
    int insertDeptLst(DeptLstVO deptLstVO);
    int updateDeptLst(DeptLstVO deptLstVO);
    int deleteDeptLst(String deptCd);
} 