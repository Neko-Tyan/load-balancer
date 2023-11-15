package com.revolut.interview;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class LoadBalancerImpl implements LoadBalancer {
    
    private final List<Instance> instancePool;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public Instance nextInstance() {
        if (instancePool == null || instancePool.isEmpty()) {
            throw new EmptyLoadBalancerPoolException();
        }

        int size = instancePool.size();
        int index = currentIndex.getAndIncrement() % size;
        if (index < 0) {
            index = -index; // negative index handling
        }
        return instancePool.get(index);
    }
}