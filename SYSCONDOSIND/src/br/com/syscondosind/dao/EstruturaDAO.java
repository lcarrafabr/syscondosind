package br.com.syscondosind.dao;

import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.CondominiosVO;
import br.com.syscondosind.vo.EstruturasVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class EstruturaDAO extends AbstractDAO {

    public void save(EstruturasVO oEstrut) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO estruturas ("
                    + "CODIGO_PESSOA_CONDOMINIO,        "
                    + "POSSUI_PORTARIA,                 "
                    + "POSSUI_ELEVADOR,                 "
                    + "POSSUI_CERCA_ELETRICA,           "
                    + "POSSUI_CIRCUITO_TV_FECHADO,      "
                    + "QTD_APT,                         "
                    + "QTD_COBERTURA,                   "
                    + "TIPO_PORTARIA,                   "
                    + "FUNCIONAMENTO_HORAS_PORTARIA,    "
                    + "VAGAS_GARAGEM,                   "
                    + "TIPO_GARAGEM,                    "
                    + "QTD_ELEVADORES,                  "
                    + "NOME_CONSTRUTORA,                "
                    + "ANO_CONSTRUCAO,                  "
                    + "AREA_PRIVATIVA,                  "
                    + "AREA_PRIVATIVA_COBERTA,          "
                    + "AREA_COMUM_COBERTA,              "
                    + "AREA_TOTAL_COBERTURA,            "
                    + "POSSUI_SALAO_FESTAS,             "
                    + "POSSUI_PLAYGROUND,               "
                    + "POSSUI_AGUA_INDIVIDUALIZADA,     "
                    + "POSSUI_PISCINA,                  "
                    + "POSSUI_PORTAO_ELETRONICO,        "
                    + "POSSUI_GAS_ENCANADO,             "
                    + "POSSUI_COLETA_SELETIVA_LIXO,     "
                    + "POSSUI_COLETA_OLEO_COZINHA,      "
                    + "DIFERENCIAIS                     "
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
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?,   "
                    + "?    "
                    + ") ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oEstrut.getCodigoPessoaCondominio());
            ps.setString(2, oEstrut.getPossuiPortaria());
            ps.setString(3, oEstrut.getPossuiElevador());
            ps.setString(4, oEstrut.getPossuiCercaEletrica());
            ps.setString(5, oEstrut.getPossuiTVFechado());
            ps.setInt(6, oEstrut.getQtdApto());
            ps.setInt(7, oEstrut.getQtdCobertura());
            ps.setString(8, oEstrut.getTipoPortaria());
            ps.setString(9, oEstrut.getFuncionamentoPortaria());
            ps.setInt(10, oEstrut.getVagasGaragem());
            ps.setString(11, oEstrut.getTipoGaragem());
            ps.setInt(12, oEstrut.getQtdElevadores());
            ps.setString(13, oEstrut.getNomeConstrutora());
            ps.setString(14, oEstrut.getAnoConstrucao());
            ps.setDouble(15, oEstrut.getAreaPrivativa());
            ps.setDouble(16, oEstrut.getAreaPrivativaCoberta());
            ps.setDouble(17, oEstrut.getAreaComunCoberta());
            ps.setDouble(18, oEstrut.getAreaTotalCoberta());
            ps.setString(19, oEstrut.getPossuiSalaoFestas());
            ps.setString(20, oEstrut.getPossuiPlayground());
            ps.setString(21, oEstrut.getPossuiAguaIndividualizada());
            ps.setString(22, oEstrut.getPossuiPiscina());
            ps.setString(23, oEstrut.getPossuiPortaoEletronico());
            ps.setString(24, oEstrut.getPossuiGasEncanado());
            ps.setString(25, oEstrut.getPossuiColetaSeletivaLixo());
            ps.setString(26, oEstrut.getPossuiColetaOleoCozinha());
            ps.setString(27, oEstrut.getDiferenciais());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Falha ao cadastrar", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }

    public List<EstruturasVO> findEstrutura(int codigoEstrutura) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            cn = getConnection();

            String comando = "SELECT * "
                    + "FROM estruturas "
                    + "WHERE CODIGO_PESSOA_CONDOMINIO = ? ";

            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoEstrutura);
            
            rs = ps.executeQuery();
            
            List<EstruturasVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                EstruturasVO oEstrut = new EstruturasVO();
                
                oEstrut.setCodigoPessoaCondominio(rs.getInt("CODIGO_PESSOA_CONDOMINIO"));
                oEstrut.setPossuiPortaria(rs.getString("POSSUI_PORTARIA"));
                oEstrut.setPossuiElevador(rs.getString("POSSUI_ELEVADOR"));
                oEstrut.setPossuiCercaEletrica(rs.getString("POSSUI_CERCA_ELETRICA"));
                oEstrut.setPossuiTVFechado(rs.getString("POSSUI_CIRCUITO_TV_FECHADO"));
                oEstrut.setQtdApto(rs.getInt("QTD_APT"));
                oEstrut.setQtdCobertura(rs.getInt("QTD_COBERTURA"));
                oEstrut.setTipoPortaria(rs.getString("TIPO_PORTARIA"));
                oEstrut.setFuncionamentoPortaria(rs.getString("FUNCIONAMENTO_HORAS_PORTARIA"));
                oEstrut.setVagasGaragem(rs.getInt("VAGAS_GARAGEM"));
                oEstrut.setTipoGaragem(rs.getString("TIPO_GARAGEM"));
                oEstrut.setQtdElevadores(rs.getInt("QTD_ELEVADORES"));
                oEstrut.setNomeConstrutora(rs.getString("NOME_CONSTRUTORA"));
                oEstrut.setAnoConstrucao(rs.getString("ANO_CONSTRUCAO"));
                oEstrut.setAreaPrivativa(rs.getDouble("AREA_PRIVATIVA"));
                oEstrut.setAreaPrivativaCoberta(rs.getDouble("AREA_PRIVATIVA_COBERTA"));
                oEstrut.setAreaComunCoberta(rs.getDouble("AREA_COMUM_COBERTA"));
                oEstrut.setAreaTotalCoberta(rs.getDouble("AREA_TOTAL_COBERTURA"));
                oEstrut.setPossuiSalaoFestas(rs.getString("POSSUI_SALAO_FESTAS"));
                oEstrut.setPossuiPlayground(rs.getString("POSSUI_PLAYGROUND"));
                oEstrut.setPossuiAguaIndividualizada(rs.getString("POSSUI_AGUA_INDIVIDUALIZADA"));
                oEstrut.setPossuiPiscina(rs.getString("POSSUI_PISCINA"));
                oEstrut.setPossuiPortaoEletronico(rs.getString("POSSUI_PORTAO_ELETRONICO"));
                oEstrut.setPossuiGasEncanado(rs.getString("POSSUI_GAS_ENCANADO"));
                oEstrut.setPossuiColetaSeletivaLixo(rs.getString("POSSUI_COLETA_SELETIVA_LIXO"));
                oEstrut.setPossuiColetaOleoCozinha(rs.getString("POSSUI_COLETA_OLEO_COZINHA"));
                oEstrut.setDiferenciais(rs.getString("DIFERENCIAIS"));
                
                list.add(oEstrut);
                
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao carregar dados", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(EstruturasVO oEstrut) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "UPDATE estruturas "
                    + "SET                                  "
                    + "POSSUI_PORTARIA = ?,                 "
                    + "POSSUI_ELEVADOR = ?,                 "
                    + "POSSUI_CERCA_ELETRICA = ?,           "
                    + "POSSUI_CIRCUITO_TV_FECHADO = ?,      "
                    + "QTD_APT = ?,                         "
                    + "QTD_COBERTURA = ?,                   "
                    + "TIPO_PORTARIA = ?,                   "
                    + "FUNCIONAMENTO_HORAS_PORTARIA = ?,    "
                    + "VAGAS_GARAGEM = ?,                   "
                    + "TIPO_GARAGEM = ?,                    "
                    + "QTD_ELEVADORES = ?,                  "
                    + "NOME_CONSTRUTORA = ?,                "
                    + "ANO_CONSTRUCAO = ?,                  "
                    + "AREA_PRIVATIVA = ?,                  "
                    + "AREA_PRIVATIVA_COBERTA = ?,          "
                    + "AREA_COMUM_COBERTA = ?,              "
                    + "AREA_TOTAL_COBERTURA = ?,            "
                    + "POSSUI_SALAO_FESTAS = ?,             "
                    + "POSSUI_PLAYGROUND = ?,               "
                    + "POSSUI_AGUA_INDIVIDUALIZADA = ?,     "
                    + "POSSUI_PISCINA = ?,                  "
                    + "POSSUI_PORTAO_ELETRONICO = ?,        "
                    + "POSSUI_GAS_ENCANADO = ?,             "
                    + "POSSUI_COLETA_SELETIVA_LIXO = ?,     "
                    + "POSSUI_COLETA_OLEO_COZINHA = ?,      "
                    + "DIFERENCIAIS = ?                     "
                    + "WHERE "
                    + "CODIGO_PESSOA_CONDOMINIO = ?        ";

            ps = cn.prepareStatement(comando);

            
            ps.setString(1, oEstrut.getPossuiPortaria());
            ps.setString(2, oEstrut.getPossuiElevador());
            ps.setString(3, oEstrut.getPossuiCercaEletrica());
            ps.setString(4, oEstrut.getPossuiTVFechado());
            ps.setInt(5, oEstrut.getQtdApto());
            ps.setInt(6, oEstrut.getQtdCobertura());
            ps.setString(7, oEstrut.getTipoPortaria());
            ps.setString(8, oEstrut.getFuncionamentoPortaria());
            ps.setInt(9, oEstrut.getVagasGaragem());
            ps.setString(10, oEstrut.getTipoGaragem());
            ps.setInt(11, oEstrut.getQtdElevadores());
            ps.setString(12, oEstrut.getNomeConstrutora());
            ps.setString(13, oEstrut.getAnoConstrucao());
            ps.setDouble(14, oEstrut.getAreaPrivativa());
            ps.setDouble(15, oEstrut.getAreaPrivativaCoberta());
            ps.setDouble(16, oEstrut.getAreaComunCoberta());
            ps.setDouble(17, oEstrut.getAreaTotalCoberta());
            ps.setString(18, oEstrut.getPossuiSalaoFestas());
            ps.setString(19, oEstrut.getPossuiPlayground());
            ps.setString(20, oEstrut.getPossuiAguaIndividualizada());
            ps.setString(21, oEstrut.getPossuiPiscina());
            ps.setString(22, oEstrut.getPossuiPortaoEletronico());
            ps.setString(23, oEstrut.getPossuiGasEncanado());
            ps.setString(24, oEstrut.getPossuiColetaSeletivaLixo());
            ps.setString(25, oEstrut.getPossuiColetaOleoCozinha());
            ps.setString(26, oEstrut.getDiferenciais());
            ps.setInt(27, oEstrut.getCodigoPessoaCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Falha ao atualizar registro", e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    public void setDelete(EstruturasVO oEstrut) throws DAOException {
        Connection cn = null;
        PreparedStatement ps = null;

        try {

            cn = getConnection();

            String comando = "DELETE FROM estruturas "
                    + "WHERE CODIGO_PESSOA_CONDOMINIO = ? ";

            ps = cn.prepareStatement(comando);

            ps.setInt(1, oEstrut.getCodigoPessoaCondominio());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erro ao deletar registro", e);
        } finally {
            closeResources(cn, ps, null);
        }

    }

}
