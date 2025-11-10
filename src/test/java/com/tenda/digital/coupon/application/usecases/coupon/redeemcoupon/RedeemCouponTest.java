package com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon;

import com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.application.usecases.coupon.publishcoupon.PublishCouponUseCase;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedeemCouponTest {

    @Autowired
    private CreateCouponUsecase createCouponUsecase;

    @Autowired
    private PublishCouponUseCase publishCouponUseCase;

    @Autowired
    private RedeemCouponUseCase redeemCouponUseCase;

    @Autowired
    private  DomainCouponRepository domainCouponRepository;

    @Test
    @DisplayName("Deve resgatar cupom publicado e salvar estado no banco")
    void shouldRedeemCouponAndPersistChanges() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        publishCouponUseCase.execute(created.getId());
        RedeemCouponResponseDTO response = redeemCouponUseCase.execute(created.getId());

        assertTrue(response.getRedeemed());
        assertNotNull(response.getId());

        Coupon redeemed = domainCouponRepository.findById(created.getId())
                .orElseThrow(() -> new AssertionError("Cupom não encontrado após resgate"));

        assertTrue(redeemed.getRedeemed());
        assertTrue(redeemed.getPublished());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom não publicado")
    void testRedeemUnpublishedCoupon() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> redeemCouponUseCase.execute(created.getId())
        );

        assertTrue(exception.getMessage().contains("não publicado"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom expirado após publicação")
    void testRedeemExpiredCoupon() {
        CreateCouponRequestDTO request = CreateCouponBuilder.validRequest();
        CreateCouponResponseDTO created = createCouponUsecase.execute(request);

        publishCouponUseCase.execute(created.getId());

        Coupon coupon = domainCouponRepository.findById(created.getId())
                .orElseThrow(() -> new AssertionError("Cupom não encontrado para expiração"));
        coupon.update(
                coupon.getCode().value(),
                coupon.getDescription().value(),
                coupon.getDiscountValue(),
                LocalDate.now().minusDays(1)
        );
        domainCouponRepository.save(coupon);
        UUID couponId = created.getId();
        DomainException exception = assertThrows(DomainException.class, () -> redeemCouponUseCase.execute(couponId));

        assertNotNull(exception.getMessage());
        assertTrue(
                exception.getMessage().toLowerCase().contains("expirado"),
                "Mensagem de erro deve indicar que o cupom está expirado"
        );
    }
}