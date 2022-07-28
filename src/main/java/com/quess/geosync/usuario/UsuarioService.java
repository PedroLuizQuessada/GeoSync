package com.quess.geosync.usuario;

import com.quess.geosync.ponto.Ponto;
import com.quess.geosync.ponto.PontoNaoEncontradoException;
import com.quess.geosync.ponto.PontoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final PontoRepository pontoRepository;

    public UsuarioService(UsuarioRepository repo, PontoRepository pontoRepository) {
        this.repo = repo;
        this.pontoRepository = pontoRepository;
    }

    public List<Usuario> listAll() {
        return repo.findAll();
    }

    public void save(Usuario usuario) {
        repo.save(usuario);
    }

    public Usuario get(Integer id) throws UsuarioNaoEncontradoException {
        Optional<Usuario> usuarioOptional = repo.findById(id);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        }
        else {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    public void delete(Integer id) throws UsuarioNaoEncontradoException {
        get(id);
        repo.deleteById(id);
    }

    public boolean blockToggle(Integer id) throws UsuarioNaoEncontradoException {
        Usuario usuario = get(id);
        if (usuario.getTentativasAcesso() == 3) {
            usuario.setTentativasAcesso(0);
        }
        else {
            usuario.setTentativasAcesso(3);
        }
        repo.save(usuario);
        return usuario.getTentativasAcesso() == 3;
    }

    public boolean admToggle(Integer id) throws UsuarioNaoEncontradoException {
        Usuario usuario = get(id);
        usuario.setAdm(!usuario.isAdm());
        repo.save(usuario);
        return usuario.isAdm();
    }

    public String getCentral(Integer id) throws UsuarioNaoEncontradoException, PontoNaoEncontradoException {
        Usuario usuario = get(id);
        Integer centralId = usuario.getCentral().getId();

        Optional<Ponto> optionalPonto = pontoRepository.findById(centralId);
        if (!optionalPonto.isPresent()) {
            throw new PontoNaoEncontradoException(centralId);
        }

        return optionalPonto.get().getNome();
    }
}
