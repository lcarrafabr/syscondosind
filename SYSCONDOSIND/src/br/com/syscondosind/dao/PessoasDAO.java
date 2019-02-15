/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.dao;

import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.PessoasVO;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class PessoasDAO extends AbstractDAO {

    public void save(PessoasVO oPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        FileInputStream fi = null;

        try {
            cn = getConnection();

            String querySetLimit = "SET GLOBAL max_allowed_packet=104857600 "; // 10 MB
            Statement stSetLimit = cn.createStatement();
            stSetLimit.execute(querySetLimit);

            String comando = "INSERT INTO pessoas("
                    + "NOME_PESSOA, 	"
                    + "STATUS,          "
                    + "TIPO_PESSOA,     "
                    + "DATA_CADASTRO,   "
                    + "AVATAR           "
                    + ") "
                    + "VALUES ("
                    + "?, 		"
                    + "?, 		"
                    + "?,               "
                    + "?,               "
                    + "? 		"
                    + ") ";

            ps = cn.prepareStatement(comando);

            if (oPessoa.getOperadorFile() > 0) {
                System.out.println("passei pelo operador");
                try {
                    if (oPessoa.getCaminhoImagemPasta() != null) {
                        File file = new File(oPessoa.getCaminhoImagemPasta());
                        fi = new FileInputStream(file);
                    } else {
                        fi = null;
                    }

                } catch (Exception e) {
                    Funcoes.messageAlert("Erro ao cadastrar imagem\n" + e, Alert.AlertType.ERROR);
                }
            }

            ps.setString(1, oPessoa.getNomePessoa());
            ps.setString(2, oPessoa.getStatus());
            ps.setString(3, oPessoa.getTipoPessoa());
            ps.setDate(4, Date.valueOf(oPessoa.getDataCadastro()));
            ps.setBinaryStream(5, fi);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Falha ao cadastrar", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

//    public List<PessoasVO> find(String nome, int paginacao, int linePerPage) throws DAOException {
    public List<PessoasVO> find(String nome, String tipoPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            String comando = "SELECT * "
                    + "FROM pessoas "
                    + "WHERE TIPO_PESSOA = '" + tipoPessoa + "' ";

            int quantparam = 0;

            if (nome != null) {
                comando = comando + "AND NOME_PESSOA LIKE ? ";
            }

//            comando = comando + "ORDER BY CODIGO_PESSOA LIMIT " + paginacao + ", " + linePerPage + " ";
            ps = cn.prepareStatement(comando);

            if (nome != null) {
                quantparam = quantparam + 1;
                ps.setString(quantparam, "%" + nome + "%");
            }

            rs = ps.executeQuery();

            List<PessoasVO> pessoaLista = new ArrayList<>();

            while (rs.next()) {

                LocalDate data = rs.getDate("DATA_CADASTRO").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = data.format(formatter);

                PessoasVO oPessoa = new PessoasVO();
                oPessoa.setCodigoPessoa(rs.getInt("CODIGO_PESSOA"));
                oPessoa.setNomePessoa(rs.getString("NOME_PESSOA"));
                oPessoa.setStatus(rs.getString("STATUS"));
                oPessoa.setDataCadastro(rs.getDate("DATA_CADASTRO").toLocalDate());
                oPessoa.setDataFormatada(dataFormatada);
                oPessoa.setTipoPessoa(rs.getString("TIPO_PESSOA"));

                pessoaLista.add(oPessoa);

            }
            return pessoaLista;
        } catch (Exception e) {
            throw new DAOException("Falha ao realizar a pesquisa de pessoa", e);
        } finally {
            closeResources(cn, ps, rs);
        }

    }
    
    public List<PessoasVO> findAll() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            String comando = "SELECT * "
                    + "FROM pessoas ";

            int quantparam = 0;

            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();

            List<PessoasVO> pessoaLista = new ArrayList<>();

            while (rs.next()) {

                LocalDate data = rs.getDate("DATA_CADASTRO").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = data.format(formatter);

                PessoasVO oPessoa = new PessoasVO();
                oPessoa.setCodigoPessoa(rs.getInt("CODIGO_PESSOA"));
                oPessoa.setNomePessoa(rs.getString("NOME_PESSOA"));
                oPessoa.setStatus(rs.getString("STATUS"));
                oPessoa.setDataCadastro(rs.getDate("DATA_CADASTRO").toLocalDate());
                oPessoa.setDataFormatada(dataFormatada);
                oPessoa.setTipoPessoa(rs.getString("TIPO_PESSOA"));

                pessoaLista.add(oPessoa);

            }
            return pessoaLista;
        } catch (SQLException e) {
            throw new DAOException("Falha ao realizar a pesquisa de pessoa", e);
        } finally {
            closeResources(cn, ps, rs);
        }

    }

    public List<PessoasVO> findPerson(int codigoPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            String comando = "SELECT * "
                    + "FROM pessoas ";

            if (codigoPessoa > 0) {
                comando = comando + "WHERE CODIGO_PESSOA = ? ";
            }

            ps = cn.prepareStatement(comando);

            if (codigoPessoa > 0) {

                ps.setInt(1, codigoPessoa);
            }

            rs = ps.executeQuery();

            List<PessoasVO> pessoaLista = new ArrayList<>();

            while (rs.next()) {

                LocalDate data = rs.getDate("DATA_CADASTRO").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = data.format(formatter);

                PessoasVO oPessoa = new PessoasVO();
                oPessoa.setCodigoPessoa(rs.getInt("CODIGO_PESSOA"));
                oPessoa.setNomePessoa(rs.getString("NOME_PESSOA"));
                oPessoa.setStatus(rs.getString("STATUS"));
                oPessoa.setDataCadastro(rs.getDate("DATA_CADASTRO").toLocalDate());
                oPessoa.setDataFormatada(dataFormatada);
                oPessoa.setTipoPessoa(rs.getString("TIPO_PESSOA"));
                oPessoa.setAvatar(rs.getBlob("AVATAR"));

                pessoaLista.add(oPessoa);
            }
            return pessoaLista;
        } catch (Exception e) {
            throw new DAOException("Falha ao realizar a pesquisa de pessoa", e);
        } finally {
            closeResources(cn, ps, rs);
        }

    }

    public List<PessoasVO> totalRegistrosPessoas() throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = getConnection();
            String comando = "SELECT count(CODIGO_PESSOA)AS TOTAL_REGISTROS "
                    + "FROM pessoas ";

            ps = cn.prepareStatement(comando);

            rs = ps.executeQuery();

            List<PessoasVO> pessoaLista = new ArrayList<>();

            while (rs.next()) {
                PessoasVO oPessoa = new PessoasVO();
                oPessoa.setTotalRegistros(rs.getInt("TOTAL_REGISTROS"));

                pessoaLista.add(oPessoa);
            }

            return pessoaLista;
        } catch (Exception e) {
            throw new DAOException("Falha ao realizar a pesquisa de pessoa", e);
        } finally {
            closeResources(cn, ps, rs);
        }

    }

    public void getAlterar(PessoasVO oPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        FileInputStream fi = null;

        try {
            cn = getConnection();
            String comando = "UPDATE pessoas "
                    + "SET "
                    + "NOME_PESSOA = ?,     "
                    + "STATUS = ?,          "
                    + "DATA_CADASTRO = ?,   "
                    + "TIPO_PESSOA = ?,     "
                    + "AVATAR = ?           "
                    + "WHERE "
                    + "CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);
            
            if (oPessoa.getOperadorFile() > 0) {
                System.out.println("passei pelo operador");
                try {
                    if (oPessoa.getCaminhoImagemPasta() != null) {
                        File file = new File(oPessoa.getCaminhoImagemPasta());
                        fi = new FileInputStream(file);
                    } else {
                        fi = null;
                    }

                } catch (Exception e) {
                    Funcoes.messageAlert("Erro ao cadastrar imagem\n" + e, Alert.AlertType.ERROR);
                }
            }

            ps.setString(1, oPessoa.getNomePessoa());
            ps.setString(2, oPessoa.getStatus());
            ps.setDate(3, Date.valueOf(oPessoa.getDataCadastro()));
            ps.setString(4, oPessoa.getTipoPessoa());
            ps.setBinaryStream(5, fi);
            ps.setInt(6, oPessoa.getCodigoPessoa());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public void getDelete(PessoasVO oPessoa) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();
            String comando = "DELETE FROM pessoas "
                    + "WHERE "
                    + "CODIGO_PESSOA = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oPessoa.getCodigoPessoa());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DAOException("Falha ao atualizar os dados da pessoa", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

}
