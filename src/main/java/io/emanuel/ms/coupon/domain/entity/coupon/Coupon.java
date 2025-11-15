package io.emanuel.ms.coupon.domain.entity.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import io.emanuel.ms.coupon.common.exceptions.DomainException;
import io.emanuel.ms.coupon.domain.entity.validators.CouponValidator;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponCode;
import io.emanuel.ms.coupon.domain.entity.valueobjects.CouponData;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponDescription;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Coupon {

    private final UUID id;
    private CouponCode code;
    private CouponDescription description;
    private Double discountValue;
    private LocalDate expirationDate;
    private Boolean published;
    private Boolean redeemed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final CouponValidator validator = new CouponValidator();

    public static Coupon create(CouponData data) {
        Coupon coupon = Coupon.builder()
                .id(UUID.randomUUID())
                .code(data.getCode())
                .description(data.getDescription())
                .discountValue(data.getDiscountValue())
                .expirationDate(data.getExpirationDate())
                .published(false)
                .redeemed(false)
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
            throw new DomainException("Não é permitido publicar um cupom com desconto menor que 0.5.");
        }
        if (isExpired()) {
            throw new DomainException("Não é permitido publicar um cupom expirado.");
        }
        this.published = true;
        this.updatedAt = LocalDateTime.now();
        selfValidate(this);
    }

    public void redeem() {
        if (Boolean.FALSE.equals(published)) {
            throw new DomainException("Não é possível resgatar um cupom não publicado.");
        }
        if (isExpired()) {
            throw new DomainException("Cupom expirado.");
        }
        this.redeemed = true;
        this.updatedAt = LocalDateTime.now();
        selfValidate(this);
    }

    public boolean isExpired() {
        return expirationDate != null && expirationDate.isBefore(LocalDate.now());
    }

    public void update(String rawCode, String description, Double discountValue, LocalDate expirationDate) {
        this.code = CouponCode.of(rawCode);
        this.description = CouponDescription.of(description);
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.updatedAt = LocalDateTime.now();
        selfValidate(this);
    }

    private static void selfValidate(Coupon coupon) {
        ValidationResult result = validator.validate(coupon);
        if (!result.isValid()) {
            throw new DomainException(
                    "coupon com instancia invalida",
                    result.getErrors().stream().map(Error::getMessage).toList()
            );
        }
    }
}