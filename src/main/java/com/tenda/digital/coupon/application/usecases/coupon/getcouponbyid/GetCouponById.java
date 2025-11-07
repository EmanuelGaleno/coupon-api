package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

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
public class GetCouponById implements GetCouponByIdUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public GetCouponByIdResponseDTO execute(UUID id) {

        try {
            Coupon coupon = domainCouponRepository.findById(id)
                    .orElseThrow(() -> new DomainException("cupom não encontrado para o id: " + id));

            return OutputMapper.toOutput(coupon);

        } catch (Exception ex) {
            log.error("erro ao buscar cupom por id: {}", ex.getMessage(), ex);
            throw new DomainException("erro ao buscar cupom por id: " + ex.getMessage());
        }
    }
}