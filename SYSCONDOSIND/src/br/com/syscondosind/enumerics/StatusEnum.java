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
public enum StatusEnum {
    
    ATIVO("ATIVO"),
    INATIVO("INATIVO");
    
    String status;

    private StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
