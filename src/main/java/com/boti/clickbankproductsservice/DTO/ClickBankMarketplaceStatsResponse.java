package com.boti.clickbankproductsservice.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickBankMarketplaceStatsResponse {
        @JsonFormat(pattern = "MM-dd-yyyy")
        private LocalDate activateDate;
        private String category;
        private String subCategory;
        private Double initialDollarsPerSale;
        private Double averageDollarsPerSale;
        private Double gravity;
        private Double totalRebill;
        private boolean de;
        private boolean en;
        private boolean es;
        private boolean fr;
        private boolean it;
        private boolean pt;
        private boolean standard;
        private boolean physical;
        private boolean rebill;
        private boolean upsell;
        private boolean standardUrlPresent;
        private boolean mobileEnabled;
        private boolean whitelistVendor;
        private boolean cpaVisible;
        private boolean dollarTrial;
        private boolean hasAdditionalSiteHoplinks;

}


