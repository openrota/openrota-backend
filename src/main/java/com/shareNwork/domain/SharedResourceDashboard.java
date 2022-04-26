package com.shareNwork.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import com.shareNwork.domain.constants.ResourceAvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class SharedResourceDashboard {

    private int totalSharedResources;
    private int availableSharedResources;
    private int unavailableSharedResources;

    public SharedResourceDashboard getData() {
        AtomicInteger available = new AtomicInteger();
        AtomicInteger unavailable = new AtomicInteger();
        List<SharedResource> sharedResource = SharedResource.listAll();
        sharedResource.parallelStream().forEach(sr -> {
            if (sr.getStatus().equals(ResourceAvailabilityStatus.AVAILABLE)) {
                available.getAndIncrement();
            }
            if (sr.getStatus().equals(ResourceAvailabilityStatus.UNAVAILABLE)) {
                unavailable.getAndIncrement();
            }
        });
        return new SharedResourceDashboard(sharedResource.size(), available.get(), unavailable.get());
    }
}
