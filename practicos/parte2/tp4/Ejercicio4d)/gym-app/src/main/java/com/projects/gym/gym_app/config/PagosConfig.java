package com.projects.gym.gym_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;

import jakarta.annotation.PostConstruct;

@Configuration
public class PagosConfig {
	
	@Value("${mercadopago.access-token}")
	private String accessToken;
	
	@PostConstruct
	public void init() {
		System.out.println("MercadoPago AccessToken: " + accessToken);
		MercadoPagoConfig.setAccessToken(accessToken);
	}
	
	@Bean
    public PaymentClient paymentClient() {
        
        MercadoPagoConfig.setAccessToken(accessToken);

        return new PaymentClient();
    }

}
