package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class TelefonesVO {
    
    private Integer codigoTelefone;
    private Integer codigoPessoa;
    private Integer codigoOperadora;
    private String ddd;
    private String numeroTelefone;
    private String ramal;
    private String tipoTelefone;
    private String telefoneCompleto;

    public TelefonesVO(Integer codigoTelefone, Integer codigoPessoa, Integer codigoOperadora, String ddd, String numeroTelefone, String ramal, String tipoTelefone) {
        this.codigoTelefone = codigoTelefone;
        this.codigoPessoa = codigoPessoa;
        this.codigoOperadora = codigoOperadora;
        this.ddd = ddd;
        this.numeroTelefone = numeroTelefone;
        this.ramal = ramal;
        this.tipoTelefone = tipoTelefone;
    }

    public void setTelefoneCompleto(String telefoneCompleto) {
        this.telefoneCompleto = telefoneCompleto;
    }

    public String getTelefoneCompleto() {
        return telefoneCompleto;
    }

    public Integer getCodigoTelefone() {
        return codigoTelefone;
    }

    public void setCodigoTelefone(Integer codigoTelefone) {
        this.codigoTelefone = codigoTelefone;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public Integer getCodigoOperadora() {
        return codigoOperadora;
    }

    public void setCodigoOperadora(Integer codigoOperadora) {
        this.codigoOperadora = codigoOperadora;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }
    
}
