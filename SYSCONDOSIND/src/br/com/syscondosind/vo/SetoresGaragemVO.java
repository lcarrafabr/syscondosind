package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class SetoresGaragemVO {

    /*Variaveis usadas para controle do formulario*/
    private Integer codigoSetorGaragem;
    private String nomeSetor;
    private String status;
    private String codigoSetor;
    private String observacao;
    /*Variaveis usadas para os resultados de setores*/
    private int qtdTotal;
    private int qtdLiberado;
    private int qtdManutencao;
    private int qtdInterditado;

    /*Variaveis Usadas na consulta de QTD de vagas por setor (USADO NO FORM VAGAS GARAGEM*/
    //A variavel nomeSetor ja existe e foi aproveitado.
    private int qtdVagas;

    public Integer getCodigoSetorGaragem() {
        return codigoSetorGaragem;
    }

    public void setCodigoSetorGaragem(Integer codigoSetorGaragem) {
        this.codigoSetorGaragem = codigoSetorGaragem;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getCodigoSetor() {
        return codigoSetor;
    }

    public void setCodigoSetor(String codigoSetor) {
        this.codigoSetor = codigoSetor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(int qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public int getQtdLiberado() {
        return qtdLiberado;
    }

    public void setQtdLiberado(int qtdLiberado) {
        this.qtdLiberado = qtdLiberado;
    }

    public int getQtdManutencao() {
        return qtdManutencao;
    }

    public void setQtdManutencao(int qtdManutencao) {
        this.qtdManutencao = qtdManutencao;
    }

    public int getQtdInterditado() {
        return qtdInterditado;
    }

    public void setQtdInterditado(int qtdInterditado) {
        this.qtdInterditado = qtdInterditado;
    }

    public int getQtdVagas() {
        return qtdVagas;
    }

    public void setQtdVagas(int qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    @Override
    public String toString() {
        return nomeSetor;
    }

}
