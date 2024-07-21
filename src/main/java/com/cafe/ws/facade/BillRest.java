package com.cafe.ws.facade;

import com.cafe.bean.Bill;
import com.cafe.service.facade.BillService;
import com.cafe.ws.converter.BillConverter;
import com.cafe.ws.dto.BillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillRest {
    @Autowired
    private BillService billService;
    @Autowired
    private BillConverter converter;
    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody BillDto dto) throws Exception{
        ResponseEntity<String> res = null;
        if(dto!=null){
            Bill myT = converter.toItem(dto);
            String report = billService.generateReport(myT);
            if (report == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                return new ResponseEntity<>(report, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }

}
