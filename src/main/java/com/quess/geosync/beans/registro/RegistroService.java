package com.quess.geosync.beans.registro;

import com.quess.geosync.util.ConversorUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistroService {
    private final RegistroRepository registroRepository;
    private final ConversorUtil conversorUtil;

    private final String COLUNA_DATA_HORA = "Data e hora";
    private final String COLUNA_LATITUDE = "Latitude";
    private final String COLUNA_LONGITUDE = "Longitude";
    private final String COLUNA_ALTITUDE = "Altitude";
    private final String COLUNA_VELOCIDADE = "Velocidade";
    private final String COLUNA_SENTIDO = "Sentido";

    public RegistroService(RegistroRepository registroRepository, ConversorUtil conversorUtil) {
        this.registroRepository = registroRepository;
        this.conversorUtil = conversorUtil;
    }

    public Map<String, Object> getInfos(Integer idPonto, List<String> idSensores) {
        Map<String, Object> retorno = new HashMap<>();
        List<String> colunas = new ArrayList<>();
        List<Map<String, String>> registros = new ArrayList<>();

        colunas.add(COLUNA_DATA_HORA);

        if (idSensores.contains("1")) {
            addGps(colunas, null, null);
        }

        for (Registro registro : listAllByPontoId(idPonto)) {
            Map<String, String> mapRegistro = new HashMap<>();
            mapRegistro.put(COLUNA_DATA_HORA, conversorUtil.getSqlTimestampToString(registro.getDataHora()));

            if (idSensores.contains("1")) {
                addGps(colunas, mapRegistro, registro);
            }

            registros.add(mapRegistro);
        }

        retorno.put("colunas", colunas);
        retorno.put("registros", registros);
        return retorno;
    }

    private List<Registro> listAllByPontoId(Integer id) {
        List<Registro> registros = (List<Registro>) registroRepository.findAll();
        registros.removeIf(registro -> !registro.getPonto().getId().equals(id));
        return registros;
    }

    private void addGps(List<String> colunas, Map<String, String> mapRegistro, Registro registro) {
        if (colunas != null) {
            colunas.add(COLUNA_LATITUDE);
            colunas.add(COLUNA_LONGITUDE);
            colunas.add(COLUNA_ALTITUDE);
            colunas.add(COLUNA_VELOCIDADE);
            colunas.add(COLUNA_SENTIDO);
        }

        if (mapRegistro != null) {
            mapRegistro.put(COLUNA_LATITUDE, conversorUtil.getFloatToString(registro.getLatitude()));
            mapRegistro.put(COLUNA_LONGITUDE, conversorUtil.getFloatToString(registro.getLongitude()));
            mapRegistro.put(COLUNA_ALTITUDE, conversorUtil.getFloatToString(registro.getAltitude()));
            mapRegistro.put(COLUNA_VELOCIDADE, conversorUtil.getFloatToString(registro.getVelocidade()));
            mapRegistro.put(COLUNA_SENTIDO, conversorUtil.getFloatToString(registro.getSentido()));
        }
    }
}
