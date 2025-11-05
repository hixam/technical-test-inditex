package com.hicham.technicaltestinditex.integration.infrastructure.adapter.out.persistence;

import com.hicham.technicaltestinditex.domain.model.BrandId;
import com.hicham.technicaltestinditex.domain.model.Price;
import com.hicham.technicaltestinditex.domain.model.ProductId;
import com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence.PricePersistenceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {
        "com.hicham.technicaltestinditex.infrastructure.adapter.out.persistence"
})
@Sql(scripts = {
        "/db/migration/V1__Create_brands_and_prices_tables.sql",
        "/db/migration/V2__Insert_initial_prices_data.sql"
})
@DisplayName("PricePersistenceAdapter Integration Tests")
class PricePersistenceAdapterIT {

    @Autowired
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    @DisplayName("Should find applicable prices for given criteria")
    void shouldFindApplicablePrices() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1L);

        // When
        List<Price> prices = pricePersistenceAdapter.findApplicablePrices(
                applicationDate,
                productId,
                brandId
        );

        // Then
        assertThat(prices).isNotEmpty();
        assertThat(prices).hasSize(1);
        assertThat(prices.get(0).getProductId().getValue()).isEqualTo(35455L);
        assertThat(prices.get(0).getBrandId().getValue()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should find multiple overlapping prices when multiple apply")
    void shouldFindMultipleOverlappingPrices() {
        // Given - Fecha en rango de múltiples precios
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1L);

        // When
        List<Price> prices = pricePersistenceAdapter.findApplicablePrices(
                applicationDate,
                productId,
                brandId
        );

        // Then
        assertThat(prices).hasSize(2);
        assertThat(prices)
                .extracting(price -> price.getPriceList())
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    @DisplayName("Should return empty list when no prices match criteria")
    void shouldReturnEmptyListWhenNoPricesMatch() {
        // Given - Fecha fuera de rango
        LocalDateTime applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1L);

        // When
        List<Price> prices = pricePersistenceAdapter.findApplicablePrices(
                applicationDate,
                productId,
                brandId
        );

        // Then
        assertThat(prices).isEmpty();
    }

    @Test
    @DisplayName("Should return empty list for non-existent product")
    void shouldReturnEmptyListForNonExistentProduct() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        ProductId productId = ProductId.of(99999L);
        BrandId brandId = BrandId.of(1L);

        // When
        List<Price> prices = pricePersistenceAdapter.findApplicablePrices(
                applicationDate,
                productId,
                brandId
        );

        // Then
        assertThat(prices).isEmpty();
    }

    @Test
    @DisplayName("Should map entity fields correctly to domain model")
    void shouldMapEntityFieldsCorrectlyToDomainModel() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        ProductId productId = ProductId.of(35455L);
        BrandId brandId = BrandId.of(1L);

        // When
        List<Price> prices = pricePersistenceAdapter.findApplicablePrices(
                applicationDate,
                productId,
                brandId
        );

        // Then
        assertThat(prices).isNotEmpty();
        Price price = prices.get(0);
        
        assertThat(price.getId()).isNotNull();
        assertThat(price.getBrandId()).isNotNull();
        assertThat(price.getProductId()).isNotNull();
        assertThat(price.getPriceRange()).isNotNull();
        assertThat(price.getPriceList()).isNotNull();
        assertThat(price.getPriority()).isNotNull();
        assertThat(price.getPrice()).isNotNull();
        assertThat(price.getCurrency()).isNotNull();
    }
}

