package com.tenda.digital.coupon.application.usecases.coupon.createcoupon;


import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import org.springframework.stereotype.Component;

@Component
public class OutputMapper {

    public CreateCouponResponseDTO toResponse(Coupon coupon) {
        return new CreateCouponResponseDTO(
                coupon.getId(),
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate()
        );
    }
}
