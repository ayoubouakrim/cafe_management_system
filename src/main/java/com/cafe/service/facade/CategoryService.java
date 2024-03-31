package com.cafe.service.facade;

import com.cafe.bean.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category findByName(String name);

    int deleteByName(String name);

    List<Category> findAll();

    Category save(Category category);

    Category update(Category t);
}
