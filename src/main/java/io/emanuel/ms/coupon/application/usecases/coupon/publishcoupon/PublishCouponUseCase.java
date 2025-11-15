package io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon;

import java.util.UUID;

public interface PublishCouponUseCase {
    PublishCouponResponseDTO execute(UUID id);
}
