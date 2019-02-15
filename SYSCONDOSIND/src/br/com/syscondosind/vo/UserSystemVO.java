package br.com.syscondosind.vo;

import br.com.syscondosind.funcoes.Funcoes;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class UserSystemVO {

    private Integer codigoPessoa;
    private String user;
    private String password;
    private String nivelAcesso;
    private LocalDate dataUltimaAlteracao;
    private String status;
    /*Variavel de retorno de consulta*/
    private String nomePessoa;

    /**/

    /**
     *Faz a criptografia da senha ao ser usado
     * @param codigoPessoa
     * @param user
     * @param password
     * @param nivelAcesso
     * @param dataUltimaAlteracao
     * @param status
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */

    public UserSystemVO(Integer codigoPessoa, String user, String password, String nivelAcesso, LocalDate dataUltimaAlteracao, String status) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.codigoPessoa = codigoPessoa;
        this.user = user;
        this.password = Funcoes.criptografiaSHA(password);
        this.nivelAcesso = nivelAcesso;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.status = status;
    }

    /**
     *Usado para as consultas, não retorna o hash da senha.
     * @param codigoPessoa
     * @param user
     * @param nivelAcesso
     * @param dataUltimaAlteracao
     * @param status
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public UserSystemVO(Integer codigoPessoa, String user, String nivelAcesso, LocalDate dataUltimaAlteracao, String status) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.codigoPessoa = codigoPessoa;
        this.user = user;
        this.nivelAcesso = nivelAcesso;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.status = status;
    }

    /**
     *usado para a tela de login
     * @param codigoPessoa
     * @param user
     * @param password
     * @param nivelAcesso
     * @param status
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public UserSystemVO(Integer codigoPessoa, String user, String password, String nivelAcesso, String status) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.codigoPessoa = codigoPessoa;
        this.user = user;
        this.password = password;
        this.nivelAcesso = nivelAcesso;
        this.status = status;
    }

    /**
     * Usado para excluir dados
     * @param codigoPessoa ****
     */
    public UserSystemVO(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public LocalDate getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDate dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

}
