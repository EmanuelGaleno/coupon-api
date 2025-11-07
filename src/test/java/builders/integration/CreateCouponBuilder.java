package builders.integration;

import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import java.time.LocalDate;

public class CreateCouponBuilder {

    public static CreateCouponRequestDTO validCouponCreateRequest() {
        CreateCouponRequestDTO dto = new CreateCouponRequestDTO();
        dto.setCode("TESTECODIGO123");
        dto.setDescription("teste de cupom válido");
        dto.setDiscountValue(10.0);
        dto.setExpirationDate(LocalDate.now().plusDays(10));
        return dto;
    }

    public static CreateCouponRequestDTO invalidCouponCreateRequest() {
        CreateCouponRequestDTO dto = new CreateCouponRequestDTO();
        dto.setCode("INVALIDO");
        dto.setDescription("cupom inválido");
        dto.setDiscountValue(0.2);
        dto.setExpirationDate(LocalDate.now().plusDays(10));
        return dto;
    }
}