package com.cafe.ws.converter;

import com.cafe.bean.Category;
import com.cafe.ws.dto.CategoryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CategoryConverter {

    public Category toItem(CategoryDto dto){

        Category item = new Category();
        item.setId(dto.getId());
        item.setName(dto.getName());
        return item;
    }

    public CategoryDto toDto(Category item){

        CategoryDto dto = new CategoryDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        return dto;
    }

    public List<CategoryDto> toDto(List<Category> items){

        List<CategoryDto> listDto = new ArrayList<>();
        for(Category item : items){
            listDto.add(toDto(item));
        }
        return listDto;
    }

    public List<Category> toItem(List<CategoryDto> dtos){
        return dtos.stream().map(this::toItem).toList();
    }


    public void copy(CategoryDto dto, Category item) {
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
