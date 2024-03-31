package com.cafe.ws.converter;

import com.cafe.bean.Product;
import com.cafe.ws.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductConverter {
    @Autowired
    private CategoryConverter categoryConverter;
    public Product toItem(ProductDto dto){

        Product item = new Product();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setCategory(categoryConverter.toItem(dto.getCategoryDto()));
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setStatus(Boolean.valueOf(dto.getStatus()));
        return item;
    }

    public ProductDto toDto(Product item){

        ProductDto dto = new ProductDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setCategoryDto(categoryConverter.toDto(item.getCategory()));
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setStatus((item.getStatus()).toString());
        return dto;
    }

    public List<ProductDto> toDto(List<Product> items){

        List<ProductDto> listDto = new ArrayList<>();
        for(Product item : items){
            listDto.add(toDto(item));
        }
        return listDto;
    }

    public List<Product> toItem(List<ProductDto> dtos){
        return dtos.stream().map(this::toItem).toList();
    }


    public void copy(ProductDto dto, Product item) {
        if (dto != null && item != null) {
            copyNonNullProperties(dto, item);
        }
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
