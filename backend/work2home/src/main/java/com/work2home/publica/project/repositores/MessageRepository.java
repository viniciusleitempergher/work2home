package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
