package com.tenda.digital.coupon.application.usecases.coupon.getcouponbycode;

import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCouponByCode implements GetCouponByCodeUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public GetCouponByCodeResponseDTO execute(String code) {

        Coupon coupon = domainCouponRepository.findByCode(code)
                .orElseThrow(() -> new DomainException("cupom não encontrado para o código: " + code));

        return OutputMapper.toOutput(coupon);
    }
}

