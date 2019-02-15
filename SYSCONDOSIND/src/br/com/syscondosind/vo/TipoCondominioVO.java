/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.vo;

/**
 *
 * @author User
 */
public class TipoCondominioVO {
    
    private Integer codigoTipoCondominio;
    private String tipoCondominio;

    public Integer getCodigoTipoCondominio() {
        return codigoTipoCondominio;
    }

    public void setCodigoTipoCondominio(Integer codigoTipoCondominio) {
        this.codigoTipoCondominio = codigoTipoCondominio;
    }

    public String getTipoCondominio() {
        return tipoCondominio;
    }

    public void setTipoCondominio(String tipoCondominio) {
        this.tipoCondominio = tipoCondominio;
    }

    @Override
    public String toString() {
        return tipoCondominio;
    }
    
}
