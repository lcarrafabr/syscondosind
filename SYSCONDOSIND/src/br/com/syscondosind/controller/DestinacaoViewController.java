/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.controller;

import br.com.syscondosind.dao.DestinacaoDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.DestinacaoVO;
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
import javafx.scene.control.Alert.AlertType;
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
public class DestinacaoViewController implements Initializable {
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtDestinacao;
    @FXML
    private TableView<DestinacaoVO> gradeDestinacao;
    @FXML
    private TableColumn<DestinacaoVO, Integer> colIdDestinacao;
    @FXML
    private TableColumn<DestinacaoVO, String> colTipoDestinacao;
    
    public int codigoDestinacao;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnEditar;
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
        colIdDestinacao.setCellValueFactory(new PropertyValueFactory<>("codigoDestinacaoCond"));
        colTipoDestinacao.setCellValueFactory(new PropertyValueFactory<>("tipoDestinacao"));    
    }
    
    private void getGrade(){
        
        try {
            
            DestinacaoDAO dao = new DestinacaoDAO();
            
            List<DestinacaoVO> list = dao.findAll();
            
            gradeDestinacao.getItems().clear();
            gradeDestinacao.getItems().addAll(list);
            
        } catch (DAOException e) {
            System.err.println("Erro\n"+e);
            Funcoes.messageAlert("Erro ao carregar grade.", Alert.AlertType.ERROR);
        }
        
    }
    
    private void getFieldGradeDestinacao() {

        try {
            DestinacaoDAO dao = new DestinacaoDAO();

            codigoDestinacao = gradeDestinacao.getSelectionModel().getSelectedItem().getCodigoDestinacaoCond();

            List<DestinacaoVO> lista = dao.findDestin(codigoDestinacao);

            lista.stream().forEach((lista1) -> {

                txtDestinacao.setText(lista1.getTipoDestinacao());
            });

        } catch (DAOException e) {
            System.err.println("Erro\n"+e.getMessage());
            Funcoes.messageAlert("Erro ao carregar dados da grade", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.NOVO);
        txtDestinacao.requestFocus();
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if(txtDestinacao.getText().isEmpty()){
            Funcoes.messageAlert("O campo destinação é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            DestinacaoVO oDestin = new DestinacaoVO();
            
            oDestin.setTipoDestinacao(txtDestinacao.getText().trim().toUpperCase());
            
            DestinacaoDAO dao = new DestinacaoDAO();
            dao.save(oDestin);
            
            Funcoes.messageAlert("Registro cadastrado com sucesso", Alert.AlertType.INFORMATION);
            
            getGrade();
            
            
        } catch (Exception e) {
            System.out.println("Erro\n"+e);
            Funcoes.messageAlert("Erro ao cadastrar\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if(txtDestinacao.getText().isEmpty()){
            Funcoes.messageAlert("O campo destinação é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        try {
            
            DestinacaoVO oDestin = new DestinacaoVO();
            
            oDestin.setCodigoDestinacaoCond(codigoDestinacao);
            oDestin.setTipoDestinacao(txtDestinacao.getText().trim().toUpperCase());
            
            DestinacaoDAO dao = new DestinacaoDAO();
            
            dao.getAlterar(oDestin);
            Funcoes.messageAlert("Registro alterado com sucesso!", Alert.AlertType.INFORMATION);
            getGrade();
            
            controlMenuButton(ControlButtonsEnum.ALTERAR);
        } catch (Exception e) {
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event)throws DAOException{
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensagem do sistema");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir este registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            try {
                
                DestinacaoVO oDestin = new DestinacaoVO();
                oDestin.setCodigoDestinacaoCond(codigoDestinacao);
                
                DestinacaoDAO dao = new DestinacaoDAO();
                dao.getDelete(oDestin);
                
                Funcoes.messageAlert("Registro deletado com sucesso", AlertType.INFORMATION);
                limparOnAction(event);
                getGrade();
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            } catch (DAOException e) {
                System.err.println("Erro\n"+e.getMessage());
                Funcoes.messageAlert("Erro ao deletar registro\n"+e.getMessage(), AlertType.ERROR);
            }
            
        }
 
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        txtDestinacao.setText("");
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void gradeDestinacaoMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldGradeDestinacao();
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
