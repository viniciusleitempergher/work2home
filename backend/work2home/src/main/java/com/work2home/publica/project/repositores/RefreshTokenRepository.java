package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.project.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
