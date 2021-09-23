package com.shareNwork.domain;

import com.shareNwork.domain.constants.InvitationStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Invitation extends PanacheEntity {

    @Column(unique = true)
    private String emailId;

   private InvitationStatus status;
}
