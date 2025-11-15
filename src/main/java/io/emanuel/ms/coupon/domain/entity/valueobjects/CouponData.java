package io.emanuel.ms.coupon.domain.entity.valueobjects;

import io.emanuel.ms.coupon.domain.entity.aggregates.CouponCode;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponDescription;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CouponData {

    private CouponCode code;
    private CouponDescription description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean redeemed;
}
