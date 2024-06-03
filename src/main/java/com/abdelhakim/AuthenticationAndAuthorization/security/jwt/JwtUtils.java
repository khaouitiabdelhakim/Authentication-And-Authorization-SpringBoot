package com.abdelhakim.AuthenticationAndAuthorization.security.jwt;



import com.abdelhakim.AuthenticationAndAuthorization.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret ;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${app.jwtCookieName}")
  private String jwtCookie;



  public ResponseCookie getCleanJwtCookie() {
      return ResponseCookie.from(jwtCookie, null)
            .path("/api")
            .build();
  }


  public String getUserNameFromJwtToken(String token) {
    SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    System.out.println("Token Claims: " + claims);
    return claims.getSubject();
  }


  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .claim("roles", userPrincipal.getAuthorities())
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
            .compact();
  }


  public boolean validateJwtToken(String authToken) {
    if (authToken == null || authToken.isEmpty()) {
      return false;
    }
    try {
      SecretKey secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
      Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(authToken);
      return true;
    } catch (Exception ignored) {}

    return false;
  }
}
