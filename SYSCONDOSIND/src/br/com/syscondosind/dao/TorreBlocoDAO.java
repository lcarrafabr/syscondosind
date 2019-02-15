package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TorresVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class TorreBlocoDAO extends AbstractDAO{
    
    public void save(TorresVo oTorre) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "INSERT INTO torres ("
                    + "NOME_TORRE,      "
                    + "POSSUI_ELEVADOR  "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oTorre.getNome_torre());
            ps.setString(2, oTorre.getPossuiElevador());
            
            ps.executeUpdate();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao cadastrar", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<TorresVo> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM torres "
                    + "ORDER BY NOME_TORRE ";
            
            ps = cn.prepareStatement(comando);
            
           rs = ps.executeQuery();
           
           List<TorresVo> list = new ArrayList<>();
           
            while (rs.next()) {
                
                Integer codigoTorre = rs.getInt("CODIGO_TORRE");
                String nomeTorre = rs.getString("NOME_TORRE");
                String possuiElevador = rs.getString("POSSUI_ELEVADOR");
                
                TorresVo oTorre = new TorresVo(nomeTorre, possuiElevador);
                oTorre.setCodigoTorre(codigoTorre);
                
                list.add(oTorre);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao consultar Torres/Blocos", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<TorresVo> findTorreBloco(Integer codigoTorre) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM torres "
                    + "WHERE CODIGO_TORRE = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoTorre);
            
           rs = ps.executeQuery();
           
           List<TorresVo> list = new ArrayList<>();
           
            while (rs.next()) {
                
                
                String nomeTorre = rs.getString("NOME_TORRE");
                String possuiElevador = rs.getString("POSSUI_ELEVADOR");
                
                TorresVo oTorre = new TorresVo(nomeTorre, possuiElevador);
                oTorre.setCodigoTorre(codigoTorre);
                
                list.add(oTorre);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao consultar Torres/Blocos", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(TorresVo oTorre) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE torres "
                    + "SET "
                    + "NOME_TORRE = ?, "
                    + "POSSUI_ELEVADOR = ? "
                    + "WHERE "
                    + "CODIGO_TORRE = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oTorre.getNome_torre());
            ps.setString(2, oTorre.getPossuiElevador());
            ps.setInt(3, oTorre.getCodigoTorre());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar registro", e);
        }finally{
            closeResources(cn, ps, null);
        } 
    }
    
    public void delete(TorresVo oTorre) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comand = "DELETE FROM torres "
                    + "WHERE CODIGO_TORRE = ? ";
            
            ps = cn.prepareStatement(comand);
            
            ps.setInt(1, oTorre.getCodigoTorre());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao excluir registro", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
