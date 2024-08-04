package com.cafe.ws.facade;

import com.cafe.bean.Bill;
import com.cafe.service.facade.BillService;
import com.cafe.ws.converter.BillConverter;
import com.cafe.ws.dto.BillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/bill")
public class BillRest {
    @Autowired
    private BillService billService;
    @Autowired
    private BillConverter converter;

    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody BillDto dto) throws Exception {
        ResponseEntity<String> res = null;
        if (dto != null) {
            Bill myT = converter.toItem(dto);
            String report = billService.generateReport(myT);
            if (report == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            } else {
                return new ResponseEntity<>(report, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/getBills")
    public ResponseEntity<List<BillDto>> getBills(String username) throws Exception {
        ResponseEntity<List<BillDto>> res = null;
        List<Bill> items = billService.findByCreatedBy(username);
        if (items != null) {
            List<BillDto> dtos = converter.toDto(items);
            res = new ResponseEntity<>(dtos, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return res;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BillDto>> findAll() throws Exception {
        ResponseEntity<List<BillDto>> res = null;
        List<Bill> items = billService.findAll();
        List<BillDto> dtos = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        dtos = converter.toDto(items);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @GetMapping("/getPdf/uuid/{uuid}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String uuid) throws Exception {
        ResponseEntity<byte[]> res = null;
        if (uuid != null) {
            byte[] pdf = billService.getPdf(uuid);
            if (pdf != null) {
                res = new ResponseEntity<>(pdf, HttpStatus.OK);
            } else {
                res = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return res;
        }

        res = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return res;
    }
    @DeleteMapping("/delete/uuid/{uuid}")
    public ResponseEntity<String> delete(@PathVariable String uuid) throws Exception {
        ResponseEntity<String> res = null;
        if (uuid != null) {
            billService.deleteByUuid(uuid);
            res = new ResponseEntity<>(uuid +"is deleted", HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return res;
    }

}
