
package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.EnderecoEletronicosVO;
import br.com.syscondosind.vo.VeiculosVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class EnderecosEletronicosDAO extends AbstractDAO{
    
    public void save(EnderecoEletronicosVO oEnd) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO enderecos_eletronicos ("
                    + "CODIGO_PESSOA, "
                    + "EMAIL, "
                    + "PAGINA_WEB, "
                    + "TIPO_EMAIL, "
                    + "STATUS "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oEnd.getCodigoPessoa());
            ps.setString(2, oEnd.getEmail());
            ps.setString(3, oEnd.getPaginaWeb());
            ps.setString(4, oEnd.getTipoEmail());
            ps.setString(5, oEnd.getStatus());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no save\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<EnderecoEletronicosVO> findEmailPessoa(int codigoPessoa) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT E.CODIGO_ENDERECO_ELETRONICO, E.CODIGO_PESSOA, P.NOME_PESSOA, E.EMAIL, E.PAGINA_WEB, "
                    + "E.TIPO_EMAIL, E.STATUS "
                    + "FROM enderecos_eletronicos E "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = E.CODIGO_PESSOA "
                    + "WHERE E.CODIGO_PESSOA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoPessoa);
            
            rs = ps.executeQuery();
            
            List<EnderecoEletronicosVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                EnderecoEletronicosVO oEnd = new EnderecoEletronicosVO(rs.getInt("CODIGO_ENDERECO_ELETRONICO"), 
                        rs.getInt("CODIGO_PESSOA"), 
                        rs.getString("EMAIL"), 
                        rs.getString("PAGINA_WEB"), 
                        rs.getString("TIPO_EMAIL"), 
                        rs.getString("STATUS")
                );
                
                list.add(oEnd);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findEmailPessoa\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<EnderecoEletronicosVO> findEmailPessoaGetField(int codigoPessoa, int codigoEmail) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT E.CODIGO_ENDERECO_ELETRONICO, E.CODIGO_PESSOA, P.NOME_PESSOA, E.EMAIL, E.PAGINA_WEB, "
                    + "E.TIPO_EMAIL, E.STATUS "
                    + "FROM enderecos_eletronicos E "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = E.CODIGO_PESSOA "
                    + "WHERE E.CODIGO_PESSOA = ? "
                    + "AND E.CODIGO_ENDERECO_ELETRONICO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoPessoa);
            ps.setInt(2, codigoEmail);
            
            rs = ps.executeQuery();
            
            List<EnderecoEletronicosVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                EnderecoEletronicosVO oEnd = new EnderecoEletronicosVO(rs.getInt("CODIGO_ENDERECO_ELETRONICO"), 
                        rs.getInt("CODIGO_PESSOA"), 
                        rs.getString("EMAIL"), 
                        rs.getString("PAGINA_WEB"), 
                        rs.getString("TIPO_EMAIL"), 
                        rs.getString("STATUS")
                );
                
                list.add(oEnd);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findEmailPessoa\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(EnderecoEletronicosVO oEnd) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE enderecos_eletronicos "
                    + "SET "
                    + "CODIGO_PESSOA = ?, "
                    + "EMAIL = ?, "
                    + "PAGINA_WEB = ?, "
                    + "TIPO_EMAIL = ?, "
                    + "STATUS = ? "
                    + "WHERE "
                    + "CODIGO_ENDERECO_ELETRONICO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oEnd.getCodigoPessoa());
            ps.setString(2, oEnd.getEmail());
            ps.setString(3, oEnd.getPaginaWeb());
            ps.setString(4, oEnd.getTipoEmail());
            ps.setString(5, oEnd.getStatus());
            ps.setInt(6, oEnd.getCodigoEnderecoEletronico());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro no update\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(EnderecoEletronicosVO oEnd) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "DELETE FROM enderecos_eletronicos "
                    + "WHERE CODIGO_ENDERECO_ELETRONICO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oEnd.getCodigoEnderecoEletronico());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no DELETE.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }    
}
