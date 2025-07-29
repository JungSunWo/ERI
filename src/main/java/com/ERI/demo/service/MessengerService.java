package com.ERI.demo.service;

import com.ERI.demo.dto.MessengerAlertDto;
import com.ERI.demo.dto.MessengerAlertResponseDto;
import com.ERI.demo.dto.MessengerMessageDto;
import com.ERI.demo.dto.MessengerMessageResponseDto;
import com.ERI.demo.config.MessengerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 메신저 알람/쪽지 전송 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessengerService {
    
    private final MessengerConfig messengerConfig;
    private final RestTemplate restTemplate;
    
    /**
     * 메신저 알람 전송
     * 
     * @param alertDto 알람 전송 정보
     * @return 전송 결과
     */
    public MessengerAlertResponseDto sendAlert(MessengerAlertDto alertDto) {
        try {
            log.info("메신저 알람 전송 시작: {}", alertDto);
            
            // 필수 파라미터 검증
            if (!validateRequiredFields(alertDto)) {
                return MessengerAlertResponseDto.builder()
                    .success(false)
                    .message("필수 파라미터가 누락되었습니다.")
                    .code("MISSING_REQUIRED_FIELDS")
                    .build();
            }
            
            // 기본값 설정
            setDefaultValues(alertDto);
            
            // API 호출
            String url = getMessengerApiUrl();
            HttpHeaders headers = createHeaders();
            HttpEntity<Map<String, String>> request = createRequest(alertDto, headers);
            
            log.info("메신저 API 호출: URL={}, Request={}", url, request.getBody());
            
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                request, 
                String.class
            );
            
            log.info("메신저 API 응답: Status={}, Body={}", response.getStatusCode(), response.getBody());
            
            return processResponse(response, alertDto);
            
        } catch (HttpClientErrorException e) {
            log.error("메신저 API 클라이언트 오류: {}", e.getMessage());
            return MessengerAlertResponseDto.builder()
                .success(false)
                .message("클라이언트 오류: " + e.getMessage())
                .code("CLIENT_ERROR")
                .build();
                
        } catch (HttpServerErrorException e) {
            log.error("메신저 API 서버 오류: {}", e.getMessage());
            return MessengerAlertResponseDto.builder()
                .success(false)
                .message("서버 오류: " + e.getMessage())
                .code("SERVER_ERROR")
                .build();
                
        } catch (ResourceAccessException e) {
            log.error("메신저 API 연결 오류: {}", e.getMessage());
            return MessengerAlertResponseDto.builder()
                .success(false)
                .message("연결 오류: " + e.getMessage())
                .code("CONNECTION_ERROR")
                .build();
                
        } catch (Exception e) {
            log.error("메신저 알람 전송 중 예외 발생: {}", e.getMessage(), e);
            return MessengerAlertResponseDto.builder()
                .success(false)
                .message("알 수 없는 오류: " + e.getMessage())
                .code("UNKNOWN_ERROR")
                .build();
        }
    }
    
    /**
     * 메신저 쪽지 전송
     * 
     * @param messageDto 쪽지 전송 정보
     * @return 전송 결과
     */
    public MessengerMessageResponseDto sendMessage(MessengerMessageDto messageDto) {
        try {
            log.info("메신저 쪽지 전송 시작: {}", messageDto);
            
            // 필수 파라미터 검증
            if (!validateMessageRequiredFields(messageDto)) {
                return MessengerMessageResponseDto.builder()
                    .success(false)
                    .message("필수 파라미터가 누락되었습니다.")
                    .code("MISSING_REQUIRED_FIELDS")
                    .build();
            }
            
            // 기본값 설정
            setMessageDefaultValues(messageDto);
            
            // API 호출
            String url = getMessengerMessageApiUrl();
            HttpHeaders headers = createHeaders();
            HttpEntity<Map<String, String>> request = createMessageRequest(messageDto, headers);
            
            log.info("메신저 쪽지 API 호출: URL={}, Request={}", url, request.getBody());
            
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                request, 
                String.class
            );
            
            log.info("메신저 쪽지 API 응답: Status={}, Body={}", response.getStatusCode(), response.getBody());
            
            return processMessageResponse(response, messageDto);
            
        } catch (HttpClientErrorException e) {
            log.error("메신저 쪽지 API 클라이언트 오류: {}", e.getMessage());
            return MessengerMessageResponseDto.builder()
                .success(false)
                .message("클라이언트 오류: " + e.getMessage())
                .code("CLIENT_ERROR")
                .build();
                
        } catch (HttpServerErrorException e) {
            log.error("메신저 쪽지 API 서버 오류: {}", e.getMessage());
            return MessengerMessageResponseDto.builder()
                .success(false)
                .message("서버 오류: " + e.getMessage())
                .code("SERVER_ERROR")
                .build();
                
        } catch (ResourceAccessException e) {
            log.error("메신저 쪽지 API 연결 오류: {}", e.getMessage());
            return MessengerMessageResponseDto.builder()
                .success(false)
                .message("연결 오류: " + e.getMessage())
                .code("CONNECTION_ERROR")
                .build();
                
        } catch (Exception e) {
            log.error("메신저 쪽지 전송 중 예외 발생: {}", e.getMessage(), e);
            return MessengerMessageResponseDto.builder()
                .success(false)
                .message("알 수 없는 오류: " + e.getMessage())
                .code("UNKNOWN_ERROR")
                .build();
        }
    }
    
    /**
     * 비동기 메신저 알람 전송
     * 
     * @param alertDto 알람 전송 정보
     * @return CompletableFuture<MessengerAlertResponseDto>
     */
    public CompletableFuture<MessengerAlertResponseDto> sendAlertAsync(MessengerAlertDto alertDto) {
        return CompletableFuture.supplyAsync(() -> sendAlert(alertDto));
    }
    
    /**
     * 비동기 메신저 쪽지 전송
     * 
     * @param messageDto 쪽지 전송 정보
     * @return CompletableFuture<MessengerMessageResponseDto>
     */
    public CompletableFuture<MessengerMessageResponseDto> sendMessageAsync(MessengerMessageDto messageDto) {
        return CompletableFuture.supplyAsync(() -> sendMessage(messageDto));
    }
    
    /**
     * 알람 필수 파라미터 검증
     */
    private boolean validateRequiredFields(MessengerAlertDto alertDto) {
        if (alertDto.getSrvCode() == null || alertDto.getSrvCode().trim().isEmpty()) {
            log.error("발송서버코드가 누락되었습니다.");
            return false;
        }
        
        if (alertDto.getRecipient() == null || alertDto.getRecipient().trim().isEmpty()) {
            log.error("수신자리스트가 누락되었습니다.");
            return false;
        }
        
        return true;
    }
    
    /**
     * 쪽지 필수 파라미터 검증
     */
    private boolean validateMessageRequiredFields(MessengerMessageDto messageDto) {
        if (messageDto.getUserId() == null || messageDto.getUserId().trim().isEmpty()) {
            log.error("쪽지 요청자 사번이 누락되었습니다.");
            return false;
        }
        
        if (messageDto.getRcvId() == null || messageDto.getRcvId().trim().isEmpty()) {
            log.error("쪽지 대상자 사번이 누락되었습니다.");
            return false;
        }
        
        if (messageDto.getMsg() == null || messageDto.getMsg().trim().isEmpty()) {
            log.error("쪽지 내용이 누락되었습니다.");
            return false;
        }
        
        if (messageDto.getChnlTypeClcd() == null || messageDto.getChnlTypeClcd().trim().isEmpty()) {
            log.error("시스템 고유 구분 코드가 누락되었습니다.");
            return false;
        }
        
        return true;
    }
    
    /**
     * 알람 기본값 설정
     */
    private void setDefaultValues(MessengerAlertDto alertDto) {
        if (alertDto.getSend() == null || alertDto.getSend().trim().isEmpty()) {
            alertDto.setSend(messengerConfig.getDefaultSender());
        }
        
        if (alertDto.getSenderAlias() == null || alertDto.getSenderAlias().trim().isEmpty()) {
            alertDto.setSenderAlias(messengerConfig.getDefaultSenderAlias());
        }
        
        // 제목과 본문 길이 제한
        if (alertDto.getTitle() != null && alertDto.getTitle().length() > 11) {
            alertDto.setTitle(alertDto.getTitle().substring(0, 11));
        }
        
        if (alertDto.getBody() != null && alertDto.getBody().length() > 36) {
            alertDto.setBody(alertDto.getBody().substring(0, 36));
        }
    }
    
    /**
     * 쪽지 기본값 설정
     */
    private void setMessageDefaultValues(MessengerMessageDto messageDto) {
        // 메시지 타입 기본값 설정 (1: 쪽지)
        if (messageDto.getMsgType() == null || messageDto.getMsgType().trim().isEmpty()) {
            messageDto.setMsgType("1");
        }
        
        // 응답 형태 기본값 설정
        if (messageDto.getResType() == null || messageDto.getResType().trim().isEmpty()) {
            messageDto.setResType("JSON");
        }
    }
    
    /**
     * 메신저 알람 API URL 반환
     */
    private String getMessengerApiUrl() {
        if ("prod".equals(messengerConfig.getEnvironment())) {
            return "http://" + messengerConfig.getProdUrl() + "/servlet/AnnounceService";
        } else {
            return messengerConfig.getDevUrl();
        }
    }
    
    /**
     * 메신저 쪽지 API URL 반환
     */
    private String getMessengerMessageApiUrl() {
        if ("prod".equals(messengerConfig.getEnvironment())) {
            return "http://" + messengerConfig.getProdMessageUrl() + "/msg/sendMessage.do";
        } else {
            return messengerConfig.getDevMessageUrl();
        }
    }
    
    /**
     * HTTP 헤더 생성
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
    
    /**
     * 알람 HTTP 요청 생성
     */
    private HttpEntity<Map<String, String>> createRequest(MessengerAlertDto alertDto, HttpHeaders headers) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("SRV_CODE", alertDto.getSrvCode());
        requestBody.put("RECIPIENT", alertDto.getRecipient());
        requestBody.put("SEND", alertDto.getSend());
        requestBody.put("SENDER_ALIAS", alertDto.getSenderAlias());
        requestBody.put("TITLE", alertDto.getTitle());
        requestBody.put("BODY", alertDto.getBody());
        requestBody.put("LINKTXT", alertDto.getLinkTxt());
        requestBody.put("LINKURL", alertDto.getLinkUrl());
        requestBody.put("POPUP", alertDto.getPopup());
        requestBody.put("SVR_NAME", alertDto.getSvrName());
        
        return new HttpEntity<>(requestBody, headers);
    }
    
    /**
     * 쪽지 HTTP 요청 생성
     */
    private HttpEntity<Map<String, String>> createMessageRequest(MessengerMessageDto messageDto, HttpHeaders headers) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("resType", messageDto.getResType());
        requestBody.put("userld", messageDto.getUserId());
        requestBody.put("rcvld", messageDto.getRcvId());
        requestBody.put("msg", messageDto.getMsg());
        requestBody.put("msgType", messageDto.getMsgType());
        requestBody.put("Chnl_type_clcd", messageDto.getChnlTypeClcd());
        
        return new HttpEntity<>(requestBody, headers);
    }
    
    /**
     * 알람 응답 처리
     */
    private MessengerAlertResponseDto processResponse(ResponseEntity<String> response, MessengerAlertDto alertDto) {
        if (response.getStatusCode().is2xxSuccessful()) {
            String[] recipients = alertDto.getRecipient().split(",");
            int totalRecipients = recipients.length;
            
            return MessengerAlertResponseDto.builder()
                .success(true)
                .message("알람이 성공적으로 전송되었습니다.")
                .code("SUCCESS")
                .sentCount(totalRecipients)
                .failedCount(0)
                .totalRecipients(totalRecipients)
                .build();
        } else {
            return MessengerAlertResponseDto.builder()
                .success(false)
                .message("알람 전송에 실패했습니다. 상태코드: " + response.getStatusCode())
                .code("HTTP_ERROR")
                .build();
        }
    }
    
    /**
     * 쪽지 응답 처리
     */
    private MessengerMessageResponseDto processMessageResponse(ResponseEntity<String> response, MessengerMessageDto messageDto) {
        if (response.getStatusCode().is2xxSuccessful()) {
            String[] recipients = messageDto.getRcvId().split(",");
            int totalRecipients = recipients.length;
            
            return MessengerMessageResponseDto.builder()
                .success(true)
                .message("쪽지가 성공적으로 전송되었습니다.")
                .code("SUCCESS")
                .sentCount(totalRecipients)
                .failedCount(0)
                .totalRecipients(totalRecipients)
                .messageType(messageDto.getMsgType())
                .senderId(messageDto.getUserId())
                .build();
        } else {
            return MessengerMessageResponseDto.builder()
                .success(false)
                .message("쪽지 전송에 실패했습니다. 상태코드: " + response.getStatusCode())
                .code("HTTP_ERROR")
                .build();
        }
    }
}