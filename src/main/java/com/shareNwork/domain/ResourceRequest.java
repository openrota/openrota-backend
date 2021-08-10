package com.shareNwork.domain;

import com.shareNwork.domain.constants.ResourceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourceRequest extends BaseEntity {

    private String requestName;

    private String description;

    @ManyToOne
    private Slot slot;

    @ManyToOne
    private Employee requester;

    private Integer requiredExperience;

    private ResourceRequestStatus resourceRequestStatus;

}
