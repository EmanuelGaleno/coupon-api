package com.tenda.digital.coupon.application.usecases.coupon.getcouponbycode;

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
class GetCouponByCodeTest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private GetCouponByCodeUseCase getCouponByCodeUseCase;

    @Test
    @DisplayName("Deve retornar cupom existente por código")
    void shouldFindCouponByCode() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        GetCouponByCodeResponseDTO response = getCouponByCodeUseCase.execute(created.getCode());

        assertNotNull(response);
        assertEquals(created.getCode(), response.getCode());
        assertEquals(created.getDescription(), response.getDescription());
        assertEquals(created.getDiscountValue(), response.getDiscountValue());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cupom inexistente por código")
    void shouldThrowExceptionWhenCodeNotFound() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> getCouponByCodeUseCase.execute("CODIGO_INEXISTENTE")
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}