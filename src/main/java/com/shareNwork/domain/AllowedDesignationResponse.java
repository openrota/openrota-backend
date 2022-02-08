package com.shareNwork.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllowedDesignationResponse {

    private boolean isgranted;

    private String designationName;

    public AllowedDesignationResponse(boolean isgranted, String designationName) {
        this.isgranted = isgranted;
        this.designationName = designationName;
    }
}
