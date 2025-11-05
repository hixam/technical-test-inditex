package com.hicham.technicaltestinditex.application.service;

import com.hicham.technicaltestinditex.application.dto.PriceQueryRequest;
import com.hicham.technicaltestinditex.application.dto.PriceResponse;
import com.hicham.technicaltestinditex.application.exception.PriceNotFoundException;
import com.hicham.technicaltestinditex.application.port.in.GetPriceUseCase;
import com.hicham.technicaltestinditex.domain.model.BrandId;
import com.hicham.technicaltestinditex.domain.model.Price;
import com.hicham.technicaltestinditex.domain.model.ProductId;
import com.hicham.technicaltestinditex.domain.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Application service implementing the get price use case.
 * Contains the business logic for finding and selecting the applicable price.
 */
@Service
@RequiredArgsConstructor
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public PriceResponse getPrice(PriceQueryRequest request) {
        BrandId brandId = BrandId.of(request.getBrandId());
        ProductId productId = ProductId.of(request.getProductId());

        List<Price> applicablePrices = priceRepositoryPort.findApplicablePrices(
                request.getApplicationDate(),
                productId,
                brandId
        );

        Price selectedPrice = applicablePrices.stream()
        //Ambiguous filtering since it's already filtered in the infrastructure layer 
        //but we want to apply best practices (domain rules in domain layer) by filtering in the Domain layer we keep both just for demo purposes.
                .filter(price -> price.isApplicableFor(request.getApplicationDate()))
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for product %d, brand %d at %s",
                                request.getProductId(),
                                request.getBrandId(),
                                request.getApplicationDate())
                ));

        return buildPriceResponse(selectedPrice);
    }

    private PriceResponse buildPriceResponse(Price price) {
        return PriceResponse.builder()
                .productId(price.getProductId().getValue())
                .brandId(price.getBrandId().getValue())
                .priceList(price.getPriceList())
                .startDate(price.getPriceRange().getStartDate())
                .endDate(price.getPriceRange().getEndDate())
                .price(price.getPrice())
                .build();
    }
}

