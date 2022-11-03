
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author salajrawi
 */
public enum Coins {
    PENNY(new BigDecimal("0.01")),
    NICKLE(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.1")),
    QUARTERS(new BigDecimal("0.25"));

    private final BigDecimal value;

    Coins(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue()
    {
        return value.setScale(2, RoundingMode.DOWN);
    }
}
