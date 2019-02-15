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
public enum TipoPessoaEnumerics {
    
    FISICA("FÍSICA"),
    JURIDICA("JURÍDICA");
    
    String tipoPessoa;

    private TipoPessoaEnumerics(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }
    
    
    
}
