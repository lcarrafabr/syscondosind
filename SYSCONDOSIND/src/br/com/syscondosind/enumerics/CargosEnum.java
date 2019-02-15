package br.com.syscondosind.enumerics;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public enum CargosEnum {

    ALMOXERIFE("ALMOXERIFE"),
    LIMPEZA("LIMPEZA"),
    PORTEIRO("PORTEIRO"),
    SEGURANCA("SEGURANÇA"),
    ZELADOR("ZELADOR");
    
    String cargo;

    private CargosEnum(String cargo) {
        this.cargo = cargo;
    }

    public static CargosEnum getALMOXERIFE() {
        return ALMOXERIFE;
    }

    public static CargosEnum getLIMPEZA() {
        return LIMPEZA;
    }

    public static CargosEnum getPORTEIRO() {
        return PORTEIRO;
    }

    public static CargosEnum getSEGURANCA() {
        return SEGURANCA;
    }

    public static CargosEnum getZELADOR() {
        return ZELADOR;
    }

    public String getCargo() {
        return cargo;
    }
    
    

}
