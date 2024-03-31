package com.cafe.dao;

import com.cafe.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
     Product findByName(String name);

     List<Product> findByCategoryName(String name);

    int deleteByName(String name);

    int deleteByCategoryName(String name);

}
