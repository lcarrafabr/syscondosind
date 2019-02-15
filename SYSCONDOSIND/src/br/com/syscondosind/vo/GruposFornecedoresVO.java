package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class GruposFornecedoresVO {

    private Integer codigoGrupoFornecedor;
    private String nomeGrupo;
    private String observacao;

    /**
     * Construtor usado para cadastrar item
     *
     * @param nomeGrupo
     * @param observacao
     */
    public GruposFornecedoresVO(String nomeGrupo, String observacao) {
        this.nomeGrupo = nomeGrupo;
        this.observacao = observacao;
    }

    /**
     * Construtor usado para retorno de itens e update
     *
     * @param codigoGrupoFornecedor
     * @param nomeGrupo
     * @param observacao
     */
    public GruposFornecedoresVO(Integer codigoGrupoFornecedor, String nomeGrupo, String observacao) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
        this.nomeGrupo = nomeGrupo;
        this.observacao = observacao;
    }

    /**
     * Construtor usado para deletar registro
     *
     * @param codigoGrupoFornecedor
     */
    public GruposFornecedoresVO(Integer codigoGrupoFornecedor) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
    }

    @Override
    public String toString() {
        return nomeGrupo;
    }

    public Integer getCodigoGrupoFornecedor() {
        return codigoGrupoFornecedor;
    }

    public void setCodigoGrupoFornecedor(Integer codigoGrupoFornecedor) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
