package br.com.syscondosind.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class BancosViewController implements Initializable {

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
    private JFXTextField txtNomeBanco;
    @FXML
    private JFXTextField txtDigito;
    @FXML
    private JFXTextField txtSiteBanco;
    @FXML
    private TableView<?> gradeBanco;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colNomeBanco;
    @FXML
    private TableColumn<?, ?> colDigito;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarOnAction(ActionEvent event) {
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
    }

    @FXML
    private void EditarOnAction(ActionEvent event) {
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
    }

    @FXML
    private void canelarOnAction(ActionEvent event) {
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
    }

    @FXML
    private void gradeBancoMouseOnClicked(MouseEvent event) {
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        
        Stage stage = (Stage)btnSair.getScene().getWindow();
        stage.close();
    }
    
}
