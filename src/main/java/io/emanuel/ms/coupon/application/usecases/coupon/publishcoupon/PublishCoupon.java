package io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon;

import io.emanuel.ms.coupon.common.exceptions.DomainException;
import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishCoupon implements PublishCouponUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public PublishCouponResponseDTO execute(UUID id) {

        Coupon coupon = domainCouponRepository.findById(id)
                .orElseThrow(() -> new DomainException("cupom não encontrado para o id: " + id));

        coupon.publish();
        Coupon updatedCoupon = domainCouponRepository.save(coupon);

        log.info("cupom publicado com sucesso: {}", updatedCoupon.getCode().value());
        return OutputMapper.toOutput(updatedCoupon);
    }
}