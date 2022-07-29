package com.quess.geosync;

import com.quess.geosync.beans.ponto.Ponto;
import com.quess.geosync.beans.ponto.PontoRepository;
import com.quess.geosync.beans.usuario.Usuario;
import com.quess.geosync.beans.usuario.UsuarioRepository;
import com.quess.geosync.util.SenhaUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UsarioRepositoryTests {
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PontoRepository pontoRepositorio;

    @Test
    public void testAddNew() {
        String senha = "boov2305";

        Usuario usuario = new Usuario();
        usuario.setNome("Joyce");
        usuario.setEmail("moraes.jjoyce@gmail.com");
        usuario.setSenha(SenhaUtil.criptografar(senha));
        usuario.setTentativasAcesso(0);

        Optional<Ponto> optionalPonto = pontoRepositorio.findById(1);
        Assertions.assertThat(optionalPonto).isPresent();
        usuario.setCentral(optionalPonto.get());

        Usuario usuarioSalvo = repo.save(usuario);

        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Usuario> usuarios = repo.findAll();
        Assertions.assertThat(usuarios).hasSizeGreaterThan(0);

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    @Test
    public void testUpdate() {
        Integer usuarioId = 1;
        String novaSenha = SenhaUtil.criptografar("teste");

        Optional<Usuario> optionalUsuario = repo.findById(usuarioId);
        Assertions.assertThat(optionalUsuario).isPresent();

        Usuario usuario = optionalUsuario.get();
        usuario.setSenha(novaSenha);
        repo.save(usuario);

        Optional<Usuario> usuarioAtualizadoOptional = repo.findById(usuarioId);
        Assertions.assertThat(usuarioAtualizadoOptional).isPresent();
        Usuario usuarioAtualizado = usuarioAtualizadoOptional.get();
        Assertions.assertThat(usuarioAtualizado.getSenha()).isEqualTo((novaSenha));
    }

    @Test
    public void testGet() {
        Integer usuarioId = 3;
        Optional<Usuario> optionalUsuario = repo.findById(usuarioId);
        Assertions.assertThat(optionalUsuario).isPresent();
        System.out.println(optionalUsuario.get());
    }

    @Test
    public void testDelete() {
        Integer usuarioId = 1;
        repo.deleteById(usuarioId);
        Optional<Usuario> optionalUsuario = repo.findById(usuarioId);
        Assertions.assertThat(optionalUsuario).isNotPresent();
    }

    @Test
    public void testFindUserByEmail() {
        String email = "moraes.jjoyce@gmail.com";
        Usuario usuario = repo.findByEmail(email);
        Assertions.assertThat(usuario).isNotNull();
    }
}
