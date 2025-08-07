package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpRightsBoardOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 게시글 일련번호
    private String ttl;                  // 제목
    private String cntn;                 // 내용
    private String stsCd;                // 상태코드 (STS001: 임시저장, STS002: 등록완료, STS003: 삭제)
    
    // 분류 정보
    private String categoryCd;           // 카테고리 코드 (CAT001: 업무환경, CAT002: 복리후생, CAT003: 인사제도, CAT004: 기타)
    private String noticeYn;             // 공지여부 (Y/N)
    private String anonymousYn;          // 익명여부 (Y/N)
    private String nickname;             // 익명 닉네임 (익명인 경우)
    
    // 첨부파일 관련
    private String fileAttachYn;         // 첨부파일 존재 여부
    private Integer fileCount;           // 첨부파일 개수
    
    // 통계 정보
    private Integer viewCnt;             // 조회수
    private Integer likeCnt;             // 좋아요 수
    private Integer commentCnt;          // 댓글 수
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명 (익명이 아닌 경우)
    private String updEmpNm;             // 수정직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 연관 데이터
    private List<EmpRightsFileAttachOutVo> files; // 첨부파일 목록
}
