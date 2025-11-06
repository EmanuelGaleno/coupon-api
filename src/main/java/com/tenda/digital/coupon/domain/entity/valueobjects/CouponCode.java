package com.tenda.digital.coupon.domain.entity.valueobjects;

import com.tenda.digital.coupon.application.utility.StringTransformUtil;
import com.tenda.digital.coupon.common.exceptions.DomainException;

public record CouponCode(String value) {

    public CouponCode(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("Código do cupom não pode ser vazio");
        }

        this.value = StringTransformUtil.transform(
                value,
                StringTransformUtil.toUpperCase,
                StringTransformUtil.removeAccents,
                StringTransformUtil.removeAllSpaces,
                StringTransformUtil.removeNonAlphanumeric
        );
    }

    public static CouponCode of(String rawValue) {
        return new CouponCode(rawValue);
    }
}
