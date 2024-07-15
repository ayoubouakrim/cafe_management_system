package com.cafe.service.facade;

import com.cafe.bean.Bill;

public interface BillService {
    String generateReport(Bill dto) throws Exception;
}
