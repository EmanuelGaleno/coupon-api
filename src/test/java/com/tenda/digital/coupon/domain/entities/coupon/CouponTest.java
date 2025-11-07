package com.tenda.digital.coupon.domain.entities.coupon;

import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponCode;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static builders.unit.CouponBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    @DisplayName("Deve criar um cupom válido com sucesso")
    void testCreateCoupon() {
        Coupon coupon = createValidCoupon();

        assertNotNull(coupon.getId());
        assertFalse(coupon.getPublished());
        assertFalse(coupon.getRedeemed());
    }

    @Test
    @DisplayName("Deve lançar exceção ao publicar cupom expirado")
    void testPublishExpired() {
        Coupon coupon = createExpiredCoupon();

        DomainException exception = assertThrows(DomainException.class, coupon::publish);
        assertEquals("Não é permitido publicar um cupom expirado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve publicar cupom válido com sucesso")
    void testPublishCouponSuccess() {
        Coupon coupon = createValidCoupon();

        coupon.publish();

        assertTrue(coupon.getPublished());
    }

    @Test
    @DisplayName("Deve resgatar cupom publicado")
    void testRedeemCouponSuccess() {
        Coupon coupon = createPublishedCoupon();

        coupon.redeem();

        assertTrue(coupon.getRedeemed());
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar cupom não publicado")
    void testRedeemUnpublishedCoupon() {
        Coupon coupon = createValidCoupon();

        DomainException exception = assertThrows(DomainException.class, coupon::redeem);

        assertEquals("Não é possível resgatar um cupom não publicado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar cupom customizado")
    void testCreateCustomCoupon() {
        Coupon coupon = createCustomCoupon("CUSTOM", "desconto especial", 7.5, LocalDate.now().plusDays(5));

        assertEquals("CUSTOM", coupon.getCode().value());
        assertEquals("desconto especial", coupon.getDescription().value());
    }

    @Test
    @DisplayName("Deve reconstruir um cupom existente com sucesso")
    void testRebuildCoupon() {
        UUID id = UUID.randomUUID();

        CouponData data = CouponData.builder()
                .code(CouponCode.of("REBUILD"))
                .description(CouponDescription.of("reconstruído"))
                .discountValue(10.0)
                .expirationDate(LocalDate.now().plusDays(5))
                .published(false)
                .redeemed(false)
                .build();

        Coupon coupon = rebuildCoupon(id, data);

        assertEquals(id, coupon.getId());
        assertEquals("REBUILD", coupon.getCode().value());
        assertEquals("reconstruído", coupon.getDescription().value());
    }
}