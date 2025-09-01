package com.knr.hospital.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GlobalErrorHandler {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new CustomErrorAttributes();
    }

    static class CustomErrorAttributes implements ErrorAttributes {

        @Override
        public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
            Throwable error = getError(request);
            Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("message", error.getMessage());
            errorAttributes.put("path", request.path());
            if (options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
                errorAttributes.put("stackTrace", java.util.Arrays.toString(error.getStackTrace()));
            }
            return errorAttributes;
        }

        @Override
        public Throwable getError(ServerRequest request) {
            return (Throwable) request.attribute("org.springframework.boot.web.reactive.error.ErrorAttributes.ERROR")
                    .orElseThrow(() -> new IllegalStateException("Missing exception attribute in ServerRequest"));
        }

        @Override
        public void storeErrorInformation(Throwable error, ServerWebExchange exchange) {
            exchange.getAttributes().put("org.springframework.boot.web.reactive.error.ErrorAttributes.ERROR", error);
        }
    }

    @Component
    @Order(-2) // High priority
    public static class GatewayExceptionHandler {

        public Mono<ServerResponse> handleNotFound(NotFoundException ex) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Service not found: " + ex.getMessage());
        }

        public Mono<ServerResponse> handleGeneralException(Exception ex) {
            System.err.println("Unhandled error: " + ex.getMessage());
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error: " + ex.getMessage());
        }

        private Mono<ServerResponse> buildErrorResponse(HttpStatus status, String message) {
            Map<String, String> error = new HashMap<>();
            error.put("error", message);
            error.put("status", String.valueOf(status.value()));
            return ServerResponse.status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(error); // Serializable map
        }
    }
}
