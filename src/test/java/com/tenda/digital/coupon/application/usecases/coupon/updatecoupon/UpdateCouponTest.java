package com.tenda.digital.coupon.application.usecases.coupon.updatecoupon;

import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.builders.UpdateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateCouponTest {

    @Autowired
    private DomainCouponRepository domainCouponRepository;

    @Autowired
    private UpdateCouponUsecase updateCouponUsecase;

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Test
    @DisplayName("Deve atualizar cupom")
    void shouldUpdateCouponSuccessfully() {
        CreateCouponResponseDTO created = createCouponUsecase.execute(CreateCouponBuilder.validRequest());
        UpdateCouponRequestDTO update = UpdateCouponBuilder.validRequest();

        UpdateCouponResponseDTO response = updateCouponUsecase.execute(created.getId(), update);

        assertEquals(update.getCode(), response.getCode());
        assertEquals(update.getDescription().toLowerCase(), response.getDescription());
        assertEquals(update.getDiscountValue(), response.getDiscountValue());

        Coupon updated = domainCouponRepository.findById(created.getId())
                .orElseThrow(() -> new AssertionError("Cupom não encontrado no banco"));

        assertEquals(update.getCode(), updated.getCode().value());
        assertFalse(updated.getRedeemed());
        assertFalse(updated.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar cupom inexistente")
    void testUpdateCouponNotFound() {
        UUID randomId = UUID.randomUUID();
        UpdateCouponRequestDTO request = UpdateCouponBuilder.validRequest();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> updateCouponUsecase.execute(randomId, request)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}