package com.tenda.digital.coupon;

import io.restassured.config.FailureConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;

public class RestAssuredFactory {

    public static RestAssuredConfig getDefaultConfig() {

        ResponseValidationFailureListener failureListener =
                (reqSpec, resSpec, response) ->
                        System.out.printf(
                                "We have a failure, response status was %s and the body container %s",
                                response.getStatusCode(), response.getBody().asPrettyString());

        return RestAssuredConfig.config()
                .logConfig(
                        LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener));
    }
}
