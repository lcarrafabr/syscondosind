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
public enum FuncionamentoPortariaEnum {
    
    VINTE_QUATRO_HORAS("24H"),
    QUARENTA_E_OITO_HORAS("48H"),
    NAO_SE_APLICA("NÃO SE APLICA"),
    OUTROS("OUTROS");
    
    String horarioFuncionamento;

    private FuncionamentoPortariaEnum(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }
}
