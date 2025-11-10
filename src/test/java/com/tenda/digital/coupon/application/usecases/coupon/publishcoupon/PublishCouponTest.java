package com.tenda.digital.coupon.application.usecases.coupon.publishcoupon;

import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublishCouponTest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private PublishCouponUseCase publishCouponUseCase;

    @Test
    @DisplayName("Deve publicar cupom válido com sucesso")
    void testPublishCouponSuccess() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        PublishCouponResponseDTO response = publishCouponUseCase.execute(created.getId());

        assertNotNull(response);
        assertTrue(response.getPublished());
        assertNotNull(response.getId());
        assertEquals(created.getCode(), response.getCode());
        assertEquals(created.getDescription(), response.getDescription());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar publicar cupom expirado")
    void shouldThrowWhenPublishingExpiredCoupon() {
        CreateCouponRequestDTO expired = CreateCouponBuilder.expiredRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(expired);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> publishCouponUseCase.execute(created.getId())
        );

        assertTrue(exception.getMessage().toLowerCase().contains("expirado"));
    }
}