package com.work2home.publica.project.model;

import com.work2home.publica.project.enums.StatusEmail;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "email_tb")
public class Email {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_from", nullable = false)
    private String emailFrom;

    @Column(name = "email_to", nullable = false)
    private String emailTo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "texto", nullable = false, columnDefinition = "TEXT", length=500)
    private String texto;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @Column(name = "status_email")
    @Enumerated(value = EnumType.ORDINAL)
    private StatusEmail status;
}
