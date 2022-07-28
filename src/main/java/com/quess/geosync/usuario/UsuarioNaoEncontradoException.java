package com.quess.geosync.usuario;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException(Integer id) {
        super(String.format("Usuário %d não encontrado", id));
    }
}
