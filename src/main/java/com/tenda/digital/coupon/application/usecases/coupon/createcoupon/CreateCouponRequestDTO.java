package com.tenda.digital.coupon.application.usecases.coupon.createcoupon;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateCouponRequestDTO {

    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;
}
