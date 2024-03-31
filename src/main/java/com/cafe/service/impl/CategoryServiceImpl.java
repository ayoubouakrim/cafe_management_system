package com.cafe.service.impl;

import com.cafe.bean.Category;
import com.cafe.dao.CategoryDao;
import com.cafe.service.facade.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;


    @Override

    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public int deleteByName(String name) {
        return categoryDao.deleteByName(name);
    }

    @Override

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category save(Category category) {
        if (categoryDao.findByName(category.getName()) != null) {
            return null;
        } else {
            return categoryDao.save(category);
        }

    }
@Override
    public Category update(Category t) {

        Category loadedItem = categoryDao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("Item not found");
        } else {
            categoryDao.save(t);
            return loadedItem;
        }
    }


}
