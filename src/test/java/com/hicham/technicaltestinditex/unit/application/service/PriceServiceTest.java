package com.hicham.technicaltestinditex.unit.application.service;

import com.hicham.technicaltestinditex.application.query.GetPriceQuery;
import com.hicham.technicaltestinditex.application.exception.PriceNotFoundException;
import com.hicham.technicaltestinditex.application.service.PriceService;
import com.hicham.technicaltestinditex.domain.entity.Price;
import com.hicham.technicaltestinditex.domain.valueObject.*;
import com.hicham.technicaltestinditex.application.port.out.PriceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PriceService Unit Tests")
class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    private GetPriceQuery request;

    @BeforeEach
    void setUp() {
        request = new GetPriceQuery(
                ProductId.of(35455L),
                BrandId.of(1L),
                LocalDateTime.of(2020, 6, 14, 10, 0)
        );
    }

    @Test
    @DisplayName("Should throw PriceNotFoundException when no prices found")
    void shouldThrowExceptionWhenNoPricesFound() {
        // Given
        when(priceRepositoryPort.findApplicablePrices(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // When & Then
        assertThatThrownBy(() -> priceService.getPrice(request))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No price found for product 35455, brand 1");
    }

    @Test
    @DisplayName("Should return price when single applicable price found")
    void shouldReturnPriceWhenSinglePriceFound() {
        // Given
        Price price = createPrice(1, 0, "35.50",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59)
        );
        when(priceRepositoryPort.findApplicablePrices(any(), any(), any()))
                .thenReturn(List.of(price));

        // When
        Price response = priceService.getPrice(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(35455L);
        assertThat(response.getBrandId()).isEqualTo(1L);
        assertThat(response.getPriceList()).isEqualTo(1);
        assertThat(response.getPrice()).isEqualByComparingTo("35.50");
    }

    @Test
    @DisplayName("Should return highest priority price when multiple prices found")
    void shouldReturnHighestPriorityPriceWhenMultiplePricesFound() {
        // Given
        Price lowPriorityPrice = createPrice(1, 0, "35.50",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59)
        );
        Price highPriorityPrice = createPrice(2, 1, "25.45",
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30)
        );

        when(priceRepositoryPort.findApplicablePrices(any(), any(), any()))
                .thenReturn(Arrays.asList(lowPriorityPrice, highPriorityPrice));

        GetPriceQuery afternoonRequest = new GetPriceQuery(
                ProductId.of(35455L),
                BrandId.of(1L),
                LocalDateTime.of(2020, 6, 14, 16, 0)
        );

        // When
        Price response = priceService.getPrice(afternoonRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getPriceList()).isEqualTo(2);
        assertThat(response.getPrice()).isEqualByComparingTo("25.45");
    }

    @Test
    @DisplayName("Should filter out prices outside date range")
    void shouldFilterOutPricesOutsideDateRange() {
        // Given
        Price futurePrice = createPrice(1, 0, "35.50",
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59)
        );

        when(priceRepositoryPort.findApplicablePrices(any(), any(), any()))
                .thenReturn(List.of(futurePrice));

        // When & Then
        assertThatThrownBy(() -> priceService.getPrice(request))
                .isInstanceOf(PriceNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw exception when brand ID is invalid")
    void shouldThrowExceptionWhenBrandIdIsInvalid() {
        // Given
        GetPriceQuery invalidRequest = new GetPriceQuery(
                ProductId.of(35455L),
                BrandId.of(-1L),
                LocalDateTime.of(2020, 6, 14, 10, 0)
        );

        // When & Then
        assertThatThrownBy(() -> priceService.getPrice(invalidRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Brand ID must be a positive number");
    }

    private Price createPrice(Integer priceList, Integer priority, String priceValue,
                              LocalDateTime startDate, LocalDateTime endDate) {
        return new Price(
                (PriceId.of(1L)),
                (BrandId.of(1L)),
                (ProductId.of(35455L)),
                (PriceRange.of(startDate, endDate)),
                (priceList),
                (priority),
                (new BigDecimal(priceValue)),
                ("EUR")
        );
    }
}

