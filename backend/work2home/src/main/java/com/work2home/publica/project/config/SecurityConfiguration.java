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
			.antMatchers(HttpMethod.GET, "/ordem-servico").hasAnyRole("CLIENTE", "ADMIN", "PRESTADOR")
			.antMatchers(HttpMethod.GET, "/ordem-servico/filtro/*").hasAnyRole("CLIENTE", "ADMIN", "PRESTADOR")
			.antMatchers(HttpMethod.POST, "/ordem-servico/solicitar").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.POST, "/ordem-servico/*/imagem").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/aceitar-solicitacao").hasAnyRole("PRESTADOR")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/negar-solicitacao").hasAnyRole("PRESTADOR", "CLIENTE")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/aceitar-orcamento").hasAnyRole("PRESTADOR", "CLIENTE")
			.antMatchers(HttpMethod.PATCH, "/ordem-servico/*/finalizar-os").hasAnyRole("PRESTADOR")
			.antMatchers(HttpMethod.GET, "ordem-servico/filtro/*").hasAnyRole("CLIENTE", "PRESTADOR")
			.antMatchers(HttpMethod.GET, "ordem-servico/quantidades-servicos").hasAnyRole("CLIENTE", "PRESTADOR")

			//requisicoes avaliacao
			.antMatchers("/avaliacao/prestador-avalia-cliente/*").hasAnyRole("PRESTADOR")
			.antMatchers("/avaliacao/cliente-avalia-prestador/*").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.GET, "/avaliacao/avaliacao-existe/*").hasAnyRole("CLIENTE", "PRESTADOR")

			//requisicoes denuncia
			.antMatchers(HttpMethod.POST, "/denuncia").hasAnyRole("PRESTADOR", "CLIENTE")
			.antMatchers(HttpMethod.GET, "/denuncia/*").hasAnyRole("ADMIN")
			
			// requisicoes do cliente
			.antMatchers(HttpMethod.POST, "/endereco").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PUT, "/endereco").hasAnyRole("CLIENTE")
			.antMatchers(HttpMethod.PUT, "/cliente").hasAnyRole("CLIENTE", "CADASTRO_INCOMPLETO")
			.antMatchers(HttpMethod.GET, "/prestador/filtro").hasAnyRole("CLIENTE")
			
			// requisicoes do prestador
			.antMatchers(HttpMethod.PUT, "/prestador").hasAnyRole("PRESTADOR", "CADASTRO_INCOMPLETO")
			.antMatchers(HttpMethod.POST,"/cidade").hasAnyRole("PRESTADOR")
			.antMatchers("/prestador/categoria/*").hasAnyRole("PRESTADOR")
			.antMatchers("/prestador/cidade/*").hasAnyRole("PRESTADOR")
			
			// requisicoes de qualquer usuario
			.antMatchers(HttpMethod.GET, "/cliente/*").hasAnyRole("ADMIN", "CLIENTE", "PRESTADOR")	
		    .antMatchers(HttpMethod.GET, "/prestador/*").hasAnyRole("ADMIN", "CLIENTE", "PRESTADOR")
			.antMatchers(HttpMethod.POST, "/usuario/imagem").hasAnyRole("ADMIN", "CLIENTE", "PRESTADOR")
			.antMatchers(HttpMethod.GET, "/relatorio/servico/*").hasAnyRole("ADMIN", "CLIENTE", "PRESTADOR")
			
			// requisicoes do admin
			.antMatchers(HttpMethod.GET, "/endereco").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/cliente").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/usuario/denunciado/*").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/prestador").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/admin").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/admin/*").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/usuario/banimento/*").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/categoria/*/imagem").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/categoria").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/categoria/*").hasAnyRole("ADMIN")
			.antMatchers("/relatorio/**").hasAnyRole("ADMIN")

			// requisicoes abertas
			.antMatchers(HttpMethod.GET, "/usuario/me").permitAll()
			.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers(HttpMethod.POST, "/cliente").permitAll()
			.antMatchers(HttpMethod.POST, "/prestador").permitAll()
			.antMatchers(HttpMethod.GET, "/categoria").permitAll()
			.antMatchers("/email/**").permitAll()
			.antMatchers("/usuario/me").permitAll()
			
			// mensagens
			.antMatchers("/messages").permitAll()

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
