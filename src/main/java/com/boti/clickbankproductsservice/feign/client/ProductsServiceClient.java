package com.boti.clickbankproductsservice.feign.client;

import com.boti.clickbankproductsservice.DTO.ClickBankParamsRequest;
import com.boti.clickbankproductsservice.DTO.ClickBankResponse;
import com.boti.clickbankproductsservice.DTO.ProductDTO;
import com.boti.clickbankproductsservice.feign.fallback.ClickBankClientFallbackFactory;
import com.boti.clickbankproductsservice.feign.fallback.ProductsServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "${productService.service.name}", path = "/${productService.path}",
        fallback = ProductsServiceClientFallbackFactory.class)
public interface ProductsServiceClient {
    @PostMapping(value = "/products")
    String saveProduct(@RequestBody()ProductDTO productDTO);

    @PutMapping(value = "/products/beforeUpdate/CLICKBANK")
    String beforeUpdate();

    @DeleteMapping(value = "/products/afterUpdate/CLICKBANK")
    String afterUpdate();

}
