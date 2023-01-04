package com.shareNwork.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.shareNwork.domain.constants.InvitationStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccessRequest extends PanacheEntity {
    @Column
    private String emailId;

    @Column
    private InvitationStatus status;

    @Column
    private String reason;


}
