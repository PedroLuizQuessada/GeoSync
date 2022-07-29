package com.quess.geosync.beans.usuario;

import com.quess.geosync.beans.ponto.Ponto;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String senha;

    @Column(nullable = false, name = "tentativas_acesso")
    private Integer tentativasAcesso;

    @Column(nullable = false)
    private boolean adm;

    @ManyToOne
    private Ponto central;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTentativasAcesso() {
        return tentativasAcesso;
    }

    public void setTentativasAcesso(Integer tentativasAcesso) {
        this.tentativasAcesso = tentativasAcesso;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public Ponto getCentral() {
        if (central == null) {
            return new Ponto();
        }

        return central;
    }

    public void setCentral(Ponto central) {
        this.central = central;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tentativasAcesso=" + tentativasAcesso +
                '}';
    }
}
