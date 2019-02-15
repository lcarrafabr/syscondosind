package br.com.syscondosind.vo;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.time.LocalDate;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class PessoasVO {
    
    private Integer codigoPessoa;
    private String nomePessoa;
    private String status;
    private LocalDate dataCadastro;
    private String dataFormatada;
    private String tipoPessoa;
    private Blob avatar;
    private Integer totalRegistros;
    private String caminhoImagemPasta;
    private Integer operadorFile;
    private InputStream is;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public Integer getOperadorFile() {
        return operadorFile;
    }

    public void setOperadorFile(Integer operadorFile) {
        this.operadorFile = operadorFile;
    }

    public String getCaminhoImagemPasta() {
        return caminhoImagemPasta;
    }

    public void setCaminhoImagemPasta(String caminhoImagemPasta) {
        this.caminhoImagemPasta = caminhoImagemPasta;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public String getDataFormatada() {
        return dataFormatada;
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }
    
    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return nomePessoa;
    }
    
    
    
}
