package com.ERI.demo.service;

import com.ERI.demo.dto.NtiLstPageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.NtiLstMapper;
import com.ERI.demo.vo.NtiLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NtiLstService {

    @Autowired
    private NtiLstMapper ntiLstMapper;

    /**
     * 공지사항 목록 조회
     * @param ntiLstVO 조회 조건
     * @return 공지사항 목록
     */
    public List<NtiLstVO> getNtiLstList(NtiLstVO ntiLstVO) {
        return ntiLstMapper.selectNtiLstList(ntiLstVO);
    }

    /**
     * 공지사항 상세 조회
     * @param seq 공지사항 일련번호
     * @return 공지사항 상세 정보
     */
    public NtiLstVO getNtiLst(Long seq) {
        return ntiLstMapper.selectNtiLst(seq);
    }

    /**
     * 공지사항 등록
     * @param ntiLstVO 공지사항 정보
     * @return 등록 결과 (1: 성공, 0: 실패)
     */
    public int insertNtiLst(NtiLstVO ntiLstVO) {
        // 기본값 설정
        if (ntiLstVO.getStsCd() == null || ntiLstVO.getStsCd().isEmpty()) {
            ntiLstVO.setStsCd("STS001"); // 기본 상태코드
        }
        if (ntiLstVO.getRegEmpId() == null) {
            ntiLstVO.setRegEmpId(ntiLstVO.getRegEmpId());
        }
        
        return ntiLstMapper.insertNtiLst(ntiLstVO);
    }

    /**
     * 공지사항 수정
     * @param ntiLstVO 공지사항 정보
     * @return 수정 결과 (1: 성공, 0: 실패)
     */
    public int updateNtiLst(NtiLstVO ntiLstVO) {
        // 수정자 정보 설정
        if (ntiLstVO.getUpdEmpId() == null) {
            ntiLstVO.setUpdEmpId(ntiLstVO.getUpdEmpId());
        }
        
        return ntiLstMapper.updateNtiLst(ntiLstVO);
    }

    /**
     * 공지사항 삭제 (논리삭제)
     * @param seq 공지사항 일련번호
     * @return 삭제 결과 (1: 성공, 0: 실패)
     */
    public int deleteNtiLst(Long seq) {
        return ntiLstMapper.deleteNtiLst(seq);
    }

    /**
     * 공지사항 페이징 목록 조회
     * @param pageRequest 페이징 요청 정보
     * @return 페이징된 공지사항 목록
     */
    public PageResponseDto<NtiLstVO> getNtiLstListWithPaging(NtiLstPageRequestDto pageRequest) {
        // 검색 조건을 VO로 변환
        NtiLstVO searchVO = new NtiLstVO();
        searchVO.setTtl(pageRequest.getTtl());
        searchVO.setCntn(pageRequest.getCntn());
        searchVO.setStsCd(pageRequest.getStsCd());
        searchVO.setRegEmpId(pageRequest.getRegEmpId());
        searchVO.setStartDate(pageRequest.getStartDate());
        searchVO.setEndDate(pageRequest.getEndDate());
        
        // 정렬 정보 설정
        searchVO.setSortBy(pageRequest.getSortBy());
        searchVO.setSortDirection(pageRequest.getSortDirection());
        
        // 페이징 정보 설정
        searchVO.setOffset(pageRequest.getOffset());
        searchVO.setSize(pageRequest.getSize());
        
        // 총 개수 조회
        int totalCount = ntiLstMapper.selectNtiLstCount(searchVO);
        
        // 데이터가 없으면 빈 페이지 반환
        if (totalCount == 0) {
            return PageResponseDto.empty(pageRequest.getPage(), pageRequest.getSize());
        }
        
        // 페이징된 목록 조회
        List<NtiLstVO> noticeList = ntiLstMapper.selectNtiLstListWithPaging(searchVO);
        
        // 페이징 응답 생성
        return PageResponseDto.<NtiLstVO>builder()
                .content(noticeList)
                .totalElements(totalCount)
                .currentPage(pageRequest.getPage())
                .size(pageRequest.getSize())
                .totalPages((int) Math.ceil((double) totalCount / pageRequest.getSize()))
                .hasNext(pageRequest.getPage() < (int) Math.ceil((double) totalCount / pageRequest.getSize()))
                .hasPrevious(pageRequest.getPage() > 1)
                .startPage(Math.max(1, pageRequest.getPage() - 2))
                .endPage(Math.min((int) Math.ceil((double) totalCount / pageRequest.getSize()), pageRequest.getPage() + 2))
                .build();
    }
} 