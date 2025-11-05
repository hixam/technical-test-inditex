package com.hicham.technicaltestinditex.application.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for price response in the application layer.
 */
@Value
@Builder
public class PriceResponse {
    Long productId;
    Long brandId;
    Integer priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal price;
}

