package com.work2home.publica.userauthentication.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.userauthentication.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
