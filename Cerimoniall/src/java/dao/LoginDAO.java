package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Login;
import util.ConnectionFactory;
import util.exception.ErroSistema;

public class LoginDAO {
    
    public Integer autenticar(Login lg)throws ErroSistema{
        String sql = "SELECT * FROM cerimonial.PESSOA WHERE DSC_EMAIL=? AND DSC_SENHA=?";
        try {
            Connection con = ConnectionFactory.getConexao();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, lg.getUser());
            ps.setString(2, lg.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return 1;
            }else{
                return -1;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        }
    }

    public Integer getSeqUsr(Login lg) throws ErroSistema{
        String sql = "SELECT SEQ_PESSOA FROM cerimonial.PESSOA WHERE DSC_EMAIL=? AND DSC_SENHA=?";
        try {
            Connection con = ConnectionFactory.getConexao();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, lg.getUser());
            ps.setString(2, lg.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("SEQ_PESSOA");
            }else{
                return -1;
            }
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar se logar!", ex);
        }
    }
}
