package beans;

import dao.EventoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Evento;
import model.Pessoa;
import util.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class EventoController implements Serializable{
    private Evento evento = new Evento();
    private EventoDAO edao = new EventoDAO();
    private List<Evento> eventos = new ArrayList();
    String textoBusca = "";
    String opBusca = "";
    Evento eventoSelecionada;
    
    public void cadastra_evento(){
        try {
            edao.cadastrar(this.evento, 1);//Passar SEQ do usuario que 
            evento = new Evento();
            adicionarMensagem("Concluido!", "Evento cadastrado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    
    public void listar(){
        try {
            System.out.println("TESTEEE\n\n\n\n");
            setEventos(edao.buscar(this.getTextoBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    /*
    public void deletar(Pessoa p){
        try{
            pdao.apagar(p);
        }catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(Pessoa p){
        try{
            pdao.edita(p);
        }catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    */

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EventoDAO getEdao() {
        return edao;
    }

    public void setEdao(EventoDAO edao) {
        this.edao = edao;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
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

    public Evento getEventoSelecionada() {
        return eventoSelecionada;
    }

    public void setEventoSelecionada(Evento eventoSelecionada) {
        this.eventoSelecionada = eventoSelecionada;
    }
    
    
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
    
}
