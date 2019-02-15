package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.GruposFornecedoresVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class GruposFornecedoresDAO extends AbstractDAO{
    
    /**
     *Salva os registros no banco de dados
     * @param oGrupo
     * @throws DAOException
     */
    public void save(GruposFornecedoresVO oGrupo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO GRUPOS_FORNECEDORES ("
                    + "GRUPO_FORNECEDOR, "
                    + "OBSERVACAO "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oGrupo.getNomeGrupo());
            ps.setString(2, oGrupo.getObservacao());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no save.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    /**
     *Retorna a lista completa de Grupos fornecedores
     * @return
     * @throws DAOException
     */
    public List<GruposFornecedoresVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM GRUPOS_FORNECEDORES ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<GruposFornecedoresVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigo = rs.getInt("CODIGO_GRUPO_FORNECEDOR");
                String nomeGrupo = rs.getString("GRUPO_FORNECEDOR");
                String obs = rs.getString("OBSERVACAO");
                
                GruposFornecedoresVO oGrupo = new GruposFornecedoresVO(codigo, nomeGrupo, obs);
                
                list.add(oGrupo);
            }
            return list;
            
        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    /**
     * Retorna o grupo forneceddor conforme o codigo grupo forn... informado.
     * @param codigoGrupo
     * @return
     * @throws DAOException
     */
    public List<GruposFornecedoresVO> findGrupo(Integer codigoGrupo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM GRUPOS_FORNECEDORES "
                    + "WHERE CODIGO_GRUPO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoGrupo);
            
            rs = ps.executeQuery();
            
            List<GruposFornecedoresVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigo = rs.getInt("CODIGO_GRUPO_FORNECEDOR");
                String nomeGrupo = rs.getString("GRUPO_FORNECEDOR");
                String obs = rs.getString("OBSERVACAO");
                
                GruposFornecedoresVO oGrupo = new GruposFornecedoresVO(codigo, nomeGrupo, obs);
                
                list.add(oGrupo);
            }
            return list;
            
        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(GruposFornecedoresVO oGrupo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE GRUPOS_FORNECEDORES "
                    + "SET "
                    + "GRUPO_FORNECEDOR = ?, "
                    + "OBSERVACAO = ? "
                    + "WHERE "
                    + "CODIGO_GRUPO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oGrupo.getNomeGrupo());
            ps.setString(2, oGrupo.getObservacao());
            ps.setInt(3, oGrupo.getCodigoGrupoFornecedor());
            
            ps.executeUpdate();            
            
        } catch (SQLException e) {
            throw new DAOException("Erro no update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(GruposFornecedoresVO oGrupo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "DELETE FROM GRUPOS_FORNECEDORES "
                    + "WHERE CODIGO_GRUPO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oGrupo.getCodigoGrupoFornecedor());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("erro no delete.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
