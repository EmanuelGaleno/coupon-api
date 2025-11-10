package com.tenda.digital.coupon.application.usecases.builders;

import java.util.UUID;

public class GetCouponByIdBuilder {

    public static UUID validCouponId() {
        return UUID.randomUUID();
    }

    public static UUID invalidCouponId() {
        // Usado para simular busca de cupom inexistente
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}