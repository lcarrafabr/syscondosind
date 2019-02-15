package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class GaragemMoradorVO {
    
    private Integer codigoGaragemMorador;
    private Integer codigoPessoa;
    private Integer codigoVeiculoMorador;
    private Integer codigoVagaGaragem;
    private String tipoVaga;
    private String observacao;
/****variaveis usadas para preencher a tableview******/
    
    private String nomePessoa;
    private String marca;
    private String modelo;
    private String veiculoMorador;//concatenacao marca - modelo
    private String nomevaga;
    private String nomeSetor;
    
    public GaragemMoradorVO(Integer codigoGaragemMorador, Integer codigoPessoa, Integer codigoVeiculoMorador, Integer codigoVagaGaragem, String tipoVaga, String observacao) {
        this.codigoGaragemMorador = codigoGaragemMorador;
        this.codigoPessoa = codigoPessoa;
        this.codigoVeiculoMorador = codigoVeiculoMorador;
        this.codigoVagaGaragem = codigoVagaGaragem;
        this.tipoVaga = tipoVaga;
        this.observacao = observacao;
    }

    public Integer getCodigoGaragemMorador() {
        return codigoGaragemMorador;
    }

    public void setCodigoGaragemMorador(Integer codigoGaragemMorador) {
        this.codigoGaragemMorador = codigoGaragemMorador;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public Integer getCodigoVeiculoMorador() {
        return codigoVeiculoMorador;
    }

    public void setCodigoVeiculoMorador(Integer codigoVeiculoMorador) {
        this.codigoVeiculoMorador = codigoVeiculoMorador;
    }

    public Integer getCodigoVagaGaragem() {
        return codigoVagaGaragem;
    }

    public void setCodigoVagaGaragem(Integer codigoVagaGaragem) {
        this.codigoVagaGaragem = codigoVagaGaragem;
    }

    public String getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(String tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVeiculoMorador() {
        return veiculoMorador;
    }

    public void setVeiculoMorador(String veiculoMorador) {
        this.veiculoMorador = veiculoMorador;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getNomevaga() {
        return nomevaga;
    }

    public void setNomevaga(String nomevaga) {
        this.nomevaga = nomevaga;
    }
    
    
    
}
