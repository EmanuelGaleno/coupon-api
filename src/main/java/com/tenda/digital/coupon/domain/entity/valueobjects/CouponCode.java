package com.tenda.digital.coupon.domain.entity.valueobjects;

import com.tenda.digital.coupon.application.utility.StringTransformUtil;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import lombok.Value;

@Value
public class CouponCode {

    String value;

    private CouponCode(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("Código do cupom não pode ser vazio");
        }

        this.value = StringTransformUtil.transform(
                value,
                StringTransformUtil.toUpperCase,
                StringTransformUtil.toLowerCase,
                StringTransformUtil.removeAccents,
                StringTransformUtil.removeAllSpaces,
                StringTransformUtil.removeExtraSpaces,
                StringTransformUtil.removeNonAlphanumeric,
                StringTransformUtil.removeNonAlphanumericExceptCommaAndSpace
        );
    }

    public static CouponCode of(String rawValue) {
        return new CouponCode(rawValue);
    }

    public String getValue() {
        return value;
    }
}
