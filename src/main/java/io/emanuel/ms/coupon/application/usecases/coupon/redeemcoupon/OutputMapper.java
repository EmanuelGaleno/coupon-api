package io.emanuel.ms.coupon.application.usecases.coupon.redeemcoupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;

public class OutputMapper {

    private OutputMapper() {
    }

    public static RedeemCouponResponseDTO toOutput(Coupon coupon) {
        return new RedeemCouponResponseDTO(
                coupon.getId(),
                coupon.getRedeemed()
        );
    }
}
