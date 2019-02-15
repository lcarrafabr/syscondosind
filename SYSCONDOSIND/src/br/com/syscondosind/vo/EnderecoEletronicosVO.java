package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class EnderecoEletronicosVO {
    
    private Integer codigoEnderecoEletronico;
    private Integer codigoPessoa;
    private String email;
    private String paginaWeb;
    private String tipoEmail;
    private String status;    

    public EnderecoEletronicosVO(Integer codigoEnderecoEletronico, Integer codigoPessoa, String email, String paginaWeb, String tipoEmail, String status) {
        this.codigoEnderecoEletronico = codigoEnderecoEletronico;
        this.codigoPessoa = codigoPessoa;
        this.email = email;
        this.paginaWeb = paginaWeb;
        this.tipoEmail = tipoEmail;
        this.status = status;
    }

    public Integer getCodigoEnderecoEletronico() {
        return codigoEnderecoEletronico;
    }

    public void setCodigoEnderecoEletronico(Integer codigoEnderecoEletronico) {
        this.codigoEnderecoEletronico = codigoEnderecoEletronico;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getTipoEmail() {
        return tipoEmail;
    }

    public void setTipoEmail(String tipoEmail) {
        this.tipoEmail = tipoEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
