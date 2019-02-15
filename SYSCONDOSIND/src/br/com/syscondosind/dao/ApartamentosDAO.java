package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.ApartamentosVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class ApartamentosDAO extends AbstractDAO{
    
    public void save(ApartamentosVO oApto) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO apartamentos ("
                    + "NOME_APARTAMENTO, "
                    + "COBERTURA "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oApto.getNomeApartamento());
            ps.setString(2, oApto.getPossuiCobertura());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao cadastrar\n"+ e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<ApartamentosVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM apartamentos "
                    + "ORDER BY NOME_APARTAMENTO ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<ApartamentosVO> list = new ArrayList<>();
            while (rs.next()) {                
                
                Integer codigoApto = rs.getInt("CODIGO_APARTAMENTO");
                String nomeApto = rs.getString("NOME_APARTAMENTO");
                String cobertura = rs.getString("COBERTURA");
                
                ApartamentosVO oApto = new ApartamentosVO(codigoApto, nomeApto, cobertura);
                list.add(oApto);
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar.\n"+ e.getMessage(), e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<ApartamentosVO> findApartamento(int codigoApartamento) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                    + "FROM apartamentos "
                    + "WHERE CODIGO_APARTAMENTO = ? "
                    + "ORDER BY NOME_APARTAMENTO ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoApartamento);
            
            rs = ps.executeQuery();
            
            List<ApartamentosVO> list = new ArrayList<>();
            while (rs.next()) {                
                
                Integer codigoApto = rs.getInt("CODIGO_APARTAMENTO");
                String nomeApto = rs.getString("NOME_APARTAMENTO");
                String cobertura = rs.getString("COBERTURA");
                
                ApartamentosVO oApto = new ApartamentosVO(codigoApto, nomeApto, cobertura);
                list.add(oApto);
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar.\n"+ e.getMessage(), e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(ApartamentosVO oApto) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "UPDATE apartamentos "
                    + "SET "
                    + "NOME_APARTAMENTO = ?, "
                    + "COBERTURA = ? "
                    + "WHERE "
                    + "CODIGO_APARTAMENTO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oApto.getNomeApartamento());
            ps.setString(2, oApto.getPossuiCobertura());
            ps.setInt(3, oApto.getCodigoApartamento());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar registro\n"+ e.getMessage(), e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(ApartamentosVO oApto) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "DELETE FROM apartamentos "
                    + "WHERE CODIGO_APARTAMENTO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oApto.getCodigoApartamento());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao deletar registro.\n" + e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
