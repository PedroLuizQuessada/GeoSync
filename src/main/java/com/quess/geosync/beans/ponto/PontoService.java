package com.quess.geosync.beans.ponto;

import com.quess.geosync.beans.usuario.Usuario;
import exceptions.UsuarioNaoEncontradoException;
import com.quess.geosync.beans.usuario.UsuarioService;
import exceptions.PontoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PontoService {
    private final PontoRepository pontoRepository;

    private final UsuarioService usuarioService;

    public PontoService(PontoRepository pontoRepository, UsuarioService usuarioService) {
        this.pontoRepository = pontoRepository;
        this.usuarioService = usuarioService;
    }

    public List<Ponto> listAll(boolean ativo) {
        List<Ponto> pontos = (List<Ponto>) pontoRepository.findAll();
        pontos.removeIf(ponto -> ponto.isAtivo() != ativo);
        return pontos;
    }

    public Ponto get(Integer id) throws PontoNaoEncontradoException {
        Optional<Ponto> pontoOptional = pontoRepository.findById(id);
        if (pontoOptional.isPresent()) {
            return pontoOptional.get();
        }
        else {
            throw new PontoNaoEncontradoException(id);
        }
    }

    public void salvar(Ponto ponto) {
        pontoRepository.save(ponto);
    }

    public boolean ativoToggle(Integer id) throws PontoNaoEncontradoException {
        Ponto ponto = get(id);
        ponto.setAtivo(!ponto.isAtivo());
        pontoRepository.save(ponto);
        return ponto.isAtivo();
    }

    public void renomear(Integer id, String nome) throws PontoNaoEncontradoException {
        Ponto ponto = get(id);
        ponto.setNome(nome);
        pontoRepository.save(ponto);
    }

    public void adotarCentral(Integer idUsuario, Integer idCentral) throws UsuarioNaoEncontradoException, PontoNaoEncontradoException {
        Usuario usuario = usuarioService.get(idUsuario);
        Ponto ponto = get(idCentral);
        usuario.setCentral(ponto);
        usuarioService.save(usuario);
    }
}
