package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.FornecedoresVO;
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
public class FornecedoresDAO extends AbstractDAO {

    /**
     * Salva os fornecedores no banco de dados
     *
     * @param oForn
     * @throws br.com.syscondosind.persistence.DAOException
     */
    public void save(FornecedoresVO oForn) throws DAOException {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = getConnection();

            String comando = "INSERT INTO FORNECEDORES ("
                    + "CODIGO_PESSOA_FORNECEDOR, "
                    + "CODIGO_TIPO_FORNECEDOR, "
                    + "STATUS, "
                    + "TIPO_PESSOA, "
                    + "CNPJ_CPF, "
                    + "INSC_ESTADUAL, "
                    + "INSC_MUNICIPAL, "
                    + "REPRESENTANTE, "
                    + "PRAZO_PAGAMENTO, "
                    + "CUSTO_MEDIO_FRETE, "
                    + "OBSERVACAO "
                    + ") "
                    + "VALUES ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "? "
                    + ") ";
            ps = cn.prepareStatement(comando);

            ps.setInt(1, oForn.getCodigoPessoaFornecedor());
            ps.setInt(2, oForn.getCodigoTipoFornecedor());
            ps.setString(3, oForn.getStatus());
            ps.setString(4, oForn.getTipoPessoa());
            ps.setString(5, oForn.getCpf_cnpj());
            ps.setString(6, oForn.getInscEstadual());
            ps.setString(7, oForn.getInscMunicipal());
            ps.setString(8, oForn.getRepresentante());
            ps.setString(9, oForn.getPrazoPagamento());
            ps.setDouble(10, oForn.getCustoMediofrete());
            ps.setString(11, oForn.getObservacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro no Save.\n" + e, e);
        } finally {
            closeResources(cn, ps, null);
        }
    }
    
    public List<FornecedoresVO> findAll() throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT F.CODIGO_FORNECEDOR, F.CODIGO_PESSOA_FORNECEDOR, P.NOME_PESSOA, F.CODIGO_TIPO_FORNECEDOR, "
                    + "T.TIPO_FORNECEDOR, T.CODIGO_GRUPO_FORNECEDOR, G.GRUPO_FORNECEDOR,F.STATUS, "
                    + "F.TIPO_PESSOA, F.CNPJ_CPF, F.INSC_ESTADUAL, F.INSC_MUNICIPAL, F.REPRESENTANTE, "
                    + "F.PRAZO_PAGAMENTO, F.CUSTO_MEDIO_FRETE, F.OBSERVACAO "
                    + "FROM FORNECEDORES F "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = F.CODIGO_PESSOA_FORNECEDOR "
                    + "INNER JOIN TIPOS_FORNECEDORES T ON T.CODIGO_TIPO_FORNECEDOR = F.CODIGO_TIPO_FORNECEDOR "
                    + "INNER JOIN GRUPOS_FORNECEDORES G ON G.CODIGO_GRUPO_FORNECEDOR = T.CODIGO_GRUPO_FORNECEDOR ";
            
            ps = cn.prepareStatement(comando);
            
            rs = ps.executeQuery();
            
            List<FornecedoresVO>list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoFornecedor = rs.getInt("CODIGO_FORNECEDOR");
                Integer codigoPessoaFornecedor = rs.getInt("CODIGO_PESSOA_FORNECEDOR");
                Integer codigoTipoFornecedor = rs.getInt("CODIGO_TIPO_FORNECEDOR");
                String status = rs.getString("STATUS");
                String tipoPessoa = rs.getString("TIPO_PESSOA");
                String CPF_CNPJ = rs.getString("CNPJ_CPF");
                String inscEstadual = rs.getString("INSC_ESTADUAL");
                String inscMunicipal = rs.getString("INSC_MUNICIPAL");
                String representante = rs.getString("REPRESENTANTE");
                String prazoPagamento = rs.getString("PRAZO_PAGAMENTO");
                double custoMedioFrete = rs.getDouble("CUSTO_MEDIO_FRETE");
                String observacao = rs.getString("OBSERVACAO");
                
                FornecedoresVO oForn = new FornecedoresVO(codigoFornecedor, 
                        codigoPessoaFornecedor, 
                        codigoTipoFornecedor, 
                        status, 
                        tipoPessoa, 
                        CPF_CNPJ, 
                        inscEstadual, 
                        inscMunicipal, 
                        representante, 
                        prazoPagamento, 
                        custoMedioFrete, 
                        observacao
                );
                
                oForn.setNomeFornecedor(rs.getString("NOME_PESSOA"));
                oForn.setCodigoGrupoFornecedor(rs.getInt("CODIGO_GRUPO_FORNECEDOR"));
                oForn.setNomeGrupoFornecedor(rs.getString("GRUPO_FORNECEDOR"));
                oForn.setNomeTipoFornecedor(rs.getString("TIPO_FORNECEDOR"));
                
