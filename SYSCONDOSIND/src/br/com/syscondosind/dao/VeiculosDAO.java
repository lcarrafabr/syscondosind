package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
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
public class VeiculosDAO extends AbstractDAO{
    
    public void save(VeiculosVO oVeiculo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO veiculos ("
                    + "CODIGO_PESSOA_MORADOR, "
                    + "MARCA, "
                    + "MODELO, "
                    + "PLACA, "
                    + "ANO, "
                    + "COR "
                    + ")"
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVeiculo.getCodigoPessoa());
            ps.setString(2, oVeiculo.getMarca());
            ps.setString(3, oVeiculo.getModelo());
            ps.setString(4, oVeiculo.getPlaca());
            ps.setString(5, oVeiculo.getAno());
            ps.setString(6, oVeiculo.getCor());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Save\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<VeiculosVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.*, P.NOME_PESSOA "
                    + "FROM veiculos V "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<VeiculosVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoVeiculo = rs.getInt("CODIGO_VEICULO");
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA_MORADOR");
                String marca = rs.getString("MARCA");
                String modelo = rs.getString("MODELO");
                String placa = rs.getString("PLACA");
                String ano = rs.getString("ANO");
                String cor = rs.getString("COR");
                
                VeiculosVO oVeiculo = new VeiculosVO(codigoVeiculo, codigoPessoa, marca, modelo, placa, ano, cor);
                oVeiculo.setNomeMorador(rs.getString("NOME_PESSOA"));
                
                list.add(oVeiculo);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<VeiculosVO> findAllVeiculo(int codigoVeiculoPessoa) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.*, P.NOME_PESSOA "
                    + "FROM veiculos V "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR "
                    + "WHERE V.CODIGO_VEICULO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoVeiculoPessoa);
            
            rs = ps.executeQuery();
            
            List<VeiculosVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoVeiculo = rs.getInt("CODIGO_VEICULO");
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA_MORADOR");
                String marca = rs.getString("MARCA");
                String modelo = rs.getString("MODELO");
                String placa = rs.getString("PLACA");
                String ano = rs.getString("ANO");
                String cor = rs.getString("COR");
                
                VeiculosVO oVeiculo = new VeiculosVO(codigoVeiculo, codigoPessoa, marca, modelo, placa, ano, cor);
                oVeiculo.setNomeMorador(rs.getString("NOME_PESSOA"));
                
                list.add(oVeiculo);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<VeiculosVO> findAllVeiculoPessoa(int codigoPessoa2) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT V.*, P.NOME_PESSOA "
                    + "FROM veiculos V "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = V.CODIGO_PESSOA_MORADOR "
                    + "WHERE V.CODIGO_PESSOA_MORADOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoPessoa2);
            
            rs = ps.executeQuery();
            
            List<VeiculosVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoVeiculo = rs.getInt("CODIGO_VEICULO");
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA_MORADOR");
                String marca = rs.getString("MARCA");
                String modelo = rs.getString("MODELO");
                String placa = rs.getString("PLACA");
                String ano = rs.getString("ANO");
                String cor = rs.getString("COR");
                
                VeiculosVO oVeiculo = new VeiculosVO(codigoVeiculo, codigoPessoa, marca, modelo, placa, ano, cor);
                oVeiculo.setNomeMorador(rs.getString("NOME_PESSOA"));
                
                list.add(oVeiculo);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAll\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(VeiculosVO oVeiculo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE veiculos "
                    + "SET "
                    + "CODIGO_PESSOA_MORADOR = ?, "
                    + "MARCA = ?, "
                    + "MODELO = ?, "
                    + "PLACA = ?, "
                    + "ANO = ?, "
                    + "COR = ? "
                    + "WHERE "
                    + "CODIGO_VEICULO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVeiculo.getCodigoPessoa());
            ps.setString(2, oVeiculo.getMarca());
            ps.setString(3, oVeiculo.getModelo());
            ps.setString(4, oVeiculo.getPlaca());
            ps.setString(5, oVeiculo.getAno());
            ps.setString(6, oVeiculo.getCor());
            ps.setInt(7, oVeiculo.getCodigoVeiculo());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Update\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(VeiculosVO oVeiculo) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM veiculos "
                    + "WHERE CODIGO_VEICULO = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oVeiculo.getCodigoVeiculo());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no delete\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
