package com.tenda.digital.coupon.application.usecases.coupon.updatecoupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCouponResponseDTO {
    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
}