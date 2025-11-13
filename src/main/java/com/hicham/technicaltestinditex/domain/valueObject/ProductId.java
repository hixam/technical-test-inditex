package com.hicham.technicaltestinditex.domain.valueObject;

import lombok.Value;

/**
 * Value Object representing a Product identifier.
 */
public record ProductId(Long value) {

    public ProductId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}

