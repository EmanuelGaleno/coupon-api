package io.emanuel.ms.coupon.application.usecases.coupon.redeemcoupon;

import io.emanuel.ms.coupon.E2ETest;
import io.emanuel.ms.coupon.application.usecases.builders.CreateCouponBuilder;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon.PublishCouponUseCase;
import io.emanuel.ms.coupon.common.exceptions.DomainException;
import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.repository.DomainCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RedeemCouponUseCase")
@Transactional
class RedeemCouponTest extends E2ETest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private PublishCouponUseCase publishCouponUseCase;

    @Autowired
    private RedeemCouponUseCase redeemCouponUseCase;

    @Autowired
    private DomainCouponRepository domainCouponRepository;

    @Test
    @DisplayName("Deve resgatar cupom publicado e persistir alteração no banco")
    void shouldRedeemCouponSuccessfully() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        publishCouponUseCase.execute(created.getId());
        RedeemCouponResponseDTO response = redeemCouponUseCase.execute(created.getId());

        assertTrue(response.getRedeemed());

        Coupon redeemed = domainCouponRepository.findById(created.getId())
                .orElseThrow(AssertionError::new);

        assertTrue(redeemed.getRedeemed());
        assertTrue(redeemed.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom não publicado")
    void shouldThrowWhenRedeemingUnpublishedCoupon() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        UUID couponId = created.getId();
        assertThrows(DomainException.class, () -> redeemCouponUseCase.execute(couponId));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom expirado após publicação")
    void shouldThrowWhenRedeemingExpiredCoupon() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        publishCouponUseCase.execute(created.getId());

        Coupon coupon = domainCouponRepository.findById(created.getId())
                .orElseThrow(AssertionError::new);

        coupon.update(
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                LocalDate.now().minusDays(1)
        );

        domainCouponRepository.save(coupon);
        UUID expiredId = created.getId();

        assertThrows(DomainException.class, () -> redeemCouponUseCase.execute(expiredId));
    }
}
