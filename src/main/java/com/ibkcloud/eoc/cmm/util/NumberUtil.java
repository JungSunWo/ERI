package com.ibkcloud.eoc.cmm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @파일명 : NumberUtil
 * @논리명 : 숫자 유틸리티
 * @작성자 : 시스템
 * @설명   : 숫자 제어용 static 메서드 제공
 */
public class NumberUtil {
    
    /**
     * 문자열을 Long으로 변환
     */
    public static Long toLong(String str) {
        if (StringUtil.isEmpty(str)) return null;
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 문자열을 Integer로 변환
     */
    public static Integer toInteger(String str) {
        if (StringUtil.isEmpty(str)) return null;
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 문자열을 Double로 변환
     */
    public static Double toDouble(String str) {
        if (StringUtil.isEmpty(str)) return null;
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 문자열을 BigDecimal로 변환
     */
    public static BigDecimal toBigDecimal(String str) {
        if (StringUtil.isEmpty(str)) return null;
        try {
            return new BigDecimal(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 숫자가 0인지 확인
     */
    public static boolean isZero(Number number) {
        if (number == null) return false;
        if (number instanceof BigDecimal) {
            return BigDecimal.ZERO.compareTo((BigDecimal) number) == 0;
        }
        return number.doubleValue() == 0.0;
    }
    
    /**
     * 숫자가 양수인지 확인
     */
    public static boolean isPositive(Number number) {
        if (number == null) return false;
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).compareTo(BigDecimal.ZERO) > 0;
        }
        return number.doubleValue() > 0.0;
    }
    
    /**
     * 숫자가 음수인지 확인
     */
    public static boolean isNegative(Number number) {
        if (number == null) return false;
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).compareTo(BigDecimal.ZERO) < 0;
        }
        return number.doubleValue() < 0.0;
    }
    
    /**
     * BigDecimal을 지정된 소수점 자리수로 반올림
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        if (value == null) return null;
        return value.setScale(scale, RoundingMode.HALF_UP);
    }
    
    /**
     * 두 BigDecimal을 더하기
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.add(b);
    }
    
    /**
     * 두 BigDecimal을 빼기
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) return b == null ? null : b.negate();
        if (b == null) return a;
        return a.subtract(b);
    }
    
    /**
     * 두 BigDecimal을 곱하기
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.multiply(b);
    }
    
    /**
     * 두 BigDecimal을 나누기
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        if (isZero(b)) return null;
        return a.divide(b, 10, RoundingMode.HALF_UP);
    }
} 