package org.aibles.userservice.security;

import io.jsonwebtoken.*;
import org.aibles.userservice.exception.JwtAuthenticationException;
import org.aibles.userservice.model.Role;
import org.aibles.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    @Autowired
    private UserService userDetailsService;

    private static String authorizationHeader = "Authorization";

    private static String secretKey = "aibles";

    private static long expirationTime = 604800;

    public String generateToken(String email, Set<Role> role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now  = new Date();
        Date expirationDate = new Date (now.getTime() + expirationTime * 1000);
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }catch (IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(authorizationHeader);
        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

}
