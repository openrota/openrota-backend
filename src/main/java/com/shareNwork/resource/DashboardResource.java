package com.shareNwork.resource;

import com.shareNwork.domain.DashboardDTO;
import com.shareNwork.domain.ResourceRequestDashboard;
import com.shareNwork.domain.SharedResourceDashboard;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class DashboardResource {

    private SharedResourceDashboard sharedResourceDashboard;
    private ResourceRequestDashboard resourceRequestDashboard;

    @Query("dashboard")
    @Description("Get dashboard data")
    public DashboardDTO getDashboardData() {
        return new DashboardDTO(sharedResourceDashboard.getData(),
                                resourceRequestDashboard.getData());
    }
}
