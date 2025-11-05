package com.hicham.technicaltestinditex.unit.domain.model;

import com.hicham.technicaltestinditex.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Price Domain Model Unit Tests")
class PriceTest {

    @Test
    @DisplayName("Should create valid Price with all required fields")
    void shouldCreateValidPrice() {
        // Given
        PriceId id = PriceId.of(1L);
        BrandId brandId = BrandId.of(1L);
        ProductId productId = ProductId.of(35455L);
        PriceRange priceRange = PriceRange.of(
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59)
        );

        // When
        Price price = Price.builder()
                .id(id)
                .brandId(brandId)
                .productId(productId)
                .priceRange(priceRange)
                .priceList(1)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        // Then
        assertThat(price).isNotNull();
        assertThat(price.getId()).isEqualTo(id);
        assertThat(price.getBrandId()).isEqualTo(brandId);
        assertThat(price.getProductId()).isEqualTo(productId);
        assertThat(price.getPriceList()).isEqualTo(1);
        assertThat(price.getPriority()).isEqualTo(0);
        assertThat(price.getPrice()).isEqualByComparingTo("35.50");
        assertThat(price.getCurrency()).isEqualTo("EUR");
    }

    @Test
    @DisplayName("Should check if price is applicable for given date within range")
    void shouldReturnTrueWhenDateIsWithinRange() {
        // Given
        Price price = createTestPrice(
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 6, 14, 23, 59)
        );
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When
        boolean isApplicable = price.isApplicableFor(applicationDate);

        // Then
        assertThat(isApplicable).isTrue();
    }

    @Test
    @DisplayName("Should return false when date is before range")
    void shouldReturnFalseWhenDateIsBeforeRange() {
        // Given
        Price price = createTestPrice(
                LocalDateTime.of(2020, 6, 14, 10, 0),
                LocalDateTime.of(2020, 6, 14, 20, 0)
        );
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 9, 59);

        // When
        boolean isApplicable = price.isApplicableFor(applicationDate);

        // Then
        assertThat(isApplicable).isFalse();
    }

    @Test
    @DisplayName("Should return false when date is after range")
    void shouldReturnFalseWhenDateIsAfterRange() {
        // Given
        Price price = createTestPrice(
                LocalDateTime.of(2020, 6, 14, 10, 0),
                LocalDateTime.of(2020, 6, 14, 20, 0)
        );
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 20, 1);

        // When
        boolean isApplicable = price.isApplicableFor(applicationDate);

        // Then
        assertThat(isApplicable).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when brand ID is null")
    void shouldThrowExceptionWhenBrandIdIsNull() {
        assertThatThrownBy(() ->
                Price.builder()
                        .brandId(null)
                        .productId(ProductId.of(35455L))
                        .priceRange(PriceRange.of(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59)
                        ))
                        .priceList(1)
                        .priority(0)
                        .price(new BigDecimal("35.50"))
                        .currency("EUR")
                        .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Brand ID cannot be null");
    }

    @Test
    @DisplayName("Should throw exception when price is zero or negative")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        assertThatThrownBy(() ->
                Price.builder()
                        .brandId(BrandId.of(1L))
                        .productId(ProductId.of(35455L))
                        .priceRange(PriceRange.of(
                                LocalDateTime.of(2020, 6, 14, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59)
                        ))
                        .priceList(1)
                        .priority(0)
                        .price(BigDecimal.ZERO)
                        .currency("EUR")
                        .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must be greater than zero");
    }

    @Test
    @DisplayName("BrandId should throw exception when value is negative")
    void shouldThrowExceptionWhenBrandIdIsNegative() {
        assertThatThrownBy(() -> BrandId.of(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Brand ID must be a positive number");
    }

    @Test
    @DisplayName("ProductId should throw exception when value is null")
    void shouldThrowExceptionWhenProductIdIsNull() {
        assertThatThrownBy(() -> ProductId.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product ID must be a positive number");
    }

    @Test
    @DisplayName("PriceRange should throw exception when start date is after end date")
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        assertThatThrownBy(() ->
                PriceRange.of(
                        LocalDateTime.of(2020, 6, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 0, 0)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start date cannot be after end date");
    }

    private Price createTestPrice(LocalDateTime startDate, LocalDateTime endDate) {
        return Price.builder()
                .id(PriceId.of(1L))
                .brandId(BrandId.of(1L))
                .productId(ProductId.of(35455L))
                .priceRange(PriceRange.of(startDate, endDate))
                .priceList(1)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();
    }
}

