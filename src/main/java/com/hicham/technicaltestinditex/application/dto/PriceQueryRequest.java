package com.hicham.technicaltestinditex.application.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for price query request in the application layer.
 */
@Value
@Builder
public class PriceQueryRequest {
    LocalDateTime applicationDate;
    Long productId;
    Long brandId;
}

