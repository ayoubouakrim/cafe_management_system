package com.cafe.ws.facade;

import com.cafe.ws.dto.BillDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillRest {
    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody BillDto dto) throws Exception{
        return null;
    }
}
