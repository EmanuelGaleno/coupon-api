package com.tenda.digital.coupon.bussiness.interfaces;

import com.tenda.digital.coupon.models.dto.CouponDTO;
import com.tenda.digital.coupon.models.dto.CouponRedeemedDTO;
import com.tenda.digital.coupon.models.dto.CouponResponseDTO;
import com.tenda.digital.coupon.models.dto.CouponUpdateDTO;

import java.util.UUID;

public interface ICouponBO {

    CouponResponseDTO createCoupon(CouponDTO dto);
    CouponResponseDTO getCouponByCode(String code);
    CouponResponseDTO getCouponById(UUID id);
    CouponResponseDTO publishCoupon(UUID id);

    CouponRedeemedDTO redeemCoupon(UUID id);
    CouponUpdateDTO updateCoupon(UUID id, CouponUpdateDTO dto);

}
