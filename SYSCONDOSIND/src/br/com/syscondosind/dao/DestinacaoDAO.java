/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.DestinacaoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DestinacaoDAO extends AbstractDAO{
    
    public void save(DestinacaoVO oDestin) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO destinacao_condominio("
                    + "TIPO_DESTINACAO 	"
                    + ") "
                    + "VALUES ("
                    + "? 		"
                    + ") ";

            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oDestin.getTipoDestinacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Falha ao cadastrar", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    public List<DestinacaoVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                + "FROM destinacao_condominio ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<DestinacaoVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                DestinacaoVO oDestin = new DestinacaoVO();
                
                oDestin.setCodigoDestinacaoCond(rs.getInt("CODIGO_DESTINACAO_COND"));
                oDestin.setTipoDestinacao(rs.getString("TIPO_DESTINACAO"));
                
                list.add(oDestin);
                
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar destinação.", e);
        }finally{
            closeResources(cn, ps, rs);
        }    
    }
    
    public List<DestinacaoVO> findDestin(int codigoDestincacao) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT * "
                + "FROM destinacao_condominio ";
            
            
            if (codigoDestincacao > 0) {
                comando = comando + "WHERE CODIGO_DESTINACAO_COND = ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if (codigoDestincacao > 0) {

                ps.setInt(1, codigoDestincacao);
            }
            
            rs = ps.executeQuery();
            
            List<DestinacaoVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                DestinacaoVO oDestin = new DestinacaoVO();
                
                oDestin.setCodigoDestinacaoCond(rs.getInt("CODIGO_DESTINACAO_COND"));
                oDestin.setTipoDestinacao(rs.getString("TIPO_DESTINACAO"));
                
                list.add(oDestin); 
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar destinação.", e);
        }finally{
            closeResources(cn, ps, rs);
        }    
    }
    
    public void getAlterar(DestinacaoVO oDestin) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();
            String comando = "UPDATE destinacao_condominio "
                    + "SET "
                    + "TIPO_DESTINACAO = ?     "
                    + "WHERE "
                    + "CODIGO_DESTINACAO_COND = ? ";

            ps = cn.prepareStatement(comando);

            ps.setString(1, oDestin.getTipoDestinacao());
            ps.setInt(2, oDestin.getCodigoDestinacaoCond());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    public void getDelete(DestinacaoVO oDestin) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();
            String comando = "DELETE FROM destinacao_condominio "
                    + "WHERE "
                    + "CODIGO_DESTINACAO_COND = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oDestin.getCodigoDestinacaoCond());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    
}
