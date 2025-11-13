package com.hicham.technicaltestinditex.domain.valueObject;

import lombok.Value;

/**
 * Value Object representing a Brand identifier.
 */
public record BrandId(Long value) {

    public BrandId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
    }

    public static BrandId of(Long value) {
        return new BrandId(value);
    }
}

