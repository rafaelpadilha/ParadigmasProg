package beans;

import dao.LoginDAO;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Login;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private Login login = new Login();
    private LoginDAO ldao = new LoginDAO();

    public String logar() {
        try {
            if (ldao.autenticar(login) == 1) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("usuario", login);
                return "/app/index?faces-redirect=true";
            } else {
                adicionarMensagem("Falha no login!", "Verifique se as informações estão corretas", FacesMessage.SEVERITY_WARN);
                //return "/security/login?faces-redirect=true";
                return "";
            }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return "";
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/security/login?faces-redirect=true";
    }


    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public LoginDAO getLdao() {
        return ldao;
    }

    public void setLdao(LoginDAO ldao) {
        this.ldao = ldao;
    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext contex = FacesContext.getCurrentInstance();
        FacesMessage mensage = new FacesMessage(tipoErro, sumario, detalhe);
        contex.addMessage(null, mensage);

    }

}
