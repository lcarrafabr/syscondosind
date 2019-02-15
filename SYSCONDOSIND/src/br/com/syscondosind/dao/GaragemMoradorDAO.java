package br.com.syscondosind.dao;

import br.com.syscondosind.persistence.AbstractDAO;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.GaragemMoradorVO;
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
public class GaragemMoradorDAO extends AbstractDAO{
    
   public void save(GaragemMoradorVO oGaragem) throws DAOException{
       
       Connection cn = null;
       PreparedStatement ps = null;
       
       try {
           cn = getConnection();
           
           String comando = "INSERT INTO garagem_morador ("
                   + "CODIGO_PESSOA_MORADOR, "
                   + "CODIGO_VEICULO_MORADOR, "
                   + "CODIGO_VAGA_GARAGEM, "
                   + "TIPO_VAGA, "
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
           
           ps.setInt(1, oGaragem.getCodigoPessoa());
           ps.setInt(2, oGaragem.getCodigoVeiculoMorador());
           ps.setInt(3, oGaragem.getCodigoVagaGaragem());
           ps.setString(4, oGaragem.getTipoVaga());
           ps.setString(5, oGaragem.getObservacao());
           
           ps.executeUpdate();
           
       } catch (SQLException e) {
           throw new DAOException("Erro no Save.\n"+e, e);
       }finally{
           closeResources(cn, ps, null);
       }
   }
   
   public List<GaragemMoradorVO> findAll() throws DAOException{
       
       Connection cn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
       
       try {
           cn = getConnection();
           
           String comando = "SELECT M.CODIGO_GARAGEM_MORADOR, M.CODIGO_PESSOA_MORADOR, P.NOME_PESSOA,  "
                   + "M.CODIGO_VEICULO_MORADOR, V.MARCA, V.MODELO, "
                   + "M.CODIGO_VAGA_GARAGEM, VG.NOME_VAGA, S.NOME_SETOR ,M.TIPO_VAGA, M.OBSERVACAO "
                   + "FROM garagem_morador M "
                   + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = M.CODIGO_PESSOA_MORADOR "
                   + "INNER JOIN veiculos V ON V.CODIGO_VEICULO = M.CODIGO_VEICULO_MORADOR "
                   + "INNER JOIN vagas_garagem VG ON VG.CODIGO_VAGA_GARAGEM = M.CODIGO_VAGA_GARAGEM "
                   + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = VG.CODIGO_SETOR_GARAGEM ";
           
           ps = cn.prepareStatement(comando);
           
           rs = ps.executeQuery();
           
           List<GaragemMoradorVO> list = new ArrayList<>();
           
           while (rs.next()) {               
               
               Integer codigoGaragemMorador = rs.getInt("CODIGO_GARAGEM_MORADOR");
               Integer codigoPessoaMorador = rs.getInt("CODIGO_PESSOA_MORADOR");
               Integer codigoVeiculoMorador = rs.getInt("CODIGO_VEICULO_MORADOR");
               Integer codigoVagaGaragem = rs.getInt("CODIGO_VAGA_GARAGEM");
               String tipoBaga = rs.getString("TIPO_VAGA");
               String observacao = rs.getString("OBSERVACAO");
               
               GaragemMoradorVO oGaragem = new GaragemMoradorVO(codigoGaragemMorador, 
                       codigoPessoaMorador, 
                       codigoVeiculoMorador, 
                       codigoVagaGaragem, 
                       tipoBaga, 
                       observacao);
               
               oGaragem.setNomePessoa(rs.getString("NOME_PESSOA"));
               oGaragem.setMarca(rs.getString("MARCA"));
               oGaragem.setModelo(rs.getString("MODELO"));
               oGaragem.setVeiculoMorador(rs.getString("MARCA").concat(" ").concat(rs.getString("MODELO")));
               oGaragem.setNomevaga(rs.getString("NOME_VAGA"));
               oGaragem.setNomeSetor(rs.getString("NOME_SETOR"));
               
               list.add(oGaragem);
               
           }
           
           return list;
           
       } catch (SQLException e) {
           throw new DAOException("Erro no findAll\n"+e, e);
       }finally{
           closeResources(cn, ps, rs);
       }
   }
   
   
   public List<GaragemMoradorVO> findGaragemMorador(int codigoGaragem) throws DAOException{
       
       Connection cn = null;
       PreparedStatement ps = null;
       ResultSet rs = null;
       
       try {
           cn = getConnection();
           
           String comando = "SELECT M.CODIGO_GARAGEM_MORADOR, M.CODIGO_PESSOA_MORADOR, P.NOME_PESSOA,  "
                   + "M.CODIGO_VEICULO_MORADOR, V.MARCA, V.MODELO, "
                   + "M.CODIGO_VAGA_GARAGEM, VG.NOME_VAGA, S.NOME_SETOR ,M.TIPO_VAGA, M.OBSERVACAO "
                   + "FROM garagem_morador M "
                   + "INNER JOIN pessoas P ON P.CODIGO_PESSOA = M.CODIGO_PESSOA_MORADOR "
                   + "INNER JOIN veiculos V ON V.CODIGO_VEICULO = M.CODIGO_VEICULO_MORADOR "
                   + "INNER JOIN vagas_garagem VG ON VG.CODIGO_VAGA_GARAGEM = M.CODIGO_VAGA_GARAGEM "
                   + "INNER JOIN setores_garagem S ON S.CODIGO_SETOR_GARAGEM = VG.CODIGO_SETOR_GARAGEM "
                   + "WHERE M.CODIGO_GARAGEM_MORADOR = ? ";
           
           ps = cn.prepareStatement(comando);
           
           ps.setInt(1, codigoGaragem);
           
           rs = ps.executeQuery();
           
           List<GaragemMoradorVO> list = new ArrayList<>();
           
           while (rs.next()) {               
               
               Integer codigoGaragemMorador = rs.getInt("CODIGO_GARAGEM_MORADOR");
               Integer codigoPessoaMorador = rs.getInt("CODIGO_PESSOA_MORADOR");
               Integer codigoVeiculoMorador = rs.getInt("CODIGO_VEICULO_MORADOR");
               Integer codigoVagaGaragem = rs.getInt("CODIGO_VAGA_GARAGEM");
               String tipoBaga = rs.getString("TIPO_VAGA");
               String observacao = rs.getString("OBSERVACAO");
               
               GaragemMoradorVO oGaragem = new GaragemMoradorVO(codigoGaragemMorador, 
                       codigoPessoaMorador, 
                       codigoVeiculoMorador, 
                       codigoVagaGaragem, 
                       tipoBaga, 
                       observacao);
               
               oGaragem.setNomePessoa(rs.getString("NOME_PESSOA"));
               oGaragem.setMarca(rs.getString("MARCA"));
               oGaragem.setModelo(rs.getString("MODELO"));
               oGaragem.setVeiculoMorador(rs.getString("MARCA").concat(" ").concat(rs.getString("MODELO")));
               oGaragem.setNomevaga(rs.getString("NOME_VAGA"));
               oGaragem.setNomeSetor(rs.getString("NOME_SETOR"));
               
               list.add(oGaragem);
               
           }
           
           return list;
           
       } catch (SQLException e) {
           throw new DAOException("Erro no findAll\n"+e, e);
       }finally{
           closeResources(cn, ps, rs);
       }
   }
   
