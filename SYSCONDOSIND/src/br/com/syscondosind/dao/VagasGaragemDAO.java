package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.VagasGaragemVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class VagasGaragemDAO extends AbstractDAO{
    
    public void save(VagasGaragemVO oVaga) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO vagas_garagem ("
                    + "CODIGO_SETOR_GARAGEM, "
                    + "NOME_VAGA, "
                    + "CODIGO, "
                    + "STATUS, "
                    + "OBSERVACAO "
                    + ")"
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVaga.getCodigoSetor());
            ps.setString(2, oVaga.getNomeVaga());
            ps.setString(3, oVaga.getCodigo());
            ps.setString(4, oVaga.getStatus());
            ps.setString(5, oVaga.getObservacao());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Save.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<VagasGaragemVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.CODIGO_VAGA_GARAGEM, V.CODIGO_SETOR_GARAGEM, S.NOME_SETOR, "
                    + "V.NOME_VAGA, V.CODIGO, V.STATUS, V.OBSERVACAO "
                    + "FROM vagas_garagem V "
                    + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                    + "ORDER BY V.CODIGO_VAGA_GARAGEM ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<VagasGaragemVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setCodigoVagaGaragem(rs.getInt("CODIGO_VAGA_GARAGEM"));
                oVaga.setCodigoSetor(rs.getInt("CODIGO_SETOR_GARAGEM"));
                oVaga.setNomeSetor(rs.getString("NOME_SETOR"));
                oVaga.setNomeVaga(rs.getString("NOME_VAGA"));
                oVaga.setCodigo(rs.getString("CODIGO"));
                oVaga.setStatus(rs.getString("STATUS"));
                oVaga.setObservacao(rs.getString("OBSERVACAO"));
                
                list.add(oVaga);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<VagasGaragemVO> findResultadosPorStatus() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT COUNT(NOME_VAGA) AS QTD_TOTAL, "
                    + "(SELECT COUNT(NOME_VAGA) FROM vagas_garagem WHERE STATUS = 'LIBERADO') AS QTD_LIBERADO, "
                    + "(SELECT COUNT(NOME_VAGA) FROM vagas_garagem WHERE STATUS = 'MANUTENÇÃO') AS QTD_MANUTENCAO, "
                    + "(SELECT COUNT(NOME_VAGA) FROM vagas_garagem WHERE STATUS = 'INTERDITADO') AS QTD_INTERDITADO "
                    + "FROM vagas_garagem ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<VagasGaragemVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setQtdTotal(rs.getInt("QTD_TOTAL"));
                oVaga.setQtdLiberado(rs.getInt("QTD_LIBERADO"));
                oVaga.setQtdManutencao(rs.getInt("QTD_MANUTENCAO"));
                oVaga.setQtdInterditado(rs.getInt("QTD_INTERDITADO"));
                
                list.add(oVaga);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findResultadosPorStatus.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    
    public List<VagasGaragemVO> findResultadosGeraisGaragem() throws DAOException{
        
        /**** Esta consulta é usada no form garagemMorador representando o quadro de informações *****/
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT COUNT(NOME_VAGA) AS QTD_VAGAS_TOTAL, "
                    + "(SELECT count(V.CODIGO_VAGA_GARAGEM) FROM vagas_garagem V "
                    + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                    + "LEFT JOIN garagem_morador G ON G.CODIGO_VAGA_GARAGEM = V.CODIGO_VAGA_GARAGEM "
                    + "WHERE G.CODIGO_VAGA_GARAGEM IS NULL ) AS QTD_DISPONIVEL, "
                    + ""
                    + "(SELECT count(V.CODIGO_VAGA_GARAGEM) FROM vagas_garagem V "
                    + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                    + "LEFT JOIN garagem_morador G ON G.CODIGO_VAGA_GARAGEM = V.CODIGO_VAGA_GARAGEM "
                    + "WHERE G.CODIGO_VAGA_GARAGEM IS NOT NULL ) AS QTD_OCUPADA, "
                    + ""
                    + "(SELECT COUNT(M.CODIGO_MORADOR) "
                    + "FROM moradores M "
                    + "LEFT JOIN garagem_morador G ON G.CODIGO_PESSOA_MORADOR = M.CODIGO_PESSOA "
                    + "WHERE G.CODIGO_PESSOA_MORADOR IS NULL) AS QTD_PESSOAS_SEM_VAGA, "
                    + ""
                    + "(SELECT  COUNT(DISTINCT P.NOME_PESSOA)  "
                    + "FROM veiculos V "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR) AS TOTAL_COM_VEICULO, "
                    + ""
                    + "(SELECT COUNT(P.NOME_PESSOA) "
                    + "FROM veiculos V "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR) AS TOTAL_PESSOAS, "
                    + "(SELECT COUNT(P.NOME_PESSOA) / COUNT(DISTINCT P.NOME_PESSOA) "
                    + "FROM veiculos V "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR) AS MEDIA "
                    + ""
                    + "FROM vagas_garagem; ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<VagasGaragemVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setQtdVagasTotal(rs.getInt("QTD_VAGAS_TOTAL"));
                oVaga.setQtdVagaDisponivel(rs.getInt("QTD_DISPONIVEL"));
                oVaga.setQtdvagaOcupada(rs.getInt("QTD_OCUPADA"));
                oVaga.setQtdPessoasSemVaga(rs.getInt("QTD_PESSOAS_SEM_VAGA"));
                oVaga.setMediaCarrosMorador(rs.getDouble("MEDIA"));
                
                list.add(oVaga);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findResultadosGeraisGaragem.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    
    public List<VagasGaragemVO> findVaga(int codigoVaga) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.CODIGO_VAGA_GARAGEM, V.CODIGO_SETOR_GARAGEM, S.NOME_SETOR, "
                    + "V.NOME_VAGA, V.CODIGO, V.STATUS, V.OBSERVACAO "
                    + "FROM vagas_garagem V "
                    + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                    + "WHERE V.CODIGO_VAGA_GARAGEM = ? "
                    + "ORDER BY V.CODIGO_VAGA_GARAGEM ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoVaga);
            
            rs = ps.executeQuery();
            
            List<VagasGaragemVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setCodigoVagaGaragem(rs.getInt("CODIGO_VAGA_GARAGEM"));
                oVaga.setCodigoSetor(rs.getInt("CODIGO_SETOR_GARAGEM"));
                oVaga.setNomeSetor(rs.getString("NOME_SETOR"));
                oVaga.setNomeVaga(rs.getString("NOME_VAGA"));
                oVaga.setCodigo(rs.getString("CODIGO"));
                oVaga.setStatus(rs.getString("STATUS"));
                oVaga.setObservacao(rs.getString("OBSERVACAO"));
                
                list.add(oVaga);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<VagasGaragemVO> findVagasDisponiveis() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.CODIGO_VAGA_GARAGEM, V.CODIGO_SETOR_GARAGEM, S.NOME_SETOR, "
                    + "V.NOME_VAGA, V.CODIGO, V.STATUS, V.OBSERVACAO, G.CODIGO_VAGA_GARAGEM "
                    + "FROM vagas_garagem V "
                    + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                    + "LEFT JOIN garagem_morador G ON G.CODIGO_VAGA_GARAGEM = V.CODIGO_VAGA_GARAGEM "
                    + "WHERE G.CODIGO_VAGA_GARAGEM IS NULL ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<VagasGaragemVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setCodigoVagaGaragem(rs.getInt("CODIGO_VAGA_GARAGEM"));
                oVaga.setCodigoSetor(rs.getInt("CODIGO_SETOR_GARAGEM"));
                oVaga.setNomeSetor(rs.getString("NOME_SETOR"));
                oVaga.setNomeVaga(rs.getString("NOME_VAGA"));
                oVaga.setCodigo(rs.getString("CODIGO"));
                oVaga.setStatus(rs.getString("STATUS"));
                oVaga.setObservacao(rs.getString("OBSERVACAO"));
                
                list.add(oVaga);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(VagasGaragemVO oVaga) throws DAOException{
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE vagas_garagem "
                    + "SET "
                    + "CODIGO_SETOR_GARAGEM = ?, "
                    + "NOME_VAGA = ?, "
                    + "CODIGO = ?, "
                    + "STATUS = ?, "
                    + "OBSERVACAO = ? "
                    + "WHERE "
                    + "CODIGO_VAGA_GARAGEM = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVaga.getCodigoSetor());
            ps.setString(2, oVaga.getNomeVaga());
            ps.setString(3, oVaga.getCodigo());
            ps.setString(4, oVaga.getStatus());
            ps.setString(5, oVaga.getObservacao());
            ps.setInt(6, oVaga.getCodigoVagaGaragem());
            
            ps.executeUpdate();            
            
        } catch (Exception e) {
            throw new DAOException("Erro no update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(VagasGaragemVO oVaga) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM vagas_garagem "
                    + "WHERE CODIGO_VAGA_GARAGEM = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVaga.getCodigoVagaGaragem());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no delete.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
}
