package com.ERI.demo.util;

import com.ERI.demo.dto.MessengerAlertDto;
import com.ERI.demo.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 메신저 알람 전송 유틸리티
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessengerUtil {
    
    private final MessengerService messengerService;
    
    /**
     * 단일 사용자에게 알람 전송
     * 
     * @param recipient 수신자 사번
     * @param title 알람 제목
     * @param body 알람 내용
     * @param linkUrl 링크 URL (선택사항)
     * @param linkTxt 링크 텍스트 (선택사항)
     * @return 전송 결과
     */
    public boolean sendAlertToUser(String recipient, String title, String body, String linkUrl, String linkTxt) {
        try {
            MessengerAlertDto alertDto = MessengerAlertDto.builder()
                .recipient(recipient)
                .title(title)
                .body(body)
                .linkUrl(linkUrl)
                .linkTxt(linkTxt)
                .build();
            
            var response = messengerService.sendAlert(alertDto);
            return response.isSuccess();
            
        } catch (Exception e) {
            log.error("사용자 알람 전송 실패: recipient={}, error={}", recipient, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 다중 사용자에게 알람 전송
     * 
     * @param recipients 수신자 사번 리스트
     * @param title 알람 제목
     * @param body 알람 내용
     * @param linkUrl 링크 URL (선택사항)
     * @param linkTxt 링크 텍스트 (선택사항)
     * @return 전송 결과
     */
    public boolean sendAlertToUsers(List<String> recipients, String title, String body, String linkUrl, String linkTxt) {
        try {
            String recipientList = String.join(",", recipients);
            
            MessengerAlertDto alertDto = MessengerAlertDto.builder()
                .recipient(recipientList)
                .title(title)
                .body(body)
                .linkUrl(linkUrl)
                .linkTxt(linkTxt)
                .build();
            
            var response = messengerService.sendAlert(alertDto);
            return response.isSuccess();
            
        } catch (Exception e) {
            log.error("다중 사용자 알람 전송 실패: recipients={}, error={}", recipients, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 비동기 알람 전송
     * 
     * @param recipient 수신자 사번
     * @param title 알람 제목
     * @param body 알람 내용
     * @param linkUrl 링크 URL (선택사항)
     * @param linkTxt 링크 텍스트 (선택사항)
     * @return CompletableFuture<Boolean>
     */
    public CompletableFuture<Boolean> sendAlertAsync(String recipient, String title, String body, String linkUrl, String linkTxt) {
        try {
            MessengerAlertDto alertDto = MessengerAlertDto.builder()
                .recipient(recipient)
                .title(title)
                .body(body)
                .linkUrl(linkUrl)
                .linkTxt(linkTxt)
                .build();
            
            return messengerService.sendAlertAsync(alertDto)
                .thenApply(response -> {
                    if (response.isSuccess()) {
                        log.info("비동기 알람 전송 성공: recipient={}, title={}", recipient, title);
                    } else {
                        log.warn("비동기 알람 전송 실패: recipient={}, error={}", recipient, response.getMessage());
                    }
                    return response.isSuccess();
                })
                .exceptionally(throwable -> {
                    log.error("비동기 알람 전송 중 예외 발생: recipient={}, error={}", recipient, throwable.getMessage(), throwable);
                    return false;
                });
                
        } catch (Exception e) {
            log.error("비동기 알람 전송 준비 실패: recipient={}, error={}", recipient, e.getMessage(), e);
            return CompletableFuture.completedFuture(false);
        }
    }
    
    /**
     * 게시글 알람 전송
     * 
     * @param recipients 수신자 사번 리스트
     * @param boardTitle 게시글 제목
     * @param authorName 작성자명
     * @param boardSeq 게시글 번호
     * @return 전송 결과
     */
    public boolean sendBoardAlert(List<String> recipients, String boardTitle, String authorName, Long boardSeq) {
        String title = "새 게시글";
        String body = String.format("%s님이 '%s' 게시글을 작성했습니다.", authorName, boardTitle);
        String linkUrl = String.format("/resources/board/detail/%d", boardSeq);
        String linkTxt = "게시글 보기";
        
        return sendAlertToUsers(recipients, title, body, linkUrl, linkTxt);
    }
    
    /**
     * 댓글 알람 전송
     * 
     * @param recipients 수신자 사번 리스트
     * @param boardTitle 게시글 제목
     * @param commentAuthor 댓글 작성자명
     * @param boardSeq 게시글 번호
     * @return 전송 결과
     */
    public boolean sendCommentAlert(List<String> recipients, String boardTitle, String commentAuthor, Long boardSeq) {
        String title = "새 댓글";
        String body = String.format("%s님이 '%s' 게시글에 댓글을 작성했습니다.", commentAuthor, boardTitle);
        String linkUrl = String.format("/resources/board/detail/%d", boardSeq);
        String linkTxt = "댓글 보기";
        
        return sendAlertToUsers(recipients, title, body, linkUrl, linkTxt);
    }
    
    /**
     * 공지사항 알람 전송
     * 
     * @param recipients 수신자 사번 리스트
     * @param noticeTitle 공지사항 제목
     * @param noticeSeq 공지사항 번호
     * @return 전송 결과
     */
    public boolean sendNoticeAlert(List<String> recipients, String noticeTitle, Long noticeSeq) {
        String title = "새 공지사항";
        String body = String.format("새로운 공지사항 '%s'이 등록되었습니다.", noticeTitle);
        String linkUrl = String.format("/resources/notice/detail/%d", noticeSeq);
        String linkTxt = "공지사항 보기";
        
        return sendAlertToUsers(recipients, title, body, linkUrl, linkTxt);
    }
}