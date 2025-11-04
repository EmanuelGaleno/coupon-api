package com.tenda.digital.coupon.models.dto;

import com.tenda.digital.coupon.application.persistence.coupon.CouponEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDTO {

    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean redeemed;

    public static CouponResponseDTO fromCoupon(CouponEntity couponEntity) {
        return new CouponResponseDTO(
                couponEntity.getId(),
                couponEntity.getCode(),
                couponEntity.getDescription(),
                couponEntity.getDiscountValue(),
                couponEntity.getExpirationDate(),
                couponEntity.getPublished(),
                couponEntity.getRedeemed()
        );
    }

}
