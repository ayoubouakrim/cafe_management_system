package com.cafe.dao;

import com.cafe.bean.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BillDetailsDao extends JpaRepository<BillDetails, Long> {
    List<BillDetails> findByBillName(String name);

    int deleteByBillUuid(String uuid);
}
