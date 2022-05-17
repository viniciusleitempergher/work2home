package com.work2home.publica.project.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.login.LoginRequest;
import com.work2home.publica.project.rest.dto.login.LoginResponse;
import com.work2home.publica.project.rest.dto.refresh.RefreshRequest;
import com.work2home.publica.project.rest.dto.refresh.RefreshResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.RefreshToken;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.RefreshTokenRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RefreshTokenRepository refreshRepository;
	
	@Autowired
	private JwtUtil jwt;
	
	@Value("${google.client.id}")
	String CLIENT_ID;
	
	public LoginResponse googleSignIn(String googleToken) throws GeneralSecurityException, IOException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID)).build();

		GoogleIdToken idToken = verifier.verify(googleToken);

		LoginResponse response = new LoginResponse();

		if (idToken != null) {
			Payload payload = idToken.getPayload();

			String userId = payload.getSubject();

			String email = payload.getEmail();
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			
			Usuario usuario = usuarioRepository
					.findByEmail(email)
					.orElse(new Usuario());
			
			if (usuario.getRole() == null) {
				BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
				
				usuario.setEmail(email);
				usuario.setNome(name);
				usuario.setSenha(bcrypt.encode(UUID.randomUUID().toString()));
				usuario.setImagemUrl(pictureUrl);
				usuario.setRole(Roles.CADASTRO_INCOMPLETO);
				usuario.setDataCriacao(LocalDate.now());
				usuario = usuarioRepository.save(usuario);
			}
			
			removerInatividade(usuario);
			
			return gerarTokens(usuario);
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	public LoginResponse logar(LoginRequest request) {
		
		Usuario usuario = usuarioRepository
				.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		if (!bcrypt.matches(request.getSenha(), usuario.getSenha())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
			
		removerInatividade(usuario);
		
		return gerarTokens(usuario);
	}
	
	private LoginResponse gerarTokens(Usuario usuario) {
		RefreshToken refreshToken;
		
		if (usuario.getRefreshToken() == null) {
			refreshToken = new RefreshToken();
			usuario.setRefreshToken(refreshToken);
			refreshToken.setUsuario(usuario);
		} else {
			refreshToken = usuario.getRefreshToken();
		}
		
		refreshToken.setToken(jwt.generateRefreshToken(usuario));
		
		refreshRepository.save(refreshToken);
		
		String accessToken = jwt.generateAccessToken(refreshToken);

		return LoginResponse.builder().accessToken(accessToken)
				.refreshToken(refreshToken.getToken()).build();
	}
	
	private void removerInatividade(Usuario u) {
		if(u.getRole() == Roles.INATIVO) {
			u.setRole(Roles.PRESTADOR);
			usuarioRepository.save(u);
		}
	}
	
	public RefreshResponse refresh(RefreshRequest request) {
		RefreshToken dbRefreshToken = refreshRepository
				.findByToken(request.getRefreshToken())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		String accessToken = jwt.generateAccessToken(dbRefreshToken);
		return RefreshResponse.builder().accessToken(accessToken).build();
	}
}
