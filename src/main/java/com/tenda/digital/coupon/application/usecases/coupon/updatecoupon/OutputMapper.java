package com.tenda.digital.coupon.application.usecases.coupon.updatecoupon;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;

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