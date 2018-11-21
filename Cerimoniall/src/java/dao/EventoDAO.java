/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Evento;
import model.Pessoa;
import util.ConnectionFactory;
import util.exception.ErroSistema;

/**
 *
 * @author alunotgn
 */
public class EventoDAO {
    public void cadastrar(Evento e,Integer seqPessoa) throws ErroSistema {
        String sql = "insert into cerimonial.evento(nom_evento, dsc_local, dsc_evento, seq_pessoa_encarregada, flg_ativo, dat_evento)\n" +
"values(?,?,?, ?, 1,?)";
        try {
            Connection con = ConnectionFactory.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, e.getNome());
            ps.setString(2, e.getLocal());
            ps.setString(3, e.getDescricao());
            ps.setInt(4, seqPessoa);
            ps.setDate(5, new Date(e.getData().getTime()));
            ps.execute();
            ConnectionFactory.fechaConexao();

        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao tentar cadastrar evento!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar cadastrar evento!", ex);
        }
    }

    public List<Evento> buscar(String texto, String op) throws ErroSistema {
        List<Evento> eventos = new ArrayList<>();
        String sql;
        if (texto.isEmpty()) {
            sql = "select * from cerimonial.evento where flg_ativo = 'S'";
        } else {
            sql = "select * from cerimonial.evento where " + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento e = new Evento();
                String org = null;
                e.setSequencial(rs.getString("SEQ_EVENTO"));
                e.setSeqResp(rs.getString("SEQ_PESSOA_ENCARREGADA"));
                e.setNome(rs.getString("NOM_EVENTO"));
                e.setLocal(rs.getString("DSC_LOCAL"));
                e.setDescricao(rs.getString("DSC_EVENTO"));
                org = rs.getString("FLG_ATIVO");
                if (org.equals("S")) {
                    e.setAtivo(TRUE);
                } else {
                    e.setAtivo(FALSE);
                }
                e.setData(rs.getDate("DAT_EVENTO"));
                
                eventos.add(e);
            }
            ConnectionFactory.fechaConexao();
            return eventos;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar pessoas!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar pessoas!(SQLE)", ex);
        }
    }
    
    public void apagar(Evento e) throws ErroSistema{
        String sql = "update cerimonial.evento set FLG_ATIVO = 'N' where SEQ_EVENTO = " + e.getSequencial().toString();
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
