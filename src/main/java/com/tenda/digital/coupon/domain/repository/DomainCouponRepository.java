package com.tenda.digital.coupon.domain.repository;


import com.tenda.digital.coupon.domain.entity.coupon.Coupon;

import java.util.Optional;
import java.util.UUID;
public interface DomainCouponRepository {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(UUID id);

    Optional<Coupon> findByCode(String code);
}