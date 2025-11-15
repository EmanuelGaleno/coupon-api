package io.emanuel.ms.coupon.application.usecases.coupon.getcouponbyid;

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
public class GetCouponById implements GetCouponByIdUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public GetCouponByIdResponseDTO execute(UUID id) {

        Coupon coupon = domainCouponRepository.findById(id)
                .orElseThrow(() -> new DomainException("cupom não encontrado para o id: " + id));

        return OutputMapper.toOutput(coupon);
    }
}