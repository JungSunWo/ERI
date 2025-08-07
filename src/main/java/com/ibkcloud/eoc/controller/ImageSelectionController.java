package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.ImageSelectionService;
import com.ibkcloud.eoc.controller.vo.ImageSelectionInVo;
import com.ibkcloud.eoc.controller.vo.ImageSelectionOutVo;
import com.ibkcloud.eoc.controller.vo.ImageSelectionSearchInVo;
import com.ibkcloud.eoc.controller.vo.ImageSelectionSearchOutVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 이미지 선택 REST 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/image-selection")
@RequiredArgsConstructor
public class ImageSelectionController {

    private final ImageSelectionService imageSelectionService;

    /**
     * 이미지 선택 목록 조회 (페이징/검색)
     */
    @GetMapping("/list")
    public ResponseEntity<ImageSelectionSearchOutVo> inqImageSelectionList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long imgBrdSeq,
            @RequestParam(required = false) Long imgFileSeq,
            @RequestParam(required = false) String selEmpId,
            @RequestParam(defaultValue = "SEL_DT") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        try {
            ImageSelectionSearchInVo searchVo = new ImageSelectionSearchInVo();
            searchVo.setPageNo(pageNo);
            searchVo.setPageSize(pageSize);
            searchVo.setSearchType(searchType);
            searchVo.setSearchKeyword(searchKeyword);
            searchVo.setImgBrdSeq(imgBrdSeq);
            searchVo.setImgFileSeq(imgFileSeq);
            searchVo.setSelEmpId(selEmpId);
            searchVo.setSortBy(sortBy);
            searchVo.setSortDirection(sortDirection);

            Page<ImageSelectionOutVo> result = imageSelectionService.inqImageSelectionList(searchVo);

            ImageSelectionSearchOutVo response = new ImageSelectionSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents());
            response.setCount(result.getTtalDataNbi());
            response.setPageNo(result.getPageNo());
            response.setPageSize(result.getPageSize());
            response.setTtalPageNbi(result.getTtalPageNbi());
            response.setSearchType(searchType);
            response.setSearchKeyword(searchKeyword);
            response.setImgBrdSeq(imgBrdSeq);
            response.setImgFileSeq(imgFileSeq);
            response.setSelEmpId(selEmpId);
            response.setSortBy(sortBy);
            response.setSortDirection(sortDirection);
            response.setMessage("이미지 선택 목록을 조회했습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 선택 목록 조회 실패", e);
            throw new BizException("IS001", "이미지 선택 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 상세 조회
     */
    @GetMapping("/{imgSelSeq}")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionBySeq(@PathVariable Long imgSelSeq) {
        try {
            ImageSelectionOutVo imageSelection = imageSelectionService.inqImageSelectionBySeq(imgSelSeq);
            
            if (imageSelection == null) {
                throw new BizException("IS002", "이미지 선택을 찾을 수 없습니다.");
            }
            
            imageSelection.setSuccess(true);
            imageSelection.setMessage("이미지 선택을 조회했습니다.");
            
            return ResponseEntity.ok(imageSelection);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("이미지 선택 상세 조회 실패", e);
            throw new BizException("IS003", "이미지 선택 상세 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판별 선택 목록 조회
     */
    @GetMapping("/board/{imgBrdSeq}")
    public ResponseEntity<List<ImageSelectionOutVo>> inqImageSelectionByBrdSeq(@PathVariable Long imgBrdSeq) {
        try {
            List<ImageSelectionOutVo> list = imageSelectionService.inqImageSelectionByBrdSeq(imgBrdSeq);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("이미지 게시판별 선택 목록 조회 실패", e);
            throw new BizException("IS004", "이미지 게시판별 선택 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원별 선택 목록 조회
     */
    @GetMapping("/employee/{selEmpId}")
    public ResponseEntity<List<ImageSelectionOutVo>> inqImageSelectionByEmpId(@PathVariable String selEmpId) {
        try {
            List<ImageSelectionOutVo> list = imageSelectionService.inqImageSelectionByEmpId(selEmpId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("직원별 선택 목록 조회 실패", e);
            throw new BizException("IS005", "직원별 선택 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일별 선택 목록 조회
     */
    @GetMapping("/file/{imgFileSeq}")
    public ResponseEntity<List<ImageSelectionOutVo>> inqImageSelectionByFileSeq(@PathVariable Long imgFileSeq) {
        try {
            List<ImageSelectionOutVo> list = imageSelectionService.inqImageSelectionByFileSeq(imgFileSeq);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("이미지 파일별 선택 목록 조회 실패", e);
            throw new BizException("IS006", "이미지 파일별 선택 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 등록
     */
    @PostMapping
    public ResponseEntity<ImageSelectionOutVo> rgsnImageSelection(@RequestBody ImageSelectionInVo imageSelection) {
        try {
            ImageSelectionOutVo createdSelection = imageSelectionService.rgsnImageSelection(imageSelection);
            
            createdSelection.setSuccess(true);
            createdSelection.setMessage("이미지 선택이 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(createdSelection);
        } catch (Exception e) {
            log.error("이미지 선택 등록 실패", e);
            throw new BizException("IS007", "이미지 선택 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 삭제
     */
    @DeleteMapping("/{imgSelSeq}")
    public ResponseEntity<ImageSelectionOutVo> delImageSelection(@PathVariable Long imgSelSeq) {
        try {
            ImageSelectionOutVo result = imageSelectionService.delImageSelection(imgSelSeq);
            
            result.setSuccess(true);
            result.setMessage("이미지 선택이 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("이미지 선택 삭제 실패", e);
            throw new BizException("IS008", "이미지 선택 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원별 이미지 선택 삭제
     */
    @DeleteMapping("/board/{imgBrdSeq}/employee/{selEmpId}")
    public ResponseEntity<ImageSelectionOutVo> delImageSelectionByEmpId(
            @PathVariable Long imgBrdSeq,
            @PathVariable String selEmpId) {
        try {
            ImageSelectionOutVo result = imageSelectionService.delImageSelectionByEmpId(imgBrdSeq, selEmpId);
            
            result.setSuccess(true);
            result.setMessage("직원별 이미지 선택이 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("직원별 이미지 선택 삭제 실패", e);
            throw new BizException("IS009", "직원별 이미지 선택 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 여부 확인
     */
    @GetMapping("/check")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionCheck(
            @RequestParam Long imgBrdSeq,
            @RequestParam Long imgFileSeq,
            @RequestParam String selEmpId) {
        try {
            boolean isSelected = imageSelectionService.inqImageSelectionCheck(imgBrdSeq, imgFileSeq, selEmpId);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setIsSelected(isSelected);
            response.setImgBrdSeq(imgBrdSeq);
            response.setImgFileSeq(imgFileSeq);
            response.setSelEmpId(selEmpId);
            response.setMessage(isSelected ? "이미 선택된 이미지입니다." : "선택되지 않은 이미지입니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 선택 여부 확인 실패", e);
            throw new BizException("IS010", "이미지 선택 여부 확인에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원의 선택 개수 조회
     */
    @GetMapping("/count/employee")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionCountByEmpId(
            @RequestParam Long imgBrdSeq,
            @RequestParam String selEmpId) {
        try {
            int count = imageSelectionService.inqImageSelectionCountByEmpId(imgBrdSeq, selEmpId);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setCount(count);
            response.setImgBrdSeq(imgBrdSeq);
            response.setSelEmpId(selEmpId);
            response.setMessage("직원의 선택 개수를 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("직원의 선택 개수 조회 실패", e);
            throw new BizException("IS011", "직원의 선택 개수 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일의 선택 개수 조회
     */
    @GetMapping("/count/file/{imgFileSeq}")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionCountByFileSeq(@PathVariable Long imgFileSeq) {
        try {
            int count = imageSelectionService.inqImageSelectionCountByFileSeq(imgFileSeq);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setCount(count);
            response.setImgFileSeq(imgFileSeq);
            response.setMessage("이미지 파일의 선택 개수를 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 파일의 선택 개수 조회 실패", e);
            throw new BizException("IS012", "이미지 파일의 선택 개수 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판의 전체 선택 개수 조회
     */
    @GetMapping("/count/board/{imgBrdSeq}")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionCountByBrdSeq(@PathVariable Long imgBrdSeq) {
        try {
            int count = imageSelectionService.inqImageSelectionCountByBrdSeq(imgBrdSeq);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setCount(count);
            response.setImgBrdSeq(imgBrdSeq);
            response.setMessage("이미지 게시판의 전체 선택 개수를 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 게시판의 전체 선택 개수 조회 실패", e);
            throw new BizException("IS013", "이미지 게시판의 전체 선택 개수 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 토글 (선택/해제)
     */
    @PostMapping("/toggle")
    public ResponseEntity<ImageSelectionOutVo> toggleImageSelection(
            @RequestParam Long imgBrdSeq,
            @RequestParam Long imgFileSeq,
            @RequestParam String selEmpId) {
        try {
            boolean isSelected = imageSelectionService.toggleImageSelection(imgBrdSeq, imgFileSeq, selEmpId);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setIsSelected(isSelected);
            response.setImgBrdSeq(imgBrdSeq);
            response.setImgFileSeq(imgFileSeq);
            response.setSelEmpId(selEmpId);
            response.setMessage(isSelected ? "이미지가 선택되었습니다." : "이미지 선택이 해제되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 선택 토글 실패", e);
            throw new BizException("IS014", "이미지 선택 토글에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 가능 여부 확인 (최대 선택 개수 체크)
     */
    @GetMapping("/can-select")
    public ResponseEntity<ImageSelectionOutVo> inqCanSelectImage(
            @RequestParam Long imgBrdSeq,
            @RequestParam String selEmpId,
            @RequestParam int maxSelCnt) {
        try {
            boolean canSelect = imageSelectionService.inqCanSelectImage(imgBrdSeq, selEmpId, maxSelCnt);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setCanSelect(canSelect);
            response.setImgBrdSeq(imgBrdSeq);
            response.setSelEmpId(selEmpId);
            response.setMaxSelCnt(maxSelCnt);
            response.setMessage(canSelect ? "이미지를 선택할 수 있습니다." : "최대 선택 개수를 초과하여 선택할 수 없습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 선택 가능 여부 확인 실패", e);
            throw new BizException("IS015", "이미지 선택 가능 여부 확인에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 선택 통계 조회
     */
    @GetMapping("/statistics/{imgBrdSeq}")
    public ResponseEntity<ImageSelectionOutVo> inqImageSelectionStatistics(@PathVariable Long imgBrdSeq) {
        try {
            int selectionCount = imageSelectionService.inqImageSelectionStatistics(imgBrdSeq);
            
            ImageSelectionOutVo response = new ImageSelectionOutVo();
            response.setSuccess(true);
            response.setSelectionCount(selectionCount);
            response.setImgBrdSeq(imgBrdSeq);
            response.setMessage("이미지 선택 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 선택 통계 조회 실패", e);
            throw new BizException("IS016", "이미지 선택 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 