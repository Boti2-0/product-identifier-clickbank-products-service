package com.boti.clickbankproductsservice.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickBankParamsRequest {
    public VariablesClickBank variablesClickBank;
    private String query;
    private VariablesClickBank variables;

    @Override
    public String toString() {
        return "ClickBankParamsRequest{" +
                "query='" + query + '\'' +
                ", variables=" + variables.toString() +
                '}';
    }

    public VariablesClickBank createEmptyParams(){
        VariablesClickBank variablesClickBank1 =  new VariablesClickBank();
        variablesClickBank1.setParameters(variablesClickBank1.createEmptyParams());
        return variablesClickBank1;
    }

    @Getter
    @Setter
    public
    class VariablesClickBank {
        public ParametersClickBank parametersClickBank;
        private ParametersClickBank parameters;

        public ParametersClickBank createParams(Integer resultsPerPage, Integer offset, String sortField, boolean sortDescending){
            return new ParametersClickBank(resultsPerPage,offset,sortField,sortDescending);
        }

        public ParametersClickBank createEmptyParams(){
            return new ParametersClickBank();
        }

        @Override
        public String toString() {
            return "VariablesClickBank{" +
                    "parameters=" + parameters.toString() +
                    '}';
        }

        @Getter
        @Setter
        class ParametersClickBank {
            private Integer resultsPerPage;
            private Integer offset;
            private String sortField;
            private boolean sortDescending;

            public ParametersClickBank(Integer resultsPerPage, Integer offset, String sortField, boolean sortDescending) {
                this.resultsPerPage = resultsPerPage;
                this.offset = offset;
                this.sortField = sortField;
                this.sortDescending = sortDescending;
            }

            public ParametersClickBank() {
            }

            @Override
            public String toString() {
                return "ParametersClickBank{" +
                        "resultsPerPage=" + resultsPerPage +
                        ", offset=" + offset +
                        ", sortField='" + sortField + '\'' +
                        ", sortDescending=" + sortDescending +
                        '}';
            }
        }
    }

    /*
    * {
    "query": "query ($parameters: MarketplaceSearchParameters!) {"
        marketplaceSearch(parameters: $parameters) {
            totalHits
            offset
            hits {
                site
                title
                description
                favorite
                urlmarketplace
                Stats {
                    activateDate
                    category
                    subCategory
                    initialDollarsPerSale
                    averageDollarsPerSale
                    gravity
                    totalRebill
                    de
                    en
                    es
                    fr
                    it
                    pt
                    standard
                    physical
                    rebill
                    upsell
                    standardUrl
                    Presentmobile
                    EnabledwhitelistVendor
                    cpaVisible
                    dollarTrial
                    hasAdditionalSiteHoplinks
                }
        affiliateToolsUrl
        affiliateSupportEmail
        skypeName}
        facets {
            fieldbuckets { valuecount }
                }
        }
    }",
    "variables": {
        "parameters": {
            "resultsPerPage": 50,
            "offset": 0,
            "sortField": "gravity",
            "sortDescending": false
        }
    }
}*/

}



