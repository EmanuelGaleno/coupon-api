package com.tenda.digital.coupon.bussiness.interfaces;

import com.tenda.digital.coupon.models.dto.CouponDTO;
import com.tenda.digital.coupon.models.dto.CouponRedeemedDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.models.dto.CouponUpdateDTO;

import java.util.UUID;

public interface ICouponBO {

    CreateCouponResponseDTO createCoupon(CouponDTO dto);
    CreateCouponResponseDTO getCouponByCode(String code);
    CreateCouponResponseDTO getCouponById(UUID id);
    CreateCouponResponseDTO publishCoupon(UUID id);

    CouponRedeemedDTO redeemCoupon(UUID id);
    CouponUpdateDTO updateCoupon(UUID id, CouponUpdateDTO dto);

}
