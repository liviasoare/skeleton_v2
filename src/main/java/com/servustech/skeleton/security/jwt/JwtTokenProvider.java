package com.servustech.skeleton.security.jwt;

import com.servustech.skeleton.security.constants.AuthConstants;
import com.servustech.skeleton.security.payload.UserDetailsResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Token Generator and user details provider class
 *
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.access-expiration-in-ms}")
    private int accessjwtExpirationInMs;

    @Value("${jwt.refresh-expiration-in-ms}")
    private int refreshJwtExpirationInMs;

    private final String secret;

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateRefreshToken(UserDetails userPrincipal) {
        return generateToken(userPrincipal, refreshJwtExpirationInMs);
    }

    public String generateAccessToken(UserDetails userPrincipal) {
        return generateToken(userPrincipal, accessjwtExpirationInMs);
    }

    private String generateToken(UserDetails userPrincipal, int expirationInMs) {

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + expirationInMs);
        final String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthConstants.ROLES_KEY, authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public UserDetailsResponse getUserNameAndRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return new UserDetailsResponse(claims.getSubject() , claims.get(AuthConstants.ROLES_KEY).toString());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(null, null, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }
}
