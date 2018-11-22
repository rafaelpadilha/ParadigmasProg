package beans;

import dao.CEDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.ConvidadoEvento;
import util.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class CEController {
    private ConvidadoEvento ce = new ConvidadoEvento();
    private CEDAO cedao = new CEDAO();
    private List<ConvidadoEvento> ces = new ArrayList<>();
    ConvidadoEvento ceSelec;
    String textoBusca = "";
    String opBusca = "";
    
    //Dependendo de CEDAO
    void listar(){
        /*
        try {
            setCes(ces);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }*/
        
    }
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }

    public ConvidadoEvento getCe() {
        return ce;
    }

    public void setCe(ConvidadoEvento ce) {
        this.ce = ce;
    }

    public CEDAO getCedao() {
        return cedao;
    }

    public void setCedao(CEDAO cedao) {
        this.cedao = cedao;
    }

    public List<ConvidadoEvento> getCes() {
        return ces;
    }

    public void setCes(List<ConvidadoEvento> ces) {
        this.ces = ces;
    }

    public ConvidadoEvento getCeSelec() {
        return ceSelec;
    }

    public void setCeSelec(ConvidadoEvento ceSelec) {
        this.ceSelec = ceSelec;
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    public String getOpBusca() {
        return opBusca;
    }

    public void setOpBusca(String opBusca) {
        this.opBusca = opBusca;
    }
    
    
}
