package com.tenda.digital.coupon.application.usecases.builders;

import java.util.UUID;

public class PublishCouponBuilder {

    public static UUID validCouponId() {
        return UUID.randomUUID();
    }

    public static UUID invalidCouponId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static UUID expiredCouponId() {
        return UUID.randomUUID();
    }
}