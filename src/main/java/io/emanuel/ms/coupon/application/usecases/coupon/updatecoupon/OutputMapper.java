package io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;

class OutputMapper {

    private OutputMapper() {
    }

    static UpdateCouponResponseDTO toOutput(Coupon coupon) {
        return new UpdateCouponResponseDTO(
                coupon.getId(),
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getPublished()
        );
    }
}