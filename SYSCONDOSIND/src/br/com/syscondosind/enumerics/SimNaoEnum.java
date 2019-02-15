/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.enumerics;

/**
 *
 * @author User
 */
public enum SimNaoEnum {
    
    SIM("SIM"),
    NAO("NÃO");
    
    String opcao;

    private SimNaoEnum(String opcao) {
        this.opcao = opcao;
    }

    public String getOpcao() {
        return opcao;
    }    
}
