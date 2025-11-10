package com.tenda.digital.coupon.domain.entity.aggregates;

import com.tenda.digital.coupon.common.utility.StringTransformUtil;
import com.tenda.digital.coupon.common.exceptions.DomainException;

public final class CouponDescription {

    private final String value;

    private CouponDescription(String value) {
        this.value = value;
    }

    public static CouponDescription of(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new DomainException("Descrição do cupom não pode ser vazia");
        }

        String transformed = StringTransformUtil.transform(
                rawValue,
                StringTransformUtil.toLowerCase,
                StringTransformUtil.removeExtraSpaces
        );

        return new CouponDescription(transformed);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}