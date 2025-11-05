package com.hicham.technicaltestinditex.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain entity representing a Price with its business rules.
 */
@Value
@Builder
public class Price {
    PriceId id;
    BrandId brandId;
    ProductId productId;
    PriceRange priceRange;
    Integer priceList;
    Integer priority;
    BigDecimal price;
    String currency;

    public Price(PriceId id, BrandId brandId, ProductId productId, PriceRange priceRange,
                 Integer priceList, Integer priority, BigDecimal price, String currency) {
        if (brandId == null) {
            throw new IllegalArgumentException("Brand ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (priceRange == null) {
            throw new IllegalArgumentException("Price range cannot be null");
        }
        if (priceList == null || priceList <= 0) {
            throw new IllegalArgumentException("Price list must be a positive number");
        }
        if (priority == null || priority < 0) {
            throw new IllegalArgumentException("Priority cannot be null or negative");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }

        this.id = id;
        this.brandId = brandId;
        this.productId = productId;
        this.priceRange = priceRange;
        this.priceList = priceList;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    /**
     * Checks if this price is applicable for the given date.
     *
     * @param applicationDate the date to check
     * @return true if this price applies, false otherwise
     */
    public boolean isApplicableFor(LocalDateTime applicationDate) {
        return priceRange.contains(applicationDate);
    }
}

