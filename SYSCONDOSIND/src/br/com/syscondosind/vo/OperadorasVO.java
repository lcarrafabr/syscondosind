package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class OperadorasVO {
    
    private Integer codigoOperadora;
    private String nomeOperadora;

    public OperadorasVO(Integer codigoOperadora, String nomeOperadora) {
        this.codigoOperadora = codigoOperadora;
        this.nomeOperadora = nomeOperadora;
    }

    public Integer getCodigoOperadora() {
        return codigoOperadora;
    }

    public void setCodigoOperadora(Integer codigoOperadora) {
        this.codigoOperadora = codigoOperadora;
    }

    public String getNomeOperadora() {
        return nomeOperadora;
    }

    public void setNomeOperadora(String nomeOperadora) {
        this.nomeOperadora = nomeOperadora;
    }

    @Override
    public String toString() {
        return nomeOperadora;
    }
    
    
    
}
