package io.emanuel.ms.coupon.application.usecases.builders;

import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import java.time.LocalDate;
import java.util.UUID;

public final class UpdateCouponBuilder {

    private UpdateCouponBuilder() {}

    private static String randomCode(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static UpdateCouponRequestDTO validRequest() {
        UpdateCouponRequestDTO dto = new UpdateCouponRequestDTO();
        dto.setCode(randomCode("NOVO"));
        dto.setDescription("cupom atualizado com sucesso");
        dto.setDiscountValue(20.0);
        dto.setExpirationDate(LocalDate.now().plusDays(20));
        dto.setPublished(false);
        return dto;
    }
}