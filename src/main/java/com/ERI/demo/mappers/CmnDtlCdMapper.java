package com.ERI.demo.mappers;

import com.ERI.demo.vo.CmnDtlCdVO;
import com.ERI.demo.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 공통 상세 코드 Mapper
 */
@Mapper
public interface CmnDtlCdMapper {
    
    /**
     * 전체 공통 상세 코드 조회 (페이징)
     */
    List<CmnDtlCdVO> selectAllWithPaging(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 공통 상세 코드 수 조회
     */
    long selectAllCount(@Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 전체 공통 상세 코드 조회 (페이징 없음)
     */
    List<CmnDtlCdVO> selectAll();
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징)
     */
    List<CmnDtlCdVO> selectByGrpCdWithPaging(@Param("grpCd") String grpCd, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 그룹 코드로 공통 상세 코드 수 조회
     */
    long selectByGrpCdCount(@Param("grpCd") String grpCd, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징 없음)
     */
    List<CmnDtlCdVO> selectByGrpCd(@Param("grpCd") String grpCd);
    
    /**
     * 그룹 코드와 상세 코드로 공통 상세 코드 조회
     */
    CmnDtlCdVO selectByGrpCdAndDtlCd(@Param("grpCd") String grpCd, @Param("dtlCd") String dtlCd);
    
    /**
     * 공통 상세 코드 등록
     */
    int insert(CmnDtlCdVO cmnDtlCdVO);
    
    /**
     * 공통 상세 코드 수정
     */
    int update(CmnDtlCdVO cmnDtlCdVO);
    
    /**
     * 공통 상세 코드 삭제
     */
    int deleteByGrpCdAndDtlCd(@Param("grpCd") String grpCd, @Param("dtlCd") String dtlCd);
    
    /**
     * 그룹 코드로 공통 상세 코드 삭제
     */
    int deleteByGrpCd(@Param("grpCd") String grpCd);
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징)
     */
    List<CmnDtlCdVO> selectByUseYnWithPaging(@Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 사용여부로 공통 상세 코드 수 조회
     */
    long selectByUseYnCount(@Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징 없음)
     */
    List<CmnDtlCdVO> selectByUseYn(@Param("useYn") String useYn);
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징)
     */
    List<CmnDtlCdVO> selectByGrpCdAndUseYnWithPaging(@Param("grpCd") String grpCd, @Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 수 조회
     */
    long selectByGrpCdAndUseYnCount(@Param("grpCd") String grpCd, @Param("useYn") String useYn, @Param("pageRequest") PageRequestDto pageRequest);
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징 없음)
     */
    List<CmnDtlCdVO> selectByGrpCdAndUseYn(@Param("grpCd") String grpCd, @Param("useYn") String useYn);
    

} 