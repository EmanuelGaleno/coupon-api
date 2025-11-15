package io.emanuel.ms.coupon.application.usecases;

public interface UseCase<I, O> {
    O execute(I input);
}

