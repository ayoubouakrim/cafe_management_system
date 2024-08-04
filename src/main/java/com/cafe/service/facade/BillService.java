package com.cafe.service.facade;

import com.cafe.bean.Bill;

import java.util.List;

public interface BillService {
    List<Bill> findByCreatedBy(String username);

    List<Bill> findAll();

    String generateReport(Bill dto) throws Exception;

    byte[] getPdf(String uuid) throws Exception;

    int deleteByUuid(String uuid);
}
