package com.boti.clickbankproductsservice.scheduled;


import com.boti.clickbankproductsservice.feign.client.ProductsServiceClient;
import com.boti.clickbankproductsservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduleds {

    private final ProductService service;
    private final ProductsServiceClient client;

    @Scheduled(fixedDelay = 100000000)
    public void getAllProductsClickBank(){

        client.beforeUpdate();
        service.getAllProducts();
        client.afterUpdate();
    }

}