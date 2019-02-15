package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class PesquisaMoradorVeiculoVO {
    
    private Integer codigo_pessoa;
    private String nomePessoa;
    private String status;
    private String tipoPessoa;
    private String nomeTorre;
    private String aptoSetor;
    private String possuiVeiculo;

    public PesquisaMoradorVeiculoVO(Integer codigo_pessoa, String nomePessoa, String status, String tipoPessoa, String nomeTorre, String aptoSetor, String possuiVeiculo) {
        this.codigo_pessoa = codigo_pessoa;
        this.nomePessoa = nomePessoa;
        this.status = status;
        this.tipoPessoa = tipoPessoa;
        this.nomeTorre = nomeTorre;
        this.aptoSetor = aptoSetor;
        this.possuiVeiculo = possuiVeiculo;
    }

    public Integer getCodigo_pessoa() {
        return codigo_pessoa;
    }

    public void setCodigo_pessoa(Integer codigo_pessoa) {
        this.codigo_pessoa = codigo_pessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
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

    public String getNomeTorre() {
        return nomeTorre;
    }

    public void setNomeTorre(String nomeTorre) {
        this.nomeTorre = nomeTorre;
    }

    public String getAptoSetor() {
        return aptoSetor;
    }

    public void setAptoSetor(String aptoSetor) {
        this.aptoSetor = aptoSetor;
    }

    public String getPossuiVeiculo() {
        return possuiVeiculo;
    }

    public void setPossuiVeiculo(String possuiVeiculo) {
        this.possuiVeiculo = possuiVeiculo;
    }    
}
