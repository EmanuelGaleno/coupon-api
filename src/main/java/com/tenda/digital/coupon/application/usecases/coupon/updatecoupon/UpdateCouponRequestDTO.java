package com.tenda.digital.coupon.application.usecases.coupon.updatecoupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCouponRequestDTO {

    @Schema(example = "TENDA30", description = "Novo código do cupom")
    private String code;

    @Schema(example = "Cupom atualizado com novo valor de desconto", description = "Descrição atualizada do cupom.")
    private String description;

    @Schema(example = "30.0", description = "Novo valor de desconto aplicado ao cupom.")
    private Double discountValue;

    @Schema(example = "2025-12-31", description = "Nova data de expiração do cupom.")
    private LocalDate expirationDate;

    @Schema(example = "true", description = "Indica se o cupom deve ser marcado como publicado.")
    private Boolean published;
}