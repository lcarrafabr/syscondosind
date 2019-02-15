package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class VeiculosVO {
    
    private Integer codigoVeiculo;
    private Integer codigoPessoa;
    private String marca;
    private String modelo;
    private String placa;
    private String Ano;
    private String cor;
    /*variavel usada apenas para preencher o nome do morador no clique da grade*/
    private String nomeMorador;

    public VeiculosVO(Integer codigoVeiculo, Integer codigoPessoa, String marca, String modelo, String placa, String Ano, String cor) {
        this.codigoVeiculo = codigoVeiculo;
        this.codigoPessoa = codigoPessoa;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.Ano = Ano;
        this.cor = cor;
    }

    public Integer getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Integer codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String Ano) {
        this.Ano = Ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }    

    public String getNomeMorador() {
        return nomeMorador;
    }

    public void setNomeMorador(String nomeMorador) {
        this.nomeMorador = nomeMorador;
    }

    @Override
    public String toString() {
        return marca + " - " + modelo;
    }
}
