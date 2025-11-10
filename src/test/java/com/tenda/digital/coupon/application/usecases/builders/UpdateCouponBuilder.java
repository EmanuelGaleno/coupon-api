package com.tenda.digital.coupon.application.usecases.builders;

import com.tenda.digital.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;

import java.time.LocalDate;
import java.util.UUID;

public final class UpdateCouponBuilder {

    private UpdateCouponBuilder() {}

    public static UpdateCouponRequestDTO validRequest() {
        UpdateCouponRequestDTO dto = new UpdateCouponRequestDTO();
        dto.setCode("NOVO" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        dto.setDescription("cupom atualizado com sucesso");
        dto.setDiscountValue(20.0);
        dto.setExpirationDate(LocalDate.now().plusDays(20));
        dto.setPublished(false);
        return dto;
    }

    public static UpdateCouponRequestDTO expiredRequest() {
        UpdateCouponRequestDTO dto = new UpdateCouponRequestDTO();
        dto.setCode("EXP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        dto.setDescription("tentando atualizar cupom expirado");
        dto.setDiscountValue(10.0);
        dto.setExpirationDate(LocalDate.now().minusDays(3));
        dto.setPublished(false);
        return dto;
    }

    public static UpdateCouponRequestDTO invalidRequest() {
        UpdateCouponRequestDTO dto = new UpdateCouponRequestDTO();
        dto.setCode("");
        dto.setDescription("");
        dto.setDiscountValue(0.0);
        dto.setExpirationDate(LocalDate.now().minusDays(10));
        dto.setPublished(false);
        return dto;
    }

    public static UpdateCouponRequestDTO publishedRequest() {
        UpdateCouponRequestDTO dto = new UpdateCouponRequestDTO();
        dto.setCode("ALTERA" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        dto.setDescription("tentando atualizar cupom publicado");
        dto.setDiscountValue(12.0);
        dto.setExpirationDate(LocalDate.now().plusDays(5));
        dto.setPublished(true);
        return dto;
    }
}