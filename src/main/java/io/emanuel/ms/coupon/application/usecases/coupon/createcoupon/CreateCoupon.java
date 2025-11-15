package io.emanuel.ms.coupon.application.usecases.coupon.createcoupon;

import io.emanuel.ms.coupon.domain.entity.coupon.Coupon;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponCode;
import io.emanuel.ms.coupon.domain.entity.valueobjects.CouponData;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponDescription;
import io.emanuel.ms.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCoupon implements CreateCouponUsecase {

    private final DomainCouponRepository domainCouponRepository;
    private final OutputMapper outputMapper;

    @Override
    public CreateCouponResponseDTO execute(CreateCouponRequestDTO input) {

        CouponData data = CouponData.builder()
                .code(CouponCode.of(input.getCode()))
                .description(CouponDescription.of(input.getDescription()))
                .discountValue(input.getDiscountValue())
                .expirationDate(input.getExpirationDate())
                .published(false)
                .redeemed(false)
                .build();

        Coupon coupon = Coupon.create(data);
        Coupon savedCoupon = domainCouponRepository.save(coupon);

        log.info("cupom criado com sucesso: {}", savedCoupon.getCode().value());

        return outputMapper.toResponse(savedCoupon);
    }
}