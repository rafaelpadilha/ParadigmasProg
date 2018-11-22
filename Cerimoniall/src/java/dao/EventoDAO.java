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

    public void cadastrar(Evento e, Integer seqPessoa) throws ErroSistema {
        String sql = "insert into cerimonial.evento(nom_evento, dsc_local, dsc_evento, seq_pessoa_encarregada, flg_ativo, dat_evento)\n"
                + "values(?,?,?, ?, 'S',?)";
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
            throw new ErroSistema("Erro ao tentar cadastrar evento!(SQLE)", ex);
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
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
    }

    //Testar
    public Evento buscar1(Integer sequencial) throws ErroSistema {
        String sql = "select * from cerimonial.evento where flg_ativo = 'S' and SEQ_EVENTO = " + sequencial.toString();
        Evento e = new Evento();
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            }

            ConnectionFactory.fechaConexao();
            return e;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
    }

    public void apagar(Evento e) throws ErroSistema {
        String sql = "update cerimonial.evento set FLG_ATIVO = 'N' where SEQ_EVENTO = " + e.getSequencial().toString();
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao apagar o evento!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao apagar o evento!(SQLE)", ex);
        }
        ConnectionFactory.fechaConexao();
    }

    public void edita(Evento e) throws ErroSistema {
        /*
    private String sequencial;
    private String nome;
    private String local;
    private String descricao;
    private Date data;
    private String seqResp;
    private Boolean ativo;
         */
        String sql = "update cerimonial.evento set NOM_EVENTO = ?,DSC_LOCAL = ?, DSC_EVENTO = ?,SEQ_PESSOA_ENCARREGADA=?,DAT_EVENTO=? where SEQ_EVENTO = " + e.getSequencial();
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, e.getNome());
            ps.setString(2, e.getLocal());
            ps.setString(3, e.getDescricao());
            ps.setInt(4, Integer.parseInt(e.getSeqResp()));
            ps.setDate(5, new Date(e.getData().getTime()));
            ps.execute();
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao editar o evento!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao editar o evento!(SQLE)", ex);
        }
        ConnectionFactory.fechaConexao();
    }
}
