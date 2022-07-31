package com.quess.geosync.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SenhaUtil {

    public String criptografar(String senhaDescriptografada) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senhaDescriptografada);
    }

    public String gerarNovaSenha() {
        return String.valueOf(UUID.randomUUID()).substring(0, 7);
    }
}
