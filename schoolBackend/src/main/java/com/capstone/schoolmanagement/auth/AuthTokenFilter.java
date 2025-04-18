package com.capstone.schoolmanagement.auth;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capstone.schoolmanagement.auth.jwt.JwtUtils;
import com.capstone.schoolmanagement.auth.users.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      log.debug("[doFilterInternal] jwt " + jwt);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUsernameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.debug("[doFilterInternal] token valido");
        userDetails.getAuthorities().stream().forEach(g -> log.debug("[doFilterInternal] " + g.getAuthority()));

        log.debug("[doFilterInternal] autorities found   " + userDetails.getAuthorities().size() + "");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}
