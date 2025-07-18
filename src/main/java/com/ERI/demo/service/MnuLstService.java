package com.ERI.demo.service;

import com.ERI.demo.mappers.MnuLstMapper;
import com.ERI.demo.vo.MnuLstVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MnuLstService {

    private final MnuLstMapper mnuLstMapper;

    /**
     * 메뉴 목록 조회 (계층 구조)
     */
    public List<MnuLstVO> getMenuList() {
        List<MnuLstVO> menuList = mnuLstMapper.selectMenuList();
        return formatMenuList(menuList);
    }

    /**
     * 전체 메뉴 목록 조회 (계층 구조)
     */
    public List<MnuLstVO> getAllMenus(boolean isAdmin) {
        List<MnuLstVO> menuList = mnuLstMapper.selectAllMenus(isAdmin);
        return formatMenuList(menuList);
    }

    /**
     * 메뉴 목록 조회 (페이징)
     */
    public List<MnuLstVO> getMenuListWithPaging(int page, int size, String searchKeyword, 
                                               Integer mnuLvl, String mnuUseYn, String mnuAdminYn) {
        int offset = (page - 1) * size;
        List<MnuLstVO> menuList = mnuLstMapper.selectMenuListWithPaging(offset, size, searchKeyword, mnuLvl, mnuUseYn, mnuAdminYn);
        return formatMenuList(menuList);
    }

    /**
     * 메뉴 총 개수 조회
     */
    public int getMenuCount(String searchKeyword, Integer mnuLvl, String mnuUseYn, String mnuAdminYn) {
        return mnuLstMapper.selectMenuCount(searchKeyword, mnuLvl, mnuUseYn, mnuAdminYn);
    }

    /**
     * 메뉴 상세 조회
     */
    public MnuLstVO getMenuByCd(String mnuCd) {
        MnuLstVO menu = mnuLstMapper.selectMenuByCd(mnuCd);
        if (menu != null) {
            formatMenuDisplay(menu);
        }
        return menu;
    }

    /**
     * 상위 메뉴 목록 조회 (1depth 메뉴만)
     */
    public List<MnuLstVO> getParentMenuList() {
        List<MnuLstVO> menuList = mnuLstMapper.selectParentMenuList();
        return formatMenuList(menuList);
    }

    /**
     * 하위 메뉴 목록 조회
     */
    public List<MnuLstVO> getSubMenuList(String pMnuCd) {
        List<MnuLstVO> menuList = mnuLstMapper.selectSubMenuList(pMnuCd);
        return formatMenuList(menuList);
    }

    /**
     * 메뉴 등록
     */
    @Transactional
    public boolean insertMenu(MnuLstVO mnuLstVO, String empId) {
        try {
            // 메뉴 코드 중복 확인
            if (mnuLstMapper.checkMenuCdExists(mnuLstVO.getMnuCd()) > 0) {
                throw new IllegalArgumentException("이미 존재하는 메뉴 코드입니다: " + mnuLstVO.getMnuCd());
            }

            // 필수 필드 설정
            mnuLstVO.setRgstEmpId(empId);
            mnuLstVO.setUpdtEmpId(empId);
            mnuLstVO.setRegEmpId(empId);
            mnuLstVO.setUpdEmpId(empId);

            // 메뉴 레벨이 2인 경우 상위 메뉴 코드 필수
            if (mnuLstVO.getMnuLvl() == 2 && (mnuLstVO.getPMnuCd() == null || mnuLstVO.getPMnuCd().trim().isEmpty())) {
                throw new IllegalArgumentException("하위 메뉴는 상위 메뉴 코드가 필요합니다.");
            }

            int result = mnuLstMapper.insertMenu(mnuLstVO);
            return result > 0;
        } catch (Exception e) {
            log.error("메뉴 등록 실패", e);
            throw e;
        }
    }

    /**
     * 메뉴 수정
     */
    @Transactional
    public boolean updateMenu(MnuLstVO mnuLstVO, String empId) {
        try {
            // 기존 메뉴 확인
            MnuLstVO existingMenu = mnuLstMapper.selectMenuByCd(mnuLstVO.getMnuCd());
            if (existingMenu == null) {
                throw new IllegalArgumentException("존재하지 않는 메뉴입니다: " + mnuLstVO.getMnuCd());
            }

            // 메뉴 레벨이 2인 경우 상위 메뉴 코드 필수
            if (mnuLstVO.getMnuLvl() == 2 && (mnuLstVO.getPMnuCd() == null || mnuLstVO.getPMnuCd().trim().isEmpty())) {
                throw new IllegalArgumentException("하위 메뉴는 상위 메뉴 코드가 필요합니다.");
            }

            // 자기 자신을 상위 메뉴로 설정하는 것 방지
            if (mnuLstVO.getMnuCd().equals(mnuLstVO.getPMnuCd())) {
                throw new IllegalArgumentException("자기 자신을 상위 메뉴로 설정할 수 없습니다.");
            }

            mnuLstVO.setUpdtEmpId(empId);
            mnuLstVO.setUpdEmpId(empId);

            int result = mnuLstMapper.updateMenu(mnuLstVO);
            return result > 0;
        } catch (Exception e) {
            log.error("메뉴 수정 실패", e);
            throw e;
        }
    }

    /**
     * 메뉴 삭제
     */
    @Transactional
    public boolean deleteMenu(String mnuCd, String empId) {
        try {
            // 하위 메뉴가 있는지 확인
            List<MnuLstVO> subMenus = mnuLstMapper.selectSubMenuList(mnuCd);
            if (!subMenus.isEmpty()) {
                throw new IllegalArgumentException("하위 메뉴가 존재하여 삭제할 수 없습니다. 하위 메뉴를 먼저 삭제해주세요.");
            }

            int result = mnuLstMapper.deleteMenu(mnuCd, empId);
            return result > 0;
        } catch (Exception e) {
            log.error("메뉴 삭제 실패", e);
            throw e;
        }
    }

    /**
     * 메뉴 순서 변경
     */
    @Transactional
    public boolean updateMenuOrder(String mnuCd, Integer mnuOrd, String empId) {
        try {
            int result = mnuLstMapper.updateMenuOrder(mnuCd, mnuOrd, empId);
            return result > 0;
        } catch (Exception e) {
            log.error("메뉴 순서 변경 실패", e);
            throw e;
        }
    }

    /**
     * 사용자별 접근 가능한 메뉴 목록 조회
     */
    public List<MnuLstVO> getUserAccessibleMenus(String empId, boolean isAdmin) {
        List<MnuLstVO> menuList = mnuLstMapper.selectUserAccessibleMenus(empId, isAdmin);
        return formatMenuList(menuList);
    }

    /**
     * 메뉴 목록 포맷팅
     */
    private List<MnuLstVO> formatMenuList(List<MnuLstVO> menuList) {
        return menuList.stream()
                .peek(this::formatMenuDisplay)
                .collect(Collectors.toList());
    }

    /**
     * 메뉴 표시용 필드 포맷팅
     */
    private void formatMenuDisplay(MnuLstVO menu) {
        // 메뉴 레벨 텍스트
        if (menu.getMnuLvl() != null) {
            menu.setMnuLvlText(menu.getMnuLvl() == 1 ? "대메뉴" : "서브메뉴");
        }

        // 사용여부 텍스트
        if (menu.getMnuUseYn() != null) {
            menu.setMnuUseYnText("Y".equals(menu.getMnuUseYn()) ? "사용" : "미사용");
        }

        // 관리자전용여부 텍스트
        if (menu.getMnuAdminYn() != null) {
            menu.setMnuAdminYnText("Y".equals(menu.getMnuAdminYn()) ? "관리자전용" : "일반사용자");
        }

        // 등록일 텍스트
        if (menu.getRegDate() != null) {
            menu.setRegDateText(menu.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        // 수정일 텍스트
        if (menu.getUpdDate() != null) {
            menu.setUpdDateText(menu.getUpdDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }
} 