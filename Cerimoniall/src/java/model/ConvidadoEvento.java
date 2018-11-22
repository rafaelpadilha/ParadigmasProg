
package model;

import java.util.Date;
import java.util.Objects;

public class ConvidadoEvento {
    private Integer sequencial;
    private Pessoa convidado = new Pessoa();
    private Evento evento = new Evento();
    private Boolean confirmado;
    private Date datConvite;

    public Integer getSequencial() {
        return sequencial;
    }

    public void setSequencial(Integer sequencial) {
        this.sequencial = sequencial;
    }

    public Pessoa getConvidado() {
        return convidado;
    }

    public void setConvidado(Pessoa convidado) {
        this.convidado = convidado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Date getDatConvite() {
        return datConvite;
    }

    public void setDatConvite(Date datConvite) {
        this.datConvite = datConvite;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.sequencial);
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
        final ConvidadoEvento other = (ConvidadoEvento) obj;
        if (!Objects.equals(this.sequencial, other.sequencial)) {
            return false;
        }
        return true;
    }
    
    
}
