package beans;

import dao.PessoaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Pessoa;
import util.exception.ErroSistema;

@ManagedBean
@SessionScoped
public class PessoaController implements Serializable{
    private Pessoa pessoa = new Pessoa();
    private PessoaDAO pdao = new PessoaDAO();
    private List<Pessoa> pessoas = new ArrayList();
    String textoBusca = "";
    String opBusca = "";
    Pessoa pessoaSelecionada;
    
    public void cadastra_pessoa(){
        try {
            System.out.println("TESTE");
            pdao.cadastrar(this.pessoa);
            pessoa = new Pessoa();
            adicionarMensagem("Concluido!", "Pessoa cadastrado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void listar(){
        try {
            setPessoas(pdao.buscar(this.getTextoBusca(), this.getOpBusca()));
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
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
