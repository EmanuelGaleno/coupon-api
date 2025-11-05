package com.tenda.digital.coupon.domain.entity.validators;

import br.com.fluentvalidator.AbstractValidator;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

public class CouponValidator extends AbstractValidator<Coupon> {

    @Override
    public void rules() {

        ruleFor(coupon -> coupon.getCode() != null ? coupon.getCode().getValue() : null)
                .must(not(stringEmptyOrNull()))
                .withMessage("O código do cupom é obrigatório.");

        ruleFor(Coupon::getDescription)
                .must(not(stringEmptyOrNull()))
                .withMessage("A descrição é obrigatória.");

        ruleFor(Coupon::getDiscountValue)
                .must(v -> v != null && v >= 0.5)
                .withMessage("O valor de desconto deve ser pelo menos 0.5.");

        ruleFor(Coupon::getPublished)
                .must(p -> p == null || p.equals(true) || p.equals(false))
                .withMessage("Campo 'published' deve ser booleano.");

        ruleFor(Coupon::getRedeemed)
                .must(r -> r == null || r.equals(true) || r.equals(false))
                .withMessage("Campo 'redeemed' deve ser booleano.");
    }
}