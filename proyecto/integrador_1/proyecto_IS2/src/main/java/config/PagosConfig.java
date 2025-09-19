package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;

import jakarta.annotation.PostConstruct;

@Configuration
public class PagosConfig {
	
	@Value("${mercadopago.access-token}")
	private String accessToken;
	
	@PostConstruct
	public void init() {
		MercadoPagoConfig.setAccessToken(accessToken);
	}


}
