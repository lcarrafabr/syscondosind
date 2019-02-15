/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.controller;

import br.com.syscondosind.dao.TiposCondominiosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TipoCondominioVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TipoCondominioViewController implements Initializable {
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnExcluir;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnLimpar;
    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXTextField txtTipoCondominio;
    @FXML
    private TableView<TipoCondominioVO> gradeTipoCondominio;
    @FXML
    private TableColumn<TipoCondominioVO, Integer> colCodigoTipoCondominio;
    @FXML
    private TableColumn<TipoCondominioVO, String> colTipoCondominio;
    
    private int codigoTipoCOndominio;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        renderizaGrade();
        getGrade();
    }    
    
    private void renderizaGrade(){
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        
        colCodigoTipoCondominio.setCellValueFactory(new PropertyValueFactory<>("codigoTipoCondominio"));
        colTipoCondominio.setCellValueFactory(new PropertyValueFactory<>("tipoCondominio"));
    }
    
    private void getGrade(){
        
        try {
            
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            
            List<TipoCondominioVO> list = dao.findAll();
            
            gradeTipoCondominio.getItems().clear();
            gradeTipoCondominio.getItems().addAll(list);
            
        } catch (DAOException e) {
            System.err.println("Erro\n"+e);
            Funcoes.messageAlert("Erro ao consultar os dados na grade.", Alert.AlertType.ERROR);
        }
    }
    
    private void getFieldTipoCondominio(){
        
        try {
            
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            
            codigoTipoCOndominio = gradeTipoCondominio.getSelectionModel().getSelectedItem().getCodigoTipoCondominio();
            
            List<TipoCondominioVO> lista = dao.findTipoCondominio(codigoTipoCOndominio);
            
            lista.stream().forEach((lista1) ->{
            
            txtTipoCondominio.setText(lista1.getTipoCondominio());
        });
            
        } catch (DAOException e) {
            System.err.println("Erro\n"+e);
            Funcoes.messageAlert("Erro ao localizar registro.", Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }


    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if(txtTipoCondominio.getText().isEmpty()){
            Funcoes.messageAlert("O campo Tipo condomínio é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            
            TipoCondominioVO oTipCond = new TipoCondominioVO();
            
            oTipCond.setTipoCondominio(txtTipoCondominio.getText().trim().toUpperCase());
            
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            dao.save(oTipCond);
            
            Funcoes.messageAlert("Registro cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            getGrade();
            
            controlMenuButton(ControlButtonsEnum.SALVAR);
            
        } catch (DAOException e) {
            System.err.println("Erro\n" + e);
            Funcoes.messageAlert("Erro ao cadastrar.\n"+e.getMessage(), Alert.AlertType.ERROR);
        }
        
        
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if(txtTipoCondominio.getText().isEmpty()){
            Funcoes.messageAlert("O campo Tipo condomínio é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        try {
            
            TipoCondominioVO oTipoCond = new TipoCondominioVO();
            
            oTipoCond.setCodigoTipoCondominio(codigoTipoCOndominio);
            oTipoCond.setTipoCondominio(txtTipoCondominio.getText().trim().toUpperCase());
            
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            
            dao.getAlterar(oTipoCond);
            
            Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);
            
            getGrade();
            
            controlMenuButton(ControlButtonsEnum.ALTERAR);
            
        } catch (DAOException e) {
            System.err.println("Erro\n"+e);
            Funcoes.messageAlert("Erro ao alterar registro", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event) throws DAOException {
        
        try {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem do sistema");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir este registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            TipoCondominioVO oTipoCond = new TipoCondominioVO();
            
            oTipoCond.setCodigoTipoCondominio(codigoTipoCOndominio);
            
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            dao.getDelete(oTipoCond);
            
            Funcoes.messageAlert("Registro deletado com sucesso!", Alert.AlertType.INFORMATION);
            
            getGrade();
            controlMenuButton(ControlButtonsEnum.EXCLUIR);
            
        }
            
            
        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao deletar registro\n"+e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        txtTipoCondominio.setText("");
        controlMenuButton(ControlButtonsEnum.ABERTURA);
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        txtTipoCondominio.requestFocus();
        limparOnAction(event);
        controlMenuButton(ControlButtonsEnum.NOVO);
    }

    @FXML
    private void gradeTipoCondominioOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldTipoCondominio();
    }
    
    private void controlMenuButton(ControlButtonsEnum controle) {
        /*
         *TRUE - desativa o botao
         *FALSE - ativa o botao
         */
        final boolean ativa = false;
        final boolean desativa = true;

        switch (controle) {

            case ABERTURA:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case NOVO:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(ativa);
//                btnSalvarMais.setDisable(ativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(ativa);
                break;

            case EDITAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(ativa);
                btnExcluir.setDisable(ativa);
                btnCancelar.setDisable(ativa);
                break;

            case SALVAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case ALTERAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case EXCLUIR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case CANCELAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
//                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;
        }
    }
    
}
