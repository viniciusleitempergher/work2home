package com.work2home.publica.userauthentication.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.userauthentication.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
