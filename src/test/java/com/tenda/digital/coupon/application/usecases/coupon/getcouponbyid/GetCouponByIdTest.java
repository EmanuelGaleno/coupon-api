package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

import com.tenda.digital.coupon.E2ETest;
import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.builders.GetCouponByIdBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GetCouponByIdUseCase")
class GetCouponByIdTest extends E2ETest {

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
        var invalidId = GetCouponByIdBuilder.invalidCouponId();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> getCouponByIdUseCase.execute(invalidId)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}
