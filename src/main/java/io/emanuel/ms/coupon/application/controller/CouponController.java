package io.emanuel.ms.coupon.application.controller;


import io.emanuel.ms.coupon.application.controller.docs.CouponAPI;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbycode.GetCouponByCodeResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbycode.GetCouponByCodeUseCase;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbyid.GetCouponByIdResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbyid.GetCouponByIdUseCase;
import io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon.PublishCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon.PublishCouponUseCase;
import io.emanuel.ms.coupon.application.usecases.coupon.redeemcoupon.RedeemCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.redeemcoupon.RedeemCouponUseCase;
import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController implements CouponAPI {

    private final CreateCouponUsecase createCouponUsecase;
    private final GetCouponByIdUseCase getCouponByIdUseCase;
    private final GetCouponByCodeUseCase getCouponByCodeUseCase;
    private final UpdateCouponUsecase updateCouponUsecase;
    private final PublishCouponUseCase publishCouponUseCase;
    private final RedeemCouponUseCase redeemCouponUsecase;

    @Override
    @PostMapping
    public ResponseEntity<CreateCouponResponseDTO> createCoupon(
            @RequestBody CreateCouponRequestDTO request
    ) {
        CreateCouponResponseDTO createCouponResponseDTO = createCouponUsecase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCouponResponseDTO);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<GetCouponByCodeResponseDTO> getCouponByCode(@PathVariable String code) {
        return ResponseEntity.ok(getCouponByCodeUseCase.execute(code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCouponByIdResponseDTO> getCouponById(@PathVariable UUID id) {
        return ResponseEntity.ok(getCouponByIdUseCase.execute(id));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<PublishCouponResponseDTO> publishCoupon(@PathVariable UUID id) {
        return ResponseEntity.ok(publishCouponUseCase.execute(id));
    }

    @PostMapping("/{id}/redeem")
    public ResponseEntity<RedeemCouponResponseDTO> redeemCoupon(@PathVariable UUID id) {
        return ResponseEntity.ok(redeemCouponUsecase.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCouponResponseDTO> updateCoupon(
            @PathVariable UUID id,
            @RequestBody UpdateCouponRequestDTO request
    ) {
        return ResponseEntity.ok(updateCouponUsecase.execute(id, request));
    }
}
