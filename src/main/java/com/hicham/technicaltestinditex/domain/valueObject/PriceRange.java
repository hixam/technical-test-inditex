package com.hicham.technicaltestinditex.domain.valueObject;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * Value Object representing a date range for price validity.
 */
public record PriceRange(LocalDateTime startDate, LocalDateTime endDate) {

    public PriceRange {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    public static PriceRange of(LocalDateTime startDate, LocalDateTime endDate) {
        return new PriceRange(startDate, endDate);
    }

    /**
     * Checks if the given date falls within this price range.
     *
     * @param date the date to check
     * @return true if the date is within the range, false otherwise
     */
    public boolean contains(LocalDateTime date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}

