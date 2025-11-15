package io.emanuel.ms.coupon.common.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ApplicationException extends BaseException {
    private final List<String> details;

    public ApplicationException(String message) {
        super(message);
        this.details = new ArrayList<>();
    }

    public ApplicationException(String message, List<String> details) {
        super(message);
        this.details = details;
    }
}