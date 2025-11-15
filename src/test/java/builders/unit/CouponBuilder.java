package builders.unit;

import java.time.LocalDate;
import io.emanuel.ms.coupon.domain.entity.valueobjects.CouponData;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponCode;
import io.emanuel.ms.coupon.domain.entity.aggregates.CouponDescription;

public class CouponBuilder {

    public static CouponData validCouponData() {
        return buildData("TENDA10", "desconto padrão", 10.0, LocalDate.now().plusDays(10));
    }

    public static CouponData expiredCouponData() {
        return buildData("EXPIRED", "expirado", 5.0, LocalDate.now().minusDays(1));
    }

    private static CouponData buildData(String code, String description, double discount, LocalDate expirationDate) {
        return CouponData.builder()
                .code(CouponCode.of(code))
                .description(CouponDescription.of(description))
                .discountValue(discount)
                .expirationDate(expirationDate)
                .published(false)
                .redeemed(false)
                .build();
    }
}