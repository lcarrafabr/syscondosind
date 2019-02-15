package br.com.syscondosind.controller;

import br.com.syscondosind.dao.ApartamentosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.SimNaoEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.ApartamentosVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
 * @author Luciano Carrafa Benfica
 */
public class ApartamentosViewController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnSalvarMais;
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
    private TableColumn<ApartamentosVO, Integer> colId;
    @FXML
    private TableColumn<ApartamentosVO, String> colNomeTorre;
    @FXML
    private JFXTextField txtNomeApto;
    @FXML
    private JFXComboBox<String> comboPossuiCobertura;
    @FXML
    private TableView<ApartamentosVO> gradeApartamento;
    @FXML
    private TableColumn<ApartamentosVO, String> colPossuiCobertura;

    private Integer codigoGradeApto;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboCobertura();
        renderizaGrade();
        getGrade();
    }

    private void renderizaGrade() {
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoApartamento"));
        colNomeTorre.setCellValueFactory(new PropertyValueFactory<>("nomeApartamento"));
        colPossuiCobertura.setCellValueFactory(new PropertyValueFactory<>("possuiCobertura"));
    }

    private void getGrade() {

        try {

            ApartamentosDAO dao = new ApartamentosDAO();

            List<ApartamentosVO> lista = dao.findAll();

            gradeApartamento.getItems().clear();
            gradeApartamento.getItems().addAll(lista);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar grade\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldApartamento() {

        try {

            codigoGradeApto = gradeApartamento.getSelectionModel().getSelectedItem().getCodigoApartamento();

            if (codigoGradeApto > 0) {
                ApartamentosDAO dao = new ApartamentosDAO();

                List<ApartamentosVO> lista = dao.findApartamento(codigoGradeApto);

                lista.stream().forEach((lista1) -> {
                    txtNomeApto.setText(lista1.getNomeApartamento());
                    comboPossuiCobertura.getSelectionModel().select(lista1.getPossuiCobertura());
                });
            }

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar dados no clique da grade", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limparCampos();
        controlMenuButton(ControlButtonsEnum.NOVO);
        txtNomeApto.requestFocus();
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeApto.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um nome de Apto/Casa/Setor", Alert.AlertType.WARNING);
            return;
        }
        if (comboPossuiCobertura.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe se existe cobertura.", Alert.AlertType.WARNING);
            return;
        }

        try {

            String nomeApto = txtNomeApto.getText().trim().toUpperCase();
            String cobertura = comboPossuiCobertura.getSelectionModel().getSelectedItem();

            ApartamentosVO oApto = new ApartamentosVO(null, nomeApto, cobertura);

            ApartamentosDAO dao = new ApartamentosDAO();
            dao.save(oApto);

            getGrade();
            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Dados cadastrados com sucesso", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n" + e, Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void salvarMaisOnAction(ActionEvent event) {
        salvarOnAction(event);
        novoOnAction(event);

    }

    @FXML
    private void alterarOnAction(ActionEvent event) throws DAOException {
        if (txtNomeApto.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um nome de Apto/Casa/Setor", Alert.AlertType.WARNING);
            return;
        }
        if (comboPossuiCobertura.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe se existe cobertura.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            
            String nomeApto = txtNomeApto.getText().trim().toUpperCase();
            String cobertura = comboPossuiCobertura.getSelectionModel().getSelectedItem();
            
            ApartamentosVO oApto = new ApartamentosVO(codigoGradeApto, nomeApto, cobertura);
            ApartamentosDAO dao = new ApartamentosDAO();
            
            dao.update(oApto);
            
            Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);
            
            getGrade();
            controlMenuButton(ControlButtonsEnum.ALTERAR);
        } catch (Exception e) {
            throw new DAOException("Erro ao alterar registro\n"+e, e);
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
            
            if(result.get() == ButtonType.OK){
                
                ApartamentosVO oApto = new ApartamentosVO(codigoGradeApto, null, null);
                
                ApartamentosDAO dao = new ApartamentosDAO();
                
                dao.delete(oApto);
                
                Funcoes.messageAlert("Registro deletado com sucesso", Alert.AlertType.INFORMATION);
                
                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }
            
        } catch (Exception e) {
            throw new DAOException("Erro ao deletar.\n"+e, e);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
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
    private void gradeAptoOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldApartamento();
    }

    private void carregaComboCobertura() {
        for (SimNaoEnum s : SimNaoEnum.values()) {
            comboPossuiCobertura.getItems().addAll(s.getOpcao());
        }
    }

    private void limparCampos() {
        txtNomeApto.setText("");
        comboPossuiCobertura.getSelectionModel().select(-1);
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
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case NOVO:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(ativa);
                btnSalvarMais.setDisable(ativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(ativa);
                break;

            case EDITAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(desativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(ativa);
                btnExcluir.setDisable(ativa);
                btnCancelar.setDisable(ativa);
                break;

            case SALVAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case ALTERAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case EXCLUIR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;

            case CANCELAR:
                btnBuscar.setDisable(ativa);
                btnNovo.setDisable(ativa);
                btnEditar.setDisable(desativa);
                btnSalvar.setDisable(desativa);
                btnSalvarMais.setDisable(desativa);
                btnAlterar.setDisable(desativa);
                btnExcluir.setDisable(desativa);
                btnCancelar.setDisable(desativa);
                break;
        }
    }

}
