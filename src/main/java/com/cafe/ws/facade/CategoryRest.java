package com.cafe.ws.facade;

import com.cafe.bean.Category;
import com.cafe.service.facade.CategoryService;

import com.cafe.ws.converter.CategoryConverter;
import com.cafe.ws.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/user/category")
public class CategoryRest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryConverter converter;

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto dto) throws Exception {
        if(dto!=null){

            Category myT = converter.toItem(dto);
            Category t = categoryService.save(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CategoryDto myDto = converter.toDto(myT);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAll() throws Exception {
        ResponseEntity<List<CategoryDto>> res = null;
        List<Category> items = categoryService.findAll();
        List<CategoryDto> dtos = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        dtos = converter.toDto(items);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<CategoryDto> findByName(@PathVariable String name) throws Exception {
        ResponseEntity<CategoryDto> res = null;
        Category item = categoryService.findByName(name);
        CategoryDto dto = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (item != null) {
            dto = converter.toDto(item);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> update(CategoryDto dto) throws Exception {
        ResponseEntity<CategoryDto> res ;
        if (dto.getId() == null || categoryService.findByName(dto.getName()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Category t = categoryService.findByName(dto.getName());
            converter.copy(dto,t);
            Category updated = categoryService.update(t);
            CategoryDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }


}
