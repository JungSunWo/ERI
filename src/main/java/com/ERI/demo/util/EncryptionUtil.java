package com.ERI.demo.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 이메일과 전화번호 암복화 유틸리티
 */
public class EncryptionUtil {
    
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ERI_EMPLOYEE_SECRET_KEY_2024"; // 실제 운영환경에서는 환경변수로 관리
    private static SecretKey secretKey;
    
    static {
        try {
            // 고정된 시크릿 키 생성
            byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            // AES 키는 16, 24, 32 바이트여야 함
            byte[] paddedKey = new byte[32];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
            secretKey = new SecretKeySpec(paddedKey, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("암호화 키 초기화 실패", e);
        }
    }
    
    /**
     * 문자열 암호화
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.trim().isEmpty()) {
            return plainText;
        }
        
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("암호화 실패: " + e.getMessage());
            return plainText; // 암호화 실패시 원본 반환
        }
    }
    
    /**
     * 문자열 복호화
     */
    public static String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.trim().isEmpty()) {
            return encryptedText;
        }
        
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("복호화 실패: " + e.getMessage());
            return encryptedText; // 복호화 실패시 원본 반환
        }
    }
    
    /**
     * 이메일 주소 암호화
     */
    public static String encryptEmail(String email) {
        return encrypt(email);
    }
    
    /**
     * 이메일 주소 복호화
     */
    public static String decryptEmail(String encryptedEmail) {
        return decrypt(encryptedEmail);
    }
    
    /**
     * 전화번호 암호화
     */
    public static String encryptPhone(String phone) {
        return encrypt(phone);
    }
    
    /**
     * 전화번호 복호화
     */
    public static String decryptPhone(String encryptedPhone) {
        return decrypt(encryptedPhone);
    }
    
    /**
     * 암호화된 데이터인지 확인
     */
    public static boolean isEncrypted(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            Base64.getDecoder().decode(text);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
} 