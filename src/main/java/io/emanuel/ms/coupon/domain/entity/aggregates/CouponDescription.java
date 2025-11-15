package io.emanuel.ms.coupon.domain.entity.aggregates;

import io.emanuel.ms.coupon.common.utility.StringTransformUtil;
import io.emanuel.ms.coupon.common.exceptions.DomainException;

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