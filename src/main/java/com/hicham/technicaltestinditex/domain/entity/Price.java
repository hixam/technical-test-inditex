package com.hicham.technicaltestinditex.domain.entity;

import com.hicham.technicaltestinditex.domain.valueObject.BrandId;
import com.hicham.technicaltestinditex.domain.valueObject.PriceId;
import com.hicham.technicaltestinditex.domain.valueObject.PriceRange;
import com.hicham.technicaltestinditex.domain.valueObject.ProductId;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain entity representing a Price with its business rules.
 */
public class Price {
    private final PriceId id;
    private final BrandId brandId;
    private final ProductId productId;
    private final PriceRange priceRange;
    private final Integer priceList;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;


    public Price(PriceId id, BrandId brandId, ProductId productId, PriceRange priceRange,
                 Integer priceList, Integer priority, BigDecimal price, String currency) {

        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.brandId = Objects.requireNonNull(brandId, "brandId cannot be null");
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.priceRange = Objects.requireNonNull(priceRange, "priceRange cannot be null");

        // Invariantes mínimas para tipos primitivos/“no VO”
        this.priceList = Objects.requireNonNull(priceList, "priceList cannot be null");
        if (priceList <= 0) throw new IllegalArgumentException("priceList must be > 0");

        this.priority = Objects.requireNonNull(priority, "priority cannot be null");
        if (priority < 0) throw new IllegalArgumentException("priority must be >= 0");

        this.price = Objects.requireNonNull(price, "price cannot be null");
        if (price.signum() <= 0) throw new IllegalArgumentException("price must be > 0");

        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
        if (currency.isBlank()) throw new IllegalArgumentException("currency cannot be blank");

    }

    public static Price of(
            PriceId id,
            BrandId brandId,
            ProductId productId,
            PriceRange priceRange,
            Integer priceList,
            Integer priority,
            BigDecimal price,
            String currency
    ) {
        return new Price(id, brandId, productId, priceRange, priceList, priority, price, currency);
    }

    public PriceId getId() { return id; }
    public ProductId getProductId() { return productId; }
    public BrandId getBrandId() { return brandId; }
    public PriceRange getPriceRange() { return priceRange; }
    public Integer getPriceList() { return priceList; }
    public Integer getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    /**
     * Checks if this price is applicable for the given date.
     *
     * @param applicationDate the date to check
     * @return true if this price applies, false otherwise
     */
    public boolean isApplicableFor(LocalDateTime applicationDate) {
        return priceRange.contains(applicationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

