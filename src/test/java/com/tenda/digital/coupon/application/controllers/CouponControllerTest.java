package com.tenda.digital.coupon.application.controllers;

import com.tenda.digital.coupon.E2ETest;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponResponseDTO;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponUsecase;
import com.tenda.digital.coupon.application.usecases.coupon.publishcoupon.PublishCouponUseCase;
import com.tenda.digital.coupon.application.usecases.coupon.redeemcoupon.RedeemCouponUseCase;
import com.tenda.digital.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;

import static com.tenda.digital.coupon.application.usecases.builders.CreateCouponBuilder.validRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CouponController")
class CouponControllerTest extends E2ETest {

    private static final String BASE_PATH = "/coupon";

    private final CreateCouponUsecase createCouponUsecase;
    private final PublishCouponUseCase publishCouponUseCase;
    private final RedeemCouponUseCase redeemCouponUseCase;

    @Autowired
    CouponControllerTest(
            CreateCouponUsecase createCouponUsecase,
            PublishCouponUseCase publishCouponUseCase,
            RedeemCouponUseCase redeemCouponUseCase
    ) {
        this.createCouponUsecase = createCouponUsecase;
        this.publishCouponUseCase = publishCouponUseCase;
        this.redeemCouponUseCase = redeemCouponUseCase;
    }

    @BeforeEach
    void setUp() {
        assertTrue(POSTGRES_CONTAINER.isRunning());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @Nested
    @DisplayName("Criação de cupom")
    class CreateCouponTests {

        @Test
        @DisplayName("Deve criar um cupom com sucesso")
        void shouldCreateCouponSuccessfully() {
            CreateCouponRequestDTO request = validRequest();

            given()
                    .body(request)
                    .when()
                    .post()
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", notNullValue())
                    .body("code", notNullValue())
                    .body("description", equalTo(request.getDescription()))
                    .body("discountValue", equalTo(request.getDiscountValue().floatValue()));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar criar cupom inválido")
        void shouldReturn422WhenInvalidData() {
            CreateCouponRequestDTO invalid = new CreateCouponRequestDTO();
            invalid.setCode("");
            invalid.setDescription("");
            invalid.setDiscountValue(0.0);
            invalid.setExpirationDate(LocalDate.now().minusDays(1));

            given()
                    .body(invalid)
                    .when()
                    .post()
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("status", equalTo(422))
                    .body("error", equalTo("Unprocessable Entity"));
        }
    }

    @Nested
    @DisplayName("Buscar cupom por ID")
    class GetCouponByIdTests {

        @Test
        @DisplayName("Deve retornar cupom existente por ID")
        void shouldReturnCouponById() {
            CreateCouponResponseDTO created = createCouponUsecase.execute(validRequest());

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(created.getId().toString()))
                    .body("code", equalTo(created.getCode()))
                    .body("description", equalTo(created.getDescription()))
                    .body("discountValue", equalTo(created.getDiscountValue().floatValue()));
        }

        @Test
        @DisplayName("Deve retornar 422 para ID inexistente")
        void shouldReturn422ForInvalidId() {
            UUID invalidId = UUID.randomUUID();

            given()
                    .pathParam("id", invalidId)
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    @DisplayName("Buscar cupom por código")
    class GetCouponByCodeTests {

        @Test
        @DisplayName("Deve retornar cupom existente por código")
        void shouldReturnCouponByCode() {
            CreateCouponResponseDTO created = createCouponUsecase.execute(validRequest());

            given()
                    .pathParam("code", created.getCode())
                    .when()
                    .get("/code/{code}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("code", equalTo(created.getCode()))
                    .body("description", equalTo(created.getDescription()));
        }

        @Test
        @DisplayName("Deve retornar 422 para código inexistente")
        void shouldReturn422ForInvalidCode() {
            given()
                    .pathParam("code", "CUPOM_INEXISTENTE")
                    .when()
                    .get("/code/{code}")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    @DisplayName("Atualização de cupom")
    class UpdateCouponTests {

        @Test
        @DisplayName("Deve atualizar cupom com sucesso")
        void shouldUpdateCouponSuccessfully() {
            CreateCouponResponseDTO created = createCouponUsecase.execute(validRequest());

            UpdateCouponRequestDTO update = new UpdateCouponRequestDTO();
            update.setCode("NOVOCUPOM10");
            update.setDescription("cupom atualizado");
            update.setDiscountValue(15.0);
            update.setExpirationDate(LocalDate.now().plusDays(10));

            given()
                    .pathParam("id", created.getId())
                    .body(update)
                    .when()
                    .put("/{id}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("code", equalTo(update.getCode()))
                    .body("description", equalTo(update.getDescription().toLowerCase()))
                    .body("discountValue", equalTo(update.getDiscountValue().floatValue()));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar atualizar cupom inexistente")
        void shouldReturn422WhenUpdatingInvalidId() {
            UpdateCouponRequestDTO update = new UpdateCouponRequestDTO();
            update.setCode("INVALIDO");
            update.setDescription("teste");
            update.setDiscountValue(5.0);
            update.setExpirationDate(LocalDate.now().plusDays(10));

            given()
                    .pathParam("id", UUID.randomUUID())
                    .body(update)
                    .when()
                    .put("/{id}")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    @DisplayName("Publicação de cupom")
    class PublishCouponTests {

        @Test
        @DisplayName("Deve publicar cupom válido")
        void shouldPublishCouponSuccessfully() {
            CreateCouponResponseDTO created = createCouponUsecase.execute(validRequest());

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .post("/{id}/publish")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("published", equalTo(true));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar publicar cupom expirado")
        void shouldReturn422WhenPublishingExpiredCoupon() {
            CreateCouponRequestDTO expired = validRequest();
            expired.setExpirationDate(LocalDate.now().minusDays(2));
            CreateCouponResponseDTO created = createCouponUsecase.execute(expired);

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .post("/{id}/publish")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("status", equalTo(422))
                    .body("error", equalTo("Unprocessable Entity"));
        }
    }

    @Nested
    @DisplayName("Resgate de cupom")
    class RedeemCouponTests {

        @Test
        @DisplayName("Deve resgatar cupom publicado com sucesso")
        void shouldRedeemPublishedCouponSuccessfully() {
            CreateCouponRequestDTO request = validRequest();
            CreateCouponResponseDTO created = createCouponUsecase.execute(request);

            publishCouponUseCase.execute(created.getId());
            redeemCouponUseCase.execute(created.getId());

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(created.getId().toString()))
                    .body("code", equalTo(created.getCode()))
                    .body("redeemed", equalTo(true))
                    .body("published", equalTo(true));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar resgatar cupom não publicado")
        void shouldReturn422WhenRedeemingUnpublishedCoupon() {
            CreateCouponResponseDTO created = createCouponUsecase.execute(validRequest());

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .post("/{id}/redeem")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar resgatar cupom expirado")
        void shouldReturn422WhenRedeemingExpiredCoupon() {
            CreateCouponRequestDTO expired = validRequest();
            expired.setExpirationDate(LocalDate.now().minusDays(3));
            CreateCouponResponseDTO created = createCouponUsecase.execute(expired);

            given()
                    .pathParam("id", created.getId())
                    .when()
                    .post("/{id}/redeem")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("status", equalTo(422))
                    .body("error", equalTo("Unprocessable Entity"))
                    .body("path", containsString("/redeem"));
        }
    }
}