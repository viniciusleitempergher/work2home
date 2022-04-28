package com.work2home.publica.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.login.LoginRequest;
import com.work2home.publica.project.dto.login.LoginResponse;
import com.work2home.publica.project.dto.refresh.RefreshRequest;
import com.work2home.publica.project.dto.refresh.RefreshResponse;
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

	public LoginResponse logar(LoginRequest request) {
		
		Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
		
		if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if (!bcrypt.matches(request.getSenha(), usuario.getSenha())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		
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
		
		LoginResponse response = LoginResponse.builder().accessToken(accessToken)
				.refreshToken(refreshToken.getToken()).build();
		
		return response;
	}
	
	public RefreshResponse refresh(RefreshRequest request) {
		String refreshToken = request.getRefreshToken();
		
		RefreshToken dbRefreshToken = refreshRepository.findByToken(refreshToken);
		
		if (dbRefreshToken == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		
		String accessToken = jwt.generateAccessToken(dbRefreshToken);
		
		return RefreshResponse.builder().accessToken(accessToken).build();
	}
}
