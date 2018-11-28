package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pessoa {
    private Integer sequencial;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Boolean organizador = false;
    private String telefone;
    private List<ConvidadoEvento> convites = new ArrayList<>();

    public Integer getSequencial() {
        return sequencial;
    }

    public void setSequencial(Integer sequencial) {
        this.sequencial = sequencial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Boolean getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Boolean organizador) {
        this.organizador = organizador;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<ConvidadoEvento> getConvites() {
        return convites;
    }

    public void setConvites(List<ConvidadoEvento> convites) {
        this.convites = convites;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.sequencial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.sequencial, other.sequencial)) {
            return false;
        }
        return true;
    }
    
    
}
