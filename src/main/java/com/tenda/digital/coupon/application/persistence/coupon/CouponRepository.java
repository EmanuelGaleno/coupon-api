package com.tenda.digital.coupon.application.persistence.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {

    @Query("SELECT c FROM CouponEntity c WHERE c.code = :code")
    Optional<CouponEntity> findByCode(@Param("code") String code);
}