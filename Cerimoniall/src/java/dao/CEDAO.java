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

    private final EventoDAO edao = new EventoDAO();
    private final PessoaDAO pdao = new PessoaDAO();

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
                    + "CE.SEQ_PESSOA,\n"
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
                Pessoa tmp_p = new Pessoa();
                ce.setSequencial(rs.getInt("CE.SEQ_CONVIDADO_EVENTO"));

                tmp_e = edao.buscar1(rs.getInt("CE.SEQ_EVENTO"));
                ce.setEvento(tmp_e);
                System.out.println("\nGGGGGG\n");
                //tmp_p = pdao.buscar1(rs.getInt("CE.SEQ_PESSOA"));//Nao ta entrando ? 
                System.out.println("\nQQQQ\n");
                ce.setConvidado(tmp_p);

                org = rs.getString("CE.FLG_CONFIRMADO");
                if (org.equals("S")) {
                    ce.setConfirmado(TRUE);
                } else {
                    ce.setConfirmado(FALSE);
                }

                ce.setDatConvite(rs.getDate("CE.DAT_CONVITE_EMITIDO"));
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
                + seq.toString() + " ORDER BY DAT_ALTERACAO DESC";
        
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                ConnectionFactory.fechaConexao();
                return rs.getString(1);
            }
            
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
        return null;
    }
}
