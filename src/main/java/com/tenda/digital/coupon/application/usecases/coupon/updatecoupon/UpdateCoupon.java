package com.tenda.digital.coupon.application.usecases.coupon.updatecoupon;

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
public class UpdateCoupon implements UpdateCouponUsecase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public UpdateCouponResponseDTO execute(UUID id, UpdateCouponRequestDTO request) {

        try {
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

        } catch (Exception ex) {
            log.error("erro ao atualizar cupom: {}", ex.getMessage(), ex);
            throw new DomainException("erro ao atualizar cupom: " + ex.getMessage());
        }
    }
}