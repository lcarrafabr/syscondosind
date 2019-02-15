/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.SetoresGaragemVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class SetoresGaragemDAO extends AbstractDAO{
    
    public void save(SetoresGaragemVO oSetor) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "INSERT INTO setores_garagem ("
                    + "NOME_SETOR, "
                    + "STATUS, "
                    + "CODIGO_SETOR, "
                    + "OBSERVACAO "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oSetor.getNomeSetor());
            ps.setString(2, oSetor.getStatus());
            ps.setString(3, oSetor.getCodigoSetor());
            ps.setString(4, oSetor.getObservacao());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Save.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<SetoresGaragemVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
                String comando = "SELECT * "
                        + "FROM setores_garagem ";
                
                ps = cn.prepareStatement(comando);
                
                rs = ps.executeQuery();
                
                List<SetoresGaragemVO>lista = new ArrayList<>();
                
                while (rs.next()) {                    
                    
                    SetoresGaragemVO oSetor = new SetoresGaragemVO();
                    
                    oSetor.setCodigoSetorGaragem(rs.getInt("CODIGO_SETOR_GARAGEM"));
                    oSetor.setNomeSetor(rs.getString("NOME_SETOR"));
                    oSetor.setStatus(rs.getString("STATUS"));
                    oSetor.setCodigoSetor(rs.getString("CODIGO_SETOR"));
                    oSetor.setObservacao(rs.getString("OBSERVACAO"));
                    
                    lista.add(oSetor);
                }
                return lista;
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<SetoresGaragemVO> findAllResultados() throws DAOException{
        
        /*Query usada para os resultados do Form Setores*/
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
                String comando = "SELECT count(NOME_SETOR) AS QTD_TOTAL, "
                        + "(SELECT COUNT(NOME_SETOR) FROM setores_garagem where status = 'LIBERADO') as QTD_LIBERADO, "
                        + "(SELECT COUNT(NOME_SETOR) FROM setores_garagem where status = 'MANUTENÇÃO') as QTD_MANUTENCAO, "
                        + "(SELECT COUNT(NOME_SETOR) FROM setores_garagem where status = 'INTERDITADO') as QTD_INTERDITADO "
                        + "FROM setores_garagem ";
                
                ps = cn.prepareStatement(comando);
                
                rs = ps.executeQuery();
                
                List<SetoresGaragemVO>lista = new ArrayList<>();
                
                while (rs.next()) {                    
                    
                    SetoresGaragemVO oSetor = new SetoresGaragemVO();
                    
                    oSetor.setQtdTotal(rs.getInt("QTD_TOTAL"));
                    oSetor.setQtdLiberado(rs.getInt("QTD_LIBERADO"));
                    oSetor.setQtdManutencao(rs.getInt("QTD_MANUTENCAO"));
                    oSetor.setQtdInterditado(rs.getInt("QTD_INTERDITADO"));
                    
                    lista.add(oSetor);
                }
                return lista;
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<SetoresGaragemVO> findAllSetor(int codigoSetor) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
                String comando = "SELECT * "
                        + "FROM setores_garagem "
                        + "WHERE CODIGO_SETOR_GARAGEM = ? ";
                
                ps = cn.prepareStatement(comando);
                
                ps.setInt(1, codigoSetor);
                
                rs = ps.executeQuery();
                
                List<SetoresGaragemVO>lista = new ArrayList<>();
                
                while (rs.next()) {                    
                    
                    SetoresGaragemVO oSetor = new SetoresGaragemVO();
                    
                    oSetor.setCodigoSetorGaragem(rs.getInt("CODIGO_SETOR_GARAGEM"));
                    oSetor.setNomeSetor(rs.getString("NOME_SETOR"));
                    oSetor.setStatus(rs.getString("STATUS"));
                    oSetor.setCodigoSetor(rs.getString("CODIGO_SETOR"));
                    oSetor.setObservacao(rs.getString("OBSERVACAO"));
                    
                    lista.add(oSetor);
                }
                return lista;
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<SetoresGaragemVO> findQTDVagasSetor() throws DAOException{
        
        /*Query usada o FORM VAGAS GARAGEM na grade
        QTD de vagas por setor*/
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
                String comando = "SELECT S.NOME_SETOR, COUNT(V.NOME_VAGA) AS QTD_VAGA "
                        + "FROM setores_garagem S "
                        + "LEFT JOIN vagas_garagem V ON S.CODIGO_SETOR_GARAGEM = V.CODIGO_SETOR_GARAGEM "
                        + "GROUP BY S.NOME_SETOR ";
                
                ps = cn.prepareStatement(comando);
                
                rs = ps.executeQuery();
                
                List<SetoresGaragemVO>lista = new ArrayList<>();
                
                while (rs.next()) {                    
                    
                    SetoresGaragemVO oSetor = new SetoresGaragemVO();
                    
                    oSetor.setNomeSetor(rs.getString("NOME_SETOR"));
                    oSetor.setQtdVagas(rs.getInt("QTD_VAGA"));
                    
                    lista.add(oSetor);
                }
                return lista;
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(SetoresGaragemVO oSetor) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE setores_garagem "
                    + "SET "
                    + "NOME_SETOR = ?, "
                    + "STATUS = ?, "
                    + "CODIGO_SETOR = ?, "
                    + "OBSERVACAO = ? "
                    + "WHERE "
                    + "CODIGO_SETOR_GARAGEM = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oSetor.getNomeSetor());
            ps.setString(2, oSetor.getStatus());
            ps.setString(3, oSetor.getCodigoSetor());
            ps.setString(4, oSetor.getObservacao());
            ps.setInt(5, oSetor.getCodigoSetorGaragem());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(SetoresGaragemVO oSetor) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM setores_garagem "
                    + "WHERE CODIGO_SETOR_GARAGEM = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oSetor.getCodigoSetorGaragem());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Delete.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
