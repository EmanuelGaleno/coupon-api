package io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon;

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
public class UpdateCoupon implements UpdateCouponUsecase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public UpdateCouponResponseDTO execute(UUID id, UpdateCouponRequestDTO request) {

        Coupon coupon = domainCouponRepository.findById(id)
                .orElseThrow(() -> new DomainException("cupom não encontrado para o id: " + id));

        coupon.update(
                request.getCode(),
                request.getDescription(),
                request.getDiscountValue(),
                request.getExpirationDate()
        );

        Coupon updatedCoupon = domainCouponRepository.save(coupon);

        log.info("cupom atualizado com sucesso: {}", updatedCoupon.getCode().value());
        return OutputMapper.toOutput(updatedCoupon);
    }
}