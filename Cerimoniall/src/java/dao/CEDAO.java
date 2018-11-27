package dao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConvidadoEvento;
import model.Evento;
import model.Pessoa;
import util.ConnectionFactory;
import util.exception.ErroSistema;

public class CEDAO {

    EventoDAO edao = new EventoDAO();
    PessoaDAO pdao = new PessoaDAO();

    public List<ConvidadoEvento> buscar(String texto, String op) throws ErroSistema {
        List<ConvidadoEvento> ces = new ArrayList<>();
        String sql;
        if (texto.isEmpty()) {
            sql = "SELECT \n"
                    + "CE.SEQ_CONVIDADO_EVENTO,\n"
                    + "CE.SEQ_PESSOA,\n"
                    + "CE.SEQ_EVENTO,\n"
                    + "CE.FLG_CONFIRMADO,\n"
                    + "CE.DAT_CONVITE_EMITIDO\n"
                    + "FROM CERIMONIAL.CONVIDADO_EVENTO CE\n"
                    + "WHERE CE.FLG_ATIVO = 'S'";
        } else {
            sql = "SELECT \n"
                    + "CE.SEQ_CONVIDADO_EVENTO,\n"
                    + "SEQ_PESSOA,\n"
                    + "CE.SEQ_EVENTO,\n"
                    + "CE.FLG_CONFIRMADO,\n"
                    + "CE.DAT_CONVITE_EMITIDO\n"
                    + "FROM CERIMONIAL.CONVIDADO_EVENTO CE\n"
                    + "WHERE CE.FLG_ATIVO = 'S' AND "
                    + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConvidadoEvento ce = new ConvidadoEvento();
                String org;
                Evento tmp_e;
                Pessoa tmp_p;
                
                ce.setSequencial(rs.getInt("CE.SEQ_CONVIDADO_EVENTO"));

                tmp_p = pdao.buscar1(rs.getInt("CE.SEQ_PESSOA"));
                ce.setConvidado(tmp_p);
                
                tmp_e = edao.buscar1(rs.getInt("CE.SEQ_EVENTO"));
                ce.setEvento(tmp_e);

                org = rs.getString("CE.FLG_CONFIRMADO");
                if (org.equals("S")) {
                    ce.setConfirmado(TRUE);
                } else {
                    ce.setConfirmado(FALSE);
                }

                ce.setDatConvite(rs.getDate("CE.DAT_CONVITE_EMITIDO"));
                
                ce.setStatusConvite(getSitConvite(ce.getSequencial()));
                
                ces.add(ce);
            }
            ConnectionFactory.fechaConexao();
            return ces;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
    }

    public String getSitConvite(Integer seq) throws ErroSistema {
        String sql = "SELECT DSC_SITUACAO_CONVITE FROM CERIMONIAL.SITUACAO_CONVITE WHERE SEQ_CONVIDADO_EVENTO = "
                + seq.toString() + " ORDER BY SEQ_SITUACAO_CONVITE DESC";
        
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(sql);
            if (rs.next()) {
                String ret = rs.getString(1);
                return ret;
            }
            
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
        return null;
    }
    
    public void apagar(ConvidadoEvento ce)throws ErroSistema{
        try{
            pdao.cancelaConvidado(ce.getConvidado(), ce.getEvento());
        }catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao apagar convidado!", ex);
        }
    }
    
    public void emitirConvite(ConvidadoEvento ce)throws ErroSistema{
        String sql = "UPDATE CERIMONIAL.CONVIDADO_EVENTO SET DAT_CONVITE_EMITIDO = CURDATE() WHERE SEQ_CONVIDADO_EVENTO = " + ce.getSequencial().toString();
        try{
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
            pdao.inserirSituacao(ce.getSequencial(), "Convite Emitido.");
            System.out.println("QQQQ");
            ConnectionFactory.fechaConexao();
        }catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao emitir convite!", ex);
        }catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
    }
}
