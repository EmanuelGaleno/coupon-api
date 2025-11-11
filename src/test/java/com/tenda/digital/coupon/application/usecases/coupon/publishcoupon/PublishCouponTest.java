package com.tenda.digital.coupon.application.usecases.coupon.publishcoupon;

import com.tenda.digital.coupon.E2ETest;
import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PublishCouponUseCase")
@Transactional
class PublishCouponTest extends E2ETest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private PublishCouponUseCase publishCouponUseCase;

    @Autowired
    private DomainCouponRepository domainCouponRepository;

    @Test
    @DisplayName("Deve publicar cupom válido com sucesso")
    void shouldPublishCouponSuccessfully() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        PublishCouponResponseDTO response = publishCouponUseCase.execute(created.getId());

        assertNotNull(response);
        assertTrue(response.getPublished());
        assertEquals(created.getCode(), response.getCode());

        Coupon published = domainCouponRepository.findById(created.getId())
                .orElseThrow(AssertionError::new);

        assertTrue(published.getPublished());
        assertFalse(published.getRedeemed());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar publicar cupom expirado")
    void shouldThrowWhenPublishingExpiredCoupon() {
        CreateCouponRequestDTO expiredRequest = CreateCouponBuilder.expiredRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(expiredRequest);

        UUID couponId = created.getId();

        assertThrows(DomainException.class, () -> publishCouponUseCase.execute(couponId));
    }
}
