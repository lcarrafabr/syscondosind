/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.funcoes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.animation.FadeTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class Funcoes {
    /**/

    /**
     *Retorna o LocalDate com a data atual do sistema
     * @return
     */

    public static LocalDate getNow() {

        LocalDate getNow = LocalDate.now();
        return getNow;
    }
    
    public static String formataLocalDateBR(LocalDate data){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String hojeFormatado = data.format(formatter);
        
        return hojeFormatado;
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static void makeFadeOpenTransition(AnchorPane rootPane) {
        /*Efeito de abrir a tela com opacidade zero a 1
        O parametro solictado é um AnchoPane como base do Stage*/
        rootPane.setOpacity(0);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(260));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static void makeFadeClosedTransition(AnchorPane rootPane) {
        /*Efeito de abrir a tela com opacidade zero a 1*/
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        rootPane.setOpacity(0);
        fadeTransition.play();
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    /**/

    /**
     *Função para envio de mensagem para a tela
     * @param messageAlert
     * @param iconAlert
     */

    public static void messageAlert(String messageAlert, AlertType iconAlert) {
        Alert alert = new Alert(iconAlert);
        alert.setTitle("Mensagem do sistema");
        alert.setHeaderText(null);
        alert.setContentText(messageAlert);
        alert.showAndWait();
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static String getCodigoFormat(int codigo) throws ParseException, java.text.ParseException {
        //Formatar codigo
        String codigoRetorno;
        DecimalFormat formatoCodigo = new DecimalFormat("0000000");
        codigoRetorno = formatoCodigo.format(codigo);

        return codigoRetorno;
    }/*------------------------------------------------------------------------------------------------------------------*/

    public static double totalPaginas(double totalRegistros, double qtdPorpagina) {

        int total_paginas = (int) Math.round(totalRegistros / qtdPorpagina);

        return total_paginas;
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static String indicePaginacao(int totalPaginas, int paginaAtual) {

        String indice = Integer.toString(paginaAtual).concat("/").concat(Integer.toString(totalPaginas));

        return indice;
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static String calculaPercentual(double valorTotal, double percentual) {
        double resultado;
        String resultadoFinal;
        try {
            resultado = (percentual / valorTotal) * 100;

//            DecimalFormat df = new DecimalFormat("#,###.00");
            DecimalFormat df = new DecimalFormat("#");
            resultadoFinal = df.format(resultado);

        } catch (Exception e) {
//            System.out.println("err: " + e);
            resultadoFinal = "0";
        }
        return resultadoFinal;
    }

    /*------------------------------------------------------------------------------------------------------------------*/
    public static String formtaDouble2Decimal(double valor) {
        String valorFormatado;
        try {

            DecimalFormat df = new DecimalFormat("#,###.00");
            valorFormatado = df.format(valor);
        } catch (Exception e) {
            valorFormatado = "0";
        }
        return valorFormatado;
    }

    /*------------------------------------------------------------------------------------------------------------------*/

    public static String criptografiaSHA(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String HexPassword;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = md.digest(password.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte b : messageDigest) {
            sb.append(String.format("%02X", 0xFF & b));
        }

        HexPassword = sb.toString();

//        System.out.println("criptogria SHA hexa: " + HexPassword);
        return HexPassword;
    }
    /*------------------------------------------------------------------------------------------------------------------*/

    /**/

    /**
     *Função para comprar duas datas (Data Início e data Fim)
     * (-1 a DATA INICIO meios DATA FIM)
     * (0 zero. DATA INICIO igual DATA FIM) 
     * (1 ou mais DATA INICIO menor que DATA FIM)
     * @param dataInicio
     * @param dataFim
     * @return 
     */

    public static int comparaDatas(LocalDate dataInicio, LocalDate dataFim){
        
        int compara = dataFim.compareTo(dataInicio);
        
        return compara;
    }
}
