package org.aibles.failwall.authentication.provider;

import io.jsonwebtoken.*;
import org.aibles.failwall.exception.FailWallBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtProvider {

    private final static String JWT_HEADER = "Authorization";
    private final static String JWT_SECRET_KEY = "aibles";
    private final static String JWT_PREFIX= "Bearer";
    private final static long JWT_LIFE_TIME_MILLISECONDS = 604_800_000;

    public String generateToken(String email){
        Claims claims = Jwts.claims().setSubject(email);
        Date now  = new Date();
        Date expirationDate = new Date (now.getTime() + JWT_LIFE_TIME_MILLISECONDS);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date()); // return false if expiration before now
        }catch (JwtException e){
            throw new FailWallBusinessException("JWT token is expired", HttpStatus.UNAUTHORIZED);
        }catch (IllegalArgumentException e){
            throw new FailWallBusinessException("JWT token is invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(JWT_HEADER);
        if (token != null && token.startsWith(JWT_PREFIX)){
            return token.substring(7);
        }
        return null;
    }

}
