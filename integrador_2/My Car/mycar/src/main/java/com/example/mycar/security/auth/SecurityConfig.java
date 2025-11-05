package com.example.mycar.security.auth;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.example.mycar.security.CustomUserDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1️⃣ Authorization Server (endpoints OAuth2, JWT)
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    // 2️⃣ Resource Server (endpoints protegidos)
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/cambiar-clave/**").authenticated()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()) // 🔥 necesario para JWT tipo Bearer
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    /*
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/auth/login").permitAll()
            	    .requestMatchers("/api/auth/cambiar-clave/**").authenticated().
            	    anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults()); // opcional, para pruebas API

            //.formLogin(Customizer.withDefaults());
        return http.build();
    }*/

    // 3️⃣ AuthenticationManager para AuthController
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       CustomUserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder)
                   .and()
                   .build();
    }

    // 4️⃣ UserDetailsService usando tu CustomUserDetailsService
    /*
    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }*/

    // 5️⃣ PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 6️⃣ JWT (JWKSource + decoder)
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
	    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
	            .clientId("client-app")
	            .clientSecret("{noop}secret")
	            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client-app")
	            .postLogoutRedirectUri("http://127.0.0.1:8080/")
	            .scope(OidcScopes.OPENID)
	            .scope(OidcScopes.PROFILE)
	            .clientSettings(ClientSettings.builder()
	                    .requireAuthorizationConsent(true)
	                    .build())
	            .build();
	
	    return new InMemoryRegisteredClientRepository(client);
	}

	/*
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }*/

    // 7️⃣ Configuración del Authorization Server
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        // Debe ser la misma clave secreta que usás en JwtUtil
        String secretKey = "claveSecretaMuySegura123456789012345678901234567890"; 
        return NimbusJwtDecoder.withSecretKey(
                new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
        ).build();
    }
}