package com.quess.geosync.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailUtil {

    private final JavaMailSender mailSender;

    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException, UnsupportedEncodingException {
        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email);

        helper.setFrom("quesssystems@gmail.com", "Quess Systems Suporte");
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(mensagem, true);

        mailSender.send(email);
    }
}
