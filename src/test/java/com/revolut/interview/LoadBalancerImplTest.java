package com.revolut.interview;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerImplTest {

    LoadBalancer loadBalancer;
    
    @Test
    @DisplayName("Should return non-null server instance")
    void shouldReturnIp() {
        loadBalancer = new LoadBalancerImpl(getInstancePool());
        var instance = loadBalancer.nextInstance();
        
        assertNotNull(instance);
    }

    @Test
    @DisplayName("Should return different server instances on each call")
    void shouldReturnDifferentInstance() {
        loadBalancer = new LoadBalancerImpl(getInstancePool());
        var instance1 = loadBalancer.nextInstance();
        var instance2 = loadBalancer.nextInstance();
        
        assertNotEquals(instance1, instance2);
    }

    @Test
    @DisplayName("Should return next in line server instances on each call")
    void shouldReturnNextInstance() {
        var instancePool = getInstancePool();
        loadBalancer = new LoadBalancerImpl(instancePool);
        
        var instance1 = loadBalancer.nextInstance();
        var instance2 = loadBalancer.nextInstance();
        
        assertEquals(instancePool.get(0), instance1);
        assertEquals(instancePool.get(1), instance2);
    }

    @Test
    @DisplayName("Should throw error if instance pool is empty")
    void shouldThrowIfNoInstances() {
        loadBalancer = new LoadBalancerImpl(new CopyOnWriteArrayList<>());
        assertThrows(EmptyLoadBalancerPoolException.class, () -> loadBalancer.nextInstance());
    }
    
    private List<Instance> getInstancePool() {
        var instancePool = new CopyOnWriteArrayList<Instance>();
        instancePool.add(new Instance("192.168.0.1"));
        instancePool.add(new Instance("192.168.0.2"));
        instancePool.add(new Instance("192.168.0.3"));
        return instancePool;
    }
}