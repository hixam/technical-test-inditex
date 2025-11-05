package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standardized error response DTO for REST API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response with details")
public class ErrorResponseDto {

    @Schema(description = "Timestamp of the error", example = "2020-06-14T10:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error message", example = "Price not found")
    private String message;

    @Schema(description = "Request path", example = "/api/prices")
    private String path;
}

