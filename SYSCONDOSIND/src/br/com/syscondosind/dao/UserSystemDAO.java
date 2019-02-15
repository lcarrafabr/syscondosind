package br.com.syscondosind.dao;

import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.UserSystemVO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class UserSystemDAO extends AbstractDAO {

    public void save(UserSystemVO oUser) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO users_system ("
                    + "CODIGO_PESSOA, "
                    + "USUARIO, "
                    + "SENHA, "
                    + "NIVEL_ACESSO, "
                    + "DATA_ULTIMA_ALTERACAO, "
                    + "STATUS "
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

            ps.setInt(1, oUser.getCodigoPessoa());
            ps.setString(2, oUser.getUser());
            ps.setString(3, oUser.getPassword());
            ps.setString(4, oUser.getNivelAcesso());
            ps.setDate(5, Date.valueOf(oUser.getDataUltimaAlteracao()));
            ps.setString(6, oUser.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no save.\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public List<UserSystemVO> findAll() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT U.*, P.NOME_PESSOA "
                    + "FROM USERS_SYSTEM U "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = U.CODIGO_PESSOA ";

            ps = cn.prepareStatement(comando);

            rs = ps.executeQuery();

            List<UserSystemVO> list = new ArrayList<>();

            while (rs.next()) {

                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String user = rs.getString("USUARIO");
                String nivelAcesso = rs.getString("NIVEL_ACESSO");
                LocalDate dataUltimaAlteracao = rs.getDate("DATA_ULTIMA_ALTERACAO").toLocalDate();
                String status = rs.getString("STATUS");

                UserSystemVO oUser = new UserSystemVO(codigoPessoa,
                        user,
                        nivelAcesso,
                        dataUltimaAlteracao,
                        status
                );
                
                oUser.setNomePessoa(rs.getString("NOME_PESSOA"));

                list.add(oUser);

            }
            return list;

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | SQLException e) {
            throw new DAOException("Erro no findAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }
    
    public List<UserSystemVO> findUser(int codigoPessoauser) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT U.*, P.NOME_PESSOA "
                    + "FROM USERS_SYSTEM U "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = U.CODIGO_PESSOA "
                    + "WHERE U.CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoPessoauser);

            rs = ps.executeQuery();

            List<UserSystemVO> list = new ArrayList<>();

            while (rs.next()) {

                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String user = rs.getString("USUARIO");
                String nivelAcesso = rs.getString("NIVEL_ACESSO");
                LocalDate dataUltimaAlteracao = rs.getDate("DATA_ULTIMA_ALTERACAO").toLocalDate();
                String status = rs.getString("STATUS");

                UserSystemVO oUser = new UserSystemVO(codigoPessoa,
                        user,
                        nivelAcesso,
                        dataUltimaAlteracao,
                        status
                );
                
                oUser.setNomePessoa(rs.getString("NOME_PESSOA"));

                list.add(oUser);

            }
            return list;

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | SQLException e) {
            throw new DAOException("Erro no findAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }
    
    public List<UserSystemVO> findUserLogin(String UserId) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT U.*, P.NOME_PESSOA "
                    + "FROM USERS_SYSTEM U "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = U.CODIGO_PESSOA "
                    + "WHERE U.USUARIO LIKE ? ";

            ps = cn.prepareStatement(comando);
            
            ps.setString(1, "%" + UserId + "%");

            rs = ps.executeQuery();

            List<UserSystemVO> list = new ArrayList<>();

            while (rs.next()) {

                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String user = rs.getString("USUARIO");
                String password = rs.getString("SENHA");
                String nivelAcesso = rs.getString("NIVEL_ACESSO");
                String status = rs.getString("STATUS");

                UserSystemVO oUser = new UserSystemVO(codigoPessoa, 
                        user, 
                        password, 
                        nivelAcesso, 
                        status
                );
                
                oUser.setNomePessoa(rs.getString("NOME_PESSOA"));

                list.add(oUser);

            }
            return list;

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | SQLException e) {
            throw new DAOException("Erro no findAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(UserSystemVO oUser) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE USERS_SYSTEM "
                    + "SET "
                    + "USUARIO = ?, "
                    + "SENHA = ?, "
                    + "NIVEL_ACESSO = ?, "
                    + "DATA_ULTIMA_ALTERACAO = ?, "
                    + "STATUS = ? "
                    + "WHERE "
                    + "CODIGO_PESSOA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setString(1, oUser.getUser());
            ps.setString(2, oUser.getPassword());
            ps.setString(3, oUser.getNivelAcesso());
            ps.setDate(4, Date.valueOf(oUser.getDataUltimaAlteracao()));
            ps.setString(5, oUser.getStatus());
            ps.setInt(6, oUser.getCodigoPessoa());
            
            ps.executeUpdate();            
            
        } catch (SQLException e) {
            throw new DAOException("Erro no Update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(UserSystemVO oUser) throws DAOException{
       
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM USERS_SYSTEM "
                    + "WHERE CODIGO_PESSOA = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oUser.getCodigoPessoa());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no delete.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }

}
