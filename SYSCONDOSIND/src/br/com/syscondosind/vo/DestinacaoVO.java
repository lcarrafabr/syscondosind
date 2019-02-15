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
public class DestinacaoVO {
    
    private Integer codigoDestinacaoCond;
    private String tipoDestinacao;

    public Integer getCodigoDestinacaoCond() {
        return codigoDestinacaoCond;
    }

    public void setCodigoDestinacaoCond(Integer codigoDestinacaoCond) {
        this.codigoDestinacaoCond = codigoDestinacaoCond;
    }

    public String getTipoDestinacao() {
        return tipoDestinacao;
    }

    public void setTipoDestinacao(String tipoDestinacao) {
        this.tipoDestinacao = tipoDestinacao;
    }

    @Override
    public String toString() {
        return tipoDestinacao;
    }
    
    
    
}
