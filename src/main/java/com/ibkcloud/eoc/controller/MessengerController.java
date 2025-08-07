package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 메신저 알람/쪽지 전송 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/messenger")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class MessengerController {
    
    private final MessengerService messengerService;
    
    /**
     * 메신저 알람 전송 (동기)
     */
    @PostMapping("/alert")
    public ResponseEntity<MessengerOutVo> rgsnAlert(@RequestBody MessengerInVo messengerInVo,
                                                   HttpServletRequest request) {
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 알람 전송 시작 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient());
        
        try {
            messengerInVo.setEmpId(empId);
            messengerInVo.setMessageType("ALERT");
            
            MessengerOutVo result = messengerService.rgsnAlert(messengerInVo);
            
            result.setSuccess(true);
            result.setMessage("메신저 알람이 성공적으로 전송되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메신저 알람 전송 완료 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메신저 알람 전송 실패 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient(), e);
            throw new BizException("알람 전송에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 메신저 쪽지 전송 (동기)
     */
    @PostMapping("/message")
    public ResponseEntity<MessengerOutVo> rgsnMessage(@RequestBody MessengerInVo messengerInVo,
                                                     HttpServletRequest request) {
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 쪽지 전송 시작 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId());
        
        try {
            messengerInVo.setEmpId(empId);
            messengerInVo.setMessageType("MESSAGE");
            
            MessengerOutVo result = messengerService.rgsnMessage(messengerInVo);
            
            result.setSuccess(true);
            result.setMessage("메신저 쪽지가 성공적으로 전송되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메신저 쪽지 전송 완료 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메신저 쪽지 전송 실패 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId(), e);
            throw new BizException("쪽지 전송에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 메신저 알람 전송 (비동기)
     */
    @PostMapping("/alert/async")
    public CompletableFuture<ResponseEntity<MessengerOutVo>> rgsnAlertAsync(
            @RequestBody MessengerInVo messengerInVo,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 알람 비동기 전송 시작 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient());
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                messengerInVo.setEmpId(empId);
                messengerInVo.setMessageType("ALERT");
                messengerInVo.setIsAsync(true);
                
                MessengerOutVo result = messengerService.rgsnAlertAsync(messengerInVo);
                
                result.setSuccess(true);
                result.setMessage("메신저 알람이 비동기로 전송되었습니다.");
                result.setErrorCode("SUCCESS");
                
                log.info("메신저 알람 비동기 전송 완료 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient());
                return ResponseEntity.ok(result);
                
            } catch (Exception e) {
                log.error("메신저 알람 비동기 전송 실패 - empId: {}, recipient: {}", empId, messengerInVo.getRecipient(), e);
                throw new BizException("알람 비동기 전송에 실패했습니다: " + e.getMessage());
            }
        });
    }
    
    /**
     * 메신저 쪽지 전송 (비동기)
     */
    @PostMapping("/message/async")
    public CompletableFuture<ResponseEntity<MessengerOutVo>> rgsnMessageAsync(
            @RequestBody MessengerInVo messengerInVo,
            HttpServletRequest request) {
        
        String empId = (String) request.getAttribute("EMP_ID");
        log.info("메신저 쪽지 비동기 전송 시작 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId());
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                messengerInVo.setEmpId(empId);
                messengerInVo.setMessageType("MESSAGE");
                messengerInVo.setIsAsync(true);
                
                MessengerOutVo result = messengerService.rgsnMessageAsync(messengerInVo);
                
                result.setSuccess(true);
                result.setMessage("메신저 쪽지가 비동기로 전송되었습니다.");
                result.setErrorCode("SUCCESS");
                
                log.info("메신저 쪽지 비동기 전송 완료 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId());
                return ResponseEntity.ok(result);
                
            } catch (Exception e) {
                log.error("메신저 쪽지 비동기 전송 실패 - empId: {}, rcvId: {}", empId, messengerInVo.getRcvId(), e);
                throw new BizException("쪽지 비동기 전송에 실패했습니다: " + e.getMessage());
            }
        });
    }
    
    /**
     * 메신저 상태 조회
     */
    @GetMapping("/status")
    public ResponseEntity<MessengerOutVo> inqMessengerStatus() {
        log.info("메신저 상태 조회 시작");
        
        try {
            MessengerOutVo result = messengerService.inqMessengerStatus();
            
            result.setSuccess(true);
            result.setMessage("메신저 상태를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메신저 상태 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메신저 상태 조회 실패", e);
            throw new BizException("메신저 상태 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 