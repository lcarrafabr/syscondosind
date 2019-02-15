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
public enum EstadosUfEnum {

    AC("AC", "Acre"),
    AL("AL", "Alagoas"),
    AM("AM", "Amazonas"),
    AP("AP", "Amapá"),
    BA("BA", "Bahia"),
    CE("CE", "Ceará"),
    DF("DF", "Distrito Federal"),
    ES("ES", "Espirito Santo"),
    GO("GO", "Goias"),
    MA("MA", "Maranhão"),
    MG("MG", "Minas Gerais"),
    MS("MS", "Mato Grosso Sul"),
    MT("MT", "Mato Grosso"),
    PA("PA", "Pará"),
    PB("PB", "Paraiba"),
    PE("PE", "Pernanbuco"),
    PI("PI", "Piaui"),
    PR("PR", "Parana"),
    RJ("RJ", "Rio de Janeiro"),
    RN("RN", "Rio Grande do Norte"),
    RO("RO", "Rondônia"),
    RR("RR", "Roraima"),
    RS("RS", "Rio Grande do Sul"),
    SC("SC", "Santa Catarina"),
    SE("SE", "Sergipe"),
    SP("SP", "São Paulo"),
    TO("TO", "Tocantins");

    private String sigla;
    private String estado;

    private EstadosUfEnum(String sigla, String estado) {
        this.sigla = sigla;
        this.estado = estado;
    }

    public String getSigla() {
        return sigla;
    }

    public String getEstado() {
        return estado;
    }

    public static EstadosUfEnum getByCodigo(String sigla) {

        for (EstadosUfEnum e : EstadosUfEnum.values()) {

            if (sigla.equals(e.getSigla())) {

                return e;
            }
        }
        return null;

    }

}
