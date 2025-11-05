package com.hicham.technicaltestinditex.domain.port.out;

import com.hicham.technicaltestinditex.domain.model.BrandId;
import com.hicham.technicaltestinditex.domain.model.Price;
import com.hicham.technicaltestinditex.domain.model.ProductId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Output port for price repository operations.
 * This interface defines the contract for persistence operations from the domain perspective.
 */
public interface PriceRepositoryPort {

    /**
     * Finds all prices applicable for the given criteria.
     *
     * @param applicationDate the date for which to find prices
     * @param productId       the product identifier
     * @param brandId         the brand identifier
     * @return list of applicable prices
     */
    List<Price> findApplicablePrices(LocalDateTime applicationDate, ProductId productId, BrandId brandId);
}

