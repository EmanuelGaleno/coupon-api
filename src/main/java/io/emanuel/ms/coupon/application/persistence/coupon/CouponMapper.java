package io.emanuel.ms.coupon.application.persistence.coupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponCode;
import io.emanuel.ms.coupon.domain.entity.valueobjects.CouponData;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponDescription;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public CouponEntity toEntity(Coupon domain) {

        CouponEntity entity = new CouponEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode().value());
        entity.setDescription(domain.getDescription().value());
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
                .code(CouponCode.of(entity.getCode()))
                .description(CouponDescription.of(entity.getDescription()))
                .discountValue(entity.getDiscountValue())
                .expirationDate(entity.getExpirationDate())
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