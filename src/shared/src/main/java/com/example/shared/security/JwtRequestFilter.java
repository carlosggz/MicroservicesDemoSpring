package com.example.shared.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String AUTHORIZATION_HEADER_START = "Bearer ";

	private final JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		val jwtToken = getToken(request);
		val username = getUserName(jwtToken);

		//Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			val auth = new UsernamePasswordAuthenticationToken(username, null, jwtTokenUtil.getAuthorities(jwtToken));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {

		val requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);

		return requestTokenHeader != null && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_START)
				? requestTokenHeader.substring(AUTHORIZATION_HEADER_START.length())
				: null;
	}

	private String getUserName(String token){

		if (token == null) {
			logger.warn("JWT Token does not begin with Bearer String");
			return null;
		}

		String userName = null;

		try {
			userName = jwtTokenUtil.getUsernameFromToken(token);
		} catch (IllegalArgumentException e) {
			logger.debug("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			logger.debug("JWT Token has expired");
		} catch (Exception ex) {
			logger.debug("Something else: " + ex.getMessage());
		}

		return userName;
	}
}
