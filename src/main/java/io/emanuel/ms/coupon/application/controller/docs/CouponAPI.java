package io.emanuel.ms.coupon.application.controller.docs;

import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbycode.GetCouponByCodeResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.getcouponbyid.GetCouponByIdResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.publishcoupon.PublishCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.redeemcoupon.RedeemCouponResponseDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import io.emanuel.ms.coupon.application.usecases.coupon.updatecoupon.UpdateCouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

public interface CouponAPI {

    @Operation(
            summary = "Cria um novo cupom",
            description = """
                    Cria um novo cupom com as informações fornecidas. 
                    O código é automaticamente normalizado (sem acentos, espaços ou caracteres especiais).
                    """
    )
    ResponseEntity<CreateCouponResponseDTO> createCoupon(CreateCouponRequestDTO request);

    @Operation(
            summary = "Busca um cupom pelo código",
            description = """
                    Retorna os detalhes de um cupom a partir do seu código.
                    O código é tratado de forma normalizada (sem espaços, acentos ou caracteres especiais).
                    """
    )
    ResponseEntity<GetCouponByCodeResponseDTO> getCouponByCode(String code);

    @Operation(
            summary = "Busca um cupom pelo ID",
            description = """
                    Retorna os detalhes de um cupom a partir do seu ID (UUID).
                    """
    )
    ResponseEntity<GetCouponByIdResponseDTO> getCouponById(UUID id);

    @Operation(
            summary = "Publica um cupom",
            description = "Publica um cupom válido para ativá-lo no sistema."
    )
    ResponseEntity<PublishCouponResponseDTO> publishCoupon(UUID id);

    @Operation(
            summary = "Resgata um cupom",
            description = "Marca um cupom publicado e não expirado como resgatado."
    )
    ResponseEntity<RedeemCouponResponseDTO> redeemCoupon(UUID id);

    @Operation(
            summary = "Atualiza um cupom",
            description = "Atualiza os dados de um cupom existente."
    )
    ResponseEntity<UpdateCouponResponseDTO> updateCoupon(UUID id, UpdateCouponRequestDTO request);
}