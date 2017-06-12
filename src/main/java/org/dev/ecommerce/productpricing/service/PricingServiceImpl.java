package org.dev.ecommerce.productpricing.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.dev.ecommerce.productpricing.ProductPricingApiAppTestClient;
import org.dev.ecommerce.productpricing.model.Product;
import org.dev.ecommerce.productpricing.model.ProductPrice;
import org.dev.ecommerce.productpricing.repository.ProductPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

@Service("pricingService")
public class PricingServiceImpl implements PricingService {
	
	public static final Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);

	private final ProductPriceRepository productPriceRepository;
	private static final String GROUP = "products";
    private static final int TIMEOUT = 60000;
    public static final String REST_SERVICE_URI = "http://localhost:8080/ProductCatalogue";

	@Autowired
	public PricingServiceImpl(ProductPriceRepository productPriceRepository) {
		this.productPriceRepository = productPriceRepository;
	}
	
//    public Map<String, Map<String, Object>> getProductSummary(String productId) {
//    	
//        List<Callable<AsyncResponse>> callables = new ArrayList<>();
//        callables.add(new BackendServiceCallable("details", getProductDetails(productId)));
//        return doBackendAsyncServiceCall(callables);
//    }
//
//    private static Map<String, Map<String, Object>> doBackendAsyncServiceCall(List<Callable<AsyncResponse>> callables) {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        try {
//            List<Future<AsyncResponse>> futures = executorService.invokeAll(callables);
//            executorService.shutdown();
//            executorService.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
//            Map<String, Map<String, Object>> result = new HashMap<>();
//            for (Future<AsyncResponse> future : futures) {
//                AsyncResponse response = future.get();
//                result.put(response.serviceKey, response.response);
//            }
//            return result;
//        } catch (InterruptedException|ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    
//    @Cacheable
//    private HystrixCommand<Map<String, Object>> getProductDetails(String productId) {
//        return new HystrixCommand<Map<String, Object>>(
//                HystrixCommand.Setter
//                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(GROUP))
//                        .andCommandKey(HystrixCommandKey.Factory.asKey("getProductDetails"))
//                        .andCommandPropertiesDefaults(
//                                HystrixCommandProperties.Setter()
//                                        .withExecutionIsolationThreadTimeoutInMilliseconds(TIMEOUT)
//                        )
//        ) {
//            @Override
//            protected Map<String, Object> run() throws Exception {
//            	try {
//        			logger.debug("\n\n");
//        			logger.debug("Get Product Details from Product Catalogue API ----------");
//        			RestTemplate restTemplate = new RestTemplate();
//        			Product product = restTemplate.getForObject(REST_SERVICE_URI + "/products/p1234567890", Product.class);
//        			logger.debug(product.toString());
//        		} catch (Exception ex) {
//
//        		}
//    			return new HashMap<>();
//            }
//            @Override
//            protected Map getFallback() {
//                return new HashMap<>();
//            }
//        };
//    }
//    
//    private static class AsyncResponse {
//        private final String serviceKey;
//        private final Map<String, Object> response;
//        AsyncResponse(String serviceKey, Map<String, Object> response) {
//            this.serviceKey = serviceKey;
//            this.response = response;
//        }
//    }
//    
//    private static class BackendServiceCallable implements Callable<AsyncResponse> {
//        private final String serviceKey;
//        private final HystrixCommand<Map<String, Object>> hystrixCommand;
//        public BackendServiceCallable(String serviceKey, HystrixCommand<Map<String, Object>> hystrixCommand) {
//            this.serviceKey = serviceKey;
//            this.hystrixCommand = hystrixCommand;
//        }
//        @Override
//        public AsyncResponse call() throws Exception {
//            return new AsyncResponse(serviceKey, hystrixCommand.execute());
//        }
//    }
    
	@Override
	public ProductPrice save(ProductPrice productPrice) {
		logger.debug("\n\n");
		logger.debug("Get Product Details from Product Catalogue API ----------");
		RestTemplate restTemplate = new RestTemplate();
		Product product = null;
		try {
			product = restTemplate.getForObject(REST_SERVICE_URI + "/products/"+productPrice.getProductId(), Product.class);
		} catch(Exception ex) {
			logger.error("Seems product doesn't exist in Product Catalogue!");
		}
		if(product == null) {
			logger.error("Can't create price for non existent Product. \nProduct Catalogue doesn't have product with the given id {}", productPrice.getProductId());
			return null;
		}
		return productPriceRepository.save(productPrice);
	}

	@Override
	public ProductPrice findOne(String id) {
		return productPriceRepository.findOne(id);
	}

	@Override
	public void delete(String id) {
		ProductPrice existing = findOne(id);
		if (existing == null) {
			return;
		}
		productPriceRepository.delete(existing);
	}

	@Override
	public ProductPrice update(String id, ProductPrice product) throws IOException {
		ProductPrice existing = findOne(id);
		if (existing == null) {
			return null;
		}
		existing = product;
		return productPriceRepository.save(existing);
	}

}
