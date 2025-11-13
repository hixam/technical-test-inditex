package com.hicham.technicaltestinditex.application.service;

import com.hicham.technicaltestinditex.application.query.GetPriceQuery;
import com.hicham.technicaltestinditex.application.exception.PriceNotFoundException;
import com.hicham.technicaltestinditex.application.port.in.GetPriceUseCase;
import com.hicham.technicaltestinditex.domain.entity.Price;
import com.hicham.technicaltestinditex.application.port.out.PriceRepositoryPort;
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
    public Price getPrice(GetPriceQuery request) {

        List<Price> applicablePrices = priceRepositoryPort.findApplicablePrices(
                request.at(),
                request.productId(),
                request.brandId()
        );

        Price selectedPrice = applicablePrices.stream()
        //Ambiguous filtering since it's already filtered in the infrastructure layer
        //but we want to apply best practices (domain rules in domain layer) by filtering in the Domain layer we keep both just for demo purposes.
                .filter(price -> price.isApplicableFor(request.at()))
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for product %d, brand %d at %s",
                                request.productId().value(),
                                request.brandId().value(),
                                request.at())
                ));

        return selectedPrice;
    }
}

