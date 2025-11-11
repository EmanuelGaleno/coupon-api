package com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon;

import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedeemCoupon implements RedeemCouponUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public RedeemCouponResponseDTO execute(UUID id) {

        Coupon coupon = domainCouponRepository.findById(id)
                .orElseThrow(() -> new DomainException("cupom não encontrado para o id: " + id));

        coupon.redeem();
        Coupon updatedCoupon = domainCouponRepository.save(coupon);

        log.info("cupom resgatado com sucesso: {}", updatedCoupon.getCode().value());
        return OutputMapper.toOutput(updatedCoupon);
    }
}