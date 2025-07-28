package com.ERI.demo.mappers;

import com.ERI.demo.vo.MnuLstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MnuLstMapper {
    
    /**
     * 메뉴 목록 조회 (계층 구조)
     */
    List<MnuLstVO> selectMenuList();
    
    /**
     * 전체 메뉴 목록 조회 (계층 구조)
     */
    List<MnuLstVO> selectAllMenus(@Param("isAdmin") boolean isAdmin, @Param("isCounselor") boolean isCounselor);
    
    /**
     * 메뉴 목록 조회 (페이징)
     */
    List<MnuLstVO> selectMenuListWithPaging(@Param("offset") int offset, 
                                           @Param("limit") int limit,
                                           @Param("searchKeyword") String searchKeyword,
                                           @Param("mnuLvl") Integer mnuLvl,
                                           @Param("mnuUseYn") String mnuUseYn,
                                           @Param("mnuAdminYn") String mnuAdminYn);
    
    /**
     * 메뉴 총 개수 조회
     */
    int selectMenuCount(@Param("searchKeyword") String searchKeyword,
                       @Param("mnuLvl") Integer mnuLvl,
                       @Param("mnuUseYn") String mnuUseYn,
                       @Param("mnuAdminYn") String mnuAdminYn);
    
    /**
     * 메뉴 상세 조회
     */
    MnuLstVO selectMenuByCd(@Param("mnuCd") String mnuCd);
    
    /**
     * 상위 메뉴 목록 조회 (1depth 메뉴만)
     */
    List<MnuLstVO> selectParentMenuList();
    
    /**
     * 하위 메뉴 목록 조회
     */
    List<MnuLstVO> selectSubMenuList(@Param("pMnuCd") String pMnuCd);
    
    /**
     * 메뉴 등록
     */
    int insertMenu(MnuLstVO mnuLstVO);
    
    /**
     * 메뉴 수정
     */
    int updateMenu(MnuLstVO mnuLstVO);
    
    /**
     * 메뉴 삭제 (논리 삭제)
     */
    int deleteMenu(@Param("mnuCd") String mnuCd, 
                  @Param("updEmpId") String updEmpId);
    
    /**
     * 메뉴 순서 변경
     */
    int updateMenuOrder(@Param("mnuCd") String mnuCd, 
                       @Param("mnuOrd") Integer mnuOrd,
                       @Param("updEmpId") String updEmpId);
    
    /**
     * 메뉴 코드 중복 확인
     */
    int checkMenuCdExists(@Param("mnuCd") String mnuCd);
    
    /**
     * 사용자별 접근 가능한 메뉴 목록 조회
     */
    List<MnuLstVO> selectUserAccessibleMenus(@Param("empId") String empId,
                                            @Param("isAdmin") boolean isAdmin);
} 