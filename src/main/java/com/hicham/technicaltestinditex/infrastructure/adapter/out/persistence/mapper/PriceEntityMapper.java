package com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.mapper;

import com.hicham.technicaltestinditex.domain.entity.Price;
import com.hicham.technicaltestinditex.domain.valueObject.*;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.mapstruct.*;

/**
 * MapStruct mapper for converting between PriceEntity and Price domain model.
 * Uses MapStruct's automatic mapping with custom methods for Value Objects.
 */
@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @ObjectFactory
    default Price newPrice(PriceEntity e) {
        return new Price(
                PriceId.of(e.getId()),
                BrandId.of(e.getBrandId()),
                ProductId.of(e.getProductId()),
                PriceRange.of(e.getStartDate(), e.getEndDate()),
                e.getPriceList(),
                e.getPriority(),
                e.getPrice(),
                e.getCurrency()
        );
    }

    Price toDomain(PriceEntity entity);

    /**
     * Converts a Price domain model to a PriceEntity.
     * MapStruct autogenerates most mappings, using helper methods for Value Objects.
     *
     * @param domain the price domain model
     * @return the price entity
     */
//    @Mapping(target = "startDate", source = "priceRange.startDate")
//    @Mapping(target = "endDate", source = "priceRange.endDate")
//    PriceEntity toEntity(Price domain);

    @Mapping(target = "startDate",  source = "priceRange.startDate")
    @Mapping(target = "endDate",    source = "priceRange.endDate")
    PriceEntity toEntity(Price domain);


    default PriceId   mapPriceId(Long id)     { return id != null ? PriceId.of(id) : null; }
    default BrandId   mapBrandId(Long id)     { return id != null ? BrandId.of(id) : null; }
    default ProductId mapProductId(Long id)   { return id != null ? ProductId.of(id) : null; }

    default Long map(PriceId id)   { return id != null ? id.value() : null; }
    default Long map(BrandId id)   { return id != null ? id.value() : null; }
    default Long map(ProductId id) { return id != null ? id.value() : null; }

}

