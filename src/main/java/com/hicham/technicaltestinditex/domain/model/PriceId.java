package com.hicham.technicaltestinditex.domain.model;

import lombok.Value;

/**
 * Value Object representing a Price identifier.
 */
@Value
public class PriceId {
    Long value;

    public PriceId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Price ID must be a positive number");
        }
        this.value = value;
    }

    public static PriceId of(Long value) {
        return new PriceId(value);
    }
}

