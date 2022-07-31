package com.quess.geosync.beans.registro;

import com.quess.geosync.beans.ponto.Ponto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "registros")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_hora", nullable = false)
    private Timestamp dataHora;

    @ManyToOne
    private Ponto ponto;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column
    private Float altitude;

    @Column
    private Float velocidade;

    @Column
    private Float sentido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Float velocidade) {
        this.velocidade = velocidade;
    }

    public Float getSentido() {
        return sentido;
    }

    public void setSentido(Float sentido) {
        this.sentido = sentido;
    }
}
