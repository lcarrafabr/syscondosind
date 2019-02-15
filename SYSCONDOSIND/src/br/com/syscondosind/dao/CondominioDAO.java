/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.CondominiosVO;
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
public class CondominioDAO extends AbstractDAO {

    public void save(CondominiosVO oCond) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO condominios ("
                    + "CODIGO_PESSOA,       "
                    + "DESTINACAO_COND,     "
                    + "TIPO_CONDOMINIO,     "
                    + "CNPJ,                "
                    + "INSCRICAO_MUNICIPAL, "
                    + "CEP,                 "
                    + "LOGRADOURO,          "
                    + "NUMERO,              "
                    + "BAIRRO,              "
                    + "CIDADE,              "
                    + "ESTADO,              "
                    + "COMPLEMENTO          "
                    + ") "
                    + "VALUES ("
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?    "
                    + ") ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oCond.getCodigoPessoaCondominio());
            ps.setInt(2, oCond.getCodigoDestinacao());
            ps.setInt(3, oCond.getCodigoTipoCondominio());
            ps.setString(4, oCond.getCnpj());
            ps.setString(5, oCond.getInscricaoMunicipal());
            ps.setString(6, oCond.getCep());
            ps.setString(7, oCond.getLogradouro());
            ps.setString(8, oCond.getNumero());
            ps.setString(9, oCond.getBairro());
            ps.setString(10, oCond.getCidade());
            ps.setString(11, oCond.getEstadoSigla());
            ps.setString(12, oCond.getComplemento());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Falha ao cadastrar", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public List<CondominiosVO> findAll() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT c.CODIGO_PESSOA, P.NOME_PESSOA, c.TIPO_CONDOMINIO, TP.TIPO_CONDOMINIO as TIPO_COND, c.DESTINACAO_COND, D.TIPO_DESTINACAO "
                    + ", c.CNPJ, c.INSCRICAO_MUNICIPAL, c.CEP, c.LOGRADOURO, c.NUMERO, c.BAIRRO, c.CIDADE,c.ESTADO, c.COMPLEMENTO "
                    + "from condominios c "
                    + "inner join pessoas p on p.CODIGO_PESSOA = c.CODIGO_PESSOA "
                    + "INNER JOIN tipo_condominio TP ON TP.CODIGO_TIPO_CONDOMINIO = c.TIPO_CONDOMINIO "
                    + "INNER JOIN destinacao_condominio D ON D.CODIGO_DESTINACAO_COND = C.DESTINACAO_COND  ";

            ps = cn.prepareStatement(comando);

            rs = ps.executeQuery();

            List<CondominiosVO> list = new ArrayList<>();

            while (rs.next()) {

                CondominiosVO oCond = new CondominiosVO();

                oCond.setCodigoPessoaCondominio(rs.getInt("CODIGO_PESSOA"));
                oCond.setCodigoDestinacao(rs.getInt("DESTINACAO_COND"));
                oCond.setCodigoTipoCondominio(rs.getInt("TIPO_CONDOMINIO"));
                oCond.setCnpj(rs.getString("CNPJ"));
                oCond.setInscricaoMunicipal(rs.getString("INSCRICAO_MUNICIPAL"));
                oCond.setCep(rs.getString("CEP"));
                oCond.setLogradouro(rs.getString("LOGRADOURO"));
                oCond.setNumero(rs.getString("NUMERO"));
                oCond.setBairro(rs.getString("BAIRRO"));
                oCond.setCidade(rs.getString("CIDADE"));
                oCond.setEstadoSigla(rs.getString("ESTADO"));
                oCond.setComplemento(rs.getString("COMPLEMENTO"));
                oCond.setNomePessoaCondominio(rs.getString("NOME_PESSOA"));
                oCond.setDestinacao(rs.getString("TIPO_DESTINACAO"));
                oCond.setTipoCondominio(rs.getString("TIPO_COND"));

                list.add(oCond);

            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao consultar condominios.", e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }

    public List<CondominiosVO> findCondominio(int codigoPessoaCondominio) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            cn = getConnection();

            String comando = "SELECT c.CODIGO_PESSOA, P.NOME_PESSOA, c.TIPO_CONDOMINIO, TP.TIPO_CONDOMINIO as TIPO_COND, c.DESTINACAO_COND, D.TIPO_DESTINACAO "
                    + ", c.CNPJ, c.INSCRICAO_MUNICIPAL, c.CEP, c.LOGRADOURO, c.NUMERO, c.BAIRRO, c.CIDADE,c.ESTADO, c.COMPLEMENTO "
                    + "FROM condominios c "
                    + "INNER JOIN pessoas p on p.CODIGO_PESSOA = c.CODIGO_PESSOA "
                    + "INNER JOIN tipo_condominio TP ON TP.CODIGO_TIPO_CONDOMINIO = c.TIPO_CONDOMINIO "
                    + "INNER JOIN destinacao_condominio D ON D.CODIGO_DESTINACAO_COND = C.DESTINACAO_COND  ";

            if (codigoPessoaCondominio > 0) {
                comando = comando + "where c.CODIGO_PESSOA = ? ";
            }

            ps = cn.prepareStatement(comando);

            if (codigoPessoaCondominio > 0) {
                ps.setInt(1, codigoPessoaCondominio);
            }

            rs = ps.executeQuery();

            List<CondominiosVO> list = new ArrayList<>();

            while (rs.next()) {
                CondominiosVO oCond = new CondominiosVO();

                oCond.setCodigoPessoaCondominio(rs.getInt("CODIGO_PESSOA"));
                oCond.setNomePessoaCondominio(rs.getString("NOME_PESSOA"));
                oCond.setCodigoTipoCondominio(rs.getInt("TIPO_CONDOMINIO"));
                oCond.setTipoCondominio(rs.getString("TIPO_COND"));
                oCond.setCodigoDestinacao(rs.getInt("DESTINACAO_COND"));
                oCond.setDestinacao(rs.getString("TIPO_DESTINACAO"));
                oCond.setCnpj(rs.getString("CNPJ"));
                oCond.setInscricaoMunicipal(rs.getString("INSCRICAO_MUNICIPAL"));
                oCond.setCep(rs.getString("CEP"));
                oCond.setLogradouro(rs.getString("LOGRADOURO"));
                oCond.setNumero(rs.getString("NUMERO"));
                oCond.setBairro(rs.getString("BAIRRO"));
                oCond.setCidade(rs.getString("CIDADE"));
                oCond.setEstadoSigla(rs.getString("ESTADO"));
                oCond.setComplemento(rs.getString("COMPLEMENTO"));

                list.add(oCond);
            }
            return list;

        } catch (Exception e) {
            throw new DAOException("Erro ao consultar condomínio.", e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }

    public void getAlterar(CondominiosVO oCond) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {

            cn = getConnection();

            String comando = "UPDATE condominios "
                    + "SET "
                    + "DESTINACAO_COND = ?,     "
                    + "TIPO_CONDOMINIO = ?,     "
                    + "CNPJ = ?,                "
                    + "INSCRICAO_MUNICIPAL = ?, "
                    + "CEP = ?,                 "
                    + "LOGRADOURO = ?,          "
                    + "NUMERO = ?,              "
                    + "BAIRRO = ?,              "
                    + "CIDADE = ?,              "
                    + "ESTADO = ?,              "
                    + "COMPLEMENTO = ?          "
                    + "WHERE "
                    + "CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oCond.getCodigoDestinacao());
            ps.setInt(2, oCond.getCodigoTipoCondominio());
            ps.setString(3, oCond.getCnpj());
            ps.setString(4, oCond.getInscricaoMunicipal());
            ps.setString(5, oCond.getCep());
            ps.setString(6, oCond.getLogradouro());
            ps.setString(7, oCond.getNumero());
            ps.setString(8, oCond.getBairro());
            ps.setString(9, oCond.getCidade());
            ps.setString(10, oCond.getEstadoSigla());
            ps.setString(11, oCond.getComplemento());
            ps.setInt(12, oCond.getCodigoPessoaCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao atualizar o registro\n", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public void setDelete(CondominiosVO oCond) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;

        try {

            cn = getConnection();

            String comando = "DELETE FROM condominios "
                    + "WHERE CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oCond.getCodigoPessoaCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao deletar registro", e);
        } finally {
            closeResources(cn, ps, null);
        }

    }

}
