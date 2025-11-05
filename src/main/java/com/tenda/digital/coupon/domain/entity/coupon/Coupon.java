package com.tenda.digital.coupon.domain.entity.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.validators.CouponValidator;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Coupon {

    private final UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean redeemed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final CouponValidator validator = new CouponValidator();
    private static final String CLAZZ = Coupon.class.getSimpleName();


    public static Coupon create(CouponData data) {
        Coupon coupon = Coupon.builder()
                .id(UUID.randomUUID())
                .code(data.getCode())
                .description(data.getDescription())
                .discountValue(data.getDiscountValue())
                .expirationDate(data.getExpirationDate())
                .published(Boolean.FALSE)
                .redeemed(Boolean.FALSE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        selfValidate(coupon);
        return coupon;
    }

    public static Coupon rebuild(CouponData data, UUID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Coupon coupon = Coupon.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .code(data.getCode())
                .description(data.getDescription())
                .discountValue(data.getDiscountValue())
                .expirationDate(data.getExpirationDate())
                .published(data.getPublished())
                .redeemed(data.getRedeemed())
                .build();

        selfValidate(coupon);
        return coupon;
    }

    public void publish() {
        if (discountValue < 0.5) {
            throw new DomainException("não é permitido publicar um cupom com desconto menor que 0.5.");
        }
        if (isExpired()) {
            throw new DomainException("não é permitido publicar um cupom expirado");
        }
        this.published = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void redeem() {
        if (Boolean.FALSE.equals(published)) {
            throw new DomainException("não é possível resgatar um cupom não publicado.");
        }
        if (isExpired()) {
            throw new DomainException("cupom expirado.");
        }
        this.redeemed = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return expirationDate != null
                && expirationDate.isBefore(LocalDate.now());
    }

    public void update(String code, String description, Double discountValue, LocalDate expirationDate) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        selfValidate(this);
        this.updatedAt = LocalDateTime.now();
    }

    private static void selfValidate(Coupon coupon) {
        ValidationResult result = validator.validate(coupon);
        if (!result.isValid()) {
            throw new DomainException(
                    "falha ao validar instância de " + CLAZZ,
                    result.getErrors().stream().map(Error::getMessage).toList()
            );
        }
    }
}