package com.tenda.digital.coupon.models.dto;

import com.tenda.digital.coupon.application.persistence.coupon.CouponEntity;
import com.tenda.digital.coupon.application.utility.StringTransformUtil;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.tenda.digital.exception.generics.BadRequestException;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotNull
    private Double discountValue;

    @NotNull
    private LocalDate expirationDate;

    private Boolean published;

    public void validate() {
        if (discountValue < 0.5) {
            throw new BadRequestException("discount value must be at least 0.5");
        }
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("expiration date cannot be in the past");
        }
    }

    public void setCode(@NotBlank String code) {
        this.code = StringTransformUtil.transform(
                code,
                StringTransformUtil.toUpperCase,
                StringTransformUtil.removeAccents,
                StringTransformUtil.removeAllSpaces,
                StringTransformUtil.removeNonAlphanumeric
        );
    }

    public void setDescription(@NotBlank String description) {
        this.description = StringTransformUtil.transform(
                description,
                StringTransformUtil.toLowerCase,
                StringTransformUtil.removeExtraSpaces
        );
    }

    public CouponEntity toEntity() {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCode(this.getCode());
        couponEntity.setDescription(this.getDescription());
        couponEntity.setDiscountValue(this.getDiscountValue());
        couponEntity.setExpirationDate(this.getExpirationDate());
        couponEntity.setPublished(this.getPublished());
        return couponEntity;
    }

}
