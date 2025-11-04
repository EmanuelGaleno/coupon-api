package com.tenda.digital.coupon.models.dto;

import com.tenda.digital.coupon.application.persistence.coupon.CouponEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponUpdateDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotNull
    private Double discountValue;

    @NotNull
    private LocalDate expirationDate;

    private Boolean published;

    public static CouponUpdateDTO fromCoupon(CouponEntity couponEntity) {
        return new CouponUpdateDTO(
                couponEntity.getCode(),
                couponEntity.getDescription(),
                couponEntity.getDiscountValue(),
                couponEntity.getExpirationDate(),
                couponEntity.getPublished()
        );
    }

}
