package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Pessoa;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class PessoaDAO {
    public void cadastrar(Pessoa p) throws ErroSistema{
        String sql = "INSERT INTO CERIMONIAL.PESSOA(NOM_PESSOA, NUM_CPF, DSC_EMAIL,FLG_ATIVO, DSC_SENHA,NUM_TELEFONE,FLG_ORGANIZADOR) " + 
                "VALUES(?,?,?,'S',1234,?,?)";
        try {
            Connection con = ConnectionFactory.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getTelefone());
            if(p.getOrganizador() == true){
                ps.setString(5, "S");
            }else{
                ps.setString(5, "N");
            }
            ps.execute();
            ConnectionFactory.fechaConexao();

        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        }
    }
}
