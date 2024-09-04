package com.eCommerce.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsService userServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		Enumeration<String> enu = request.getHeaderNames();
		final String requestTokenHeader = request.getHeader("Authorization");
		logger.info(requestTokenHeader);
		
		String username = null;
		String jwtToken = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = this.jwtUtil.getUsernameFromToken(jwtToken);
			} catch(Exception ex) {
				ex.printStackTrace();
				logger.error("Token has expired");
			}
		}
		else {
			logger.error("Invalid Token Not Start with Bearer");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			final UserDetails userDetails = this.userServiceImpl.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
			}
		}
		else {
			logger.error("Token is not valid!");
		}
		
		filterChain.doFilter(request, response);
	}

}
