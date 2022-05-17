package com.work2home.publica.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Mensagem;
import com.work2home.publica.project.rest.dto.MensagemDto;
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
	public MensagemDto add(MensagemDto dto) {
		Usuario sender = usuarioRepository.findById(dto.getUserFrom()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Usuario receiver = usuarioRepository.findById(dto.getUserTo()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Mensagem message = new Mensagem();
		
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setDataEnvio(LocalDateTime.now());
		message.setText(dto.getText());
		
		sender.getMensagensEnviadas().add(message);
		receiver.getMensagensRecebidas().add(message);
		
		messageRepository.save(message);
		usuarioRepository.save(sender);
		usuarioRepository.save(receiver);
		
		return dto;
	}

	public List<MensagemDto> getMessagesByUserId(int id) {
		
		List<Mensagem> messages = messageRepository.findByReceiverIdOrSenderId(id, id);
		List<MensagemDto> messagesConverted = new ArrayList<>();
		
		for (Mensagem message : messages) {
			MensagemDto messageConverted = new MensagemDto();
			messageConverted.setText(message.getText());
			messageConverted.setSentDate(message.getDataEnvio());
			messageConverted.setUserFrom(message.getSender().getId());
			messageConverted.setUserTo(message.getReceiver().getId());
			messagesConverted.add(messageConverted);
		}
		
		return messagesConverted;
	}
}
