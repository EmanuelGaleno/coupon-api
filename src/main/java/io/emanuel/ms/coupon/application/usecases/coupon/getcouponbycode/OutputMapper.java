package io.emanuel.ms.coupon.application.usecases.coupon.getcouponbycode;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;

public class OutputMapper {

    private OutputMapper() {
    }

    static GetCouponByCodeResponseDTO toOutput(Coupon coupon) {
        return new GetCouponByCodeResponseDTO(
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
