package com.tenda.digital.coupon.application.usecases.coupon.createcoupon;

import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponCode;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponDescription;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCoupon implements CreateCouponUsecase {

    private final DomainCouponRepository domainCouponRepository;

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

        return new CreateCouponResponseDTO(
                savedCoupon.getId(),
                savedCoupon.getCode().value(),
                savedCoupon.getDescription().value(),
                savedCoupon.getDiscountValue(),
                savedCoupon.getExpirationDate()
        );
    }
}