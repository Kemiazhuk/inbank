package com.kem.inbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ResponseDecision {
    private final BigDecimal amount;
    private final BigDecimal period;
    private final DecisionStatus status;
}
