package com.eCommerce.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	public static final int EXPIRATION_IN_SECONDS = 12000;

    private static final String JWT_SECRET = "1da838f41e0b3159511271a488bfb0b608acd136f0dfefd8c4d8c5da6abc36fdb4be587f3450d6fcb3e14547a3948d71ea934d37a5164d21b5fc92a346bdab7bdac585aea607afd9b1910cc557a79f328b58022ee776bc94689067c6c7164980b3d2074b51a3de7094d3821a73fe2bde1c082370008d4ad6b2be34ee5ae5c10dbfb707ecf9d018eef477395ec9721ace600fafa8e21cd2c3a55a98519523011e4608efbeea163f9c9dbf1e0f52628b6dd2708cbfbc6f3ca4e3fae872d5523f51c29cd04e431151977a2886515b88d3863295809c10dafe8300ad8f8bb5b48cf00348a14973c1be5a18f75d0b56b17e48e7ddf6bb2d52f343d347e61c0ef10a6ba02d890a28c69f844582d3dd0099419ff3f65ec08b9fc84882f313bac65d96cbe46c1f8a2e2012e3849ee3dea5d54004eed9f329ae807374";

    
	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Date getIssuedAtDateFromToken(String token) {
        return extractClaims(token).getIssuedAt();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    @SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    public static String createToken(String username, Date issueDate) {
        @SuppressWarnings("deprecation")
		String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(issueDate)
                .setExpiration(new Date(issueDate.getTime() + EXPIRATION_IN_SECONDS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();

        return jwtToken;
    }

    public static String getSubject(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public static String refreshToken(String token, long expirationInSeconds) {
        final Claims claims = extractClaims(token);

        Date now = new Date();
        claims.setIssuedAt(now);
        claims.setExpiration(new Date(now.getTime() + EXPIRATION_IN_SECONDS));

        return createTokenFromClaims(claims);
    }

    public static boolean isTokenExpired(String token) {
        try {
        	final Claims claims = extractClaims(token);
            Date now = new Date();
            return now.after(claims.getExpiration());
        } catch (ExpiredJwtException ex) {
        	System.err.println(ex.getMessage());
        	return true;
        }
    }

    @SuppressWarnings("deprecation")
	private static String createTokenFromClaims(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    @SuppressWarnings("deprecation")
	private static Claims extractClaims(String token) {
    	Claims claims = null;
    	try {
    		claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    	} catch(ExpiredJwtException ex) {
    		throw ex;
    	} catch(Exception ex) {
    	throw new RuntimeException("InValid");
    	}

        return claims;
    }
}
