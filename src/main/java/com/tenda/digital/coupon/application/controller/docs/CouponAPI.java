package com.tenda.digital.coupon.application.controller.docs;

import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CouponAPI {
    @Operation(
            summary = "Cria um novo cupom",
            description = """
                    Cria um novo cupom com as informações fornecidas. 
                    O código do cupom é automaticamente normalizado (sem acentos, espaços ou caracteres especiais).
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cupom criado com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação ao criar o cupom.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    ResponseEntity<CreateCouponResponseDTO> createCoupon(CreateCouponRequestDTO request);
}