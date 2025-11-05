package com.tenda.digital.coupon.application.usecases.coupon.createcoupon;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
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
public class CreateCouponResponseDTO {

    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;

}
