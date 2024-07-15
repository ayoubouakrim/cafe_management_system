package com.cafe.dao;

import com.cafe.bean.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDao extends JpaRepository<Bill, Long> {
}
