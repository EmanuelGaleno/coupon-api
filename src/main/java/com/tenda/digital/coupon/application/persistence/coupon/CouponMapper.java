package com.tenda.digital.coupon.application.persistence.coupon;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public CouponEntity toEntity(Coupon domain) {

        CouponEntity entity = new CouponEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setDiscountValue(domain.getDiscountValue());
        entity.setExpirationDate(domain.getExpirationDate());
        entity.setPublished(domain.getPublished());
        entity.setRedeemed(domain.getRedeemed());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    public Coupon toDomain(CouponEntity entity) {

        CouponData data = CouponData.builder()
                .code(entity.getCode())
                .description(entity.getDescription())
                .discountValue(entity.getDiscountValue())
                .published(entity.getPublished())
                .redeemed(entity.getRedeemed())
                .build();

        return Coupon.rebuild(
                data,
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}