   public void update(GaragemMoradorVO oGaragem) throws DAOException{
       
       Connection cn = null;
       PreparedStatement ps = null;
       
       try {
           cn = getConnection();
           
           String comando = "UPDATE garagem_morador "
                   + "SET "
                   + "CODIGO_PESSOA_MORADOR = ?, "
                   + "CODIGO_VEICULO_MORADOR = ?, "
                   + "CODIGO_VAGA_GARAGEM = ?, "
                   + "TIPO_VAGA = ?, "
                   + "OBSERVACAO = ? "
                   + "WHERE "
                   + "CODIGO_GARAGEM_MORADOR = ? ";
           
           ps = cn.prepareStatement(comando);
           
           ps.setInt(1, oGaragem.getCodigoPessoa());
           ps.setInt(2, oGaragem.getCodigoVeiculoMorador());
           ps.setInt(3, oGaragem.getCodigoVagaGaragem());
           ps.setString(4, oGaragem.getTipoVaga());
           ps.setString(5, oGaragem.getObservacao());
           ps.setInt(6, oGaragem.getCodigoGaragemMorador());
           
           ps.executeUpdate();
           
       } catch (SQLException e) {
           throw new DAOException("Erro no update.\n"+e, e);
       }finally{
           closeResources(cn, ps, null);
       }
   }
   
   public void delete(GaragemMoradorVO oGaragem) throws DAOException{
       
       Connection cn = null;
       PreparedStatement ps = null;
       
       try {
           cn = getConnection();
           
           String comando = "DELETE FROM garagem_morador "
                   + "WHERE CODIGO_GARAGEM_MORADOR = ? ";
           
           ps = cn.prepareStatement(comando);
           
           ps.setInt(1, oGaragem.getCodigoGaragemMorador());
           
           ps.executeUpdate();           
           
       } catch (Exception e) {
           throw new DAOException("Erro no delete.\n"+e, e);
       }finally{
           closeResources(cn, ps, null);
       }
   }
    
}
