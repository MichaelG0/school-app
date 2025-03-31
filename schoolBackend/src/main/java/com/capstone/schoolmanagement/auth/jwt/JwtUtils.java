package com.capstone.schoolmanagement.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.capstone.schoolmanagement.auth.users.UserDetailsImpl;
import com.capstone.schoolmanagement.auth.users.UserResponse;
import com.capstone.schoolmanagement.auth.users.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
  private final UserService userService;

  @Value("${application.jwtSecret}")
  private String jwtSecret;

  @Value("${application.jwtExpirationMs}")
  private int jwtExpirationMs;

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    log.info(userPrincipal.getUsername() + " - logged in");

    UserResponse userResponse = userService.getById(userPrincipal.getId());

    Map<String, Object> claims = new HashMap<>();
    claims.put("role", userResponse.getRoles());
    claims.put("sub", userPrincipal.getUsername());
    return Jwts.builder()
      .subject(userPrincipal.getUsername())
      .claims(claims)
      .issuedAt(new Date())
      .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(getSigningKey())
      .compact();
  }

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    } catch (JwtException e) {
      log.error("JWT signature validation failed: {}", e.getMessage());
    }

    return false;
  }
}
