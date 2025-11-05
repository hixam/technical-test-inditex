package com.hicham.technicaltestinditex.domain.model;

import lombok.Value;

/**
 * Value Object representing a Product identifier.
 */
@Value
public class ProductId {
    Long value;

    public ProductId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        this.value = value;
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}

