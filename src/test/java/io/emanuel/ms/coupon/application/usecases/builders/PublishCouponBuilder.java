package io.emanuel.ms.coupon.application.usecases.builders;

import java.util.UUID;

public final class PublishCouponBuilder {

    private PublishCouponBuilder() {}

    public static UUID invalidCouponId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
