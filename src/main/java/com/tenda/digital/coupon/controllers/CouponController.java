package com.tenda.digital.coupon.controllers;

import com.tenda.digital.coupon.bussiness.interfaces.ICouponBO;
import com.tenda.digital.coupon.models.dto.CouponDTO;
import com.tenda.digital.coupon.models.dto.CouponRedeemedDTO;
import com.tenda.digital.coupon.models.dto.CouponResponseDTO;
import com.tenda.digital.coupon.models.dto.CouponUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@Tags(value = @Tag(name = "Coupon", description = "Endpoints relacionados à gestão de cupons."))
@RequiredArgsConstructor
public class CouponController {

    private final ICouponBO couponBO;

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createSchedule(
            @RequestBody CouponDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(couponBO.createCoupon(request));
    }

    @GetMapping("code/{code}")
    public ResponseEntity<CouponResponseDTO> getCouponByCode(@PathVariable String code) {
        return ResponseEntity.ok(couponBO.getCouponByCode(code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable UUID id) {
        return ResponseEntity.ok(couponBO.getCouponById(id));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<CouponResponseDTO> publishCoupon(@PathVariable UUID id) {
        return ResponseEntity.ok(couponBO.publishCoupon(id));
    }

    @PatchMapping("/{id}/redeem")
    public ResponseEntity<CouponRedeemedDTO> redeemCoupon(@PathVariable UUID id) {
        return ResponseEntity.ok(couponBO.redeemCoupon(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponUpdateDTO> updateCoupon(
            @PathVariable UUID id,
            @RequestBody CouponUpdateDTO dto
    ) {
        return ResponseEntity.ok(couponBO.updateCoupon(id, dto));
    }

}
