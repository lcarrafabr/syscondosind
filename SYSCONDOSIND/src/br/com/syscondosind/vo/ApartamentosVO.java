package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class ApartamentosVO{
    
    private Integer codigoApartamento;
    private String nomeApartamento;
    private String possuiCobertura;

    public ApartamentosVO(Integer codigoApartamento, String nomeApartamento, String possuiCobertura) {
        this.codigoApartamento = codigoApartamento;
        this.nomeApartamento = nomeApartamento;
        this.possuiCobertura = possuiCobertura;
    }

    public Integer getCodigoApartamento() {
        return codigoApartamento;
    }

    public void setCodigoApartamento(Integer codigoApartamento) {
        this.codigoApartamento = codigoApartamento;
    }

    public String getNomeApartamento() {
        return nomeApartamento;
    }

    public void setNomeApartamento(String nomeApartamento) {
        this.nomeApartamento = nomeApartamento;
    }

    public String getPossuiCobertura() {
        return possuiCobertura;
    }

    public void setPossuiCobertura(String possuiCobertura) {
        this.possuiCobertura = possuiCobertura;
    }

    @Override
    public String toString() {
        return nomeApartamento;
    }
    
}
