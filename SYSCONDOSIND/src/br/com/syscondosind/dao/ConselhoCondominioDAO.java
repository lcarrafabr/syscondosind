package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.ConselhoCondominioVO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class ConselhoCondominioDAO extends AbstractDAO {

    public void save(ConselhoCondominioVO oConselho) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO CONSELHO_CONDOMINIO ("
                    + "CODIGO_PESSOA, "
                    + "CARGO, "
                    + "INICIO_MANDATO, "
                    + "TERMINO_MANDATO, "
                    + "OBSERVACAO "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oConselho.getCodigoPessoa());
            ps.setString(2, oConselho.getCargo());
            ps.setDate(3, Date.valueOf(oConselho.getDataIniciomandato()));

            if (oConselho.getDataterminoMandato() == null) {
                ps.setDate(4, null);
            } else {
                ps.setDate(4, Date.valueOf(oConselho.getDataterminoMandato()));
            }

            ps.setString(5, oConselho.getObservacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no save.\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public List<ConselhoCondominioVO> findAll() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT C.CODIGO_CONSELHO_COND, C.CODIGO_PESSOA,P.NOME_PESSOA, C.CARGO, C.INICIO_MANDATO, "
                    + "C.TERMINO_MANDATO, C.OBSERVACAO, IF(C.TERMINO_MANDATO > 0,'INATIVO','ATIVO') AS STATUS_FILTRO "
                    + "FROM CONSELHO_CONDOMINIO C "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = C.CODIGO_PESSOA ";

            ps = cn.prepareStatement(comando);

            rs = ps.executeQuery();

            List<ConselhoCondominioVO> lista = new ArrayList<>();

            while (rs.next()) {

                LocalDate dataInicio = rs.getDate("INICIO_MANDATO").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatadaIni = dataInicio.format(formatter);
                
                String dataFormatadaFim = null;

                Integer codigoConselhoCond = rs.getInt("CODIGO_CONSELHO_COND");
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String cargo = rs.getString("CARGO");
                LocalDate dataIni = rs.getDate("INICIO_MANDATO").toLocalDate();

                LocalDate dataFim;
                if (rs.getDate("TERMINO_MANDATO") == null) {
                    dataFim = null;
                } else {

                    LocalDate dataTermino = rs.getDate("TERMINO_MANDATO").toLocalDate();
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataFormatadaFim = dataTermino.format(formatter2);

                    dataFim = rs.getDate("TERMINO_MANDATO").toLocalDate();
                    
                }

//                LocalDate dataFim = rs.getDate("TERMINO_MANDATO").toLocalDate();
                String observacao = rs.getString("OBSERVACAO");

                ConselhoCondominioVO oConselho = new ConselhoCondominioVO(codigoConselhoCond,
                        codigoPessoa,
                        cargo,
                        dataIni,
                        dataFim,
                        observacao
                );

                oConselho.setNomePessoaConselho(rs.getString("NOME_PESSOA"));
                oConselho.setStatusFiltro(rs.getString("STATUS_FILTRO"));
                oConselho.setDataIniFormat(dataFormatadaIni);
                oConselho.setDataFimFormat(dataFormatadaFim);

                lista.add(oConselho);
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Erro no FindAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }
    
    public List<ConselhoCondominioVO> findConselheiro(Integer codigoConselho) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();

            String comando = "SELECT C.CODIGO_CONSELHO_COND, C.CODIGO_PESSOA,P.NOME_PESSOA, C.CARGO, C.INICIO_MANDATO, "
                    + "C.TERMINO_MANDATO, C.OBSERVACAO, IF(C.TERMINO_MANDATO > 0,'INATIVO','ATIVO') AS STATUS_FILTRO "
                    + "FROM CONSELHO_CONDOMINIO C "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = C.CODIGO_PESSOA "
                    + "WHERE CODIGO_CONSELHO_COND = ? ";

            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoConselho);

            rs = ps.executeQuery();

            List<ConselhoCondominioVO> lista = new ArrayList<>();

            while (rs.next()) {

                LocalDate dataInicio = rs.getDate("INICIO_MANDATO").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatadaIni = dataInicio.format(formatter);
                
                String dataFormatadaFim = null;

                Integer codigoConselhoCond = rs.getInt("CODIGO_CONSELHO_COND");
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String cargo = rs.getString("CARGO");
                LocalDate dataIni = rs.getDate("INICIO_MANDATO").toLocalDate();

                LocalDate dataFim;
                if (rs.getDate("TERMINO_MANDATO") == null) {
                    dataFim = null;
                } else {

                    LocalDate dataTermino = rs.getDate("TERMINO_MANDATO").toLocalDate();
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataFormatadaFim = dataTermino.format(formatter2);

                    dataFim = rs.getDate("TERMINO_MANDATO").toLocalDate();
                    
                }

//                LocalDate dataFim = rs.getDate("TERMINO_MANDATO").toLocalDate();
                String observacao = rs.getString("OBSERVACAO");

                ConselhoCondominioVO oConselho = new ConselhoCondominioVO(codigoConselhoCond,
                        codigoPessoa,
                        cargo,
                        dataIni,
                        dataFim,
                        observacao
                );

                oConselho.setNomePessoaConselho(rs.getString("NOME_PESSOA"));
                oConselho.setStatusFiltro(rs.getString("STATUS_FILTRO"));
                oConselho.setDataIniFormat(dataFormatadaIni);
                oConselho.setDataFimFormat(dataFormatadaFim);

                lista.add(oConselho);
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Erro no FindAll.\n" + e, e);
        } finally {
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(ConselhoCondominioVO oConselho) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE CONSELHO_CONDOMINIO "
                    + "SET "
                    + "CODIGO_PESSOA = ?, "
                    + "CARGO = ?, "
                    + "INICIO_MANDATO = ?, "
                    + "TERMINO_MANDATO = ?, "
                    + "OBSERVACAO = ? "
                    + "WHERE "
                    + "CODIGO_CONSELHO_COND = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oConselho.getCodigoPessoa());
            ps.setString(2, oConselho.getCargo());
            ps.setDate(3, Date.valueOf(oConselho.getDataIniciomandato()));

            if (oConselho.getDataterminoMandato() == null) {
                ps.setDate(4, null);
            } else {
                ps.setDate(4, Date.valueOf(oConselho.getDataterminoMandato()));
            }

            ps.setString(5, oConselho.getObservacao());
            ps.setInt(6, oConselho.getCodigoConselhoCond());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(ConselhoCondominioVO oConselho) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM CONSELHO_CONDOMINIO "
                    + "WHERE CODIGO_CONSELHO_COND = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oConselho.getCodigoConselhoCond());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no delete.\n", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }

}
