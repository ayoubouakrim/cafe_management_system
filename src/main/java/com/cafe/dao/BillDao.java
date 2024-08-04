package com.cafe.dao;

import com.cafe.bean.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDao extends JpaRepository<Bill, Long> {

    List<Bill> findByCreatedBy(String createdBy);

    int deleteByUuid(String uuid);
}
