package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 익명 사용자 동일성 그룹 멤버 VO
 */
@Data
public class AnonymousIdentityMemberVO {
    
    private Long memberSeq;          // 멤버 일련번호
    private Long groupSeq;           // 그룹 일련번호
    private Long anonymousId;        // 익명 사용자 ID
    private String addEmpId;         // 추가 관리자 ID
    private LocalDateTime addDate;   // 추가일시
    private String addReason;        // 추가 사유
    private String useYn;            // 사용여부
    private String delYn;            // 삭제 여부
    private LocalDateTime delDate;   // 삭제 일시
    
    // 조인을 위한 추가 필드
    private String nickname;         // 익명 닉네임
    private String addEmpName;       // 추가 관리자명
    private String groupName;        // 그룹명
    private String groupDesc;        // 그룹 설명
    private String confidenceLevel;  // 신뢰도
} 