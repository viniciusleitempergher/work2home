package com.work2home.publica.project.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findByReceiverIdOrSenderId(Integer id, Integer id2);
}
