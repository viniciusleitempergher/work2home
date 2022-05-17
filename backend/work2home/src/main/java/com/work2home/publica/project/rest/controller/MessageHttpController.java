package com.work2home.publica.project.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.MessageDto;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.service.MessageService;
import com.work2home.publica.project.utils.JwtUtil;
@RestController
@RequestMapping("/message")
public class MessageHttpController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private JwtUtil jwt;
	
    @GetMapping("/me")
    public List<MessageDto> getMyMessages() {
		Usuario user = jwt.getUserFromHeaderToken();
    	return messageService.getMessagesByUserId(user.getId());
    }
    
    @PostMapping
    public MessageDto sendMessage(@RequestBody MessageDto dto) {
    	System.out.println(dto);
    	return messageService.add(dto);
    }
}
