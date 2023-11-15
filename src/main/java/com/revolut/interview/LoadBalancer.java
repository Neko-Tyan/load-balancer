package com.revolut.interview;

public interface LoadBalancer {
    
    Instance nextInstance();
}