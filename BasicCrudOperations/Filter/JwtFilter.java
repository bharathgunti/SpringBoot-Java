package com.example.BasicCrudOperations.Filter;

import com.example.BasicCrudOperations.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");

        String jwt=null;
        String username=null;

        if(authHeader!=null&& authHeader.startsWith("Bearer ")){

            jwt=authHeader.substring(7);
            username=jwtutil.extractUserName(jwt);
        }

        if(username!=null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if(jwtutil.validateToken(jwt,userDetails)){

                UsernamePasswordAuthenticationToken auth_obj=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                auth_obj.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth_obj);

            }
        }
        System.out.println("Authorization Header: " + authHeader);
        System.out.println("Extracted Username: " + username);

        chain.doFilter(request,response);
    }
}
