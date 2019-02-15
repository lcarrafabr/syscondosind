package br.com.syscondosind.vo;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class VagasGaragemVO {
    
    private Integer codigoVagaGaragem;
    private Integer codigoSetor;
    private String nomeVaga;
    private String codigo;
    private String status;
    private String observacao;
    private String nomeSetor;
    /*variaveis usadas na VIEW VAGAS na atra de qtd por status*/
    private int qtdTotal;
    private int qtdLiberado;
    private int qtdManutencao;
    private int qtdInterditado;
    
    /*variaveis usadas no form GaragemMorador no quadro de informações gerais */
    private int qtdVagasTotal;
    private int qtdVagaDisponivel;
    private int qtdvagaOcupada;
    private int qtdPessoasSemVaga;
    private double mediaCarrosMorador;
    

    public int getQtdVagasTotal() {
        return qtdVagasTotal;
    }

    public void setQtdVagasTotal(int qtdVagasTotal) {
        this.qtdVagasTotal = qtdVagasTotal;
    }

    public int getQtdVagaDisponivel() {
        return qtdVagaDisponivel;
    }

    public void setQtdVagaDisponivel(int qtdVagaDisponivel) {
        this.qtdVagaDisponivel = qtdVagaDisponivel;
    }

    public int getQtdvagaOcupada() {
        return qtdvagaOcupada;
    }

    public void setQtdvagaOcupada(int qtdvagaOcupada) {
        this.qtdvagaOcupada = qtdvagaOcupada;
    }

    public int getQtdPessoasSemVaga() {
        return qtdPessoasSemVaga;
    }

    public void setQtdPessoasSemVaga(int qtdPessoasSemVaga) {
        this.qtdPessoasSemVaga = qtdPessoasSemVaga;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(int qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public int getQtdLiberado() {
        return qtdLiberado;
    }

    public void setQtdLiberado(int qtdLiberado) {
        this.qtdLiberado = qtdLiberado;
    }

    public int getQtdManutencao() {
        return qtdManutencao;
    }

    public void setQtdManutencao(int qtdManutencao) {
        this.qtdManutencao = qtdManutencao;
    }

    public int getQtdInterditado() {
        return qtdInterditado;
    }

    public void setQtdInterditado(int qtdInterditado) {
        this.qtdInterditado = qtdInterditado;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public Integer getCodigoVagaGaragem() {
        return codigoVagaGaragem;
    }

    public void setCodigoVagaGaragem(Integer codigoVagaGaragem) {
        this.codigoVagaGaragem = codigoVagaGaragem;
    }

    public Integer getCodigoSetor() {
        return codigoSetor;
    }

    public void setCodigoSetor(Integer codigoSetor) {
        this.codigoSetor = codigoSetor;
    }

    public String getNomeVaga() {
        return nomeVaga;
    }

    public void setNomeVaga(String nomeVaga) {
        this.nomeVaga = nomeVaga;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public double getMediaCarrosMorador() {
        return mediaCarrosMorador;
    }

    public void setMediaCarrosMorador(double mediaCarrosMorador) {
        this.mediaCarrosMorador = mediaCarrosMorador;
    }

    @Override
    public String toString() {
        return nomeSetor + " - " + nomeVaga;
    }
    
    
    
}
