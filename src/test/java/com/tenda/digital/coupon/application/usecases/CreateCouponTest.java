package com.tenda.digital.coupon.application.usecases;

import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCoupon;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponCode;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponDescription;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.UUID;

import static builders.integration.CreateCouponBuilder.invalidCouponCreateRequest;
import static builders.integration.CreateCouponBuilder.validCouponCreateRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCouponTest {

    @Mock
    private DomainCouponRepository domainCouponRepository;

    @InjectMocks
    private CreateCoupon createCoupon;

    @Test
    @DisplayName("Deve criar cupom com sucesso")
    void testCreateCouponSuccess() {
        CreateCouponRequestDTO request = validCouponCreateRequest();

        Coupon mockCoupon = Coupon.builder()
                .id(UUID.randomUUID())
                .code(CouponCode.of(request.getCode()))
                .description(CouponDescription.of(request.getDescription()))
                .discountValue(request.getDiscountValue())
                .expirationDate(request.getExpirationDate())
                .published(false)
                .redeemed(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(domainCouponRepository.save(any(Coupon.class))).thenReturn(mockCoupon);

        CreateCouponResponseDTO response = createCoupon.execute(request);

        assertNotNull(response);
        assertEquals(request.getCode().replaceAll("[^a-zA-Z0-9]", ""), response.getCode());
        assertEquals(request.getDescription(), response.getDescription());
        verify(domainCouponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar cupom inválido")
    void testCreateCouponInvalid() {
        CreateCouponRequestDTO request = invalidCouponCreateRequest();

        DomainException exception = assertThrows(DomainException.class, () -> createCoupon.execute(request));

        assertTrue(exception.getMessage().contains("erro ao criar cupom"));
    }
}