package com.systemdesign.order.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makePayment(Long orderId) {
        // simulate payment call
        return restTemplate.postForObject(
                "http://payment:8084/api/payments/process/" + orderId,
                null,
                String.class
        );
    }
}
