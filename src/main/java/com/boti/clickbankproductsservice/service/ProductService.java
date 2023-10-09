package com.boti.clickbankproductsservice.service;


import com.boti.clickbankproductsservice.DTO.ClickBankParamsRequest;
import com.boti.clickbankproductsservice.DTO.ClickBankResponse;
import com.boti.clickbankproductsservice.DTO.ProductDTO;
import com.boti.clickbankproductsservice.feign.client.ClickBankClient;
import com.boti.clickbankproductsservice.feign.client.ProductsServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ClickBankClient clickBankClient;
    private final ProductsServiceClient productsServiceClient;


    public void getAllProducts() {
        ClickBankResponse res = clickBankClient.getProducts(getTotalHitsParams());
        log.info(res.getData().getMarketplaceSearch().getTotalHits() + " produtos na clickBank");
        Integer numberOfCalls = (res.getData().getMarketplaceSearch().getTotalHits() / 50) + 1;
        Integer offSet = 0;
        for (Integer call = 1; call <= numberOfCalls; call++) {
            log.info("Baixando produtos do " + offSet + " ao " + call * 50);
            ClickBankResponse callRes = clickBankClient.getProducts(getHitsParams(offSet));
            offSet += 50;
            callRes.getData().getMarketplaceSearch().getHits().forEach(t -> {
                ProductDTO dto = ProductDTO.builder()
                        .site(t.getSite())
                        .title(t.getTitle())
                        .description(t.getDescription())
                        .url(t.getUrl())
                        .activateDate(t.getMarketplaceStats().getActivateDate())
                        .category(t.getMarketplaceStats().getCategory())
                        .subCategory(t.getMarketplaceStats().getSubCategory())
                        .initialDollarsPerSale(t.getMarketplaceStats().getInitialDollarsPerSale())
                        .averageDollarsPerSale(t.getMarketplaceStats().getAverageDollarsPerSale())
                        .ranking(setRankingByGravity(t.getMarketplaceStats().getGravity()))
                        .totalRebill(t.getMarketplaceStats().getTotalRebill())
                        .standard(t.getMarketplaceStats().isStandard())
                        .physical(t.getMarketplaceStats().isPhysical())
                        .rebill(t.getMarketplaceStats().isRebill())
                        .upsell(t.getMarketplaceStats().isUpsell())
                        .affiliateUrl(t.getAffiliateToolsUrl())
                        .googleAdsAvailable(false)
                        .marketplace("CLICKBANK")
                        .build();
                if (Objects.nonNull(dto.getAffiliateUrl()) && !dto.getAffiliateUrl().isBlank()) {
                    log.info("Validando se contem Google Ads do produto: " + dto.getSite());
                    updateIfCanGoogleAds(dto);
                }
                try {
                    dto.setKey(dto.getMarketplace()+dto.getSite()+dto.getCategory()+dto.getSubCategory()+dto.getUrl());
                    productsServiceClient.saveProduct(dto);
                } catch (Exception ex){
                    log.error(ex.getMessage());
                }
            });
        }
    }

    private String setRankingByGravity(double gravity){
            if (gravity < 25)
                return "0 - 25";
            else if (gravity < 50)
                return "25 - 50";
            else if (gravity < 75)
                return "50 - 75";
            else if (gravity < 100)
                return "75 - 100";
            else if (gravity < 150)
                return "100 - 150";
            else
                return "150+";
    }
    private void updateIfCanGoogleAds(ProductDTO dto) {
        try {
            dto.setGoogleAdsAvailable(!readHtmlToFind(dto.getAffiliateUrl(), "GOOGLE AD"));
        } catch (IOException e) {
        }
    }

    public boolean readHtmlToFind(String url, String content) throws IOException {
        String html = Jsoup.connect(url).get().html();
        return html.toUpperCase().contains(content);
    }

    private ClickBankParamsRequest getTotalHitsParams() {
        ClickBankParamsRequest params = new ClickBankParamsRequest();
        params.setQuery("query ($parameters: MarketplaceSearchParameters!) {marketplaceSearch(parameters: $parameters) {totalHits}}");
        params.setVariables(params.createEmptyParams());
        return params;
    }

    private ClickBankParamsRequest getHitsParams(Integer offSet) {
        ClickBankParamsRequest params = new ClickBankParamsRequest();
        ClickBankParamsRequest.VariablesClickBank variablesClickBank = params.createEmptyParams();
        variablesClickBank.setParameters(variablesClickBank.createParams(50, offSet, "gravity", false));
        params.setQuery("query ($parameters: MarketplaceSearchParameters!) {\n\t\t\tmarketplaceSearch(parameters: $parameters) {\n\t\t\t\ttotalHits\n\t\t\t\toffset\n\t\t\t\thits {\n\t\t\t\t\tsite\n\t\t\t\t\ttitle\n\t\t\t\t\tdescription\n\t\t\t\t\tfavorite\n\t\t\t\t\turl\n\t\t\t\t\tmarketplaceStats {\n\t\t\t\t\t\tactivateDate\n\t\t\t\t\t\tcategory\n\t\t\t\t\t\tsubCategory\n\t\t\t\t\t\tinitialDollarsPerSale\n\t\t\t\t\t\taverageDollarsPerSale\n\t\t\t\t\t\tgravity\n\t\t\t\t\t\ttotalRebill\n\t\t\t\t\t\tde\n\t\t\t\t\t\ten\n\t\t\t\t\t\tes\n\t\t\t\t\t\tfr\n\t\t\t\t\t\tit\n\t\t\t\t\t\tpt\n\t\t\t\t\t\tstandard\n\t\t\t\t\t\tphysical\n\t\t\t\t\t\trebill\n\t\t\t\t\t\tupsell\n\t\t\t\t\t\tstandardUrlPresent\n\t\t\t\t\t\tmobileEnabled\n\t\t\t\t\t\twhitelistVendor\n\t\t\t\t\t\tcpaVisible\n\t\t\t\t\t\tdollarTrial\n\t\t\t\t\t\thasAdditionalSiteHoplinks\n\t\t\t\t\t}\n\t\t\t \t\taffiliateToolsUrl\n\t\t\t  \t\taffiliateSupportEmail\n            \t\tskypeName\n\t\t\t\t}\n        facets {\n\t\t\t\t\tfield\n\t\t\t\t\tbuckets {\n\t\t\t\t\t\tvalue\n\t\t\t\t\t\tcount\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n    }");
        params.setVariables(variablesClickBank);
        return params;
    }

}