package com.tenda.digital.coupon.application.controller;


import com.tenda.digital.coupon.application.controller.docs.CouponAPI;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController implements CouponAPI {

    private final CreateCouponUsecase createCouponUsecase;

    @Override
    @PostMapping
    public ResponseEntity<CreateCouponResponseDTO>createCoupon(
            @RequestBody CreateCouponRequestDTO request
    ){
        CreateCouponResponseDTO createCouponResponseDTO = createCouponUsecase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCouponResponseDTO);
    }

}
