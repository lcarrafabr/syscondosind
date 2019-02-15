package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class FornecedoresVO {
    
    private Integer codigoFornecedor;
    private Integer codigoPessoaFornecedor;
    private Integer codigoTipoFornecedor;
    private String status;
    private String tipoPessoa;
    private String cpf_cnpj;
    private String inscEstadual;
    private String inscMunicipal;
    private String representante;
    private String prazoPagamento;
    private double custoMediofrete;
    private String observacao;
    
    
    /*Variaveis para usar na grade fornecedores*/
    private String nomeFornecedor;
    private Integer codigoGrupoFornecedor;
    private String nomeGrupoFornecedor;
    private String nomeTipoFornecedor;

    /**
     *Construtor usado para retorno de consultas e Updates
     * @param codigoFornecedor
     * @param codigoPessoaFornecedor
     * @param codigoTipoFornecedor
     * @param status
     * @param tipoPessoa
     * @param cpf_cnpj
     * @param inscEstadual
     * @param inscMunicipal
     * @param representante
     * @param prazoPagamento
     * @param custoMediofrete
     * @param observacao
     */

    public FornecedoresVO(Integer codigoFornecedor, Integer codigoPessoaFornecedor, Integer codigoTipoFornecedor, String status, String tipoPessoa, String cpf_cnpj, String inscEstadual, String inscMunicipal, String representante, String prazoPagamento, double custoMediofrete, String observacao) {
        this.codigoFornecedor = codigoFornecedor;
        this.codigoPessoaFornecedor = codigoPessoaFornecedor;
        this.codigoTipoFornecedor = codigoTipoFornecedor;
        this.status = status;
        this.tipoPessoa = tipoPessoa;
        this.cpf_cnpj = cpf_cnpj;
        this.inscEstadual = inscEstadual;
        this.inscMunicipal = inscMunicipal;
        this.representante = representante;
        this.prazoPagamento = prazoPagamento;
        this.custoMediofrete = custoMediofrete;
        this.observacao = observacao;
    }
    
    /**
     *Construtor usado para cadastrar dados
     * @param codigoPessoaFornecedor
     * @param codigoTipoFornecedor
     * @param status
     * @param tipoPessoa
     * @param cpf_cnpj
     * @param inscEstadual
     * @param inscMunicipal
     * @param representante
     * @param prazoPagamento
     * @param custoMediofrete
     * @param observacao
     */

    public FornecedoresVO(Integer codigoPessoaFornecedor, Integer codigoTipoFornecedor, String status, String tipoPessoa, String cpf_cnpj, String inscEstadual, String inscMunicipal, String representante, String prazoPagamento, double custoMediofrete, String observacao) {
        this.codigoPessoaFornecedor = codigoPessoaFornecedor;
        this.codigoTipoFornecedor = codigoTipoFornecedor;
        this.status = status;
        this.tipoPessoa = tipoPessoa;
        this.cpf_cnpj = cpf_cnpj;
        this.inscEstadual = inscEstadual;
        this.inscMunicipal = inscMunicipal;
        this.representante = representante;
        this.prazoPagamento = prazoPagamento;
        this.custoMediofrete = custoMediofrete;
        this.observacao = observacao;
    }

    /**
     *Construtor usado para excluir dados
     * @param codigoFornecedor
     */

    public FornecedoresVO(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    public Integer getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    public Integer getCodigoPessoaFornecedor() {
        return codigoPessoaFornecedor;
    }

    public void setCodigoPessoaFornecedor(Integer codigoPessoaFornecedor) {
        this.codigoPessoaFornecedor = codigoPessoaFornecedor;
    }

    public Integer getCodigoTipoFornecedor() {
        return codigoTipoFornecedor;
    }

    public void setCodigoTipoFornecedor(Integer codigoTipoFornecedor) {
        this.codigoTipoFornecedor = codigoTipoFornecedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getInscMunicipal() {
        return inscMunicipal;
    }

    public void setInscMunicipal(String inscMunicipal) {
        this.inscMunicipal = inscMunicipal;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(String prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public double getCustoMediofrete() {
        return custoMediofrete;
    }

    public void setCustoMediofrete(double custoMediofrete) {
        this.custoMediofrete = custoMediofrete;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public Integer getCodigoGrupoFornecedor() {
        return codigoGrupoFornecedor;
    }

    public void setCodigoGrupoFornecedor(Integer codigoGrupoFornecedor) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
    }

    public String getNomeGrupoFornecedor() {
        return nomeGrupoFornecedor;
    }

    public void setNomeGrupoFornecedor(String nomeGrupoFornecedor) {
        this.nomeGrupoFornecedor = nomeGrupoFornecedor;
    }

    public String getNomeTipoFornecedor() {
        return nomeTipoFornecedor;
    }

    public void setNomeTipoFornecedor(String nomeTipoFornecedor) {
        this.nomeTipoFornecedor = nomeTipoFornecedor;
    }
    
    
    
}
