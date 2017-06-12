package org.dev.ecommerce.productpricing.repository;

import org.dev.ecommerce.productpricing.model.ProductPrice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends PagingAndSortingRepository<ProductPrice, String> {

}
