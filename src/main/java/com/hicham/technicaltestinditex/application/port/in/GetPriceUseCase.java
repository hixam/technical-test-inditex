package com.hicham.technicaltestinditex.application.port.in;

import com.hicham.technicaltestinditex.application.dto.PriceQueryRequest;
import com.hicham.technicaltestinditex.application.dto.PriceResponse;

/**
 * Input port for the get price use case.
 * This interface defines the contract for querying prices from the application perspective.
 */
public interface GetPriceUseCase {

    /**
     * Gets the applicable price for the given query parameters.
     *
     * @param request the price query request containing application date, product ID, and brand ID
     * @return the applicable price response
     * @throws com.hicham.technicaltestinditex.application.exception.PriceNotFoundException if no applicable price is found
     */
    PriceResponse getPrice(PriceQueryRequest request);
}

