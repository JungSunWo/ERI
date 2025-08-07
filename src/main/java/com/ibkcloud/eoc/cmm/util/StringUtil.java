package com.ibkcloud.eoc.cmm.util;

/**
 * @파일명 : StringUtil
 * @논리명 : 문자열 유틸리티
 * @작성자 : 시스템
 * @설명   : 문자열 제어용 static 메서드 제공
 */
public class StringUtil {
    
    /**
     * 문자열이 null이거나 빈 문자열인지 확인
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 문자열이 null이거나 빈 문자열인지 확인 (trim 옵션)
     */
    public static boolean isEmpty(String str, boolean trim) {
        if (str == null) return true;
        return trim ? str.trim().isEmpty() : str.isEmpty();
    }
    
    /**
     * 문자열이 null이거나 빈 문자열인 경우 기본값 반환
     */
    public static String isEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }
    
    /**
     * 문자열이 null이거나 빈 문자열인 경우 기본값 반환 (trim 옵션)
     */
    public static String isEmpty(String str, String defaultValue, boolean trim) {
        return isEmpty(str, trim) ? defaultValue : str;
    }
    
    /**
     * 문자열 trim 후 비교
     */
    public static boolean trimEquals(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;
        return str1.trim().equals(str2.trim());
    }
    
    /**
     * 문자열을 대문자로 변환
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }
    
    /**
     * 문자열을 소문자로 변환
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }
    
    /**
     * 문자열이 특정 패턴과 일치하는지 확인
     */
    public static boolean matches(String str, String pattern) {
        if (str == null || pattern == null) return false;
        return str.matches(pattern);
    }
    
    /**
     * 문자열에서 특정 문자열을 다른 문자열로 치환
     */
    public static String replace(String str, String target, String replacement) {
        if (str == null) return null;
        return str.replace(target, replacement);
    }
    
    /**
     * 문자열을 지정된 길이로 자르기
     */
    public static String substring(String str, int beginIndex, int endIndex) {
        if (str == null) return null;
        if (beginIndex < 0 || endIndex > str.length() || beginIndex > endIndex) {
            return str;
        }
        return str.substring(beginIndex, endIndex);
    }
} 