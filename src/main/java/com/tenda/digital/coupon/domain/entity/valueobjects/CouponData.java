package com.tenda.digital.coupon.domain.entity.valueobjects;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CouponData {

    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean redeemed;
}
