package com.work2home.publica.project.rest.controller;

import com.work2home.publica.project.model.Email;
import com.work2home.publica.project.rest.dto.email.EmailRequest;
import com.work2home.publica.project.service.EmailService;
import com.work2home.publica.project.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/resgatar-senha")
    @ResponseStatus(HttpStatus.CREATED)
    public void enviarSenhaEmail(@RequestBody @Valid EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest);
    }

    @GetMapping("/emails")
    public Page<Email> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        return emailService.findAll(pageable);
    }
}
