package com.shareNwork.service;

import io.smallrye.mutiny.subscription.Cancellable;

public interface EventBusService<T> {

    void sendRequest(final String event, T data);
}
