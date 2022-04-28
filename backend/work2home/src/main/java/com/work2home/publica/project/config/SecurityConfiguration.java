package com.work2home.publica.project.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
			.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.antMatchers(HttpMethod.POST, "/cliente").permitAll()
			.antMatchers(HttpMethod.POST, "/prestador").permitAll()
			.antMatchers("/images/**").permitAll()
			
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/aceitar-orcamento").hasAnyRole("CLIENTE")
			
			
			
			
			.antMatchers("/swagger-ui/**").permitAll().antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/swagger-ui.html").permitAll().antMatchers("/v3/api-docs").permitAll()
			.antMatchers("/v2/api-docs").permitAll().antMatchers("/webjars/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}
}
