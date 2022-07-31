package com.quess.geosync.beans.ponto;

import com.quess.geosync.beans.registro.Registro;
import com.quess.geosync.beans.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pontos")
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 45)
    private String nome;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany
    private List<Usuario> usuarios;

    @OneToMany
    private List<Registro> registros;

    @Column
    private String sensores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        if (nome == null) {
            return "";
        }

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSensores() {
        if (sensores == null) {
            return "";
        }
        else {
            return sensores;
        }
    }

    public void setSensores(String sensores) {
        this.sensores = sensores;
    }
}
