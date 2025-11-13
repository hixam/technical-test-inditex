package com.hicham.technicaltestinditex.application.query;

import com.hicham.technicaltestinditex.domain.valueObject.BrandId;
import com.hicham.technicaltestinditex.domain.valueObject.ProductId;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for price query request in the application layer.
 */

public record GetPriceQuery(ProductId productId, BrandId brandId, LocalDateTime at) {}

