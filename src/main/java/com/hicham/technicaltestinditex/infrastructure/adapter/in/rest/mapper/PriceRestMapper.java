package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.mapper;

import com.hicham.technicaltestinditex.application.dto.PriceQueryRequest;
import com.hicham.technicaltestinditex.application.dto.PriceResponse;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceQueryRequestDto;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between REST DTOs and application DTOs.
 */
@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    /**
     * Converts REST query request DTO to application query request.
     *
     * @param dto the REST DTO
     * @return the application DTO
     */
    PriceQueryRequest toApplicationRequest(PriceQueryRequestDto dto);

    /**
     * Converts application response to REST response DTO.
     *
     * @param response the application response
     * @return the REST DTO
     */
    PriceResponseDto toRestResponse(PriceResponse response);
}

