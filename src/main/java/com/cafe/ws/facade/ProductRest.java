package com.cafe.ws.facade;

import com.cafe.bean.Product;
import com.cafe.service.facade.ProductService;
import com.cafe.ws.converter.ProductConverter;
import com.cafe.ws.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/product")
public class ProductRest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConverter converter;
    @PostMapping("/add")
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto dto) throws Exception {
        if(dto!=null){

            Product myT = converter.toItem(dto);
            Product t = productService.save(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ProductDto myDto = converter.toDto(myT);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAll() throws Exception {
        ResponseEntity<List<ProductDto>> res = null;
        List<Product> items = productService.findAll();
        List<ProductDto> dtos = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        dtos = converter.toDto(items);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<ProductDto> findByName(@PathVariable String name) throws Exception {
        ResponseEntity<ProductDto> res = null;
        Product item = productService.findByName(name);
        ProductDto dto = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (item != null) {
            dto = converter.toDto(item);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto dto) throws Exception {
        ResponseEntity<ProductDto> res ;
        if (dto.getId() == null || productService.findByName(dto.getName()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Product t = productService.findByName(dto.getName());
            converter.copy(dto,t);
            Product updated = productService.update(t);
            ProductDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @DeleteMapping("/delete/name/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name)  throws Exception {
        ResponseEntity<String> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (name != null) {
            int resultDelete = productService.deleteByName(name);
            if (resultDelete>0) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(name, status);
        return res;
    }
}
