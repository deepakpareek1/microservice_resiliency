package com.entailment.gatewayserver;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	
	@Bean
	public RouteLocator entailmentRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return (RouteLocator) routeLocatorBuilder.routes()
				.route(p -> p
						.path("/entailment/accounts/**")
						.filters(f -> f.rewritePath("/entailment/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response_Time", LocalDateTime.now().toString()))
//								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
//										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/entailment/loans/**")
						.filters(f -> f.rewritePath("/entailment/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response_Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/entailment/cards/**")
						.filters(f -> f.rewritePath("/entailment/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response_Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS")).build();
		
	}

}
