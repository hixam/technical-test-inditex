package com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence;

import com.hicham.technicaltestinditex.domain.model.BrandId;
import com.hicham.technicaltestinditex.domain.model.Price;
import com.hicham.technicaltestinditex.domain.model.ProductId;
import com.hicham.technicaltestinditex.domain.port.out.PriceRepositoryPort;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.entity.PriceEntity;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.mapper.PriceEntityMapper;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter implementation of PriceRepositoryPort for persistence operations.
 * This adapter bridges the domain layer with the infrastructure persistence layer.
 */
@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceEntityMapper priceEntityMapper;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, ProductId productId, BrandId brandId) {
        List<PriceEntity> entities = priceJpaRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        brandId.getValue(),
                        productId.getValue(),
                        applicationDate,
                        applicationDate
                );

        return entities.stream()
                .map(priceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}

