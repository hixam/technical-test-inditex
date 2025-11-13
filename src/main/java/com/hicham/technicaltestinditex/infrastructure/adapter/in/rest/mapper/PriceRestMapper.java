package com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.mapper;

import com.hicham.technicaltestinditex.application.query.GetPriceQuery;
import com.hicham.technicaltestinditex.domain.entity.Price;
import com.hicham.technicaltestinditex.domain.valueObject.BrandId;
import com.hicham.technicaltestinditex.domain.valueObject.ProductId;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceQueryRequestDto;
import com.hicham.technicaltestinditex.infrastructure.adapter.in.rest.dto.PriceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for converting between REST DTOs and application DTOs.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PriceRestMapper {


    /**
     * Converts Domain to REST response DTO.
     *
     * @param response the domain entity
     * @return the REST DTO
     */
    @Mapping(target = "productId", source = "productId.value")
    @Mapping(target = "brandId",   source = "brandId.value")
    @Mapping(target = "startDate",  source = "priceRange.startDate")
    @Mapping(target = "endDate",    source = "priceRange.endDate")
    PriceResponseDto toRestResponse(Price response);
}

