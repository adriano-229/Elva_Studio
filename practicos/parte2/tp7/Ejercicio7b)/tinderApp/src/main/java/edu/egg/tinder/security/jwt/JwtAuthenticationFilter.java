package edu.egg.tinder.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.egg.tinder.services.ServicioUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = resolveToken(request);
			if (token != null && jwtTokenProvider.validateToken(token)
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				String username = jwtTokenProvider.getUsernameFromToken(token);
				UserDetails userDetails = servicioUsuario.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		return path.startsWith("/api/auth");
	}

	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER_PREFIX)) {
			return bearer.substring(BEARER_PREFIX.length());
		}
		return null;
	}
}
