package com.tenda.digital.coupon.application.usecases.coupon.createcoupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateCouponRequestDTO {

    @Schema(example = "c9a29d7a-9f83-4e54-8c8b-234bf4d9bccc", description = "Identificador único opcional do cupom")
    private UUID id;

    @Schema(example = "TENDA20", description = "Código do cupom.")
    private String code;

    @Schema(example = "Cupom de 20% de desconto na primeira compra", description = "Descrição do cupom exibida ao usuário.")
    private String description;

    @Schema(example = "20.0", description = "Valor percentual ou fixo de desconto aplicado pelo cupom.")
    private Double discountValue;

    @Schema(example = "2025-12-31", description = "Data de expiração do cupom no formato ISO.")
    private LocalDate expirationDate;
}