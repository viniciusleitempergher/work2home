package com.work2home.publica.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.work2home.publica.project.filter.JwtFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JwtFilter jwtFilter;
	
	public SecurityConfiguration(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
			.authorizeRequests()
			//requisicoes da ordem de servico
			.antMatchers(HttpMethod.GET, "/ordem-servico/all").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/ordem-servico/*").hasAnyRole("CLIENTE", "ADMIN", "PRESTADOR")
			.antMatchers(HttpMethod.POST, "/ordem-servico/solicitar").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.POST, "/ordem-servico/*/imagem").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/aceitar-solicitacao").hasAnyRole("PRESTADOR")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/negar-solicitacao").hasAnyRole("PRESTADOR")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/aceitar-orcamento").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/finalizar-os").hasAnyRole("PRESTADOR")
			
			//avaliacao
			.antMatchers("/avaliacao/**").permitAll()
			
			// requisicoes do cliente
			.antMatchers(HttpMethod.POST, "/endereco").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PUT, "/endereco").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PUT, "/cliente").hasAnyRole("CLIENTE")
			
			// requisicoes do prestador
		//	.antMatchers("/prestador/*").hasAnyRole("PRESTADOR")
			
			// requisicoes de qualquer usuario
			.antMatchers(HttpMethod.GET, "/cliente/*").hasAnyRole("ADMIN, CLIENTE, PRESTADOR")	
		//	.antMatchers(HttpMethod.GET, "/prestador/*").hasAnyRole("ADMIN, CLIENTE, PRESTADOR")
			.antMatchers("/usuario/imagem").hasAnyRole("ADMIN, CLIENTE, PRESTADOR")
			
			// requisicoes do admin
			.antMatchers(HttpMethod.GET, "/endereco").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/cliente").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/prestador").hasAnyRole("ADMIN")
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.antMatchers("/categoria/**").hasAnyRole("ADMIN")
			
			// requisicoes abertas
			.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers(HttpMethod.POST, "/cliente").permitAll()
			.antMatchers(HttpMethod.POST, "/prestador").permitAll()
			
			//Tests
			.antMatchers(HttpMethod.GET, "/prestador/**").permitAll()
			
			// requisicoes do swegger
			.antMatchers("/swagger-ui/**").permitAll().antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/swagger-ui.html").permitAll().antMatchers("/v3/api-docs").permitAll()
			.antMatchers("/v2/api-docs").permitAll().antMatchers("/webjars/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOriginPattern("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
