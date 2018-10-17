package beans;

import dao.PessoaDAO;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Pessoa;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@ManagedBean
@SessionScoped
public class PessoaController implements Serializable{
    private Pessoa pessoa = new Pessoa();
    private PessoaDAO pdao = new PessoaDAO();
    
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
    
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }
    
}
