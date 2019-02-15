package br.com.syscondosind.vo;

import java.time.LocalDate;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class MoradoresVO {
    
    private Integer codigoMorador;
    private Integer codigoPessoa;
    private Integer codigoTorre;
    private Integer codigoApto;
    private String cpf;
    private String rg;
    private String possuiVeiculo;
    private String tipomorador;
    private LocalDate datanascimento;
    private Integer codigoPessoaproprietario;
    
    //*******VARIAVEIS USADAS PARA CARREGAR GRADE****************************
    private String nomePessoa;
    private String Torre;
    private String Apto;
    
    /**
     * @param codigoMorador
     * @param codigoPessoa
     * @param codigoTorre
     * @param codigoApto
     * @param cpf
     * @param rg
     * @param possuiVeiculo
     * @param tipomorador
     * @param datanascimento
     * @param codigoPessoaproprietario
     */
    
    public MoradoresVO(Integer codigoMorador, Integer codigoPessoa, Integer codigoTorre, Integer codigoApto, String cpf, String rg, String possuiVeiculo, String tipomorador, LocalDate datanascimento, Integer codigoPessoaproprietario) {
        this.codigoMorador = codigoMorador;
        this.codigoPessoa = codigoPessoa;
        this.codigoTorre = codigoTorre;
        this.codigoApto = codigoApto;
        this.cpf = cpf;
        this.rg = rg;
        this.possuiVeiculo = possuiVeiculo;
        this.tipomorador = tipomorador;
        this.datanascimento = datanascimento;
        this.codigoPessoaproprietario = codigoPessoaproprietario;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getTorre() {
        return Torre;
    }

    public void setTorre(String Torre) {
        this.Torre = Torre;
    }

    public String getApto() {
        return Apto;
    }

    public void setApto(String Apto) {
        this.Apto = Apto;
    }

    public Integer getCodigoMorador() {
        return codigoMorador;
    }

    public void setCodigoMorador(Integer codigoMorador) {
        this.codigoMorador = codigoMorador;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public Integer getCodigoTorre() {
        return codigoTorre;
    }

    public void setCodigoTorre(Integer codigoTorre) {
        this.codigoTorre = codigoTorre;
    }

    public Integer getCodigoApto() {
        return codigoApto;
    }

    public void setCodigoApto(Integer codigoApto) {
        this.codigoApto = codigoApto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getPossuiVeiculo() {
        return possuiVeiculo;
    }

    public void setPossuiVeiculo(String possuiVeiculo) {
        this.possuiVeiculo = possuiVeiculo;
    }

    public String getTipomorador() {
        return tipomorador;
    }

    public void setTipomorador(String tipomorador) {
        this.tipomorador = tipomorador;
    }

    public LocalDate getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(LocalDate datanascimento) {
        this.datanascimento = datanascimento;
    }

    public Integer getCodigoPessoaproprietario() {
        return codigoPessoaproprietario;
    }

    public void setCodigoPessoaproprietario(Integer codigoPessoaproprietario) {
        this.codigoPessoaproprietario = codigoPessoaproprietario;
    }
    
    
    
}
