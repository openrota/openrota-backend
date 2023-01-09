package com.shareNwork.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationResponse {

    private String token;

    private int responseStatus;

    private String responseText;

    public InvitationResponse(int responseStatus, String responseText) {
        this.responseStatus = responseStatus;
        this.responseText = responseText;
    }
}
