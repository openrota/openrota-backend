package com.shareNwork.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class InvitationResponse {

   private String token;

   private int responseStatus;
}
