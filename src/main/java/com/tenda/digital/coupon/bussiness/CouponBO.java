package com.tenda.digital.coupon.bussiness;

import java.util.UUID;
import java.time.LocalDate;

import com.tenda.digital.coupon.application.persistence.coupon.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tenda.digital.coupon.models.dto.CouponDTO;
import com.tenda.digital.coupon.models.dto.CouponUpdateDTO;
import com.tenda.digital.coupon.models.dto.CouponRedeemedDTO;
import com.tenda.digital.coupon.models.dto.CouponResponseDTO;
import com.tenda.digital.coupon.repositories.CouponRepository;
import com.tenda.digital.coupon.bussiness.interfaces.ICouponBO;
import com.tenda.digital.exception.generics.BadRequestException;
import com.tenda.digital.exception.generics.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CouponBO implements ICouponBO {

    private final CouponRepository couponRepository;

    public CouponResponseDTO createCoupon(CouponDTO dto) {

        dto.validate();

        CouponEntity couponEntity = dto.toEntity();

        return CouponResponseDTO.fromCoupon(couponRepository.save(couponEntity));
    }

    public CouponResponseDTO getCouponByCode(String code) {
        return couponRepository.findByCode(code).map(CouponResponseDTO::fromCoupon)
                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
    }

    @Override
    public CouponResponseDTO getCouponById(UUID id) {
        return couponRepository.findById(id).map(CouponResponseDTO::fromCoupon)
                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
    }

    public CouponResponseDTO publishCoupon(UUID id) {
        CouponEntity couponEntity = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));

        if (couponEntity.getDiscountValue() < 0.5) {
            throw new BadRequestException("cannot publish coupon with discount < 0.5");
        }
        couponEntity.setPublished(true);
        return CouponResponseDTO.fromCoupon(couponRepository.save(couponEntity));
    }

    public CouponRedeemedDTO redeemCoupon(UUID id) {

        CouponEntity couponEntity = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));

        if (couponEntity.getPublished() != null && !couponEntity.getPublished()) {
            throw new BadRequestException("cannot redeem unpublished coupon");
        }

        if (couponEntity.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("coupon has expired");
        }

        couponEntity.setRedeemed(true);

        couponRepository.save(couponEntity);

        return new CouponRedeemedDTO(couponEntity.getId(), couponEntity.getRedeemed());
    }

    public CouponUpdateDTO updateCoupon(UUID id, CouponUpdateDTO dto) {

        CouponEntity couponEntity = couponRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));

        couponEntity.setCode(dto.getCode());
        couponEntity.setDescription(dto.getDescription());
        couponEntity.setDiscountValue(dto.getDiscountValue());
        couponEntity.setExpirationDate(dto.getExpirationDate());
        couponEntity.setPublished(dto.getPublished());

        return CouponUpdateDTO.fromCoupon(couponRepository.save(couponEntity));
    }

}
