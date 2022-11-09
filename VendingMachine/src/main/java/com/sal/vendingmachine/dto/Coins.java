
package com.sal.vendingmachine.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public enum Coins {
    PENNY(new BigDecimal(0.01)),
    NICKLE(new BigDecimal(0.05)),
    DIME(new BigDecimal(0.1)),
    QUARTERS(new BigDecimal(0.25));

    private final BigDecimal value;
    @Autowired
    private Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue()
    {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
