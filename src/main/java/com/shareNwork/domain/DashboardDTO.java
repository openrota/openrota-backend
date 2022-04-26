package com.shareNwork.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DashboardDTO {

    private SharedResourceDashboard sharedResourceDashboard;

    private ResourceRequestDashboard requestDashboard;
}
