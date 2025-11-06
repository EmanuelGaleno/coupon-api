package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

import java.util.UUID;

public interface GetCouponByIdUseCase {
    GetCouponByIdResponseDTO execute(UUID id);
}
