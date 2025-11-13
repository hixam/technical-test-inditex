package com.hicham.technicaltestinditex.domain.valueObject;

import lombok.Value;

/**
 * Value Object representing a Price identifier.
 */
public record PriceId(Long value) {

    public PriceId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Price ID must be a positive number");
        }
    }

    public static PriceId of(Long value) {
        return new PriceId(value);
    }
}

