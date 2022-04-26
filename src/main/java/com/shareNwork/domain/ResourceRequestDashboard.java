package com.shareNwork.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import com.shareNwork.domain.constants.ResourceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class ResourceRequestDashboard {

    private int totalRequests;
    private int pendingRequests;
    private int completedRequests;
    private int canceledRequests;

    public ResourceRequestDashboard getData() {
        AtomicInteger pending = new AtomicInteger();
        AtomicInteger approved = new AtomicInteger();
        AtomicInteger cancelled = new AtomicInteger();
        List<ResourceRequest> requests = ResourceRequest.listAll();
        requests.parallelStream().forEach(req -> {
            if (req.getStatus().equals(ResourceRequestStatus.PENDING)) {
                pending.getAndIncrement();
            }
            if (req.getStatus().equals(ResourceRequestStatus.COMPLETED)) {
                approved.getAndIncrement();
            }
            if (req.getStatus().equals(ResourceRequestStatus.CANCELLED)) {
                cancelled.getAndIncrement();
            }
        });
        return new ResourceRequestDashboard(requests.size(), pending.get(), approved.get(), cancelled.get());
    }
}
