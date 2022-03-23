package org.aibles.failwall.authentication.filter;

import lombok.extern.slf4j.Slf4j;
import org.aibles.failwall.authentication.payload.UserPrincipalService;
import org.aibles.failwall.authentication.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserPrincipalService userPrincipalService;

    @Autowired
    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserPrincipalService userPrincipalService) {
        this.jwtProvider = jwtProvider;
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest,
                                 HttpServletResponse servletResponse,
                                 FilterChain filterChain) throws IOException, ServletException {
        final String token = jwtProvider.getTokenFromHeader(servletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                final String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userPrincipalService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (AuthenticationException e) {
            log.error("authentication exception in filter");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
