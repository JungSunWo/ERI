package com.ibkcloud.eoc.cmm.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @파일명 : DateUtil
 * @논리명 : 날짜 유틸리티
 * @작성자 : 시스템
 * @설명   : 일자 제어용 static 메서드 제공
 */
public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATETIME_FORMATTER_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    /**
     * 현재 날짜 반환
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * 현재 날짜시간 반환
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * 문자열을 LocalDate로 변환 (yyyy-MM-dd)
     */
    public static LocalDate parseDate(String dateStr) {
        if (StringUtil.isEmpty(dateStr)) return null;
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 문자열을 LocalDateTime으로 변환 (yyyy-MM-dd HH:mm:ss)
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (StringUtil.isEmpty(dateTimeStr)) return null;
        try {
            return LocalDateTime.parse(dateTimeStr.trim(), DATETIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * LocalDate를 문자열로 변환 (yyyy-MM-dd)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * LocalDateTime을 문자열로 변환 (yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * LocalDate를 문자열로 변환 (yyyyMMdd)
     */
    public static String formatDateYYYYMMDD(LocalDate date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER_YYYYMMDD);
    }
    
    /**
     * LocalDateTime을 문자열로 변환 (yyyyMMddHHmmss)
     */
    public static String formatDateTimeYYYYMMDDHHMMSS(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DATETIME_FORMATTER_YYYYMMDDHHMMSS);
    }
    
    /**
     * 두 날짜 간의 일수 차이 계산
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return 0;
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * 두 날짜시간 간의 시간 차이 계산
     */
    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) return 0;
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }
    
    /**
     * 날짜에 일수 더하기
     */
    public static LocalDate addDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.plusDays(days);
    }
    
    /**
     * 날짜시간에 시간 더하기
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.plusHours(hours);
    }
    
    /**
     * 날짜에서 일수 빼기
     */
    public static LocalDate subtractDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.minusDays(days);
    }
    
    /**
     * 날짜시간에서 시간 빼기
     */
    public static LocalDateTime subtractHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.minusHours(hours);
    }
    
    /**
     * 날짜가 유효한지 확인
     */
    public static boolean isValidDate(String dateStr) {
        return parseDate(dateStr) != null;
    }
    
    /**
     * 날짜시간이 유효한지 확인
     */
    public static boolean isValidDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr) != null;
    }
} 