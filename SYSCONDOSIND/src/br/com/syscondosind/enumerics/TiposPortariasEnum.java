/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.enumerics;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public enum TiposPortariasEnum {
    
    PROPRIA("PRÓPRIA"),
    TERCEIRIZADA("TERCEIRIZADA"),
    NAO_POSSUI("NÃO POSSUI");
    
    String tipoPortaria;

    private TiposPortariasEnum(String tipoPortaria) {
        this.tipoPortaria = tipoPortaria;
    }

    public String getTipoPortaria() {
        return tipoPortaria;
    }
}
