package com.cafe.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;
    private String email;
    private String phone;
    private String method;
    private int total;
    @OneToMany(mappedBy = "bill")
    private List<BillDetails> productDetails;
    private String createdBy;

}
