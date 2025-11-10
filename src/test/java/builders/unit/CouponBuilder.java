package builders.unit;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.LocalDate;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponData;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponCode;
import com.tenda.digital.coupon.domain.entity.aggregates.CouponDescription;

public class CouponBuilder {

    public static Coupon createValidCoupon() {
        return Coupon.create(buildData("TENDA10", "desconto padrão", 10.0, LocalDate.now().plusDays(10)));
    }

    public static Coupon createExpiredCoupon() {
        return Coupon.create(buildData("EXPIRED", "expirado", 5.0, LocalDate.now().minusDays(1)));
    }

    public static Coupon createPublishedCoupon() {
        Coupon coupon = createValidCoupon();
        coupon.publish();
        return coupon;
    }

    public static Coupon createCustomCoupon(String code, String description, double discount, LocalDate expirationDate) {
        return Coupon.create(buildData(code, description, discount, expirationDate));
    }

    public static Coupon rebuildCoupon(UUID id, CouponData data) {
        return Coupon.rebuild(data, id, LocalDateTime.now(), LocalDateTime.now());
    }

    private static CouponData buildData(String code, String description, double discount, LocalDate expirationDate) {
        return CouponData.builder()
                .code(CouponCode.of(code))
                .description(CouponDescription.of(description))
                .discountValue(discount)
                .expirationDate(expirationDate)
                .build();
    }
}