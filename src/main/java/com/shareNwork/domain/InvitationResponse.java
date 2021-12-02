package com.shareNwork.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;

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
