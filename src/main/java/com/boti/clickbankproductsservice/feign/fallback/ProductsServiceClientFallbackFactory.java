package com.boti.clickbankproductsservice.feign.fallback;

import com.boti.clickbankproductsservice.DTO.ProductDTO;
import com.boti.clickbankproductsservice.feign.client.ProductsServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;


@Slf4j
public class ProductsServiceClientFallbackFactory implements FallbackFactory<ProductsServiceClient> {

    @Override
    public ProductsServiceClient create(Throwable cause) {
        return new ProductsServiceClient() {
            @Override
            public String saveProduct(ProductDTO productDTO) {
                log.error("fallback; getProducts reason was: " + cause.getMessage());
                return null;
            }

            @Override
            public String beforeUpdate() {
                log.error("fallback; beforeUpdate reason was: " + cause.getMessage());
                return null;
            }

            @Override
            public String afterUpdate() {
                log.error("fallback; afterUpdate reason was: " + cause.getMessage());
                return null;
            }
        };
    }
}
