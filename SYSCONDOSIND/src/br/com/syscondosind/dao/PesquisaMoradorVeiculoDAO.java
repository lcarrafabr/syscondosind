package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.PesquisaMoradorVeiculoVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class PesquisaMoradorVeiculoDAO extends AbstractDAO{
    
    public List<PesquisaMoradorVeiculoVO> findAll(String nomePessoa) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT P.CODIGO_PESSOA, P.NOME_PESSOA, P.STATUS, P.TIPO_PESSOA, T.NOME_TORRE, A.NOME_APARTAMENTO, M.POSSUI_VEICULO "
                    + "FROM pessoas P "
                    + "INNER JOIN moradores M ON M.CODIGO_PESSOA = P.CODIGO_PESSOA "
                    + "INNER JOIN torres T ON T.CODIGO_TORRE = M.CODIGO_TORRE "
                    + "INNER JOIN apartamentos A ON A.CODIGO_APARTAMENTO = M.CODIGO_APARTAMENTO "
                    + "WHERE P.STATUS = 'ATIVO' "
                    + "AND M.POSSUI_VEICULO = 'SIM' ";
            
            int quantParam = 0;
            
            if(nomePessoa != null){
                comando = comando + "AND P.NOME_PESSOA LIKE ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if(nomePessoa != null){
                quantParam = quantParam + 1;
                ps.setString(quantParam, "%" + nomePessoa + "%");
            }
            
            rs = ps.executeQuery();
            
            List<PesquisaMoradorVeiculoVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoPessoa = rs.getInt("CODIGO_PESSOA");
                String nomePessoa2 = rs.getString("NOME_PESSOA");
                String status = rs.getString("STATUS");
                String tipoPessoa = rs.getString("TIPO_PESSOA");
                String nomeTorre = rs.getString("NOME_TORRE");
                String nomeApto = rs.getString("NOME_APARTAMENTO");
                String possuiVeiculo = rs.getString("POSSUI_VEICULO");
                
                PesquisaMoradorVeiculoVO oPesquisa = new PesquisaMoradorVeiculoVO(codigoPessoa, 
                        nomePessoa2, 
                        status, 
                        tipoPessoa, 
                        nomeTorre, 
                        nomeApto, 
                        possuiVeiculo
                );
                list.add(oPesquisa);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no FindAll\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }        
    }
    
    public List<PesquisaMoradorVeiculoVO> findAllPessoa(int codigoPessoa) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "SELECT P.CODIGO_PESSOA, P.NOME_PESSOA, P.STATUS, P.TIPO_PESSOA, T.NOME_TORRE, A.NOME_APARTAMENTO, M.POSSUI_VEICULO "
                    + "FROM pessoas P "
                    + "INNER JOIN moradores M ON M.CODIGO_PESSOA = P.CODIGO_PESSOA "
                    + "INNER JOIN torres T ON T.CODIGO_TORRE = M.CODIGO_TORRE "
                    + "INNER JOIN apartamentos A ON A.CODIGO_APARTAMENTO = M.CODIGO_APARTAMENTO "
                    + "WHERE P.STATUS = 'ATIVO' "
                    + "AND M.POSSUI_VEICULO = 'SIM' ";
            
            int quantParam = 0;
            
            if(codigoPessoa > 0){
                comando = comando + "AND P.CODIGO_PESSOA = ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if(codigoPessoa > 0){
                quantParam = quantParam + 1;
                ps.setInt(quantParam, codigoPessoa);
            }
            
            rs = ps.executeQuery();
            
            List<PesquisaMoradorVeiculoVO> list = new ArrayList<>();
            
            while (rs.next()) {                
                
                Integer codigoPessoa2 = rs.getInt("CODIGO_PESSOA");
                String nomePessoa2 = rs.getString("NOME_PESSOA");
                String status = rs.getString("STATUS");
                String tipoPessoa = rs.getString("TIPO_PESSOA");
                String nomeTorre = rs.getString("NOME_TORRE");
                String nomeApto = rs.getString("NOME_APARTAMENTO");
                String possuiVeiculo = rs.getString("POSSUI_VEICULO");
                
                PesquisaMoradorVeiculoVO oPesquisa = new PesquisaMoradorVeiculoVO(codigoPessoa2, 
                        nomePessoa2, 
                        status, 
                        tipoPessoa, 
                        nomeTorre, 
                        nomeApto, 
                        possuiVeiculo
                );
                list.add(oPesquisa);
            }
            return list;
        } catch (Exception e) {
            throw new DAOException("Erro no findAllPessoa\n"+e, e);
        }finally{
            closeResources(cn, ps, rs);
        }        
    }
    
}
