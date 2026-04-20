package com.hospitalmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kpi")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KPI {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kpiId;
    
    private String name;
    private String definition;
    private Double target;
    private Double currentValue;
    private String reportingPeriod;
}
