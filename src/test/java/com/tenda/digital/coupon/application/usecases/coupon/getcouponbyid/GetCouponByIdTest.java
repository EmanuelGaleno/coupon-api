package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetCouponByIdTest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private GetCouponByIdUseCase getCouponByIdUseCase;

    @Test
    @DisplayName("Deve retornar cupom existente por ID")
    void shouldFindCouponById() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        GetCouponByIdResponseDTO response = getCouponByIdUseCase.execute(created.getId());

        assertNotNull(response);
        assertEquals(created.getId(), response.getId());
        assertEquals(created.getCode(), response.getCode());
        assertEquals(created.getDescription(), response.getDescription());
        assertFalse(response.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cupom inexistente por ID")
    void shouldThrowExceptionWhenIdNotFound() {
        UUID randomId = UUID.randomUUID();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> getCouponByIdUseCase.execute(randomId)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}