package com.tenda.digital.coupon.bussiness;

import java.util.UUID;
import java.time.LocalDate;

import com.tenda.digital.coupon.application.persistence.coupon.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tenda.digital.coupon.models.dto.CouponDTO;
import com.tenda.digital.coupon.models.dto.CouponUpdateDTO;
import com.tenda.digital.coupon.models.dto.CouponRedeemedDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.repositories.OldCouponRepository;
import com.tenda.digital.coupon.bussiness.interfaces.ICouponBO;
import com.tenda.digital.exception.generics.BadRequestException;
import com.tenda.digital.exception.generics.ResourceNotFoundException;

//////@Service
//@RequiredArgsConstructor
//public class CouponBO implements ICouponBO {
//
//    private final OldCouponRepository oldCouponRepository;
//
//    public CreateCouponResponseDTO createCoupon(CouponDTO dto) {
//
//        dto.validate();
//
//        CouponEntity couponEntity = dto.toEntity();
//
//        return CreateCouponResponseDTO.fromCoupon(oldCouponRepository.save(couponEntity));
//    }
//
//    public CreateCouponResponseDTO getCouponByCode(String code) {
//        return oldCouponRepository.findByCode(code).map(CreateCouponResponseDTO::fromCoupon)
//                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
//    }
//
//    @Override
//    public CreateCouponResponseDTO getCouponById(UUID id) {
//        return oldCouponRepository.findById(id).map(CreateCouponResponseDTO::fromCoupon)
//                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
//    }
//
//    public CreateCouponResponseDTO publishCoupon(UUID id) {
//        CouponEntity couponEntity = oldCouponRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
//
//        if (couponEntity.getDiscountValue() < 0.5) {
//            throw new BadRequestException("cannot publish coupon with discount < 0.5");
//        }
//        couponEntity.setPublished(true);
//        return CreateCouponResponseDTO.fromCoupon(oldCouponRepository.save(couponEntity));
//    }
//
//    public CouponRedeemedDTO redeemCoupon(UUID id) {
//
//        CouponEntity couponEntity = oldCouponRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
//
//        if (couponEntity.getPublished() != null && !couponEntity.getPublished()) {
//            throw new BadRequestException("cannot redeem unpublished coupon");
//        }
//
//        if (couponEntity.getExpirationDate().isBefore(LocalDate.now())) {
//            throw new BadRequestException("coupon has expired");
//        }
//
//        couponEntity.setRedeemed(true);
//
//        oldCouponRepository.save(couponEntity);
//
//        return new CouponRedeemedDTO(couponEntity.getId(), couponEntity.getRedeemed());
//    }

//    public CouponUpdateDTO updateCoupon(UUID id, CouponUpdateDTO dto) {
//
//        CouponEntity couponEntity = oldCouponRepository.findById(id)
//               .orElseThrow(() -> new ResourceNotFoundException("coupon not found"));
//
//        couponEntity.setCode(dto.getCode());
//        couponEntity.setDescription(dto.getDescription());
//        couponEntity.setDiscountValue(dto.getDiscountValue());
//        couponEntity.setExpirationDate(dto.getExpirationDate());
//        couponEntity.setPublished(dto.getPublished());
//
//        return CouponUpdateDTO.fromCoupon(oldCouponRepository.save(couponEntity));
//    }
//
//}
