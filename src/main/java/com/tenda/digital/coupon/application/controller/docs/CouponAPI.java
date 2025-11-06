package com.tenda.digital.coupon.application.controller.docs;

import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.getcouponbycode.GetCouponByCodeResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid.GetCouponByIdResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.publishcoupon.PublishCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon.RedeemCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.updatecoupon.UpdateCouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CouponAPI {

    @Operation(
            summary = "Cria um novo cupom",
            description = """
                    Cria um novo cupom com as informações fornecidas. 
                    O código é automaticamente normalizado (sem acentos, espaços ou caracteres especiais).
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<CreateCouponResponseDTO> createCoupon(CreateCouponRequestDTO request);

    @Operation(
            summary = "Busca um cupom pelo código",
            description = """
                    Retorna os detalhes de um cupom a partir do seu código.
                    O código é tratado de forma normalizada (sem espaços, acentos ou caracteres especiais).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cupom encontrado", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<GetCouponByCodeResponseDTO> getCouponByCode(String code);

    @Operation(
            summary = "Busca um cupom pelo ID",
            description = """
                    Retorna os detalhes de um cupom a partir do seu ID (UUID).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cupom encontrado", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<GetCouponByIdResponseDTO> getCouponById(UUID id);

    @Operation(
            summary = "Publica um cupom",
            description = "Publica um cupom válido para ativá-lo no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cupom publicado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<PublishCouponResponseDTO> publishCoupon(UUID id);

    @Operation(
            summary = "Resgata um cupom",
            description = "Marca um cupom publicado e não expirado como resgatado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cupom resgatado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Cupom expirado ou não publicado", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<RedeemCouponResponseDTO> redeemCoupon(UUID id);

    @Operation(
            summary = "Atualiza um cupom",
            description = "Atualiza os dados de um cupom existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cupom atualizado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<UpdateCouponResponseDTO> updateCoupon(UUID id, UpdateCouponRequestDTO request);
}