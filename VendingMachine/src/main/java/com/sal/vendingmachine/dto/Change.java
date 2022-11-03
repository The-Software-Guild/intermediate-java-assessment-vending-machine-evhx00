
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author salajrawi
 */
public class Change {
    private final HashMap<Coins,Integer> coinChangeMap;

    public Change()
    {
        coinChangeMap = new HashMap<>();
    }

    //Creates and calculates change
    public HashMap<Coins,Integer> getChange(BigDecimal funds) {
        List<Coins> coins = Arrays.asList(Coins.QUARTERS, Coins.DIME, Coins.NICKLE, Coins.PENNY);

        for (Coins coin : coins) {
            int n = funds.divide(coin.getValue(), 0, RoundingMode.DOWN).intValue();
            funds = funds.remainder(coin.getValue());
            coinChangeMap.put(coin, n);
        }

        return coinChangeMap;
    }
}
