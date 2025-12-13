package com.RadioManagement.RadioManagement.Security;

import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Service.UserDetailsServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsServices userDetailsServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //header?
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //token?
        String token = header.substring(7).trim();
        if(token==null){
            filterChain.doFilter(request,response);
            return;
        }

        //username
        String username;
        try{
        username=jwtUtil.ExtractUsername(token);}
        catch (Exception e) {
            filterChain.doFilter(request,response);
            return;
        }

       if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userdb = userDetailsServices.loadUserByUsername(username);
           if(userdb!=null && jwtUtil.validateToken(token,userdb)){
               UsernamePasswordAuthenticationToken authenticationToken
                       = new UsernamePasswordAuthenticationToken(userdb,null,userdb.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
       }

        filterChain.doFilter(request,response);

    }

}
