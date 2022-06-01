package com.shareNwork.domain;

import java.time.LocalDateTime;

import com.shareNwork.domain.constants.InvitationStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Invitation extends PanacheEntity {

    @Column(unique = true)
    private String emailId;
    private InvitationStatus status;
    private String token;
    private LocalDateTime createdAt;
    private String invitationLinkParams;

    @OneToOne
    private Role role;
}
