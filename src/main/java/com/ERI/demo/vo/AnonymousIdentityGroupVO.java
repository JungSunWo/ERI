package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 익명 사용자 동일성 그룹 VO
 */
@Data
public class AnonymousIdentityGroupVO {
    
    private Long groupSeq;           // 그룹 일련번호
    private String groupName;        // 그룹명 (관리자 지정)
    private String groupDesc;        // 그룹 설명 (판단 근거)
    private String judgeEmpId;       // 판단 관리자 ID
    private LocalDateTime judgeDate; // 판단일시
    private String confidenceLevel;  // 신뢰도 (H: 높음, M: 보통, L: 낮음)
    private String useYn;            // 사용여부
    private String delYn;            // 삭제 여부
    private LocalDateTime delDate;   // 삭제 일시
    private LocalDateTime regDate;   // 등록일시
    private LocalDateTime updDate;   // 수정일시
    
    // 조인을 위한 추가 필드
    private String judgeEmpName;     // 판단 관리자명
    private Integer memberCount;     // 그룹 멤버 수
} 