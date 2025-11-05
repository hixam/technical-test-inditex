package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for price query request from REST API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price query request parameters")
public class PriceQueryRequestDto {

    @NotNull(message = "Application date is required")
    @Schema(description = "Date and time to check price applicability", 
            example = "2020-06-14T10:00:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime applicationDate;

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    @Schema(description = "Product identifier", 
            example = "35455",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @NotNull(message = "Brand ID is required")
    @Positive(message = "Brand ID must be positive")
    @Schema(description = "Brand identifier (1 = ZARA)", 
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long brandId;
}

