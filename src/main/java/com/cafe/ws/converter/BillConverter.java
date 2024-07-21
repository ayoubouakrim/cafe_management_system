package com.cafe.ws.converter;

import com.cafe.bean.Bill;
import com.cafe.ws.dto.BillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillConverter {
    @Autowired
    private BillDetailsConverter billDetailsConverter;

    public BillDto toDto(Bill bill) {
        if (bill == null) {
            return null;
        } else {
            BillDto billDto = new BillDto();
            if (bill.getId() != null)
                billDto.setId(bill.getId());
            if (bill.getUuid() != null)
                billDto.setUuid(bill.getUuid());
            if (bill.getName() != null)
                billDto.setName(bill.getName());
            if (bill.getEmail() != null)
                billDto.setEmail(bill.getEmail());
            if (bill.getPhone() != null)
                billDto.setPhone(bill.getPhone());
            if (bill.getMethod() != null)
                billDto.setMethod(bill.getMethod());
            if (String.valueOf(bill.getTotal()) != null)
                billDto.setTotal(bill.getTotal());
            if (bill.getProductDetails() != null)
                billDto.setProductDetails(billDetailsConverter.toDto(bill.getProductDetails()));
            return billDto;

        }
    }

    public Bill toItem(BillDto billDto) {
        if (billDto == null) {
            return null;
        } else {
            Bill bill = new Bill();
            if (billDto.getId() != null)
                bill.setId(billDto.getId());
            if (billDto.getUuid() != null)
                bill.setUuid(billDto.getUuid());
            if (billDto.getName() != null)
                bill.setName(billDto.getName());
            if (billDto.getEmail() != null)
                bill.setEmail(billDto.getEmail());
            if (billDto.getPhone() != null)
                bill.setPhone(billDto.getPhone());
            if (billDto.getMethod() != null)
                bill.setMethod(billDto.getMethod());
            if (String.valueOf(billDto.getTotal()) != null)
                bill.setTotal(billDto.getTotal());
            if (billDto.getProductDetails() != null)
                bill.setProductDetails(billDetailsConverter.toItem(billDto.getProductDetails()));

            return bill;

        }
    }

    public List<Bill> toItem(List<BillDto> billDtos) {
        return billDtos.stream().map(this::toItem).toList();
    }

    public List<BillDto> toDto(List<Bill> bills) {
        return bills.stream().map(this::toDto).toList();
    }
}
