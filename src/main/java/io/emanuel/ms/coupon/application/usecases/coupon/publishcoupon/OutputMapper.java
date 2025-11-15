package io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;

public class OutputMapper {

    private OutputMapper() {
    }

    static PublishCouponResponseDTO toOutput(Coupon coupon) {
        return new PublishCouponResponseDTO(
                coupon.getId(),
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getPublished()
        );
    }
}
