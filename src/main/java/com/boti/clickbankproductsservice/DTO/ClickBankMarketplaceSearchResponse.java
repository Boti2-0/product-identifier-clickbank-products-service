package com.boti.clickbankproductsservice.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ClickBankMarketplaceSearchResponse {
   private Integer totalHits;
   private Integer offset;
   private List<ClickBankHitsResponse> hits;
}


