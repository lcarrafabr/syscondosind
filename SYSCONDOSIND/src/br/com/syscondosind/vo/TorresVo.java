package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class TorresVo {
    
    private Integer codigoTorre;
    private String nome_torre;
    private String possuiElevador;

    public TorresVo(String nome_torre, String possuiElevador) {
        this.nome_torre = nome_torre;
        this.possuiElevador = possuiElevador;
    }

    public Integer getCodigoTorre() {
        return codigoTorre;
    }

    public void setCodigoTorre(Integer codigoTorre) {
        this.codigoTorre = codigoTorre;
    }

    public String getNome_torre() {
        return nome_torre;
    }

    public void setNome_torre(String nome_torre) {
        this.nome_torre = nome_torre;
    }

    public String getPossuiElevador() {
        return possuiElevador;
    }

    public void setPossuiElevador(String possuiElevador) {
        this.possuiElevador = possuiElevador;
    }

    @Override
    public String toString() {
        return nome_torre;
    }
    
    
    
    
}
