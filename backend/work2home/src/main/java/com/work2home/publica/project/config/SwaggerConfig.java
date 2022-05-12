package com.work2home.publica.project.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	    @Bean
	    public Docket swagger(){

	        return new Docket(DocumentationType.SWAGGER_2)
	                .useDefaultResponseMessages(false)
	                .select()
	                .apis(RequestHandlerSelectors
	                        .basePackage("com.work2home.publica.project.rest.controller"))
	                .paths(PathSelectors.any())
	                .build()
	                .securitySchemes(List.of(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
	                .apiInfo(apiInfo());
	    }

	    private ApiInfo apiInfo(){
	        return new ApiInfoBuilder()
	                .title("Work2Home API")
	                .description("Api de prestações de serviços para residências")
	                .version("1.0")
	                .build();
	    }
}
