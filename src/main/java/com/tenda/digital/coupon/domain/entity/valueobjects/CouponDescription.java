package com.tenda.digital.coupon.domain.entity.valueobjects;

import com.tenda.digital.coupon.application.utility.StringTransformUtil;
import com.tenda.digital.coupon.common.exceptions.DomainException;

public record CouponDescription(String value) {

    public CouponDescription {
        if (value == null || value.isBlank()) {
            throw new DomainException("Descrição do cupom não pode ser vazia");
        }

        value = StringTransformUtil.transform(
                value,
                StringTransformUtil.toLowerCase,
                StringTransformUtil.removeExtraSpaces
        );
    }

    public static CouponDescription of(String rawValue) {
        return new CouponDescription(rawValue);
    }
}