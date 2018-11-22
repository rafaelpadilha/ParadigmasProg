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

//Fazendo
public class CEDAO {
    private EventoDAO edao = new EventoDAO();
    private PessoaDAO pdao = new PessoaDAO();

    public List<ConvidadoEvento> buscar(String texto, String op) throws ErroSistema {
        List<ConvidadoEvento> eventos = new ArrayList<>();
        String sql;
        if (texto.isEmpty()) {
            sql = "SELECT \n"
                    + "CE.SEQ_CONVIDADO_EVENTO,\n"
                    + "CE.SEQ_PESSOA,\n"
                    + "CE.SEQ_EVENTO,\n"
                    + "P.NOM_PESSOA,\n"
                    + "E.NOM_EVENTO,\n"
                    + "E.DAT_EVENTO,\n"
                    + "CE.FLG_CONFIRMADO,\n"
                    + "CE.DAT_CONVITE_EMITIDO\n"
                    + "FROM CERIMONIAL.CONVIDADO_EVENTO CE\n"
                    + "INNER JOIN CERIMONIAL.EVENTO E USING(SEQ_EVENTO)\n"
                    + "INNER JOIN CERIMONIAL.PESSOA P USING(SEQ_PESSOA)\n"
                    + "WHERE CE.FLG_ATIVO = 'S'";
        } else {
            sql = "SELECT \n"
                    + "CE.SEQ_CONVIDADO_EVENTO,\n"
                    + "CE.SEQ_PESSOA,\n"
                    + "CE.SEQ_EVENTO,\n"
                    + "P.NOM_PESSOA,\n"
                    + "E.NOM_EVENTO,\n"
                    + "E.DAT_EVENTO,\n"
                    + "CE.FLG_CONFIRMADO,\n"
                    + "CE.DAT_CONVITE_EMITIDO\n"
                    + "FROM CERIMONIAL.CONVIDADO_EVENTO CE\n"
                    + "INNER JOIN CERIMONIAL.EVENTO E USING(SEQ_EVENTO)\n"
                    + "INNER JOIN CERIMONIAL.PESSOA P USING(SEQ_PESSOA)\n"
                    + "WHERE CE.FLG_ATIVO = 'S' AND"
                    + op + " like \'%" + texto + "%\'";
        }
        try {
            Connection conexao = ConnectionFactory.getConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConvidadoEvento ce = new ConvidadoEvento();
                String org = null;
                Evento tmp_e = new Evento();
                Pessoa tmp_p = new Pessoa();
                ce.setSequencial(rs.getInt("CE.SEQ_CONVIDADO_EVENTO"));
                tmp_e.setSequencial(rs.getString("CE.SEQ_EVENTO"));
                tmp_p.setSequencial(rs. getInt("CE.SEQ_PESSOA"));
                /*
                ce.setSequencial(rs.getString("SEQ_EVENTO"));
                ce.setSeqResp(rs.getString("SEQ_PESSOA_ENCARREGADA"));
                ce.setNome(rs.getString("NOM_EVENTO"));
                ce.setLocal(rs.getString("DSC_LOCAL"));
                ce.setDescricao(rs.getString("DSC_EVENTO"));
                org = rs.getString("FLG_ATIVO");
                if (org.equals("S")) {
                    ce.setAtivo(TRUE);
                } else {
                    ce.setAtivo(FALSE);
                }
                ce.setData(rs.getDate("DAT_EVENTO"));
                
                eventos.add(ce);
                 */
            }
            ConnectionFactory.fechaConexao();
            return eventos;
        } catch (ErroSistema ex) {
            throw new ErroSistema("Erro ao listar eventos!", ex);
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar eventos!(SQLE)", ex);
        }
    }
}
