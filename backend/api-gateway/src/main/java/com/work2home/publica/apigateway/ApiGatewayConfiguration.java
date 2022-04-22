package com.work2home.publica.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(p -> p.path("/user-authentication/**").uri("lb://user-authentication"))
				.route(p -> p.path("/user-utilities/**").uri("lb://user-utilities"))
				.route(p -> p.path("/user-utilities/cidade/**").uri("lb://user-utilities"))
				.route(p -> p.path("/main-services/**").uri("lb://main-services"))
        .route(p -> p.path("**/main-services/avaliacao/**").uri("lb://main-services"))
				.build();
	}
}
