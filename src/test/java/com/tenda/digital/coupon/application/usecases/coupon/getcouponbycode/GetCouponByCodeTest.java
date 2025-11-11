package com.tenda.digital.coupon.application.usecases.coupon.getcouponbycode;

import com.tenda.digital.coupon.E2ETest;
import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.builders.GetCouponByCodeBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GetCouponByCodeUseCase")
class GetCouponByCodeTest extends E2ETest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private GetCouponByCodeUseCase getCouponByCodeUseCase;

    @Test
    @DisplayName("Deve retornar cupom existente por código")
    void shouldFindCouponByCode() {
        String validCode = GetCouponByCodeBuilder.validCouponCode();
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        request.setCode(validCode);

        CreateCouponResponseDTO created = createCouponUsecase.execute(request);
        GetCouponByCodeResponseDTO response = getCouponByCodeUseCase.execute(validCode);

        assertNotNull(response);
        assertEquals(created.getCode(), response.getCode());
        assertEquals(created.getDescription(), response.getDescription());
        assertEquals(created.getDiscountValue(), response.getDiscountValue());
        assertFalse(response.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cupom inexistente por código")
    void shouldThrowExceptionWhenCodeNotFound() {
        String invalidCode = GetCouponByCodeBuilder.invalidCouponCode();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> getCouponByCodeUseCase.execute(invalidCode)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}
