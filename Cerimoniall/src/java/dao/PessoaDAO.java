package dao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pessoa;
import util.ConnectionFactory;
import util.exception.ErroSistema;

public class PessoaDAO {

    public void cadastrar(Pessoa p) throws ErroSistema {
        String sql = "INSERT INTO CERIMONIAL.PESSOA(NOM_PESSOA, NUM_CPF, DSC_EMAIL,FLG_ATIVO, DSC_SENHA,NUM_TELEFONE,FLG_ORGANIZADOR) "
                + "VALUES(?,?,?,'S',1234,?,?)";
        try {
            Connection con = ConnectionFactory.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getTelefone());
            if (p.getOrganizador() == true) {
                ps.setString(5, "S");
            } else {
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

    public List<Pessoa> buscar(String texto, String op) throws ErroSistema {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql;
        if (texto.isEmpty()) {
            sql = "select * from cerimonial.pessoa where flg_ativo = 'S'";
        } else {
            sql = "select * from cerimonial.pessoa where " + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pessoa p = new Pessoa();
                String org = null;
                p.setSequencial(rs.getInt("SEQ_PESSOA"));
                p.setNome(rs.getString("NOM_PESSOA"));
                p.setCpf(rs.getString("NUM_CPF"));
                p.setEmail(rs.getString("DSC_EMAIL"));
                p.setSenha(rs.getString("DSC_SENHA"));
                org = rs.getString("FLG_ORGANIZADOR");
                if (org.equals("S")) {
                    p.setOrganizador(TRUE);
                } else {
                    p.setOrganizador(FALSE);
                }
                p.setTelefone(rs.getString("NUM_TELEFONE"));
                pessoas.add(p);
            }
            ConnectionFactory.fechaConexao();
            return pessoas;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar pessoas!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar pessoas!(SQLE)", ex);
        }
    }
    
    public void apagar(Pessoa p) throws ErroSistema{
        String sql = "update pessoa set FLG_ATIVO = 'N' where SEQ_PESSOA = " + p.getSequencial().toString();
        try{
        Connection conexao = ConnectionFactory.getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.execute();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao apagar a pessoa!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao apagar a pessoa!(SQLE)", ex);
        }
        ConnectionFactory.fechaConexao();
    }
    
    public void edita(Pessoa p) throws ErroSistema{
//        String nome;
//    String cpf;
//    String email;
//    String senha;
//    Boolean organizador = false;
//    String telefone;
        String sql = "update pessoa set NOM_PESSOA = ?,NUM_CPF = ?,DSC_EMAIL=?,FLG_ORGANIZADOR=?,NUM_TELEFONE = ? where SEQ_PESSOA = " + p.getSequencial().toString();
        try{
        Connection conexao = ConnectionFactory.getConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getCpf());
        ps.setString(3, p.getEmail());
        if(p.getOrganizador()==true){
            ps.setString(4, "S");
        }else{
            ps.setString(4, "N");
        }
        ps.setString(5, p.getTelefone());
        
        ps.execute();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao editar a pessoa!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar a pessoa!(SQLE)", ex);
        }
        ConnectionFactory.fechaConexao();
    }
}
