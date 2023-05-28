package com.boti.clickbankproductsservice.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickBankHitsResponse {
    private String site;
    private String title;
    private String description;
    private boolean favorite;
    private String url;
    private ClickBankMarketplaceStatsResponse marketplaceStats;
    private String affiliateToolsUrl;
    private String affiliateSupportEmail;
    private String skypeName;

}


