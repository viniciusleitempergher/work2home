package com.work2home.publica.project.service;

import com.work2home.publica.project.enums.StatusEmail;
import com.work2home.publica.project.model.Email;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.EmailRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.rest.dto.email.EmailRequest;
import com.work2home.publica.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwt;

    @Value("${url.frontend}")
    private String urlFrontEnd;

    @Value("${email.from}")
    private String emailFrom;


    public void sendEmail(EmailRequest emailRequest) {

        String emailUsuario = emailRequest.getEmail();

        Usuario usuario = usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));


        String token = jwt.generateAccessToken(usuario.getRefreshToken());


        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String novaSenha = UUID.randomUUID().toString();
        Email email = criarEmail(emailUsuario, token);

        usuario.setSenha(bcrypt.encode(novaSenha));

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getTitulo());
            message.setText(email.getTexto());
            emailSender.send(message);

            email.setStatusEmail(StatusEmail.ENVAIDO);
        } catch (MailException e){
            email.setStatusEmail(StatusEmail.ERRO);
        } finally {
            usuarioRepository.save(usuario);
        }
    }

    private Email criarEmail(String emailTo, String token){
        return Email.builder()
                .emailTo(emailTo)
                .emailFrom(emailFrom)
                .titulo("Recuperação de senha")
                .texto("Clique no link para recuperar sua senha: " + urlFrontEnd + "/alterar-senha/" + token + "\n Expira em 1h")
                .dataEnvio(LocalDateTime.now())
                .build();
    }

    public Page<Email> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }
}
