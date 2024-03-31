package com.cafe.service.impl;

import com.cafe.bean.Category;
import com.cafe.bean.Product;
import com.cafe.dao.ProductDao;
import com.cafe.service.facade.CategoryService;
import com.cafe.service.facade.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        return productDao.findByCategoryName(name);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public int deleteByName(String name) {
        return productDao.deleteByName(name);
    }

    @Override
    @Transactional
    public int deleteByCategoryName(String name) {
        return productDao.deleteByCategoryName(name);
    }

    @Override
    public Product save(Product product) {
        Category category1 = categoryService.findByName(product.getCategory().getName());
        product.setCategory(category1);
        if (category1 == null) {
            return null;
        } else if (productDao.findByName(product.getName()) != null) {
            return null;
        } else {
            return productDao.save(product);
        }

    }

    @Override
    public Product update(Product t) {

        Product loadedItem = productDao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("Item not found");
        } else {
            productDao.save(t);
            return loadedItem;
        }
    }
}
