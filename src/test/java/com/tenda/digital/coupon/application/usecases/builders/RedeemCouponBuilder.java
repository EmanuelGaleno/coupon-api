package com.tenda.digital.coupon.application.usecases.builders;

import java.util.UUID;

public class RedeemCouponBuilder {

    public static UUID validPublishedCouponId() {
        return UUID.randomUUID();
    }

    public static UUID unpublishedCouponId() {
        return UUID.randomUUID();
    }

    public static UUID expiredCouponId() {
        return UUID.randomUUID();
    }
}