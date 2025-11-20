package com.alivio.rede.rede_de_alivio_back.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alivio.rede.rede_de_alivio_back.service.TokenService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilterTemp implements Filter {

    private final TokenService tokenService = new TokenService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String path = req.getRequestURI();

        if (path.startsWith("/api/auth") || req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (tokenService.validateToken(token) != null) {
                chain.doFilter(request, response);
                return;
            }
        }

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("{\"message\": \"Acesso negado. Token invalido ou ausente.\"}");
    }
}