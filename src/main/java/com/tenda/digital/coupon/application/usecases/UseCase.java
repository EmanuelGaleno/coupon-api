package com.tenda.digital.coupon.application.usecases;

public interface UseCase<I, O> {
    O execute(I input);
}

