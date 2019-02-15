package br.com.syscondosind.controller;

import br.com.syscondosind.enumerics.CargosEnum;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.funcoes.Funcoes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class FuncionariosVIEWController implements Initializable {

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
    private JFXTextField txtFuncionario;
    @FXML
    private JFXDatePicker dateDataAdmissao;
    @FXML
    private JFXDatePicker dateDataDesligamento;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private JFXTextField txtCarteiraDeTrabalho;
    @FXML
    private JFXComboBox<String> comboTipoFuncionario;
    @FXML
    private JFXTextField txtProlabore;
    @FXML
    private JFXTextField txtHoraTrabalho;
    @FXML
    private JFXComboBox<String> comboCargo;
    @FXML
    private JFXToggleButton btnPermissao;
    @FXML
    private TableView<?> gradeFuncionarios;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colFuncionario;
    @FXML
    private TableColumn<?, ?> colDataAdmissao;
    @FXML
    private TableColumn<?, ?> colDataDeslg;
    @FXML
    private TableColumn<?, ?> colTipoFuncionario;
    @FXML
    private TableColumn<?, ?> colCargo;

    private Integer recebeCodigoPessoa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboTipoFuncionario();
        carregaComboStatus();
        carregaComboCargos();
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        limparCampos();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/PessoasFX.fxml"));

        Parent parent = loader.load();

        /*Este código é usado para receber dados de outro controller
         Ou seja receber dados da segunda janela para a primeira*/
        PessoasFXController controller = loader.getController();
        controller.pessoaFisica();
//------------------------------------------------------------------     

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        recebeCodigoPessoa = controller.codigo_pessoa;
        txtFuncionario.setText(controller.nomePessoa);//aqui resgata os dados do segundo controller.

        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if (recebeCodigoPessoa > 0) {
            controlMenuButton(ControlButtonsEnum.NOVO);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtFuncionario.getText().isEmpty()) {
            Funcoes.messageAlert("É obrigatório informar um funcionario no sistema.", Alert.AlertType.WARNING);
            return;
        }
        if (dateDataAdmissao.getValue() == null) {
            Funcoes.messageAlert("Informe uma data de admissão.", Alert.AlertType.WARNING);
            dateDataAdmissao.requestFocus();
            return;
        }
        if (comboStatus.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Status é obrigatório.", Alert.AlertType.WARNING);
            comboStatus.requestFocus();
            return;
        }
        if (comboTipoFuncionario.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("O campo Tipo Funcionário é obrigatório.", Alert.AlertType.WARNING);
            comboTipoFuncionario.requestFocus();
            return;
        }
        if (comboTipoFuncionario.getSelectionModel().getSelectedItem().equals("PRÓPRIO")
                && txtProlabore.getText().isEmpty()) {
            Funcoes.messageAlert("Informe o prolabore do funcionário.", Alert.AlertType.WARNING);
            txtProlabore.requestFocus();
            return;
        }
        if(comboCargo.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe o cargo do funcionário.", Alert.AlertType.WARNING);
            comboCargo.requestFocus();
            return;
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void permissaoOnAction(ActionEvent event) {
    }

    @FXML
    private void gradeFuncionariosOnMouseClicked(MouseEvent event) {
    }

    private void carregaComboTipoFuncionario() {

        try {
            comboTipoFuncionario.getItems().addAll("PRÓPRIO", "TERCEIRIZADO");
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar combo tipo funcionario.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboStatus() {
        try {
            for (StatusEnum s : StatusEnum.values()) {
                comboStatus.getItems().addAll(s.getStatus());
            }
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar combo status.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboCargos() {
        try {
            for (CargosEnum c : CargosEnum.values()) {
                comboCargo.getItems().addAll(c.getCargo());
            }
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar o combo Cargos.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void limparCampos() {
        recebeCodigoPessoa = -1;
        txtFuncionario.setText("");
        dateDataAdmissao.setValue(null);
        dateDataDesligamento.setValue(null);
        comboStatus.getSelectionModel().select(-1);
        txtCarteiraDeTrabalho.setText("");
        comboTipoFuncionario.getSelectionModel().select(-1);
        txtProlabore.setText("");
        txtHoraTrabalho.setText("");
        comboCargo.getSelectionModel().select(-1);
        btnPermissao.setSelected(false);
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
