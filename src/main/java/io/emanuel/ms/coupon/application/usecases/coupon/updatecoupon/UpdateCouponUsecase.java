package io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon;

import java.util.UUID;

public interface UpdateCouponUsecase {
    UpdateCouponResponseDTO execute(UUID id, UpdateCouponRequestDTO request);
}