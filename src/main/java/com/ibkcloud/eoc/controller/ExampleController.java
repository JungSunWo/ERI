package com.ibkcloud.eoc.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.ibkcloud.eoc.cmm.dto.ErrorResponseDto;
import com.ibkcloud.eoc.controller.vo.ExampleInVo;
import com.ibkcloud.eoc.controller.vo.ExampleOutVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @파일명 : ExampleController
 * @논리명 : 예제 Controller
 * @작성자 : 시스템
 * @설명   : IBK클라우드 가이드에 따른 예제 Controller 클래스
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/example")
public class ExampleController {
    
    /**
     * @메서드명 : inqExample
     * @논리명 : 예제 조회
     * @설명 : 예제 데이터를 조회하는 API
     * @param : id - 조회할 ID
     * @return : ExampleOutVo - 조회 결과
     */
    @GetMapping("/{id}")
    public ExampleOutVo inqExample(@PathVariable String id) {
        log.info("예제 조회 시작 - ID: {}", id);
        
        // 예제 응답 데이터 생성
        ExampleOutVo outVo = new ExampleOutVo();
        outVo.setId(id);
        outVo.setName("예제 데이터");
        outVo.setDescription("IBK클라우드 가이드에 따른 예제 데이터입니다.");
        
        log.info("예제 조회 완료 - ID: {}", id);
        return outVo;
    }
    
    /**
     * @메서드명 : rgsnExample
     * @논리명 : 예제 등록
     * @설명 : 예제 데이터를 등록하는 API
     * @param : inVo - 등록할 데이터
     * @return : ExampleOutVo - 등록 결과
     */
    @PostMapping("/register")
    public ExampleOutVo rgsnExample(@Valid @RequestBody ExampleInVo inVo) {
        log.info("예제 등록 시작 - Name: {}", inVo.getName());
        
        // 예제 응답 데이터 생성
        ExampleOutVo outVo = new ExampleOutVo();
        outVo.setId("REG_" + System.currentTimeMillis());
        outVo.setName(inVo.getName());
        outVo.setDescription(inVo.getDescription());
        
        log.info("예제 등록 완료 - ID: {}", outVo.getId());
        return outVo;
    }
    
    /**
     * @메서드명 : mdfcExample
     * @논리명 : 예제 수정
     * @설명 : 예제 데이터를 수정하는 API
     * @param : id - 수정할 ID
     * @param : inVo - 수정할 데이터
     * @return : ExampleOutVo - 수정 결과
     */
    @PostMapping("/{id}/modify")
    public ExampleOutVo mdfcExample(@PathVariable String id, @Valid @RequestBody ExampleInVo inVo) {
        log.info("예제 수정 시작 - ID: {}, Name: {}", id, inVo.getName());
        
        // 예제 응답 데이터 생성
        ExampleOutVo outVo = new ExampleOutVo();
        outVo.setId(id);
        outVo.setName(inVo.getName());
        outVo.setDescription(inVo.getDescription());
        
        log.info("예제 수정 완료 - ID: {}", id);
        return outVo;
    }
    
    /**
     * @메서드명 : delExample
     * @논리명 : 예제 삭제
     * @설명 : 예제 데이터를 삭제하는 API
     * @param : id - 삭제할 ID
     * @return : String - 삭제 결과 메시지
     */
    @PostMapping("/{id}/delete")
    public String delExample(@PathVariable String id) {
        log.info("예제 삭제 시작 - ID: {}", id);
        
        // 예제 삭제 처리
        String result = "ID " + id + " 데이터가 삭제되었습니다.";
        
        log.info("예제 삭제 완료 - ID: {}", id);
        return result;
    }
} 