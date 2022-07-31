package com.quess.geosync.beans.sensor;

import exceptions.SensorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor get(Integer id) throws SensorNaoEncontradoException {
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if (optionalSensor.isPresent()) {
            return optionalSensor.get();
        }
        else {
            throw new SensorNaoEncontradoException(id);
        }
    }
    public List<Sensor> listAll() {
        return (List<Sensor>) sensorRepository.findAll();
    }
}
