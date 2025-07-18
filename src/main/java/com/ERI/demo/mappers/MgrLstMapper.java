package com.ERI.demo.mappers;

import com.ERI.demo.vo.MgrLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MgrLstMapper {
    
    /**
     * 관리자 목록 조회 - 삭제되지 않은 데이터만
     */
    List<MgrLstVO> selectMgrLstList(MgrLstVO mgrLstVO);
    
    /**
     * 관리자 상세 조회 - 삭제되지 않은 데이터만
     */
    MgrLstVO selectMgrLst(String mgrEmpId);
    
    /**
     * 관리자 등록
     */
    int insertMgrLst(MgrLstVO mgrLstVO);
    
    /**
     * 관리자 수정
     */
    int updateMgrLst(MgrLstVO mgrLstVO);
    
    /**
     * 관리자 논리적 삭제
     */
    int deleteMgrLst(String mgrEmpId);
    
    /**
     * 관리자 삭제 복구
     */
    int restoreMgrLst(String mgrEmpId);
    
    /**
     * 삭제된 관리자 목록 조회
     */
    List<MgrLstVO> selectDeletedMgrLstList(MgrLstVO mgrLstVO);
    
    /**
     * 삭제된 관리자 상세 조회
     */
    MgrLstVO selectDeletedMgrLst(String mgrEmpId);
} 