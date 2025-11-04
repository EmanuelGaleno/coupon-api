package com.tenda.digital.coupon.application.persistence.coupon;

import com.tenda.digital.coupon.domain.entity.Coupon;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public @Nullable CouponEntity toEntity(Coupon domain) {
        if (domain == null) {
            return null;
        }

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

    public @Nullable Coupon toDomain(CouponEntity entity) {
        if (entity == null) {
            return null;
        }

        return Coupon.rebuild(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getPublished(),
                entity.getRedeemed(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}