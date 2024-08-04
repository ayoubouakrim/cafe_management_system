package com.cafe.service.impl;

import com.cafe.bean.BillDetails;
import com.cafe.bean.Product;
import com.cafe.dao.BillDetailsDao;
import com.cafe.service.facade.BillDetailsService;
import com.cafe.service.facade.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailsServiceImpl implements BillDetailsService {
    @Autowired
    private BillDetailsDao billDetailsDao;
    @Autowired
    private ProductService productService;

    @Override
    public BillDetails save(BillDetails entity) {
        Product product = productService.findByName(entity.getProduct().getName());
        entity.setProduct(product);

        return billDetailsDao.save(entity);
    }
    @Override
    public int deleteByBillUuid(String uuid) {
        return billDetailsDao.deleteByBillUuid(uuid);
    }
}
