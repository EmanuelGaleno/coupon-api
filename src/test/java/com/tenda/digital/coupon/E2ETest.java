package com.tenda.digital.coupon;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class E2ETest extends IntegrationTest {

    @LocalServerPort private int port;

    @BeforeEach
    public void tearUp() {
        RestAssured.config = RestAssuredFactory.getDefaultConfig();
        RestAssured.baseURI = "http://localhost:".concat(String.valueOf(port)).concat("/coupon");
    }
}
