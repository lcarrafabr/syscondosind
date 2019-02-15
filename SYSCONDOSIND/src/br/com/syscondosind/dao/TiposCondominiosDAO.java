/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TipoCondominioVO;
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
public class TiposCondominiosDAO extends AbstractDAO{
    
    public void save(TipoCondominioVO oTipoCond) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "INSERT INTO tipo_condominio("
                    + "TIPO_CONDOMINIO 	"
                    + ") "
                    + "VALUES ("
                    + "?                "
                    + ") ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oTipoCond.getTipoCondominio());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Falha ao cadastrar", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public List<TipoCondominioVO> findAll () throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT * "
                + "FROM tipo_condominio ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<TipoCondominioVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                TipoCondominioVO oTipoCondominioVO = new TipoCondominioVO();
                
                oTipoCondominioVO.setCodigoTipoCondominio(rs.getInt("CODIGO_TIPO_CONDOMINIO"));
                oTipoCondominioVO.setTipoCondominio(rs.getString("TIPO_CONDOMINIO"));
                
                list.add(oTipoCondominioVO);                
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar", e);
        }finally{
            closeResources(cn, ps, rs);
        }
        
    }
    
    public List<TipoCondominioVO> findTipoCondominio(int codigoTipoCond) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "SELECT * "
                + "FROM tipo_condominio ";
            
            if (codigoTipoCond > 0) {
                comando = comando + "WHERE CODIGO_TIPO_CONDOMINIO = ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if (codigoTipoCond > 0) {

                ps.setInt(1, codigoTipoCond);
            }
            
            rs = ps.executeQuery();
            
            List<TipoCondominioVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                TipoCondominioVO oTipoCondominioVO = new TipoCondominioVO();
                
                oTipoCondominioVO.setCodigoTipoCondominio(rs.getInt("CODIGO_TIPO_CONDOMINIO"));
                oTipoCondominioVO.setTipoCondominio(rs.getString("TIPO_CONDOMINIO"));
                
                list.add(oTipoCondominioVO);                
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao consultar", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    
    public void getAlterar(TipoCondominioVO oTipoCond) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();
            String comando = "UPDATE tipo_condominio "
                    + "SET "
                    + "TIPO_CONDOMINIO = ?     "
                    + "WHERE "
                    + "CODIGO_TIPO_CONDOMINIO = ? ";

            ps = cn.prepareStatement(comando);

            ps.setString(1, oTipoCond.getTipoCondominio());
            ps.setInt(2, oTipoCond.getCodigoTipoCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    public void getDelete(TipoCondominioVO oTipoCond) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();
            String comando = "DELETE FROM tipo_condominio "
                    + "WHERE "
                    + "CODIGO_TIPO_CONDOMINIO = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oTipoCond.getCodigoTipoCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    
    
}
