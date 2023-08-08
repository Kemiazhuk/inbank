package com.kem.inbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@Data
public class InvalidResponseDecision {
    Map<String, BigDecimal> validationErrors;
}
