package com.tenda.digital.coupon.domain.entities.coupon;

import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static builders.unit.CouponBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    @DisplayName("Deve criar um cupom válido com sucesso")
    void shouldCreateValidCoupon() {
        Coupon coupon = Coupon.create(validCouponData());

        assertNotNull(coupon.getId());
        assertFalse(coupon.getPublished());
        assertFalse(coupon.getRedeemed());
        assertNotNull(coupon.getCreatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao publicar cupom expirado")
    void shouldThrowExceptionWhenPublishExpiredCoupon() {
        Coupon coupon = Coupon.create(expiredCouponData());

        DomainException exception = assertThrows(DomainException.class, coupon::publish);
        assertEquals("Não é permitido publicar um cupom expirado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve publicar cupom válido com sucesso")
    void shouldPublishCouponSuccessfully() {
        Coupon coupon = Coupon.create(validCouponData());
        coupon.publish();

        assertTrue(coupon.getPublished());
        assertNotNull(coupon.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve resgatar cupom publicado com sucesso")
    void shouldRedeemCouponSuccessfully() {
        Coupon coupon = Coupon.create(validCouponData());
        coupon.publish();
        coupon.redeem();

        assertTrue(coupon.getRedeemed());
        assertTrue(coupon.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar cupom não publicado")
    void shouldThrowExceptionWhenRedeemUnpublishedCoupon() {
        Coupon coupon = Coupon.create(validCouponData());

        DomainException exception = assertThrows(DomainException.class, coupon::redeem);
        assertEquals("Não é possível resgatar um cupom não publicado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve reconstruir um cupom existente com sucesso")
    void shouldRebuildCouponSuccessfully() {
        UUID id = UUID.randomUUID();

        CouponData data = validCouponData();
        Coupon coupon = Coupon.rebuild(data, id, couponCreatedNow(), couponCreatedNow());

        assertEquals(id, coupon.getId());
        assertEquals(data.getCode().value(), coupon.getCode().value());
        assertEquals(data.getDescription().value(), coupon.getDescription().value());
    }

    private static java.time.LocalDateTime couponCreatedNow() {
        return java.time.LocalDateTime.now();
    }
}