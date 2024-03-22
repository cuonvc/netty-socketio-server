package com.example.nettysocketio.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider {

    private static final String SECRET_KEY = "Ym9va2NhcnZpZXRuYW1AMjAyNGVuY29kZWQ=Ym9va2NhcnZpZXRuYW1AMjAyNGVuY29kZWQ=";

    public String getPhoneFromToken(String token) {
        validateToken(token);
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String validateToken(String token) {
        String message;
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token);
            return "PASSED";
        } catch (SignatureException e) {
            message = "Invalid JWT signature";
            log.error(message);
            return message;
        } catch (MalformedJwtException e) {
            message = "Invalid JWT token";
            log.error(message);
            return message;
        } catch (ExpiredJwtException e) {
            message = "Expired JWT token";
            log.error(message);
            return message;
        } catch (UnsupportedJwtException e) {
            message = "Unsupported JWT token";
            log.error(message);
            return message;
        } catch (IllegalArgumentException e) {
            message = "JWT claims string is empty";
            log.error(message);
            return message;
        }
    }
}
