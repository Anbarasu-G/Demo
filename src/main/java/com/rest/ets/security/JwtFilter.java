package com.rest.ets.security;

import com.rest.ets.enums.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JWT_Service jwtService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if(token != null){
            token = token.substring(7);
            if(!token.isEmpty()) {

                Claims claims = jwtService.parseJwt(token);
                String role = claims.get("role", String.class);
                String email = claims.get("email", String.class);

                if(role != null && email != null){

                    UserRole userRole = UserRole.valueOf(role);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null,
                            UserRole.valueOf(role)
                                    .getPrivileges()
                                    .stream()
                                    .map((privilege) -> {
                                        return new SimpleGrantedAuthority(privilege.name());
                                    })
                                    .toList()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
