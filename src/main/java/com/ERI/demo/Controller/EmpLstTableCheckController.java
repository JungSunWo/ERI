package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpLstTableCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/emp-lst")
public class EmpLstTableCheckController {

    @Autowired
    private EmpLstTableCheckService empLstTableCheckService;

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkEmpLstTable() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = empLstTableCheckService.getEmpLstCount();
            response.put("success", true);
            response.put("count", count);
            response.put("message", "eri_db TB_EMP_LST 테이블 정상 조회");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "eri_db TB_EMP_LST 테이블 조회 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 