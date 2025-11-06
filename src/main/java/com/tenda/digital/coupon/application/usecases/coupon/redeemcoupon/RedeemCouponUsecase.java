package com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon;

import java.util.UUID;

public interface RedeemCouponUsecase {
RedeemCouponResponseDTO execute (UUID id);
}
