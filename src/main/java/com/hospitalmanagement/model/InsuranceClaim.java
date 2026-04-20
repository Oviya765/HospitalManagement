package com.hospitalmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_claim")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaim {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
    
    private String payerId; // Could be an entity, but PDF specifies PayerID FK. Since we don't have a Payer entity, we'll keep it as String or Long if it maps to generic organisaion
    
    @Column(columnDefinition = "json")
    private String claimPayloadJson;
    
    private LocalDateTime submittedAt;
    private String status;
    
    @Column(columnDefinition = "json")
    private String responseJson;
}
