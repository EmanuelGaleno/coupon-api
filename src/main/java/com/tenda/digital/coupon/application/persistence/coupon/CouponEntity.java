package com.tenda.digital.coupon.application.persistence.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupon", indexes = {
        @Index(columnList = "created_at, updated_at, redeemed, published, expiration_date, code, discount_value")
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponEntity {

    @Id
    private UUID id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "redeemed")
    private Boolean redeemed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
