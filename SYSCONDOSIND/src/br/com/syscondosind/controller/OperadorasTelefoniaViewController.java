/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.controller;

import br.com.syscondosind.dao.OperadorasDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.OperadorasVO;
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
public class OperadorasTelefoniaViewController implements Initializable {

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
    private JFXTextField txtNomeOperadora;
    @FXML
    private TableView<OperadorasVO> gradeOperadora;
    @FXML
    private TableColumn<OperadorasVO, Integer> colId;
    @FXML
    private TableColumn<OperadorasVO, String> colNomeOperadora;

    private Integer codigoGradeoperadora;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        renderizaGrade();
        getGrade();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoOperadora"));
        colNomeOperadora.setCellValueFactory(new PropertyValueFactory<>("nomeOperadora"));
    }

    private void getGrade() {

        try {

            OperadorasDAO dao = new OperadorasDAO();

            List<OperadorasVO> oOper = dao.findAll();

            gradeOperadora.getItems().clear();
            gradeOperadora.getItems().addAll(oOper);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar grade Operadoras\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldOperadoras() {

        codigoGradeoperadora = gradeOperadora.getSelectionModel().getSelectedItem().getCodigoOperadora();

        try {
            OperadorasDAO dao = new OperadorasDAO();

            List<OperadorasVO> lista = dao.findOperadora(codigoGradeoperadora);

            lista.stream().forEach((lista1) -> {
                txtNomeOperadora.setText(lista1.getNomeOperadora());
            });

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar dados no clique da grade\n" + e, Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limpaCampo();
        txtNomeOperadora.requestFocus();
        controlMenuButton(ControlButtonsEnum.NOVO);
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
        txtNomeOperadora.requestFocus();
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeOperadora.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome Operadora é obrigatório.", Alert.AlertType.WARNING);
            txtNomeOperadora.requestFocus();
            return;
        }
        try {
            String nomeOperadora = txtNomeOperadora.getText().trim().toUpperCase();

            OperadorasVO oOper = new OperadorasVO(0, nomeOperadora);

            OperadorasDAO dao = new OperadorasDAO();

            dao.save(oOper);

            getGrade();
            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeOperadora.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome Operadora é obrigatório.", Alert.AlertType.WARNING);
            txtNomeOperadora.requestFocus();
            return;
        }
        try {
            String nomeOperadora = txtNomeOperadora.getText().trim().toUpperCase();

            OperadorasVO oOper = new OperadorasVO(codigoGradeoperadora, nomeOperadora);

            OperadorasDAO dao = new OperadorasDAO();

            dao.update(oOper);

            getGrade();
            controlMenuButton(ControlButtonsEnum.ALTERAR);

            Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao atualizar registro.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem do sistema");
            alert.setHeaderText(null);
            alert.setContentText("Deseja realmente excluir este registro?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                
                OperadorasDAO dao = new OperadorasDAO();
                
                OperadorasVO oOper = new OperadorasVO(codigoGradeoperadora, null);
                
                dao.delete(oOper);
                
                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
                
                Funcoes.messageAlert("Registro apagado com sucesso", Alert.AlertType.INFORMATION);
            }
            
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao apagar registro\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        limpaCampo();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void gradeOperadoraMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldOperadoras();
    }

    private void limpaCampo() {
        txtNomeOperadora.setText("");
    }

    private void controlMenuButton(ControlButtonsEnum controle) {
        /*
         *TRUE - desativa o botao
         *FALSE - ativa o botao
         */
        //controle de botões
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
