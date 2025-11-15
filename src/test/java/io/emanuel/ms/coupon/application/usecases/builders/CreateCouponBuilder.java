package io.emanuel.ms.coupon.application.usecases.builders;

import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import java.time.LocalDate;
import java.util.UUID;

public final class CreateCouponBuilder {

    private CreateCouponBuilder() {}

    private static String randomCode(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static CreateCouponRequestDTO validRequest() {
        CreateCouponRequestDTO dto = new CreateCouponRequestDTO();
        dto.setCode(randomCode("TESTE"));
        dto.setDescription("cupom de teste válido");
        dto.setDiscountValue(10.0);
        dto.setExpirationDate(LocalDate.now().plusDays(15));
        return dto;
    }

    public static CreateCouponRequestDTO expiredRequest() {
        CreateCouponRequestDTO dto = new CreateCouponRequestDTO();
        dto.setCode(randomCode("EXP"));
        dto.setDescription("cupom expirado para teste");
        dto.setDiscountValue(5.0);
        dto.setExpirationDate(LocalDate.now().minusDays(1));
        return dto;
    }

    public static CreateCouponRequestDTO invalidRequest() {
        CreateCouponRequestDTO dto = new CreateCouponRequestDTO();
        dto.setCode("");
        dto.setDescription("");
        dto.setDiscountValue(0.0);
        dto.setExpirationDate(LocalDate.now().minusDays(5));
        return dto;
    }
}