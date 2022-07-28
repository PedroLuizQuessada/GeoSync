package com.quess.geosync;

import com.quess.geosync.ponto.Ponto;
import com.quess.geosync.ponto.PontoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class PontoRepositoryTests {
    @Autowired
    private PontoRepository repo;

    @Test
    public void testAddNew() {
        Ponto ponto = new Ponto();
        ponto.setNome("Motoboy A");
        ponto.setAtivo(false);

        Ponto pontoSalvo = repo.save(ponto);

        Assertions.assertThat(pontoSalvo.getId()).isNotNull();
        Assertions.assertThat(pontoSalvo.getId()).isGreaterThan(0);
    }
}
