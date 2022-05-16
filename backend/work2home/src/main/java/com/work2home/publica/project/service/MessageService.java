package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Message;
import com.work2home.publica.project.model.MessageDto;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.MessageRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void add(MessageDto dto) {
		Usuario sender = usuarioRepository.findById(dto.getUserFrom()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Usuario receiver = usuarioRepository.findById(dto.getUserTo()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Message message = new Message();
		
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setDataEnvio(LocalDateTime.now());
		message.setText(dto.getText());
		
		sender.getMensagensEnviadas().add(message);
		receiver.getMensagensRecebidas().add(message);
		
		messageRepository.save(message);
		usuarioRepository.save(sender);
		usuarioRepository.save(receiver);
	}

}
