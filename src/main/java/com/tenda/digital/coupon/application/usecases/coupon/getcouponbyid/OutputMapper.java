package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;

public class OutputMapper {

    private OutputMapper() {
    }

    public static GetCouponByIdResponseDTO toOutput(Coupon coupon) {
        return new GetCouponByIdResponseDTO(
                coupon.getId(),
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getPublished(),
                coupon.getRedeemed()
        );
    }
}
