package com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;


public class OutputMapper {

    private OutputMapper() {}
    public static RedeemCouponResponseDTO toOutput (Coupon coupon) {
        return new RedeemCouponResponseDTO(
                coupon.getId(),
                coupon.getRedeemed()
        );
    }
}
