package dachertanov.pal.palbackendservice.security.config;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    private final String jwtSecret;
    private final Duration jwtExpiration;
    private final String adminId;

    public JwtUtils(@Value("${pal.security.jwt.secret}") String jwtSecret,
                    @Value("${pal.security.jwt.expiration}") Duration jwtExpiration,
                    @Value("${pal.security.admin-id}") String adminId) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
        this.adminId = adminId;
    }

    public String generateToken(String userId) {
        return Jwts.builder().setSubject(userId).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration.getSeconds() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public UserDetails getUserDetailsFromJwt(String jwt) {
        Claims claims = getClaimsFromJwtToken(jwt);
        String userId = claims.getSubject();
        return new UserDetailsImpl(userId, adminId);
    }
}

