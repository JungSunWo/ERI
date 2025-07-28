package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NtiLstVO {
    private Integer rowNum;     // 순번 (ROW_NUMBER)
    private Long seq;           // 일련번호 (PK)
    private String ttl;         // 제목
    private String cntn;        // 내용
    private String stsCd;       // 상태코드
    private String fileAttachYn; // 첨부파일 존재 여부
    private String delYn;       // 삭제여부
    private LocalDateTime delDate; // 삭제일시
    private String regEmpId;    // 등록직원ID
    private String updEmpId;    // 수정직원ID
    private LocalDateTime regDate; // 등록일시
    private LocalDateTime updDate; // 수정일시
    
    // 첨부파일 관련 필드
    private Integer fileCount;  // 첨부파일 개수
    
    // 페이징 관련 필드
    private Integer offset;     // 오프셋
    private Integer size;       // 페이지 크기
    private String startDate;   // 시작일자
    private String endDate;     // 종료일자
    
    // 정렬 관련 필드
    private String sortBy;      // 정렬 필드
    private String sortDirection; // 정렬 방향
} 