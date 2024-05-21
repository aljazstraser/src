package com.astraser.code.challenge.gateway.filters;

import com.astraser.code.challenge.gateway.analytics.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    @Autowired
    private RequestCounterService requestCounterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LinkedHashSet<URI> originalUris = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        Optional<String> originalUri = Objects.requireNonNull(originalUris).stream().map(URI::getPath).findFirst();
        originalUri.ifPresent(uri -> requestCounterService.incrementCounter(originalUri.get()));
        return chain.filter(exchange);
    }


}
