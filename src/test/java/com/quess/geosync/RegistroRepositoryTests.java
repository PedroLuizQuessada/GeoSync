package com.quess.geosync;

import com.quess.geosync.beans.ponto.Ponto;
import com.quess.geosync.beans.ponto.PontoRepository;
import com.quess.geosync.beans.registro.Registro;
import com.quess.geosync.beans.registro.RegistroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RegistroRepositoryTests {

    @Autowired
    private RegistroRepository repo;

    @Autowired
    private PontoRepository pontoRepository;

    @Test
    public void testAddNew() {
        Registro registro = new Registro();
        registro.setDataHora(new Timestamp(System.currentTimeMillis()));

        Optional<Ponto> optionalPonto = pontoRepository.findById(1);
        Assertions.assertThat(optionalPonto).isPresent();
        registro.setPonto(optionalPonto.get());

        registro.setLatitude(5.0F);
        registro.setLongitude(1.95F);
        registro.setAltitude(10.5F);
        registro.setVelocidade(10.26F);
        registro.setSentido(0.62F);

        Registro registroSalvo = repo.save(registro);

        Assertions.assertThat(registroSalvo.getId()).isNotNull();
        Assertions.assertThat(registroSalvo.getId()).isGreaterThan(0);
    }
}
