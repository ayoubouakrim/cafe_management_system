package com.cafe.ws.converter;

import com.cafe.bean.BillDetails;
import com.cafe.ws.dto.BillDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillDetailsConverter {
    @Autowired
    private BillConverter billConverter;
    @Autowired
    private ProductConverter productConverter;
    public BillDetails toItem(BillDetailsDto dto){
        if (dto == null){
            return null;
        } else {
            BillDetails item = new BillDetails();
            if (dto.getId() != null)
                item.setId(dto.getId());
            if (dto.getBill() != null)
                item.setBill(billConverter.toItem(dto.getBill()));
            if (dto.getProduct() != null)
                item.setProduct(productConverter.toItem(dto.getProduct()));
            if (String.valueOf(dto.getQuantity()) != null)
                item.setQuantity(dto.getQuantity());

            return item;
        }
    }

    public BillDetailsDto toDto(BillDetails item){
        if (item == null){
            return null;
        } else {
            BillDetailsDto dto = new BillDetailsDto();
            if (item.getId() != null)
                dto.setId(item.getId());
            if (item.getBill() != null)
                dto.setBill(billConverter.toDto(item.getBill()));
            if (item.getProduct() != null)
                dto.setProduct(productConverter.toDto(item.getProduct()));
            if (String.valueOf(item.getQuantity()) != null)
                dto.setQuantity(item.getQuantity());

            return dto;
        }
    }

    public List<BillDetailsDto> toDto(List<BillDetails> items){
        return items.stream().map(this::toDto).toList();
    }

    public List<BillDetails> toItem(List<BillDetailsDto> dtos){
        return dtos.stream().map(this::toItem).toList();
    }
}
