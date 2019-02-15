package br.com.syscondosind.controller;

import br.com.syscondosind.dao.GruposFornecedoresDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.GruposFornecedoresVO;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class GruposFornecedoresViewController implements Initializable {

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
    private JFXTextField txtNomeGrupo;
    @FXML
    private TextArea txtAreaObservacao;
    @FXML
    private TableView<GruposFornecedoresVO> gradeGrupo;
    @FXML
    private TableColumn<GruposFornecedoresVO, Integer> colId;
    @FXML
    private TableColumn<GruposFornecedoresVO, String> colNomeGrupo;

    private Integer codigoGradeGrupoFornecedor;

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

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoGrupoFornecedor"));
        colNomeGrupo.setCellValueFactory(new PropertyValueFactory<>("nomeGrupo"));
    }

    private void getGrade() {

        try {
            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

            List<GruposFornecedoresVO> list = dao.findAll();

            gradeGrupo.getItems().clear();
            gradeGrupo.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar os dados na grade.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldGrupoFornecedor() {

        if (gradeGrupo.getSelectionModel().getSelectedItem() != null) {

            codigoGradeGrupoFornecedor = gradeGrupo.getSelectionModel().getSelectedItem().getCodigoGrupoFornecedor();

            try {

                GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

                List<GruposFornecedoresVO> list = dao.findGrupo(codigoGradeGrupoFornecedor);

                list.stream().forEach((lista) -> {
                    txtNomeGrupo.setText(lista.getNomeGrupo());
                    txtAreaObservacao.setText(lista.getObservacao());
                });

            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao retornar os dados no clique da grade.\n" + e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.NOVO);
        limparCampos();
        txtNomeGrupo.requestFocus();
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeGrupo.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um nome para cadastrar um grupo.", Alert.AlertType.WARNING);
            txtNomeGrupo.requestFocus();
            return;
        }

        try {
            String nomeGrupo = txtNomeGrupo.getText().trim().toUpperCase();
            String obs = txtAreaObservacao.getText().trim();

            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

            GruposFornecedoresVO oGrupo = new GruposFornecedoresVO(nomeGrupo, obs);

            dao.save(oGrupo);

            getGrade();

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeGrupo.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um nome para cadastrar um grupo.", Alert.AlertType.WARNING);
            txtNomeGrupo.requestFocus();
            return;
        }

        try {
            String nomeGrupo = txtNomeGrupo.getText().trim().toUpperCase();
            String obs = txtAreaObservacao.getText().trim();

            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

            GruposFornecedoresVO oGrupo = new GruposFornecedoresVO(codigoGradeGrupoFornecedor, nomeGrupo, obs);
            dao.update(oGrupo);

            getGrade();

            Funcoes.messageAlert("Registro atualizado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao atualizar o registro.\n" + e, Alert.AlertType.ERROR);
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

            if (result.get() == ButtonType.OK) {
                GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

                GruposFornecedoresVO oGrupo = new GruposFornecedoresVO(codigoGradeGrupoFornecedor);

                dao.delete(oGrupo);

                getGrade();

                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);

                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao deletar registro.\n" + e, Alert.AlertType.ERROR);
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
    private void gradeGrupoMouseOnCliked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldGrupoFornecedor();
    }

    private void limparCampos() {
        txtNomeGrupo.setText("");
        txtAreaObservacao.setText("");
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
