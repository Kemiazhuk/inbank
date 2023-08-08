package com.kem.inbank.validators;

import com.kem.inbank.dto.RequestDecision;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestDecisionValidation {
    public static final BigDecimal MIN_AMOUNT = new BigDecimal(2000);
    public static final BigDecimal MAX_AMOUNT = new BigDecimal(10000);
    public static final BigDecimal MIN_MONTH = new BigDecimal(12);
    public static final BigDecimal MAX_MONTH = new BigDecimal(60);

    public Map<String, BigDecimal> validate(RequestDecision requestDecision) {
        if (requestDecision != null) {
            BigDecimal amount = requestDecision.getAmount();
            BigDecimal month = requestDecision.getMonth();
            Map<String, BigDecimal> wrongAttributes = new HashMap();
            if ((amount.compareTo(MIN_AMOUNT) < 0) || (amount.compareTo(MAX_AMOUNT) > 0)) {
                wrongAttributes.put("amount", amount);
            }
            if ((month.compareTo(MIN_MONTH) < 0) || (month.compareTo(MAX_MONTH) > 0)) {
                wrongAttributes.put("month", month);

            }
            return wrongAttributes;
        }
        return null;
    }
}