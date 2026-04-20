package com.hospitalmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    
    private String code;
    private String description;
    private Double price;
    private String department;
    private String billingCategory;
    private String status;
}
