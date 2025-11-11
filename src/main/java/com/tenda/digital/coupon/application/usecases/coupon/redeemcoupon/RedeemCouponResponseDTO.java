package com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemCouponResponseDTO {
    private UUID id;
    private Boolean redeemed;
}