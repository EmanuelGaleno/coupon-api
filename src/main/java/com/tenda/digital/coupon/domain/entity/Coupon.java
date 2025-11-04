package com.tenda.digital.coupon.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import com.tenda.digital.coupon.common.exceptions.DomainException;
import lombok.Getter;

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


    private Coupon(UUID id, String code, String description, Double discountValue,
                   LocalDate expirationDate, Boolean published, Boolean redeemed,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.redeemed = redeemed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        selfValidate(this);
    }

    public static Coupon create(String code, String description, Double discountValue, LocalDate expirationDate) {
        return new Coupon(
                UUID.randomUUID(),
                code,
                description,
                discountValue,
                expirationDate,
                false,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    public static Coupon rebuild(UUID id, String code, String description, Double discountValue,
                                 LocalDate expirationDate, Boolean published, Boolean redeemed,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Coupon(id, code, description, discountValue, expirationDate, published, redeemed, createdAt, updatedAt);
    }


    public void publish() {
        if (discountValue < 0.5) {
            throw new DomainException("Não é permitido publicar um cupom com desconto menor que 0.5.");
        }
        this.published = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void redeem() {
        if (Boolean.FALSE.equals(published)) {
            throw new DomainException("Não é possível resgatar um cupom não publicado.");
        }
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new DomainException("Cupom expirado.");
        }
        this.redeemed = true;
        this.updatedAt = LocalDateTime.now();
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
                    "Falha ao validar instância de " + CLAZZ,
                    result.getErrors().stream().map(Error::getMessage).toList()
            );
        }
    }
}