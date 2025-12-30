package com.back.boundedContext.market.domain;

import org.springframework.beans.factory.annotation.Value;

public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void serProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }
}
