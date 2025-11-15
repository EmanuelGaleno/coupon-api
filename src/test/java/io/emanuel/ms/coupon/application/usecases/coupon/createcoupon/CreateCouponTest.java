package io.emanuel.ms.coupon.application.usecases.coupon.createcoupon;

import io.emanuel.ms.coupon.E2ETest;
import io.emanuel.ms.coupon.common.exceptions.DomainException;
import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.repository.DomainCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static io.emanuel.ms.coupon.application.usecases.builders.CreateCouponBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreateCouponUseCase")
@Transactional
class CreateCouponTest extends E2ETest {

    private final CreateCoupon createCoupon;
    private final DomainCouponRepository domainCouponRepository;

    @Autowired
    CreateCouponTest(DomainCouponRepository domainCouponRepository, CreateCoupon createCoupon) {
        this.createCoupon = createCoupon;
        this.domainCouponRepository = domainCouponRepository;
    }

    @Test
    @DisplayName("Deve criar cupom válido e persistir no banco")
    void shouldCreateCouponSuccessfully() {
        CreateCouponRequestDTO request = validRequest();
        CreateCouponResponseDTO response = createCoupon.execute(request);

        assertNotNull(response);
        assertEquals(request.getCode().toUpperCase(), response.getCode());
        assertEquals(request.getDescription().toLowerCase().trim(), response.getDescription());
        assertEquals(request.getDiscountValue(), response.getDiscountValue());
        assertNotNull(response.getId());
        assertNotNull(response.getExpirationDate());

        Coupon saved = domainCouponRepository.findById(response.getId())
                .orElseThrow(AssertionError::new);
        assertEquals(response.getCode(), saved.getCode().value());
        assertFalse(saved.getPublished());
        assertFalse(saved.getRedeemed());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertFalse(saved.getUpdatedAt().isBefore(saved.getCreatedAt()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar cupom inválido")
    void shouldThrowExceptionWhenInvalidCoupon() {
        CreateCouponRequestDTO invalid = invalidRequest();
        assertThrows(DomainException.class, () -> createCoupon.execute(invalid));
    }
}
