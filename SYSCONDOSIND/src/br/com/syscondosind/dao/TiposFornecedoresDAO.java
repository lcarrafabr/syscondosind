package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TiposFornecedoresVO;
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
public class TiposFornecedoresDAO extends AbstractDAO {

    /**
     * Salva um tipo de fornecedor no banco de dados.
     *
     * @param oTipoForn
     * @throws DAOException
     */
    public void save(TiposFornecedoresVO oTipoForn) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO tipos_fornecedores ("
                    + "CODIGO_GRUPO_FORNECEDOR, "
                    + "TIPO_FORNECEDOR "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "? "
                    + ") ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oTipoForn.getCodigoGrupoFornecedor());
            ps.setString(2, oTipoForn.getTipoFornecedor());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no save.\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    /**
     * Retorna a lista completa de tipos fornecedores
     *
     * @return
     * @throws DAOException
     */
    public List<TiposFornecedoresVO> findAll() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT T.CODIGO_TIPO_FORNECEDOR, T.CODIGO_GRUPO_FORNECEDOR, "
                    + "G.GRUPO_FORNECEDOR, T.TIPO_FORNECEDOR "
                    + "FROM TIPOS_FORNECEDORES T "
                    + "INNER JOIN GRUPOS_FORNECEDORES G ON G.CODIGO_GRUPO_FORNECEDOR = T.CODIGO_GRUPO_FORNECEDOR "
                    + "ORDER BY T.CODIGO_TIPO_FORNECEDOR ";

            ps = cn.prepareStatement(comando);

            rs = ps.executeQuery();

            List<TiposFornecedoresVO> list = new ArrayList<>();

            while (rs.next()) {

                Integer codigoTipoFornecedor = rs.getInt("CODIGO_TIPO_FORNECEDOR");
                Integer codigoGrupoFornecedor = rs.getInt("CODIGO_GRUPO_FORNECEDOR");
                String tipoFornecedor = rs.getString("TIPO_FORNECEDOR");

                TiposFornecedoresVO oTipoF = new TiposFornecedoresVO(codigoTipoFornecedor,
                        codigoGrupoFornecedor,
                        tipoFornecedor
                );

                oTipoF.setNomeGrupo(rs.getString("GRUPO_FORNECEDOR"));

                list.add(oTipoF);
            }

            return list;

        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }

    /**
     * Retorna o tipo fornecedor conforme o codigo do tipo fornecedor informado
     *
     * @param codigoTipoFornecedorParam
     * @return
     * @throws DAOException
     */
    public List<TiposFornecedoresVO> findTipoFornecedor(Integer codigoTipoFornecedorParam) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT T.CODIGO_TIPO_FORNECEDOR, T.CODIGO_GRUPO_FORNECEDOR, "
                    + "G.GRUPO_FORNECEDOR, T.TIPO_FORNECEDOR "
                    + "FROM TIPOS_FORNECEDORES T "
                    + "INNER JOIN GRUPOS_FORNECEDORES G ON G.CODIGO_GRUPO_FORNECEDOR = T.CODIGO_GRUPO_FORNECEDOR "
                    + "WHERE T.CODIGO_TIPO_FORNECEDOR = ? "
                    + "ORDER BY T.CODIGO_TIPO_FORNECEDOR ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, codigoTipoFornecedorParam);

            rs = ps.executeQuery();

            List<TiposFornecedoresVO> list = new ArrayList<>();

            while (rs.next()) {

                Integer codigoTipoFornecedor = rs.getInt("CODIGO_TIPO_FORNECEDOR");
                Integer codigoGrupoFornecedor = rs.getInt("CODIGO_GRUPO_FORNECEDOR");
                String tipoFornecedor = rs.getString("TIPO_FORNECEDOR");

                TiposFornecedoresVO oTipoF = new TiposFornecedoresVO(codigoTipoFornecedor,
                        codigoGrupoFornecedor,
                        tipoFornecedor
                );

                oTipoF.setNomeGrupo(rs.getString("GRUPO_FORNECEDOR"));

                list.add(oTipoF);
            }

            return list;

        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }

    public void update(TiposFornecedoresVO TipoForn) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {

            cn = getConnection();

            String comando = "UPDATE TIPOS_FORNECEDORES "
                    + "SET "
                    + "CODIGO_GRUPO_FORNECEDOR = ?, "
                    + "TIPO_FORNECEDOR = ? "
                    + "WHERE "
                    + "CODIGO_TIPO_FORNECEDOR = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, TipoForn.getCodigoGrupoFornecedor());
            ps.setString(2, TipoForn.getTipoFornecedor());
            ps.setInt(3, TipoForn.getCodigoTipoFornecedor());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no update\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public void delete(TiposFornecedoresVO tipoForn) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "DELETE FROM TIPOS_FORNECEDORES "
                    + "WHERE CODIGO_TIPO_FORNECEDOR = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, tipoForn.getCodigoTipoFornecedor());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no update \n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

}
