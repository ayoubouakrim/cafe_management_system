package com.cafe.ws.dto;

import com.cafe.bean.Category;
import jakarta.persistence.ManyToOne;

public class ProductDto {

    private Long id;

    private String name;

    private CategoryDto categoryDto;

    private String description;

    private Double price;

    private String status;

    public ProductDto(){}

    public ProductDto(Long id, String name, CategoryDto categoryDto, String description, Double price, String status) {
        this.id = id;
        this.name = name;
        this.categoryDto = categoryDto;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
