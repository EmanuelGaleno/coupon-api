package com.tenda.digital.coupon.common.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DomainException extends BaseException {

    private final List<String> details;

    public DomainException(String message) {
        super(message);
        this.details = new ArrayList<>();
    }

    public DomainException(String message, List<String> details) {
        super(message);
        this.details = new ArrayList<>(details);
    }
}