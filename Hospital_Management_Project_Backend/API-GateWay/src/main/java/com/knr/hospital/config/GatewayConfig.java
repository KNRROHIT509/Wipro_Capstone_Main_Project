package com.knr.hospital.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            // User Auth Service - No local filters needed (global filter skips it)
            .route("user-authentication-service-route", r -> r.path("/api/auth/**")
                .filters(f -> f
                    .addRequestHeader("X-Debug", "true") // Add header for debugging
                    .filter((exchange, chain) -> { // Log request for troubleshooting
                        System.out.println("Routing to USER-AUTHENTICATION-SERVICE: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://USER-AUTHENTICATION-SERVICE"))
            // Doctor Service with RewritePath
            .route("doctor-service-route", r -> r.path("/api/doctor/**")
                .filters(f -> f
                    .rewritePath("/api/doctor/(?<segment>.*)", "/${segment}") // Strip prefix
                    .addRequestHeader("X-Debug", "true") // Add header for debugging
                    .filter((exchange, chain) -> { // Log request for troubleshooting
                        System.out.println("Routing to Doctor_Service: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://Doctor_Service"))
            // Patient Service with RewritePath
            .route("patient-service-route", r -> r.path("/api/patients/**")
                .filters(f -> f
                    .rewritePath("/api/patients/(?<segment>.*)", "/${segment}") // Strip prefix
                    .addRequestHeader("X-Debug", "true")
                    .filter((exchange, chain) -> {
                        System.out.println("Routing to Patient_Service: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://Patient_Service"))
            // Appointment Service with RewritePath
            .route("appointment-service-route", r -> r.path("/api/appointments/**")
                .filters(f -> f
                    .rewritePath("/api/appointments/(?<segment>.*)", "/${segment}") // Strip prefix
                    .addRequestHeader("X-Debug", "true")
                    .filter((exchange, chain) -> {
                        System.out.println("Routing to Appointment_Service: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://Appointment_Service"))
            // Medical Record Service with RewritePath
            .route("medical-record-service-route", r -> r.path("/api/medicalrecords/**")
                .filters(f -> f
                    .rewritePath("/api/medicalrecords/(?<segment>.*)", "/${segment}") // Strip prefix
                    .addRequestHeader("X-Debug", "true")
                    .filter((exchange, chain) -> {
                        System.out.println("Routing to Medical_Record_Service: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://Medical_Record_Service"))
            // Billing Service with RewritePath
            .route("billing-service-route", r -> r.path("/api/billing/**")
                .filters(f -> f
                    .rewritePath("/api/billing/(?<segment>.*)", "/${segment}") // Strip prefix
                    .addRequestHeader("X-Debug", "true")
                    .filter((exchange, chain) -> {
                        System.out.println("Routing to Billing_Service: " + exchange.getRequest().getURI());
                        return chain.filter(exchange);
                    }))
                .uri("lb://Billing_Service"))
            .build();
    }
}
