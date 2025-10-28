package edu.egg.tinder.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtTokenProvider {

	private static final String HMAC_ALG = "HmacSHA256";
	private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
	private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

	private final ObjectMapper objectMapper;
	private final SecretKeySpec secretKeySpec;
	private final long expirationMillis;

	public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-ms}") long expirationMillis, ObjectMapper objectMapper) {
		if (secret == null || secret.length() < 32) {
			throw new IllegalArgumentException("La clave JWT debe tener al menos 32 caracteres");
		}
		this.objectMapper = objectMapper;
		this.secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALG);
		this.expirationMillis = expirationMillis;
	}

	public String generateToken(UserDetails userDetails) {
		try {
			String header = encode(Map.of("alg", "HS256", "typ", "JWT"));

			Map<String, Object> payloadMap = new HashMap<>();
			Instant now = Instant.now();
			Instant expiration = now.plusMillis(expirationMillis);

			payloadMap.put("sub", userDetails.getUsername());
			payloadMap.put("iat", now.toEpochMilli());
			payloadMap.put("exp", expiration.toEpochMilli());
			payloadMap.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

			String payload = encode(payloadMap);
			String signature = sign(header + "." + payload);

			return header + "." + payload + "." + signature;
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("No se pudo generar el token JWT", e);
		}
	}

	public boolean validateToken(String token) {
		try {
			Map<String, Object> payload = parsePayload(token);
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				return false;
			}

			String expectedSignature = sign(parts[0] + "." + parts[1]);
			if (!expectedSignature.equals(parts[2])) {
				return false;
			}

			long exp = getLong(payload.get("exp"));
			return Instant.now().toEpochMilli() < exp;
		} catch (Exception e) {
			return false;
		}
	}

	public String getUsernameFromToken(String token) {
		Map<String, Object> payload = parsePayload(token);
		Object sub = payload.get("sub");
		if (sub == null) {
			throw new IllegalStateException("El token no contiene subject");
		}
		return sub.toString();
	}

	public Instant getExpirationInstant(String token) {
		Map<String, Object> payload = parsePayload(token);
		return Instant.ofEpochMilli(getLong(payload.get("exp")));
	}

	private Map<String, Object> parsePayload(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				throw new IllegalArgumentException("Token inválido");
			}
			byte[] decoded = DECODER.decode(parts[1]);
			return objectMapper.readValue(decoded, Map.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Token inválido", e);
		}
	}

	private String encode(Map<String, Object> data) throws JsonProcessingException {
		byte[] json = objectMapper.writeValueAsBytes(data);
		return ENCODER.encodeToString(json);
	}

	private String sign(String data) {
		try {
			Mac mac = Mac.getInstance(HMAC_ALG);
			mac.init(secretKeySpec);
			return ENCODER.encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException("No es posible firmar el token JWT", e);
		}
	}

	private long getLong(Object value) {
		if (value instanceof Number number) {
			return number.longValue();
		}
		return Long.parseLong(value.toString());
	}
}
