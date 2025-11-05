package com.hicham.technicaltestinditex.domain.model;

import lombok.Value;

/**
 * Value Object representing a Brand identifier.
 */
@Value
public class BrandId {
    Long value;

    public BrandId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
        this.value = value;
    }

    public static BrandId of(Long value) {
        return new BrandId(value);
    }
}

