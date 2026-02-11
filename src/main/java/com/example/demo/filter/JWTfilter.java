package com.example.demo.filter;

import com.example.demo.model.CustomUserModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTfilter extends OncePerRequestFilter {
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserModel customUserModel;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String authHeader= request.getHeader("Authorization");
        String token = null;
        String username=null;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
             token=authHeader.substring(7);
             username = jwtUtil.extractUserName(token);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = customUserModel.loadUserByUsername(username);
            if(jwtUtil.validateToken(username,userDetails,token)){
                UsernamePasswordAuthenticationToken authDetails = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authDetails.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authDetails);
            }
        }
       filterChain.doFilter(request,response);
    }

}
