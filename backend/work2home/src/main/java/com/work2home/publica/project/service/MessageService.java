package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.work2home.publica.project.utils.JwtUtil;

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

	public List<MessageDto> getMessagesByUserId(int id) {	
		System.out.println(id);
		
		List<Message> messages = messageRepository.findByReceiverIdOrSenderId(id, id);
		List<MessageDto> messagesConverted = new ArrayList<>();
		
		for (Message message : messages) {
			MessageDto messageConverted = new MessageDto();
			messageConverted.setText(message.getText());
			messageConverted.setSentDate(LocalDateTime.now());
			messageConverted.setUserFrom(message.getSender().getId());
			messageConverted.setUserTo(message.getReceiver().getId());
			messagesConverted.add(messageConverted);
		}
		
		return messagesConverted;
		
	}
}
