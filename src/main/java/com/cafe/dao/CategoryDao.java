package com.cafe.dao;

import com.cafe.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
    public Category findByName(String name);

    public int deleteByName(String name);

}
