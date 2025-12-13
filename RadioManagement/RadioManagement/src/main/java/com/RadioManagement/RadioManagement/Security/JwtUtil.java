package com.RadioManagement.RadioManagement.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    private final String SECRET= "ThisIsSecretThisIsSecretThisIsSecretThisIsSecret";
    private final long EXPIRATION=24*60*60*1000l;

    //decode and parse
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token).getBody();
    }

    public String ExtractUsername(String token){
        Claims claim=extractAllClaims(token);
        return claim.getSubject();
    }
    public Date ExtractExpiration(String token){
        Claims claim=extractAllClaims(token);
        return claim.getExpiration();
    }


    //generatetoken
    public String generateToken(UserDetails userDetails){
        return  Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .addClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .compact();
    }

    //validatetoken
    public boolean validateToken(String token,UserDetails userDetails){
        String userName=ExtractUsername(token);
        Date Expiry =ExtractExpiration(token);
        return (userName.equals(userDetails.getUsername()) && Expiry.after(new Date(System.currentTimeMillis())));
    }
}
