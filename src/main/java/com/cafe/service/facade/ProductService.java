package com.cafe.service.facade;

import com.cafe.bean.Product;

import java.util.List;

public interface ProductService {
    Product findByName(String name);

    List<Product> findByCategoryName(String name);

    List<Product> findAll();

    int deleteByName(String name);

    int deleteByCategoryName(String name);

    Product save(Product product);

    Product update(Product t);
}
