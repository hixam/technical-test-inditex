package com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.repository;

import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for PriceEntity.
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds all prices for a given brand and product where the application date
     * falls within the price validity range.
     *
     * @param brandId         the brand identifier
     * @param productId       the product identifier
     * @param applicationDate the date to check (used twice for range check)
     * @return list of applicable price entities
     */
    List<PriceEntity> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate,
            LocalDateTime applicationDate2
    );
}

