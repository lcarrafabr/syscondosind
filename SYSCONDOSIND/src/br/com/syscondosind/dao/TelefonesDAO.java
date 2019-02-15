package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TelefonesVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class TelefonesDAO extends AbstractDAO {

    public void save(TelefonesVO oTel) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO telefones ("
                    + "CODIGO_PESSOA, "
                    + "CODIGO_OPERADORA, "
                    + "DDD, "
                    + "NUMERO_TELEFONE, "
                    + "RAMAL, "
                    + "TIPO_TELEFONE "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oTel.getCodigoPessoa());
            ps.setInt(2, oTel.getCodigoOperadora());
            ps.setString(3, oTel.getDdd());
            ps.setString(4, oTel.getNumeroTelefone());
            ps.setString(5, oTel.getRamal());
            ps.setString(6, oTel.getTipoTelefone());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Erro no save\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public List<TelefonesVO> findTelefones(int codigoPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            
            String comando = "SELECT T.CODIGO_TELEFONE, T.CODIGO_PESSOA,P.NOME_PESSOA ,T.CODIGO_OPERADORA,  "
                    + "O.NOME_OPERADORA, T.DDD, T.NUMERO_TELEFONE, T.RAMAL, T.TIPO_TELEFONE "
                    + "FROM telefones T "
                    + "INNER JOIN operadoras O ON O.CODIGO_OPERADORA = T.CODIGO_OPERADORA "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = T.CODIGO_PESSOA "
                    + "WHERE T.CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, codigoPessoa);

            rs = ps.executeQuery();

            List<TelefonesVO> list = new ArrayList<>();

            while (rs.next()) {

                TelefonesVO oTel = new TelefonesVO(rs.getInt("CODIGO_TELEFONE"),
                        rs.getInt("CODIGO_PESSOA"),
                        rs.getInt("CODIGO_OPERADORA"),
                        rs.getString("DDD"),
                        rs.getString("NUMERO_TELEFONE"),
                        rs.getString("RAMAL"),
                        rs.getString("TIPO_TELEFONE"));
                
                list.add(oTel);

            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no FindTelefones\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<TelefonesVO> findTelefonesPesoa(int codigoPessoa, int codigoTelefone) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            
            String comando = "SELECT T.CODIGO_TELEFONE, T.CODIGO_PESSOA,P.NOME_PESSOA ,T.CODIGO_OPERADORA,  "
                    + "O.NOME_OPERADORA, T.DDD, T.NUMERO_TELEFONE, T.RAMAL, T.TIPO_TELEFONE "
                    + "FROM telefones T "
                    + "INNER JOIN operadoras O ON O.CODIGO_OPERADORA = T.CODIGO_OPERADORA "
                    + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = T.CODIGO_PESSOA "
                    + "WHERE T.CODIGO_PESSOA = ? "
                    + "AND T.CODIGO_TELEFONE = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, codigoPessoa);
            ps.setInt(2, codigoTelefone);//150263

            rs = ps.executeQuery();

            List<TelefonesVO> list = new ArrayList<>();

            while (rs.next()) {

                TelefonesVO oTel = new TelefonesVO(rs.getInt("CODIGO_TELEFONE"),
                        rs.getInt("CODIGO_PESSOA"),
                        rs.getInt("CODIGO_OPERADORA"),
                        rs.getString("DDD"),
                        rs.getString("NUMERO_TELEFONE"),
                        rs.getString("RAMAL"),
                        rs.getString("TIPO_TELEFONE"));
                
                list.add(oTel);

            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no FindTelefones\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(TelefonesVO oTel) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE telefones "
                    + "SET "
                    + "CODIGO_PESSOA = ?, "
                    + "CODIGO_OPERADORA = ?, "
                    + "DDD = ?, "
                    + "NUMERO_TELEFONE = ?, "
                    + "RAMAL = ?, "
                    + "TIPO_TELEFONE = ? "
                    + "WHERE "
                    + "CODIGO_TELEFONE = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oTel.getCodigoPessoa());
            ps.setInt(2, oTel.getCodigoOperadora());
            ps.setString(3, oTel.getDdd());
            ps.setString(4, oTel.getNumeroTelefone());
            ps.setString(5, oTel.getRamal());
            ps.setString(6, oTel.getTipoTelefone());
            ps.setInt(7, oTel.getCodigoTelefone());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no Update\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(TelefonesVO oTel) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM telefones "
                    + "WHERE CODIGO_TELEFONE = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oTel.getCodigoTelefone());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro no delete\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }

}
