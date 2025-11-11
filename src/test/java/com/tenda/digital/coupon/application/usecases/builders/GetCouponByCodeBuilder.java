package com.tenda.digital.coupon.application.usecases.builders;

public final class GetCouponByCodeBuilder {

    private GetCouponByCodeBuilder() {}

    public static String validCouponCode() {
        return "CUPOM10";
    }

    public static String invalidCouponCode() {
        return "CODIGO_INEXISTENTE";
    }
}
