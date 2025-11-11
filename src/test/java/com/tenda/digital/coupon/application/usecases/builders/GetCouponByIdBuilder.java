package com.tenda.digital.coupon.application.usecases.builders;

import java.util.UUID;

public final class GetCouponByIdBuilder {

    private GetCouponByIdBuilder() {}

    public static UUID invalidCouponId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
