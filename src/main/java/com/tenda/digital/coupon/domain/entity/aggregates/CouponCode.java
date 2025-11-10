package com.tenda.digital.coupon.domain.entity.aggregates;

import com.tenda.digital.coupon.common.utility.StringTransformUtil;
import com.tenda.digital.coupon.common.exceptions.DomainException;

public final class CouponCode {

    private final String value;

    private CouponCode(String value) {
        this.value = value;
    }

    public static CouponCode of(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new DomainException("Código do cupom não pode ser vazio");
        }

        String transformed = StringTransformUtil.transform(
                rawValue,
                StringTransformUtil.toUpperCase,
                StringTransformUtil.removeAccents,
                StringTransformUtil.removeAllSpaces,
                StringTransformUtil.removeNonAlphanumeric
        );

        return new CouponCode(transformed);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}