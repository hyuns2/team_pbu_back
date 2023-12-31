package projectbuildup.mivv.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.constant.JwtExpiration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    public static final String AUTHORITIES = "authorities";

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final UserDetailsService userDetailsService;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(User user) {
        String accessToken = createToken(user, 1000 * JwtExpiration.ACCESS_SEC);
        String refreshToken = createToken(user, 1000 * JwtExpiration.REFRESH_SEC);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createToken(User user, Long expireTime) {
        Claims claims = injectValues(user, expireTime);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims injectValues(User user, Long expireTime) {
        long now = (new Date()).getTime();
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(user.getId()));
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(now + expireTime));
        claims.setId(UUID.randomUUID().toString());
        claims.put(AUTHORITIES, authorities);

        return claims;
    }

    public Authentication getAuthentication(Claims claims) {
        if (claims.get(AUTHORITIES) == null) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}