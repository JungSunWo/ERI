package com.ERI.demo.util;

import java.util.Random;

/**
 * 한글 이름 생성기
 */
public class KoreanNameGenerator {
    
    private static final Random random = new Random();
    
    // 성씨 목록 (가장 흔한 성씨들)
    private static final String[] SURNAMES = {
        "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
        "한", "오", "서", "신", "권", "황", "안", "송", "류", "전",
        "고", "문", "양", "손", "배", "조", "백", "허", "유", "남",
        "심", "노", "정", "하", "곽", "성", "차", "주", "우", "구",
        "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채"
    };
    
    // 남성 이름 첫 글자
    private static final String[] MALE_FIRST_NAMES = {
        "민", "준", "현", "우", "영", "호", "성", "재", "동", "철",
        "수", "진", "태", "민", "준", "현", "우", "영", "호", "성",
        "재", "동", "철", "수", "진", "태", "민", "준", "현", "우",
        "영", "호", "성", "재", "동", "철", "수", "진", "태", "민",
        "준", "현", "우", "영", "호", "성", "재", "동", "철", "수"
    };
    
    // 여성 이름 첫 글자
    private static final String[] FEMALE_FIRST_NAMES = {
        "지", "서", "민", "현", "영", "수", "은", "지", "서", "민",
        "현", "영", "수", "은", "지", "서", "민", "현", "영", "수",
        "은", "지", "서", "민", "현", "영", "수", "은", "지", "서",
        "민", "현", "영", "수", "은", "지", "서", "민", "현", "영",
        "수", "은", "지", "서", "민", "현", "영", "수", "은", "지"
    };
    
    // 이름 두 번째 글자 (남녀 공통)
    private static final String[] SECOND_NAMES = {
        "수", "영", "민", "현", "우", "준", "호", "성", "재", "동",
        "철", "진", "태", "은", "지", "서", "민", "현", "영", "수",
        "은", "지", "서", "민", "현", "영", "수", "은", "지", "서",
        "민", "현", "영", "수", "은", "지", "서", "민", "현", "영",
        "수", "은", "지", "서", "민", "현", "영", "수", "은", "지"
    };
    
    /**
     * 랜덤 남성 이름 생성
     */
    public static String generateMaleName() {
        String surname = SURNAMES[random.nextInt(SURNAMES.length)];
        String firstName = MALE_FIRST_NAMES[random.nextInt(MALE_FIRST_NAMES.length)];
        String secondName = SECOND_NAMES[random.nextInt(SECOND_NAMES.length)];
        
        return surname + firstName + secondName;
    }
    
    /**
     * 랜덤 여성 이름 생성
     */
    public static String generateFemaleName() {
        String surname = SURNAMES[random.nextInt(SURNAMES.length)];
        String firstName = FEMALE_FIRST_NAMES[random.nextInt(FEMALE_FIRST_NAMES.length)];
        String secondName = SECOND_NAMES[random.nextInt(SECOND_NAMES.length)];
        
        return surname + firstName + secondName;
    }
    
    /**
     * 랜덤 이름 생성 (성별 무관)
     */
    public static String generateRandomName() {
        if (random.nextBoolean()) {
            return generateMaleName();
        } else {
            return generateFemaleName();
        }
    }
    
    /**
     * 지정된 개수만큼 랜덤 이름 생성
     */
    public static String[] generateNames(int count) {
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            names[i] = generateRandomName();
        }
        return names;
    }
    
    /**
     * 지정된 개수만큼 남성 이름 생성
     */
    public static String[] generateMaleNames(int count) {
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            names[i] = generateMaleName();
        }
        return names;
    }
    
    /**
     * 지정된 개수만큼 여성 이름 생성
     */
    public static String[] generateFemaleNames(int count) {
        String[] names = new String[count];
        for (int i = 0; i < count; i++) {
            names[i] = generateFemaleName();
        }
        return names;
    }
    
    /**
     * 성별을 지정하여 이름 생성
     */
    public static String generateNameByGender(String gender) {
        if ("남성".equals(gender) || "male".equalsIgnoreCase(gender)) {
            return generateMaleName();
        } else if ("여성".equals(gender) || "female".equalsIgnoreCase(gender)) {
            return generateFemaleName();
        } else {
            return generateRandomName();
        }
    }
} 