package com.work2home.publica.userauthentication.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.userauthentication.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
