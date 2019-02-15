package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.OperadorasVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class OperadorasDAO extends AbstractDAO{
    
    public void save(OperadorasVO oOper) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO operadoras ("
                    + "NOME_OPERADORA "
                    + ") "
                    + "VALUES ("
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oOper.getNomeOperadora());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao cadastrar.", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<OperadorasVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM operadoras ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<OperadorasVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoOperadora = rs.getInt("CODIGO_OPERADORA");
                String nomeOperadora = rs.getString("NOME_OPERADORA");
                
                OperadorasVO oOper = new OperadorasVO(codigoOperadora, nomeOperadora);
                
                list.add(oOper);
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<OperadorasVO> findOperadora(int codigoOperadora) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM operadoras "
                    + "WHERE CODIGO_OPERADORA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoOperadora);
            
            rs = ps.executeQuery();
            
            List<OperadorasVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoOperadora2 = rs.getInt("CODIGO_OPERADORA");
                String nomeOperadora = rs.getString("NOME_OPERADORA");
                
                OperadorasVO oOper = new OperadorasVO(codigoOperadora2, nomeOperadora);
                
                list.add(oOper);
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro no FindOperadora\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(OperadorasVO oOper) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "UPDATE operadoras "
                    + "SET "
                    + "NOME_OPERADORA = ? "
                    + "WHERE "
                    + "CODIGO_OPERADORA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oOper.getNomeOperadora());
            ps.setInt(2, oOper.getCodigoOperadora());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Update\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(OperadorasVO oOper) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM operadoras "
                    + "WHERE CODIGO_OPERADORA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oOper.getCodigoOperadora());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no delete\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
