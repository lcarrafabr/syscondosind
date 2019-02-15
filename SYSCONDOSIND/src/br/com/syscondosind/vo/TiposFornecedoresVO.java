package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class TiposFornecedoresVO {

    private Integer codigoTipoFornecedor;
    private Integer codigoGrupoFornecedor;
    private String tipoFornecedor;
    /**
     * * VARIAVEIS USADAS PARA RETORNO DE CONSULTA ******
     */
    private String nomeGrupo;

    /**
     * Construtor usado para retorno de consultas e Updates
     *
     * @param codigoTipoFornecedor
     * @param codigoGrupoFornecedor
     * @param tipoFornecedor
     */
    public TiposFornecedoresVO(Integer codigoTipoFornecedor, Integer codigoGrupoFornecedor, String tipoFornecedor) {
        this.codigoTipoFornecedor = codigoTipoFornecedor;
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
        this.tipoFornecedor = tipoFornecedor;
    }

    /**
     * Construtor usado para cadastrar dados
     *
     * @param codigoGrupoFornecedor
     * @param tipoFornecedor
     */
    public TiposFornecedoresVO(Integer codigoGrupoFornecedor, String tipoFornecedor) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
        this.tipoFornecedor = tipoFornecedor;
    }

    /**
     * Construtor usado para excluir dados
     *
     * @param codigoTipoFornecedor
     */
    public TiposFornecedoresVO(Integer codigoTipoFornecedor) {
        this.codigoTipoFornecedor = codigoTipoFornecedor;
    }

    @Override
    public String toString() {
        return tipoFornecedor;
    }

    public Integer getCodigoTipoFornecedor() {
        return codigoTipoFornecedor;
    }

    public void setCodigoTipoFornecedor(Integer codigoTipoFornecedor) {
        this.codigoTipoFornecedor = codigoTipoFornecedor;
    }

    public Integer getCodigoGrupoFornecedor() {
        return codigoGrupoFornecedor;
    }

    public void setCodigoGrupoFornecedor(Integer codigoGrupoFornecedor) {
        this.codigoGrupoFornecedor = codigoGrupoFornecedor;
    }

    public String getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(String tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

}
