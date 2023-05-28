package com.boti.clickbankproductsservice.feign.client;

import com.boti.clickbankproductsservice.DTO.ClickBankParamsRequest;
import com.boti.clickbankproductsservice.DTO.ClickBankResponse;
import com.boti.clickbankproductsservice.feign.fallback.ClickBankClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "${clickBank.service.name}", url = "${clickBank.host}", path = "/${clickBank.path}",
        fallback = ClickBankClientFallbackFactory.class)
public interface ClickBankClient {
    @PostMapping
    ClickBankResponse getProducts(@RequestBody() ClickBankParamsRequest params);

}
