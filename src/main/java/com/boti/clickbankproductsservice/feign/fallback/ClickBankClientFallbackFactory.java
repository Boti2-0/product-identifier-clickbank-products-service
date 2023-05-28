package com.boti.clickbankproductsservice.feign.fallback;

import com.boti.clickbankproductsservice.DTO.ClickBankParamsRequest;
import com.boti.clickbankproductsservice.DTO.ClickBankResponse;
import com.boti.clickbankproductsservice.feign.client.ClickBankClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class ClickBankClientFallbackFactory implements FallbackFactory<ClickBankClient> {

    @Override
    public ClickBankClient create(Throwable cause) {
        return new ClickBankClient() {
            @Override
            public ClickBankResponse getProducts(ClickBankParamsRequest params) {
                log.error("fallback; getProducts reason was: " + cause.getMessage());
                return null;
            }
        };
    }
}
