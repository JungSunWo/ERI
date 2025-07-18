package com.ERI.demo.Controller;

import com.ERI.demo.util.KoreanNameGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 한글명 생성기 테스트 컨트롤러
 */
@RestController
@RequestMapping("/api/name-generator")
public class NameGeneratorController {
    
    /**
     * 랜덤 이름 생성 (성별 무관)
     */
    @GetMapping("/random")
    public Map<String, String> generateRandomName() {
        Map<String, String> response = new HashMap<>();
        response.put("name", KoreanNameGenerator.generateRandomName());
        response.put("type", "random");
        return response;
    }
    
    /**
     * 남성 이름 생성
     */
    @GetMapping("/male")
    public Map<String, String> generateMaleName() {
        Map<String, String> response = new HashMap<>();
        response.put("name", KoreanNameGenerator.generateMaleName());
        response.put("type", "male");
        return response;
    }
    
    /**
     * 여성 이름 생성
     */
    @GetMapping("/female")
    public Map<String, String> generateFemaleName() {
        Map<String, String> response = new HashMap<>();
        response.put("name", KoreanNameGenerator.generateFemaleName());
        response.put("type", "female");
        return response;
    }
    
    /**
     * 성별을 지정하여 이름 생성
     */
    @GetMapping("/gender/{gender}")
    public Map<String, String> generateNameByGender(@PathVariable String gender) {
        Map<String, String> response = new HashMap<>();
        response.put("name", KoreanNameGenerator.generateNameByGender(gender));
        response.put("type", gender);
        return response;
    }
    
    /**
     * 여러 개의 랜덤 이름 생성
     */
    @GetMapping("/multiple")
    public Map<String, Object> generateMultipleNames(@RequestParam(defaultValue = "5") int count) {
        Map<String, Object> response = new HashMap<>();
        response.put("names", KoreanNameGenerator.generateNames(count));
        response.put("count", count);
        response.put("type", "multiple");
        return response;
    }
    
    /**
     * 여러 개의 남성 이름 생성
     */
    @GetMapping("/multiple/male")
    public Map<String, Object> generateMultipleMaleNames(@RequestParam(defaultValue = "5") int count) {
        Map<String, Object> response = new HashMap<>();
        response.put("names", KoreanNameGenerator.generateMaleNames(count));
        response.put("count", count);
        response.put("type", "male_multiple");
        return response;
    }
    
    /**
     * 여러 개의 여성 이름 생성
     */
    @GetMapping("/multiple/female")
    public Map<String, Object> generateMultipleFemaleNames(@RequestParam(defaultValue = "5") int count) {
        Map<String, Object> response = new HashMap<>();
        response.put("names", KoreanNameGenerator.generateFemaleNames(count));
        response.put("count", count);
        response.put("type", "female_multiple");
        return response;
    }
    
    /**
     * 다양한 옵션으로 이름 생성
     */
    @PostMapping("/custom")
    public Map<String, Object> generateCustomNames(@RequestBody Map<String, Object> request) {
        String gender = (String) request.get("gender");
        Integer count = (Integer) request.get("count");
        
        if (count == null) count = 1;
        
        Map<String, Object> response = new HashMap<>();
        
        if ("male".equalsIgnoreCase(gender)) {
            response.put("names", KoreanNameGenerator.generateMaleNames(count));
        } else if ("female".equalsIgnoreCase(gender)) {
            response.put("names", KoreanNameGenerator.generateFemaleNames(count));
        } else {
            response.put("names", KoreanNameGenerator.generateNames(count));
        }
        
        response.put("count", count);
        response.put("gender", gender);
        response.put("type", "custom");
        
        return response;
    }
} 