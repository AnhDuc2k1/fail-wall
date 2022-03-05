package org.aibles.failwall.user.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PasswordResetTokenProvider {

    private static final String TOKEN_SECRET_KEY = "aibles-reset-password";

    private static final long TOKEN_LIFE_TIME_MILLISECOND = 604800 * 1000;

    public String generatePasswordResetToken(String email){
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TOKEN_LIFE_TIME_MILLISECOND);
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET_KEY)
                .compact();
    }

    public boolean isValidResetPassToken(String passwordResetToken, String email){
        return getEmailFromToken(passwordResetToken).equals(email) && !isExpiredResetPassToken(passwordResetToken);
    }

    private String getEmailFromToken(String passwordResetToken){
        return Jwts.parser().setSigningKey(TOKEN_SECRET_KEY)
                .parseClaimsJws(passwordResetToken).getBody().getSubject();
    }

    private boolean isExpiredResetPassToken(String passwordResetToken){
        return (getExpirationDateFromToken(passwordResetToken).before(new Date()));
    }

    private Date getExpirationDateFromToken(String passwordResetToken){
        return Jwts.parser().setSigningKey(TOKEN_SECRET_KEY)
                .parseClaimsJws(passwordResetToken).getBody().getExpiration();
    }

}
