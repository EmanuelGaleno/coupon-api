package com.tenda.digital.coupon.application.controllers;

import com.tenda.digital.coupon.E2ETest;
import com.tenda.digital.coupon.application.usecases.coupon.createcoupon.CreateCouponRequestDTO;
import com.tenda.digital.coupon.application.usecases.coupon.updatecoupon.UpdateCouponRequestDTO;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponCode;
import com.tenda.digital.coupon.domain.entity.valueobjects.CouponDescription;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static builders.integration.CreateCouponBuilder.validCouponCreateRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CouponControllerTest extends E2ETest {

    private static final String BASE_PATH = "/coupon";

    private final DomainCouponRepository domainCouponRepository;

    @Autowired
    CouponControllerTest(DomainCouponRepository domainCouponRepository) {
        this.domainCouponRepository = domainCouponRepository;
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
    @DisplayName("Criação de cupom (POST /coupon)")
    class CreateCouponTests {

        @Test
        @DisplayName("Deve criar um cupom com sucesso quando payload for válido")
        void shouldCreateCouponSuccessfully() {
            CreateCouponRequestDTO request = validCouponCreateRequest();

            given()
                    .body(request)
                    .when()
                    .post()
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", notNullValue())
                    .body("code", notNullValue())
                    .body("description", equalTo(request.getDescription()))
                    .body("discountValue", equalTo(request.getDiscountValue().floatValue()))
                    .body("expirationDate", notNullValue());
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar criar cupom com dados inválidos")
        void shouldReturn422WhenInvalidData() {
            CreateCouponRequestDTO invalidRequest = new CreateCouponRequestDTO();
            invalidRequest.setCode("");
            invalidRequest.setDescription("");
            invalidRequest.setDiscountValue(0.0);
            invalidRequest.setExpirationDate(LocalDate.now().minusDays(1));

            given()
                    .body(invalidRequest)
                    .when()
                    .post()
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("error", equalTo("Unprocessable Entity"))
                    .body("status", equalTo(422));
        }
    }

    @Nested
    @DisplayName("Busca de cupom por ID (GET /coupon/{id})")
    class GetCouponByIdTests {

        @Test
        @DisplayName("Deve retornar 200 e os dados do cupom quando existir")
        void shouldReturn200WhenCouponExists() {
            Coupon saved = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("TESTE123"))
                            .description(new CouponDescription("cupom válido"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().plusDays(5))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("id", saved.getId())
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(saved.getId().toString()))
                    .body("code", equalTo(saved.getCode().value()))
                    .body("description", equalTo(saved.getDescription().value()))
                    .body("discountValue", equalTo(saved.getDiscountValue().floatValue()));
        }

        @Test
        @DisplayName("Deve retornar 422 quando o cupom não for encontrado")
        void shouldReturn422WhenCouponNotFound() {
            UUID nonExistentId = UUID.randomUUID();

            given()
                    .pathParam("id", nonExistentId)
                    .when()
                    .get("/{id}")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body("status", equalTo(422))
                    .body("error", equalTo("Unprocessable Entity"));
        }
    }

    @Nested
    @DisplayName("Busca de cupom por código (GET /coupon/code/{code})")
    class GetCouponByCodeTests {

        @Test
        @DisplayName("Deve retornar 200 e o cupom correspondente ao código")
        void shouldReturn200WhenCouponFoundByCode() {
            Coupon saved = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("PROMO123"))
                            .description(new CouponDescription("Cupom de 10%"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().plusDays(7))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("code", saved.getCode().value())
                    .when()
                    .get("/code/{code}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("code", equalTo(saved.getCode().value()))
                    .body("description", equalTo(saved.getDescription().value()));
        }

        @Test
        @DisplayName("Deve retornar 422 quando o cupom com o código informado não existir")
        void shouldReturn422WhenCouponNotFoundByCode() {
            given()
                    .pathParam("code", "INEXISTENTE123")
                    .when()
                    .get("/code/{code}")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    @DisplayName("Atualização de cupom (PUT /coupon/{id})")
    class UpdateCouponTests {

        @Test
        @DisplayName("Deve atualizar o cupom com sucesso")
        void shouldUpdateCouponSuccessfully() {
            Coupon saved = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("TENDA10"))
                            .description(new CouponDescription("cupom original"))
                            .discountValue(5.0)
                            .expirationDate(LocalDate.now().plusDays(5))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            UpdateCouponRequestDTO request = new UpdateCouponRequestDTO();
            request.setCode("ATUALIZADO10");
            request.setDescription("Cupom atualizado");
            request.setDiscountValue(12.0);
            request.setExpirationDate(LocalDate.now().plusDays(15));

            given()
                    .pathParam("id", saved.getId())
                    .body(request)
                    .when()
                    .put("/{id}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("code", equalTo(request.getCode()))
                    .body("description", equalTo(request.getDescription().toLowerCase()))
                    .body("discountValue", equalTo(request.getDiscountValue().floatValue()));
        }
    }

    @Nested
    @DisplayName("Publicação de cupom (POST /coupon/{id}/publish)")
    class PublishCouponTests {

        @Test
        @DisplayName("Deve publicar um cupom válido com sucesso")
        void shouldPublishCouponSuccessfully() {
            Coupon saved = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("PUBLICAR10"))
                            .description(new CouponDescription("cupom publicável"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().plusDays(3))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("id", saved.getId())
                    .when()
                    .post("/{id}/publish")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("published", equalTo(true));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar publicar um cupom expirado")
        void shouldReturn422WhenPublishingExpiredCoupon() {
            Coupon expired = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("EXPIRADO10"))
                            .description(new CouponDescription("cupom expirado"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().minusDays(1))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("id", expired.getId())
                    .when()
                    .post("/{id}/publish")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    @DisplayName("Resgate de cupom (POST /coupon/{id}/redeem)")
    class RedeemCouponTests {

        @Test
        @DisplayName("Deve resgatar um cupom publicado com sucesso")
        void shouldRedeemPublishedCouponSuccessfully() {
            Coupon saved = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("REDEEM10"))
                            .description(new CouponDescription("cupom resgatável"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().plusDays(2))
                            .published(true)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("id", saved.getId())
                    .when()
                    .post("/{id}/redeem")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("redeemed", equalTo(true));
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar resgatar um cupom não publicado")
        void shouldReturn422WhenRedeemingUnpublishedCoupon() {
            Coupon unpublished = domainCouponRepository.save(
                    Coupon.builder()
                            .id(UUID.randomUUID())
                            .code(new CouponCode("UNPUBLISHED"))
                            .description(new CouponDescription("cupom não publicado"))
                            .discountValue(10.0)
                            .expirationDate(LocalDate.now().plusDays(2))
                            .published(false)
                            .redeemed(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            given()
                    .pathParam("id", unpublished.getId())
                    .when()
                    .post("/{id}/redeem")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }
}
