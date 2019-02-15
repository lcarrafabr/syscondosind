package br.com.syscondosind.vo;

import java.time.LocalDate;

/**
 *
 * @author Luciano Carrfa Benfica
 */
public class ConselhoCondominioVO {
    
    private Integer codigoConselhoCond;
    private Integer codigoPessoa;
    private String cargo;
    private LocalDate dataIniciomandato;
    private LocalDate dataterminoMandato;
    private String observacao;
    
    
    /****Variaveis usadas para preencher a grade****/
    private String nomePessoaConselho;
    private String statusFiltro;
    private String dataIniFormat;
    private String dataFimFormat;
    
    
    /**
     *Construtor usado para Cadastrar
     * @param codigoPessoa
     * @param cargo
     * @param dataIniciomandato
     * @param dataterminoMandato
     * @param observacao
     */

    public ConselhoCondominioVO(Integer codigoPessoa, String cargo, LocalDate dataIniciomandato, LocalDate dataterminoMandato, String observacao) {
        this.codigoPessoa = codigoPessoa;
        this.cargo = cargo;
        this.dataIniciomandato = dataIniciomandato;
        this.dataterminoMandato = dataterminoMandato;
        this.observacao = observacao;
    }

    /**
     *Construtor usado para retorno de consultas e Updates
     * @param codigoConselhoCond
     * @param codigoPessoa
     * @param cargo
     * @param dataIniciomandato
     * @param dataterminoMandato
     * @param observacao
     */

    public ConselhoCondominioVO(Integer codigoConselhoCond, Integer codigoPessoa, String cargo, LocalDate dataIniciomandato, LocalDate dataterminoMandato, String observacao) {
        this.codigoConselhoCond = codigoConselhoCond;
        this.codigoPessoa = codigoPessoa;
        this.cargo = cargo;
        this.dataIniciomandato = dataIniciomandato;
        this.dataterminoMandato = dataterminoMandato;
        this.observacao = observacao;
    }
    
    /**
     *Construtor usado para deletar registros
     * @param codigoConselhoCond
     */

    public ConselhoCondominioVO(Integer codigoConselhoCond) {
        this.codigoConselhoCond = codigoConselhoCond;
    }
    
    public Integer getCodigoConselhoCond() {
        return codigoConselhoCond;
    }

    public void setCodigoConselhoCond(Integer codigoConselhoCond) {
        this.codigoConselhoCond = codigoConselhoCond;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataIniciomandato() {
        return dataIniciomandato;
    }

    public void setDataIniciomandato(LocalDate dataIniciomandato) {
        this.dataIniciomandato = dataIniciomandato;
    }

    public LocalDate getDataterminoMandato() {
        return dataterminoMandato;
    }

    public void setDataterminoMandato(LocalDate dataterminoMandato) {
        this.dataterminoMandato = dataterminoMandato;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNomePessoaConselho() {
        return nomePessoaConselho;
    }

    public void setNomePessoaConselho(String nomePessoaConselho) {
        this.nomePessoaConselho = nomePessoaConselho;
    }

    public String getStatusFiltro() {
        return statusFiltro;
    }

    public void setStatusFiltro(String statusFiltro) {
        this.statusFiltro = statusFiltro;
    }

    public String getDataIniFormat() {
        return dataIniFormat;
    }

    public void setDataIniFormat(String dataIniFormat) {
        this.dataIniFormat = dataIniFormat;
    }

    public String getDataFimFormat() {
        return dataFimFormat;
    }

    public void setDataFimFormat(String dataFimFormat) {
        this.dataFimFormat = dataFimFormat;
    }
 
}
