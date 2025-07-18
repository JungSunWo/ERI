package com.ERI.demo.mappers;

import com.ERI.demo.vo.CmnGrpCdVO;
import com.ERI.demo.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 공통 그룹 코드 Mapper
 */
@Mapper
public interface CmnGrpCdMapper {
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징)
     */
    List<CmnGrpCdVO> selectAllWithPaging(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 공통 그룹 코드 수 조회
     */
    long selectAllCount(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징 없음)
     */
    List<CmnGrpCdVO> selectAll();
    
    /**
     * 그룹 코드로 공통 그룹 코드 조회
     */
    CmnGrpCdVO selectByGrpCd(@Param("grpCd") String grpCd);
    
    /**
     * 공통 그룹 코드 등록
     */
    int insert(CmnGrpCdVO cmnGrpCdVO);
    
    /**
     * 공통 그룹 코드 수정
     */
    int update(CmnGrpCdVO cmnGrpCdVO);
    
    /**
     * 공통 그룹 코드 삭제
     */
    int deleteByGrpCd(@Param("grpCd") String grpCd);
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징)
     */
    List<CmnGrpCdVO> selectByUseYnWithPaging(@Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 사용여부로 공통 그룹 코드 수 조회
     */
    long selectByUseYnCount(@Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징 없음)
     */
    List<CmnGrpCdVO> selectByUseYn(@Param("useYn") String useYn);
    

} 