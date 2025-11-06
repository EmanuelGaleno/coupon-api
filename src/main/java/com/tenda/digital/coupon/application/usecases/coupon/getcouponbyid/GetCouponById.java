package com.tenda.digital.coupon.application.usecases.coupon.getcouponbyid;

import com.tenda.digital.coupon.common.exceptions.DomainException;
import com.tenda.digital.coupon.domain.entity.coupon.Coupon;
import com.tenda.digital.coupon.domain.repository.DomainCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCouponById implements GetCouponByIdUseCase {

    private final DomainCouponRepository domainCouponRepository;

    @Override
    public GetCouponByIdResponseDTO execute(UUID id) {
        log.debug("Buscando cupom com ID: {}", id);
        try {
            Coupon coupon = domainCouponRepository.findById(id)
                    .orElseThrow(() -> new DomainException("Cupom não encontrado para o ID: " + id));

            return OutputMapper.toOutput(coupon);
        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Erro ao buscar cupom por ID: {}", ex.getMessage(), ex);
            throw new DomainException("Erro ao buscar cupom por ID: " + ex.getMessage());
        }
    }
}