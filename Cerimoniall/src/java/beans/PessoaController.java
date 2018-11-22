package beans;

import dao.PessoaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Evento;
import model.Pessoa;
import org.primefaces.model.DualListModel;
import util.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class PessoaController implements Serializable {

    private Pessoa pessoa = new Pessoa();
    private PessoaDAO pdao = new PessoaDAO();
    private List<Pessoa> pessoas = new ArrayList();
    private DualListModel<Pessoa> listPessoas;
    String textoBusca = "";
    String opBusca = "";
    Pessoa pessoaSelecionada;

    public void cadastra_pessoa() {
        try {
            pdao.cadastrar(this.pessoa);
            pessoa = new Pessoa();
            adicionarMensagem("Concluido!", "Pessoa cadastrado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listar() {
        try {
            setPessoas(pdao.buscar(this.getTextoBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Pessoa p) {
        try {
            pdao.apagar(p);
            adicionarMensagem("Concluido!", "Pessoa deletada com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Pessoa p) {
        try {
            pdao.edita(p);
            adicionarMensagem("Concluido!", "Pessoa editada com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void adicionarConvidados(Evento e){
        try {
            List<Pessoa> tmp = pdao.selectConvidados(e);
            List<Pessoa> tmp_t = listPessoas.getTarget();
            for(Pessoa p: tmp_t){
                if(!tmp.contains(p)){
                    pdao.insertConvidados(p, e);
                }
            }
            tmp = pdao.selectConvidados(e);
            List<Pessoa> tmp_s = listPessoas.getSource();
            for(Pessoa p: tmp_s){
                if(tmp.contains(p)){
                    pdao.cancelaConvidado(p, e);
                }
            }
            adicionarMensagem("Concluido!", "Convidados adicionados com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    //Dando Erro
    public void buscaConvidados(Evento e){
        try {
            List<Pessoa> tmp = pdao.selectConvidados(e);
            listPessoas.setTarget(tmp);
            List<Pessoa> tmp_s = listPessoas.getSource();
            for(Pessoa p:tmp){
                if(tmp_s.contains(p)){
                    tmp_s.remove(p);
                }
            }
            listPessoas.setSource(tmp_s);
           
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void teste(){
        System.out.println("SRC:" + this.listPessoas.getSource());
        System.out.println("DST:" + this.listPessoas.getTarget());
    }

    public void init() {
        //initial unselected list
        this.listar();
        List<Pessoa> sourceList = this.getPessoas();

        //initial selected list
        List<Pessoa> destinationList = new ArrayList<>();

        listPessoas = new DualListModel<>(new ArrayList<>(sourceList), destinationList);
    }

    public DualListModel<Pessoa> getListPessoas() {
        return listPessoas;
    }

    public void setListPessoas(DualListModel<Pessoa> listPessoas) {
        this.listPessoas = listPessoas;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaDAO getPdao() {
        return pdao;
    }

    public void setPdao(PessoaDAO pdao) {
        this.pdao = pdao;
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public String getOpBusca() {
        return opBusca;
    }

    public void setOpBusca(String opBusca) {
        this.opBusca = opBusca;
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }

}