                list.add(oForn);
            }
            
            return list;
            
        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<FornecedoresVO> findAllFornecedor(Integer codigoForn) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT F.CODIGO_FORNECEDOR, F.CODIGO_PESSOA_FORNECEDOR, P.NOME_PESSOA, F.CODIGO_TIPO_FORNECEDOR, "
                    + "T.TIPO_FORNECEDOR, T.CODIGO_GRUPO_FORNECEDOR, G.GRUPO_FORNECEDOR,F.STATUS, "
                    + "F.TIPO_PESSOA, F.CNPJ_CPF, F.INSC_ESTADUAL, F.INSC_MUNICIPAL, F.REPRESENTANTE, "
                    + "F.PRAZO_PAGAMENTO, F.CUSTO_MEDIO_FRETE, F.OBSERVACAO "
                    + "FROM FORNECEDORES F "
                    + "INNER JOIN PESSOAS P ON P.CODIGO_PESSOA = F.CODIGO_PESSOA_FORNECEDOR "
                    + "INNER JOIN TIPOS_FORNECEDORES T ON T.CODIGO_TIPO_FORNECEDOR = F.CODIGO_TIPO_FORNECEDOR "
                    + "INNER JOIN GRUPOS_FORNECEDORES G ON G.CODIGO_GRUPO_FORNECEDOR = T.CODIGO_GRUPO_FORNECEDOR "
                    + "WHERE F.CODIGO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, codigoForn);
            
            rs = ps.executeQuery();
            
            List<FornecedoresVO>list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoFornecedor = rs.getInt("CODIGO_FORNECEDOR");
                Integer codigoPessoaFornecedor = rs.getInt("CODIGO_PESSOA_FORNECEDOR");
                Integer codigoTipoFornecedor = rs.getInt("CODIGO_TIPO_FORNECEDOR");
                String status = rs.getString("STATUS");
                String tipoPessoa = rs.getString("TIPO_PESSOA");
                String CPF_CNPJ = rs.getString("CNPJ_CPF");
                String inscEstadual = rs.getString("INSC_ESTADUAL");
                String inscMunicipal = rs.getString("INSC_MUNICIPAL");
                String representante = rs.getString("REPRESENTANTE");
                String prazoPagamento = rs.getString("PRAZO_PAGAMENTO");
                double custoMedioFrete = rs.getDouble("CUSTO_MEDIO_FRETE");
                String observacao = rs.getString("OBSERVACAO");
                
                FornecedoresVO oForn = new FornecedoresVO(codigoFornecedor, 
                        codigoPessoaFornecedor, 
                        codigoTipoFornecedor, 
                        status, 
                        tipoPessoa, 
                        CPF_CNPJ, 
                        inscEstadual, 
                        inscMunicipal, 
                        representante, 
                        prazoPagamento, 
                        custoMedioFrete, 
                        observacao
                );
                
                oForn.setNomeFornecedor(rs.getString("NOME_PESSOA"));
                oForn.setCodigoGrupoFornecedor(rs.getInt("CODIGO_GRUPO_FORNECEDOR"));
                oForn.setNomeGrupoFornecedor(rs.getString("GRUPO_FORNECEDOR"));
                oForn.setNomeTipoFornecedor(rs.getString("TIPO_FORNECEDOR"));
                
                list.add(oForn);
            }
            
            return list;
            
        } catch (SQLException e) {
            throw new DAOException("Erro no findAll.\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(FornecedoresVO oForn) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection(); 
            
            String comando = "UPDATE FORNECEDORES "
                    + "SET "
                    + "CODIGO_PESSOA_FORNECEDOR = ?, "
                    + "CODIGO_TIPO_FORNECEDOR = ?, "
                    + "STATUS = ?, "
                    + "TIPO_PESSOA = ?, "
                    + "CNPJ_CPF = ?, "
                    + "INSC_ESTADUAL = ?, "
                    + "INSC_MUNICIPAL = ?, "
                    + "REPRESENTANTE = ?, "
                    + "PRAZO_PAGAMENTO = ?, "
                    + "CUSTO_MEDIO_FRETE = ?, "
                    + "OBSERVACAO = ? "
                    + "WHERE "
                    + "CODIGO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oForn.getCodigoPessoaFornecedor());
            ps.setInt(2, oForn.getCodigoTipoFornecedor());
            ps.setString(3, oForn.getStatus());
            ps.setString(4, oForn.getTipoPessoa());
            ps.setString(5, oForn.getCpf_cnpj());
            ps.setString(6, oForn.getInscEstadual());
            ps.setString(7, oForn.getInscMunicipal());
            ps.setString(8, oForn.getRepresentante());
            ps.setString(9, oForn.getPrazoPagamento());
            ps.setDouble(10, oForn.getCustoMediofrete());
            ps.setString(11, oForn.getObservacao());
            ps.setInt(12, oForn.getCodigoFornecedor());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no update.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(FornecedoresVO oForn) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM FORNECEDORES "
                    + "WHERE CODIGO_FORNECEDOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oForn.getCodigoFornecedor());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erro no delete.\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }

}
