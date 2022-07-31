package com.quess.geosync;

import com.quess.geosync.beans.sensor.Sensor;
import com.quess.geosync.beans.sensor.SensorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class SensorRepositoryTests {
    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void testAddNew() {
        Sensor sensor = new Sensor();
        sensor.setNome("GPS");

        Sensor sensorSalvo = sensorRepository.save(sensor);

        Assertions.assertThat(sensorSalvo.getId()).isNotNull();
        Assertions.assertThat(sensorSalvo.getId()).isGreaterThan(0);
    }
}
