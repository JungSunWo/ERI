package com.ibkcloud.eoc.cmm.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @파일명 : EncryptionUtil
 * @논리명 : 암호화 유틸리티
 * @작성자 : 시스템
 * @설명   : 이메일과 전화번호 암호화/복호화를 위한 유틸리티 클래스
 */
public class EncryptionUtil {
    
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "IBKCloudEOC2024"; // 16자리 키
    private static final String ENCRYPTED_PREFIX = "ENC:";
    
    /**
     * 이메일 암호화
     */
    public static String encryptEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return email;
        }
        
        try {
            String encrypted = encrypt(email);
            return ENCRYPTED_PREFIX + encrypted;
        } catch (Exception e) {
            return email;
        }
    }
    
    /**
     * 이메일 복호화
     */
    public static String decryptEmail(String encryptedEmail) {
        if (StringUtil.isEmpty(encryptedEmail) || !isEncrypted(encryptedEmail)) {
            return encryptedEmail;
        }
        
        try {
            String encrypted = encryptedEmail.substring(ENCRYPTED_PREFIX.length());
            return decrypt(encrypted);
        } catch (Exception e) {
            return encryptedEmail;
        }
    }
    
    /**
     * 전화번호 암호화
     */
    public static String encryptPhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return phone;
        }
        
        try {
            String encrypted = encrypt(phone);
            return ENCRYPTED_PREFIX + encrypted;
        } catch (Exception e) {
            return phone;
        }
    }
    
    /**
     * 전화번호 복호화
     */
    public static String decryptPhone(String encryptedPhone) {
        if (StringUtil.isEmpty(encryptedPhone) || !isEncrypted(encryptedPhone)) {
            return encryptedPhone;
        }
        
        try {
            String encrypted = encryptedPhone.substring(ENCRYPTED_PREFIX.length());
            return decrypt(encrypted);
        } catch (Exception e) {
            return encryptedPhone;
        }
    }
    
    /**
     * 암호화 여부 확인
     */
    public static boolean isEncrypted(String value) {
        return value != null && value.startsWith(ENCRYPTED_PREFIX);
    }
    
    /**
     * AES 암호화
     */
    private static String encrypt(String value) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    /**
     * AES 복호화
     */
    private static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }
} 