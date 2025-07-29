package com.ERI.demo.Controller;

import com.ERI.demo.dto.MessengerAlertDto;
import com.ERI.demo.dto.MessengerAlertResponseDto;
import com.ERI.demo.dto.MessengerMessageDto;
import com.ERI.demo.dto.MessengerMessageResponseDto;
import com.ERI.demo.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

/**
 * 메신저 알람/쪽지 전송 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/messenger")
@RequiredArgsConstructor
public class MessengerController {
    
    private final MessengerService messengerService;
    
    /**
     * 메신저 알람 전송 (동기)
     * 
     * @param alertDto 알람 전송 정보
     * @param request HTTP 요청
     * @return 전송 결과
     */
    @PostMapping("/alert")
    public ResponseEntity<MessengerAlertResponseDto> sendAlert(
            @RequestBody MessengerAlertDto alertDto,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 알람 전송 요청: empId={}, alertDto={}", empId, alertDto);
        
        try {
            MessengerAlertResponseDto response = messengerService.sendAlert(alertDto);
            
            if (response.isSuccess()) {
                log.info("메신저 알람 전송 성공: empId={}, recipients={}", empId, alertDto.getRecipient());
            } else {
                log.warn("메신저 알람 전송 실패: empId={}, error={}", empId, response.getMessage());
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("메신저 알람 전송 중 예외 발생: empId={}, error={}", empId, e.getMessage(), e);
            
            MessengerAlertResponseDto errorResponse = MessengerAlertResponseDto.builder()
                .success(false)
                .message("알람 전송 중 오류가 발생했습니다: " + e.getMessage())
                .code("INTERNAL_ERROR")
                .build();
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 메신저 쪽지 전송 (동기)
     * 
     * @param messageDto 쪽지 전송 정보
     * @param request HTTP 요청
     * @return 전송 결과
     */
    @PostMapping("/message")
    public ResponseEntity<MessengerMessageResponseDto> sendMessage(
            @RequestBody MessengerMessageDto messageDto,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 쪽지 전송 요청: empId={}, messageDto={}", empId, messageDto);
        
        try {
            MessengerMessageResponseDto response = messengerService.sendMessage(messageDto);
            
            if (response.isSuccess()) {
                log.info("메신저 쪽지 전송 성공: empId={}, recipients={}", empId, messageDto.getRcvId());
            } else {
                log.warn("메신저 쪽지 전송 실패: empId={}, error={}", empId, response.getMessage());
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("메신저 쪽지 전송 중 예외 발생: empId={}, error={}", empId, e.getMessage(), e);
            
            MessengerMessageResponseDto errorResponse = MessengerMessageResponseDto.builder()
                .success(false)
                .message("쪽지 전송 중 오류가 발생했습니다: " + e.getMessage())
                .code("INTERNAL_ERROR")
                .build();
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 메신저 알람 전송 (비동기)
     * 
     * @param alertDto 알람 전송 정보
     * @param request HTTP 요청
     * @return 전송 결과
     */
    @PostMapping("/alert/async")
    public CompletableFuture<ResponseEntity<MessengerAlertResponseDto>> sendAlertAsync(
            @RequestBody MessengerAlertDto alertDto,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 알람 비동기 전송 요청: empId={}, alertDto={}", empId, alertDto);
        
        return messengerService.sendAlertAsync(alertDto)
            .thenApply(response -> {
                if (response.isSuccess()) {
                    log.info("메신저 알람 비동기 전송 성공: empId={}, recipients={}", empId, alertDto.getRecipient());
                } else {
                    log.warn("메신저 알람 비동기 전송 실패: empId={}, error={}", empId, response.getMessage());
                }
                return ResponseEntity.ok(response);
            })
            .exceptionally(throwable -> {
                log.error("메신저 알람 비동기 전송 중 예외 발생: empId={}, error={}", empId, throwable.getMessage(), throwable);
                
                MessengerAlertResponseDto errorResponse = MessengerAlertResponseDto.builder()
                    .success(false)
                    .message("알람 전송 중 오류가 발생했습니다: " + throwable.getMessage())
                    .code("INTERNAL_ERROR")
                    .build();
                
                return ResponseEntity.internalServerError().body(errorResponse);
            });
    }
    
    /**
     * 메신저 쪽지 전송 (비동기)
     * 
     * @param messageDto 쪽지 전송 정보
     * @param request HTTP 요청
     * @return 전송 결과
     */
    @PostMapping("/message/async")
    public CompletableFuture<ResponseEntity<MessengerMessageResponseDto>> sendMessageAsync(
            @RequestBody MessengerMessageDto messageDto,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 쪽지 비동기 전송 요청: empId={}, messageDto={}", empId, messageDto);
        
        return messengerService.sendMessageAsync(messageDto)
            .thenApply(response -> {
                if (response.isSuccess()) {
                    log.info("메신저 쪽지 비동기 전송 성공: empId={}, recipients={}", empId, messageDto.getRcvId());
                } else {
                    log.warn("메신저 쪽지 비동기 전송 실패: empId={}, error={}", empId, response.getMessage());
                }
                return ResponseEntity.ok(response);
            })
            .exceptionally(throwable -> {
                log.error("메신저 쪽지 비동기 전송 중 예외 발생: empId={}, error={}", empId, throwable.getMessage(), throwable);
                
                MessengerMessageResponseDto errorResponse = MessengerMessageResponseDto.builder()
                    .success(false)
                    .message("쪽지 전송 중 오류가 발생했습니다: " + throwable.getMessage())
                    .code("INTERNAL_ERROR")
                    .build();
                
                return ResponseEntity.internalServerError().body(errorResponse);
            });
    }
    
    /**
     * 메신저 알람/쪽지 전송 상태 확인
     * 
     * @return 상태 정보
     */
    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "message", "메신저 알람/쪽지 서비스가 정상적으로 동작 중입니다.",
            "timestamp", System.currentTimeMillis()
        ));
    }
}