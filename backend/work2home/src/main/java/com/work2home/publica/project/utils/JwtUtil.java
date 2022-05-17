package com.work2home.publica.project.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.work2home.publica.project.model.RefreshToken;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.RefreshTokenRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	public Long JWT_REFRESH_TOKEN_VALIDITY;
	public Long JWT_TOKEN_VALIDITY;

	@Value("${jwt.token.seconds}")
	private void setTokenValidity(String seconds) {
		this.JWT_TOKEN_VALIDITY = Long.parseLong(seconds);
	}

	@Value("${jwt.refreshtoken.hours}")
	private void setRefreshTokenValidity(String hours) {
		this.JWT_REFRESH_TOKEN_VALIDITY = Long.parseLong(hours) * 60 * 60;
	}

	@Value("${jwt.refreshtoken.secret}")
	private String REFRESH_TOKEN_SECRET;

	@Autowired
	private UsuarioRepository userRepository;

	/**
	 * Returns the expiration date from the customer token
	 * 
	 * @param token - the customer login token
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Returns a JSON with the key values inside of the customer token
	 * 
	 * @param token - the customer login token
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(REFRESH_TOKEN_SECRET).parseClaimsJws(token).getBody();
	}

	/**
	 * Check if the token has expired
	 * 
	 * @param token - the customer login token
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Generates the refresh token for the customer
	 * 
	 * @param usuario - the customer object
	 */
	public String generateRefreshToken(Usuario usuario) {
		return doGenerateToken(usuario.getId().toString(), JWT_REFRESH_TOKEN_VALIDITY);
	}

	/**
	 * Generates the access token for the customer
	 * 
	 * @param refreshToken - the refreshtoken object
	 */
	public String generateAccessToken(RefreshToken refreshToken) {
		return doGenerateToken(refreshToken.getId() + "", JWT_TOKEN_VALIDITY);
	}

	/**
	 * Creates the token and defines its time of expiration
	 * 
	 * @param subject  - the id of the customer/refreshToken
	 * @param validity - the time in seconds to expire
	 */
	private String doGenerateToken(String subject, Long validity) {
		Map<String, Object> claims = new HashMap<String, Object>();

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + validity * 1000))
				.signWith(SignatureAlgorithm.HS512, REFRESH_TOKEN_SECRET).compact();
	}

	/**
	 * Checks if the refresh token is valid
	 * 
	 * @param token   - the refresh token
	 * @param usuario - the customers object
	 */
	public Boolean validateRefreshToken(String token, Usuario usuario) {
		final Integer id = getIdFromToken(token);

		return (Objects.equals(id, usuario.getId()) && !isTokenExpired(token));
	}

	/**
	 * Checks if the token is valid for this customer
	 * 
	 * @param token        - the access token
	 * @param refreshToken - the refresh tokens object
	 */
	public Boolean validateAccessToken(String token, RefreshToken refreshToken) {
		final Integer id = getIdFromToken(token);
		return (Objects.equals(id, refreshToken.getId()) && !isTokenExpired(token));
	}

	/**
	 * @throws EntityNotFoundException - if refresh token from param token doesn't
	 *                                 exists in DB
	 * 
	 * @param token - the access token
	 * @return true if valid, false if not
	 */
	public Boolean validateAccessToken(String token) {
		final Integer id = getIdFromToken(token);
		RefreshToken refreshToken = refreshTokenRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		return validateAccessToken(token, refreshToken);
	}

	/**
	 * @throws EntityNotFoundException - case refresh token from access token
	 *                                 doesn't exists
	 * @param token - the access token
	 * @return the customer from token
	 */
	public Usuario getUserFromAccessToken(String token) {
		Integer refreshTokenId = getIdFromToken(token);

		RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenId)
				.orElseThrow(EntityNotFoundException::new);

		return refreshToken.getUsuario();
	}

	/**
	 * @throws EntityNotFoundException - case customer from refresh token doesn't
	 *                                 exists
	 * @param token - the refresh token
	 * @return customer - the customers object from db
	 */
	public Usuario getUserFromRefreshToken(String token) {
		Integer usuarioId = getIdFromToken(token);

		return userRepository.findById(usuarioId).orElseThrow(EntityNotFoundException::new);
	}

	/**
	 * Returns the id of the customer token
	 * 
	 * @param token - the customer access/refresh token
	 */
	public Integer getIdFromToken(String token) {
		return Integer.parseInt(getClaimFromToken(token, Claims::getSubject));
	}

	public Usuario getUserFromHeaderToken() {
		String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		return getUserFromAccessToken(token);
	}
}