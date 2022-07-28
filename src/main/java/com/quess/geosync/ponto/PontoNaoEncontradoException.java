package com.quess.geosync.ponto;

public class PontoNaoEncontradoException extends Exception {
    public PontoNaoEncontradoException(Integer id) {
        super(String.format("Ponto %d não encontrado", id));
    }
}
