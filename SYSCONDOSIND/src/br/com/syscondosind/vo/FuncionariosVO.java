package br.com.syscondosind.vo;

import java.time.LocalDate;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class FuncionariosVO {

    private Integer codigoFuncionario;
    private Integer codigoPessoa;
    private LocalDate dataAdmissao;
    private LocalDate dataDesligamento;
    private String status;
    private String carteiraTrabalho;
    private String tipoFuncionario;
    private double prolabore;
    private int horasTrabalho;
    private String cargo;
    private String abrePedidosOcorrencia;

    /*Variaveis adversas*/
    private String dataAdmissaoFormatada;//Data em formato BR para mostrar na grade
    private String dataDesligamentoFormatada;//Data em formato BR para mostrar na grade
    private String nomeFuncionario;//Usado para sobreescrever o metodo ToString e retornar o nome do funcionario nos outros forms

    /**
     * Construtor usado para cadastrar registro
     *
     * @param codigoPessoa
     * @param dataAdmissao
     * @param dataDesligamento
     * @param status
     * @param carteiraTrabalho
     * @param tipoFuncionario
     * @param prolabore
     * @param horasTrabalho
     * @param cargo
     * @param abrePedidosOcorrencia
     */
    public FuncionariosVO(Integer codigoPessoa, LocalDate dataAdmissao, LocalDate dataDesligamento, String status, String carteiraTrabalho, String tipoFuncionario, double prolabore, int horasTrabalho, String cargo, String abrePedidosOcorrencia) {
        this.codigoPessoa = codigoPessoa;
        this.dataAdmissao = dataAdmissao;
        this.dataDesligamento = dataDesligamento;
        this.status = status;
        this.carteiraTrabalho = carteiraTrabalho;
        this.tipoFuncionario = tipoFuncionario;
        this.prolabore = prolabore;
        this.horasTrabalho = horasTrabalho;
        this.cargo = cargo;
        this.abrePedidosOcorrencia = abrePedidosOcorrencia;
    }

    /**
     * Construtor usado para retorno de dados e updates
     *
     * @param codigoFuncionario
     * @param codigoPessoa
     * @param dataAdmissao
     * @param dataDesligamento
     * @param status
     * @param carteiraTrabalho
     * @param tipoFuncionario
     * @param prolabore
     * @param horasTrabalho
     * @param cargo
     * @param abrePedidosOcorrencia
     */
    public FuncionariosVO(Integer codigoFuncionario, Integer codigoPessoa, LocalDate dataAdmissao, LocalDate dataDesligamento, String status, String carteiraTrabalho, String tipoFuncionario, double prolabore, int horasTrabalho, String cargo, String abrePedidosOcorrencia) {
        this.codigoFuncionario = codigoFuncionario;
        this.codigoPessoa = codigoPessoa;
        this.dataAdmissao = dataAdmissao;
        this.dataDesligamento = dataDesligamento;
        this.status = status;
        this.carteiraTrabalho = carteiraTrabalho;
        this.tipoFuncionario = tipoFuncionario;
        this.prolabore = prolabore;
        this.horasTrabalho = horasTrabalho;
        this.cargo = cargo;
        this.abrePedidosOcorrencia = abrePedidosOcorrencia;
    }

    /**
     * Construtor usado para deletar registros
     *
     * @param codigoFuncionario
     */
    public FuncionariosVO(Integer codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    @Override
    public String toString() {
        return nomeFuncionario;
    }

    public Integer getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(Integer codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDesligamento() {
        return dataDesligamento;
    }

    public void setDataDesligamento(LocalDate dataDesligamento) {
        this.dataDesligamento = dataDesligamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarteiraTrabalho() {
        return carteiraTrabalho;
    }

    public void setCarteiraTrabalho(String carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public double getProlabore() {
        return prolabore;
    }

    public void setProlabore(double prolabore) {
        this.prolabore = prolabore;
    }

    public int getHorasTrabalho() {
        return horasTrabalho;
    }

    public void setHorasTrabalho(int horasTrabalho) {
        this.horasTrabalho = horasTrabalho;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAbrePedidosOcorrencia() {
        return abrePedidosOcorrencia;
    }

    public void setAbrePedidosOcorrencia(String abrePedidosOcorrencia) {
        this.abrePedidosOcorrencia = abrePedidosOcorrencia;
    }

    public String getDataAdmissaoFormatada() {
        return dataAdmissaoFormatada;
    }

    public void setDataAdmissaoFormatada(String dataAdmissaoFormatada) {
        this.dataAdmissaoFormatada = dataAdmissaoFormatada;
    }

    public String getDataDesligamentoFormatada() {
        return dataDesligamentoFormatada;
    }

    public void setDataDesligamentoFormatada(String dataDesligamentoFormatada) {
        this.dataDesligamentoFormatada = dataDesligamentoFormatada;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

}
