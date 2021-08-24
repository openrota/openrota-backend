package com.shareNwork.repository;

import com.shareNwork.domain.Invitation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.lang3.time.DateUtils;
import io.jsonwebtoken.impl.TextCodec;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class InvitationRepository implements PanacheRepository<Invitation> {

   @Transactional
   public String createInvitationToken(Invitation invitation) {
/*
      Date currentDate =
*/
      String token = Jwts.builder()
              .setSubject("Invitation Token")
              .claim("email", invitation.getEmailId())
              .setIssuedAt(new java.util.Date())
              .setExpiration(DateUtils.addHours(new java.util.Date(), 3))
              .signWith(
                      SignatureAlgorithm.HS256,
                      TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
              )
              .compact();
      return token;
   }
}
