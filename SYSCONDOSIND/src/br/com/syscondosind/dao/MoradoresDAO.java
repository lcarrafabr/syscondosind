package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.MoradoresVO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class MoradoresDAO extends AbstractDAO{
    
    public void save(MoradoresVO oMorador) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            
            cn = getConnection();
            
            String comando = "INSERT INTO moradores ("
                    + "CODIGO_PESSOA, "
                    + "CODIGO_TORRE, "
                    + "CODIGO_APARTAMENTO, "
                    + "CPF, "
                    + "RG, "
                    + "POSSUI_VEICULO, "
                    + "TIPO_MORADOR, "
                    + "DATA_NASCIMENTO, "
                    + "CODIGO_PESSOA_PROPRIETARIO "
                    + ")"
                    + "VALUES ("
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
            
            ps.setInt(1, oMorador.getCodigoPessoa());
            ps.setInt(2, oMorador.getCodigoTorre());
            ps.setInt(3, oMorador.getCodigoApto());
            ps.setString(4, oMorador.getCpf());
            ps.setString(5, oMorador.getRg());
            ps.setString(6, oMorador.getPossuiVeiculo());
            ps.setString(7, oMorador.getTipomorador());
            ps.setDate(8, Date.valueOf(oMorador.getDatanascimento()));
            ps.setInt(9, oMorador.getCodigoPessoaproprietario());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao cadastrar morador\n"+e, e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    
    public List<MoradoresVO> findAll(String nomeMorador) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "select M.CODIGO_MORADOR, M.CODIGO_PESSOA,P.NOME_PESSOA, M.CODIGO_TORRE, T.NOME_TORRE  "
                    + ",M.CODIGO_APARTAMENTO, A.NOME_APARTAMENTO ,M.CPF, M.RG, M.POSSUI_VEICULO, M.TIPO_MORADOR, "
                    + "M.DATA_NASCIMENTO, M.CODIGO_PESSOA_PROPRIETARIO, P2.NOME_PESSOA "
                    + "from moradores M "
                    + "INNER JOIN pessoas P on P.CODIGO_PESSOA = M.CODIGO_PESSOA "
                    + "INNER JOIN pessoas P2 ON P2.CODIGO_PESSOA = M.CODIGO_PESSOA_PROPRIETARIO "
                    + "INNER JOIN torres T ON T.CODIGO_TORRE = M.CODIGO_TORRE "
                    + "INNER JOIN apartamentos A ON A.CODIGO_APARTAMENTO = M.CODIGO_APARTAMENTO ";
            
            int quantparam = 0;
            
            if(nomeMorador != null){
                comando = comando + "WHERE P.NOME_PESSOA LIKE ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if(nomeMorador != null){
                quantparam = quantparam + 1;
                ps.setString(quantparam, "%" + nomeMorador + "%");
                
            }
            
            rs = ps.executeQuery();
            
            List<MoradoresVO> list = new ArrayList<>();
            
            while (rs.next()) {
                
                Integer codigoMorador = rs.getInt("CODIGO_MORADOR");
                Integer codigo_pessoa = rs.getInt("CODIGO_PESSOA");
                Integer codigoTorre = rs.getInt("CODIGO_TORRE");
                Integer codigoApto = rs.getInt("CODIGO_APARTAMENTO");
                String cpf = rs.getString("CPF");
                String rg = rs.getString("RG");
                String possuiVeiculo = rs.getString("POSSUI_VEICULO");
                String tipoMorador = rs.getString("TIPO_MORADOR");
                LocalDate dataNascimento = rs.getDate("DATA_NASCIMENTO").toLocalDate();
                Integer codigoProprietario = rs.getInt("CODIGO_PESSOA_PROPRIETARIO");
                
                MoradoresVO oMorador = new MoradoresVO(codigoMorador, 
                        codigo_pessoa, 
                        codigoTorre, 
                        codigoApto, 
                        cpf, 
                        rg, 
                        possuiVeiculo, 
                        tipoMorador, 
                        dataNascimento, 
                        codigoProprietario
                );
                
                oMorador.setNomePessoa(rs.getString("NOME_PESSOA"));
                oMorador.setTorre(rs.getString("NOME_TORRE"));
                oMorador.setApto(rs.getString("NOME_APARTAMENTO"));
                oMorador.setTipomorador(rs.getString("TIPO_MORADOR"));
                
                list.add(oMorador);
                
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao gerar consulta de mnoradores\n", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public List<MoradoresVO> findMorador(int codigoMorador) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            cn = getConnection();
            
            String comando = "select M.CODIGO_MORADOR, M.CODIGO_PESSOA,P.NOME_PESSOA, M.CODIGO_TORRE, T.NOME_TORRE  "
                    + ",M.CODIGO_APARTAMENTO, A.NOME_APARTAMENTO ,M.CPF, M.RG, M.POSSUI_VEICULO, M.TIPO_MORADOR, "
                    + "M.DATA_NASCIMENTO, M.CODIGO_PESSOA_PROPRIETARIO, P2.NOME_PESSOA "
                    + "from moradores M "
                    + "INNER JOIN pessoas P on P.CODIGO_PESSOA = M.CODIGO_PESSOA "
                    + "INNER JOIN pessoas P2 ON P2.CODIGO_PESSOA = M.CODIGO_PESSOA_PROPRIETARIO "
                    + "INNER JOIN torres T ON T.CODIGO_TORRE = M.CODIGO_TORRE "
                    + "INNER JOIN apartamentos A ON A.CODIGO_APARTAMENTO = M.CODIGO_APARTAMENTO ";
            
            int quantparam = 0;
            
            if(codigoMorador > 0){
                comando = comando + "WHERE M.CODIGO_MORADOR = ? ";
            }
            
            ps = cn.prepareStatement(comando);
            
            if(codigoMorador > 0){
                quantparam = quantparam + 1;
                ps.setInt(quantparam, codigoMorador);
                
            }
            
            rs = ps.executeQuery();
            
            List<MoradoresVO> list = new ArrayList<>();
            
            while (rs.next()) {
                
//                Integer codigoMorador = rs.getInt("CODIGO_MORADOR");
                Integer codigo_pessoa = rs.getInt("CODIGO_PESSOA");
                Integer codigoTorre = rs.getInt("CODIGO_TORRE");
                Integer codigoApto = rs.getInt("CODIGO_APARTAMENTO");
                String cpf = rs.getString("CPF");
                String rg = rs.getString("RG");
                String possuiVeiculo = rs.getString("POSSUI_VEICULO");
                String tipoMorador = rs.getString("TIPO_MORADOR");
                LocalDate dataNascimento = rs.getDate("DATA_NASCIMENTO").toLocalDate();
                Integer codigoProprietario = rs.getInt("CODIGO_PESSOA_PROPRIETARIO");
                
                MoradoresVO oMorador = new MoradoresVO(codigoMorador, 
                        codigo_pessoa, 
                        codigoTorre, 
                        codigoApto, 
                        cpf, 
                        rg, 
                        possuiVeiculo, 
                        tipoMorador, 
                        dataNascimento, 
                        codigoProprietario
                );
                
                oMorador.setNomePessoa(rs.getString("NOME_PESSOA"));
                oMorador.setTorre(rs.getString("NOME_TORRE"));
                oMorador.setApto(rs.getString("NOME_APARTAMENTO"));
                oMorador.setTipomorador(rs.getString("TIPO_MORADOR"));
                
                list.add(oMorador);
                
            }
            return list;
            
        } catch (Exception e) {
            throw new DAOException("Erro ao gerar consulta de mnoradores\n", e);
        }finally{
            closeResources(cn, ps, rs);
        }
    }
    
    public void update(MoradoresVO oMorador) throws DAOException{
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "UPDATE moradores "
                    + "SET "
                    + "CODIGO_PESSOA = ?, "
                    + "CODIGO_TORRE = ?, "
                    + "CODIGO_APARTAMENTO = ?, "
                    + "CPF = ?, "
                    + "RG = ?, "
                    + "POSSUI_VEICULO = ?, "
                    + "TIPO_MORADOR = ?, "
                    + "DATA_NASCIMENTO = ?, "
                    + "CODIGO_PESSOA_PROPRIETARIO = ? "
                    + "WHERE "
                    + "CODIGO_MORADOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oMorador.getCodigoPessoa());
            ps.setInt(2, oMorador.getCodigoTorre());
            ps.setInt(3, oMorador.getCodigoApto());
            ps.setString(4, oMorador.getCpf());
            ps.setString(5, oMorador.getRg());
            ps.setString(6, oMorador.getPossuiVeiculo());
            ps.setString(7, oMorador.getTipomorador());
            ps.setDate(8, Date.valueOf(oMorador.getDatanascimento()));
            ps.setInt(9, oMorador.getCodigoPessoaproprietario());
            ps.setInt(10, oMorador.getCodigoMorador());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
    public void delete(MoradoresVO oMorador) throws DAOException{
        
        Connection cn = null;
        PreparedStatement ps = null;
        
        try {
            cn = getConnection();
            
            String comando = "DELETE FROM moradores "
                    + "WHERE CODIGO_MORADOR = ? ";
            
            ps = cn.prepareStatement(comando);
            
            ps.setInt(1, oMorador.getCodigoMorador());
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DAOException("Erro ao deletar registro", e);
        }finally{
            closeResources(cn, ps, null);
        }
    }
    
}
