package com.tenda.digital.coupon.models.dto;

import lombok.Data;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRedeemedDTO {

    private UUID id;
    private boolean redeemed;

}
