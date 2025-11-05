package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest;

import com.hicham.technicaltestinditex.application.dto.PriceQueryRequest;
import com.hicham.technicaltestinditex.application.dto.PriceResponse;
import com.hicham.technicaltestinditex.application.port.in.GetPriceUseCase;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.ErrorResponseDto;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceQueryRequestDto;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.mapper.PriceRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for price queries.
 * Exposes endpoints for querying applicable prices based on date, product, and brand.
 */
@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@Validated
@Tag(name = "Prices", description = "Price query API")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceRestMapper priceRestMapper;

    /**
     * Gets the applicable price for the given query parameters.
     *
     * @param applicationDate the date to check price applicability
     * @param productId       the product identifier
     * @param brandId         the brand identifier
     * @return the applicable price response
     */
    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Retrieves the applicable price for a product and brand at a specific date and time"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No applicable price found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<PriceResponseDto> getPrice(
            @Parameter(
                    description = "Application date and time (ISO format)",
                    example = "2020-06-14T10:00:00",
                    required = true
            )
            @RequestParam
            @NotNull(message = "Application date is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,

            @Parameter(
                    description = "Product identifier",
                    example = "35455",
                    required = true
            )
            @RequestParam
            @NotNull(message = "Product ID is required")
            @Positive(message = "Product ID must be positive")
            Long productId,

            @Parameter(
                    description = "Brand identifier (1 = ZARA)",
                    example = "1",
                    required = true
            )
            @RequestParam
            @NotNull(message = "Brand ID is required")
            @Positive(message = "Brand ID must be positive")
            Long brandId
    ) {
        PriceQueryRequest request = PriceQueryRequest.builder()
                .applicationDate(applicationDate)
                .productId(productId)
                .brandId(brandId)
                .build();

        PriceResponse response = getPriceUseCase.getPrice(request);
        PriceResponseDto dto = priceRestMapper.toRestResponse(response);

        return ResponseEntity.ok(dto);
    }
}

