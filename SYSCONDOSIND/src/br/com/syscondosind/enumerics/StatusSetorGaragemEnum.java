package br.com.syscondosind.enumerics;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public enum StatusSetorGaragemEnum {

    /*Este enum serve para a constante de setores de garagem*/
    
    LIBERADO("LIBERADO"),
    INTERDITADO("INTERDITADO"),
    MANUTENCAO("MANUTENÇÃO");
    
    private String StatusSetorGaragem;

    private StatusSetorGaragemEnum(String StatusSetorGaragem) {
        this.StatusSetorGaragem = StatusSetorGaragem;
    }

    public String getStatusSetorGaragem() {
        return StatusSetorGaragem;
    }

}
