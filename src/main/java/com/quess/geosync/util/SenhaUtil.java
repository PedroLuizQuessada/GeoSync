package com.quess.geosync.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class SenhaUtil {

    public static String criptografar(String senhaDescriptografada) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senhaDescriptografada);
    }

    public static String gerarNovaSenha() {
        return String.valueOf(UUID.randomUUID()).substring(0, 7);
    }
}
