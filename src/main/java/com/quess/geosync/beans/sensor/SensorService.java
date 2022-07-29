package com.quess.geosync.beans.sensor;

import exceptions.SensorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository repo;

    public SensorService(SensorRepository repo) {
        this.repo = repo;
    }

    public Sensor get(Integer id) throws SensorNaoEncontradoException {
        Optional<Sensor> optionalSensor = repo.findById(id);
        if (optionalSensor.isPresent()) {
            return optionalSensor.get();
        }
        else {
            throw new SensorNaoEncontradoException(id);
        }
    }
    public List<Sensor> listAll() {
        return (List<Sensor>) repo.findAll();
    }
}
