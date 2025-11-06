package com.tenda.digital.coupon.application.usecases.coupon.getcouponbycode;

public interface GetCouponByCodeUseCase {
    GetCouponByCodeResponseDTO execute(String code);
}
