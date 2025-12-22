package com.raman.security;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	
	private SecretKey getSignInKey() {
	    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	
	public String generateToken(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities()
				                       .stream()
				                       .map(role->role.getAuthority())
				                       .toList());
		return Jwts.builder()
				   .setClaims(claims)
				   .setSubject(userDetails.getUsername())
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
				   .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
				   .compact();
	}
	
	
	
	public Claims extractClaims(String token) {
		
		return Jwts.parserBuilder()
				   .setSigningKey(getSignInKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
	}
	
	public boolean isTokenValid(String username, String token, UserDetails userDetails) {
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public Date extractExpiration(String token) {
		return (Date) extractClaims(token).getExpiration();
	} 
	

	
	
	
}
