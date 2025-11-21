package com.eeit205team5.rentalmanagement.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    // 從application.properties讀取
    // 密鑰字串
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration; // ms

    // 用密鑰字串產生HMAC-SHA加密密鑰
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // 生成JWT
    // 在AuthService中，經過一系列的處理，建立好驗證成功的Authentication物件
    /* 
    * UsernamePasswordAuthenticationToken {
    * principal= UserPrincipal (對應的UserDetails物件)
    * credentials = null (為安全起見會清掉)
    * authorities = [ROLE_TENANT]/[ROLE_LANDLORD] (userPrincipal.getAuthorities())
    * authenticated = true
    * }
    */
    public String generateToken(Authentication authentication) {
        // getPrincipal():回傳自訂使用者物件(UserPrincipal)
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Instant now = Instant.now();
        Instant expiryDate = now.plusMillis(jwtExpiration);

        return Jwts.builder()
                .subject(String.valueOf(userPrincipal.getUserId())) // subject // JWT包含userId，不用查資料庫，實現stateless
                .claim("email", userPrincipal.getEmail()) // payload
                .claim("role", userPrincipal.getRole()) // payload
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiryDate))
                .signWith(getSigningKey()) // 新版JJWT只需傳入一個參數，自動判斷HS256、HS512
                .compact(); // 將JWT builder生成成字串(compact string) // xxxxx.yyyyy.zzzzz
    }

    // 從JWT中獲取userId
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser() // 建立JWT解析器，用來驗證與解析JWT
                .verifyWith(getSigningKey()) // 設定用來簽名驗證的密鑰，signature用這個key驗證是否被篡改
                .build() // 建立JwtParser物件
                .parseSignedClaims(token) // 解析JWS(Signed JWT)，驗證簽章是否正確、token是否過期，如果簽章錯誤或過期會丟例外
                .getPayload(); // 取得JWT payload(claims) // 包含sub、iat、exp、自訂claim

        return Long.parseLong(claims.getSubject());
    }

    // 從JWT token中獲取Email
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    // 驗證JWT是否有效
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            System.err.println("JWT signature無效");
        } catch (MalformedJwtException e) {
            System.err.println("JWT token無效");
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token已過期");
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token類型不支援");
        } catch (IllegalArgumentException e) {
            System.err.println("JWT的內容為空");
        }

        return false;
    }

    public Instant getExpirationFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration().toInstant();
    }
}

// signature = header + payload + secret key