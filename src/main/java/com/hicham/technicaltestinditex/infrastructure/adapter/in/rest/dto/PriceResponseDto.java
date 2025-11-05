package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for price response from REST API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price response with applicable price information")
public class PriceResponseDto {

    @Schema(description = "Product identifier", example = "35455")
    private Long productId;

    @Schema(description = "Brand identifier", example = "1")
    private Long brandId;

    @Schema(description = "Price list identifier", example = "1")
    private Integer priceList;

    @Schema(description = "Price validity start date", example = "2020-06-14T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Price validity end date", example = "2020-12-31T23:59:59")
    private LocalDateTime endDate;

    @Schema(description = "Final price to apply", example = "35.50")
    private BigDecimal price;
}

