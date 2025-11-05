package com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.mapper;

import com.hicham.technicaltestinditex.domain.model.*;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.mapstruct.*;

/**
 * MapStruct mapper for converting between PriceEntity and Price domain model.
 * Uses MapStruct's automatic mapping with custom methods for Value Objects.
 */
@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    /**
     * Converts a PriceEntity to a Price domain model.
     * MapStruct autogenerates most mappings, using helper methods for Value Objects.
     *
     * @param entity the price entity
     * @return the price domain model
     */
    @Mapping(target = "priceRange", ignore = true)
    Price toDomain(PriceEntity entity);

    /**
     * Converts a Price domain model to a PriceEntity.
     * MapStruct autogenerates most mappings, using helper methods for Value Objects.
     *
     * @param domain the price domain model
     * @return the price entity
     */
    @Mapping(target = "startDate", source = "priceRange.startDate")
    @Mapping(target = "endDate", source = "priceRange.endDate")
    PriceEntity toEntity(Price domain);

    // ========== Helper methods for Value Object conversions ==========

    /**
     * After mapping, set the PriceRange value object from start and end dates.
     */
    @AfterMapping
    default void setPriceRange(@MappingTarget Price.PriceBuilder builder, PriceEntity entity) {
        if (entity.getStartDate() != null && entity.getEndDate() != null) {
            builder.priceRange(PriceRange.of(entity.getStartDate(), entity.getEndDate()));
        }
    }

    // PriceId conversions
    default PriceId mapPriceId(Long id) {
        return id != null ? PriceId.of(id) : null;
    }

    default Long mapPriceId(PriceId priceId) {
        return priceId != null ? priceId.getValue() : null;
    }

    // BrandId conversions
    default BrandId mapBrandId(Long id) {
        return BrandId.of(id);
    }

    default Long mapBrandId(BrandId brandId) {
        return brandId.getValue();
    }

    // ProductId conversions
    default ProductId mapProductId(Long id) {
        return ProductId.of(id);
    }

    default Long mapProductId(ProductId productId) {
        return productId.getValue();
    }
}

