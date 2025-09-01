package com.knr.hospital.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.knr.hospital.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtGlobalAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtGlobalAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            
            // Skip authentication for auth endpoints
            if (request.getURI().getPath().contains("/api/auth/")) {
                log.debug("Skipping auth for path: {}", request.getURI().getPath());
                return chain.filter(exchange)
                    .doOnSuccess(v -> log.debug("Response success for path: {}", exchange.getRequest().getURI().getPath()))
                    .doOnError(e -> log.error("Response error for skipped auth path: {}", e.getMessage()));
            }
            
            log.debug("Processing request for path: {}", request.getURI().getPath());
            
            // Check if Authorization header exists
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("No Authorization header found for path: {}", request.getURI().getPath());
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Invalid Authorization format: {} for path: {}", authHeader, request.getURI().getPath());
                return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);
            log.debug("Extracted token prefix: {} for path: {}", token.substring(0, Math.min(token.length(), 20)), request.getURI().getPath());
            
            // Validate JWT token
            if (!jwtUtil.validateToken(token)) {
                log.warn("Invalid or expired token for path: {}", request.getURI().getPath());
                return onError(exchange, "Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
            }
            
            // Extract and log the role
            String userRole = jwtUtil.extractRole(token);
            log.debug("User role extracted from token: {} for path: {}", userRole, request.getURI().getPath());
            
            // Optional: Add path-based role checks here if needed (e.g., for /api/doctor/** require "DOCTOR" or "ADMIN")
            // Example:
            // if (request.getURI().getPath().startsWith("/api/doctor/") && !userRole.equals("DOCTOR") && !userRole.equals("ADMIN")) {
            //     return onError(exchange, "Insufficient permissions", HttpStatus.FORBIDDEN);
            // }

            // Add user info to headers for downstream services
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-Auth-User-Email", jwtUtil.extractUsername(token))
                    .header("X-Auth-User-Role", userRole)
                    .build();
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build())
                .doOnSuccess(v -> log.debug("Response success for path: {}", exchange.getRequest().getURI().getPath()))
                .doOnError(e -> log.error("Response error: {}", e.getMessage()));
        } catch (Exception e) {
            log.error("Exception in global JWT filter for path {}: {}", exchange.getRequest().getURI().getPath(), e.getMessage(), e);
            return onError(exchange, "Internal error during authentication: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        log.error("Error response: {} - Status: {} for path: {}", message, status, exchange.getRequest().getURI().getPath());
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 10160; // After ReactiveLoadBalancerClientFilter (10150) but before other filters
    }
}
