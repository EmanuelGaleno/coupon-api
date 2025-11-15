package io.emanuel.ms.coupon.application.persistence.coupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaCouponRepository implements DomainCouponRepository {

    private final CouponMapper mapper;
    private final CouponRepository repository;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity entity = mapper.toEntity(coupon);
        CouponEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Coupon> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }
}