package com.kem.inbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDecision {
    private String clientId;
    private BigDecimal amount;
    private BigDecimal month;
}
