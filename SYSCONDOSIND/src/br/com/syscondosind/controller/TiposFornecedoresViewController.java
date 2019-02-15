package br.com.syscondosind.controller;

import br.com.syscondosind.dao.GruposFornecedoresDAO;
import br.com.syscondosind.dao.TiposFornecedoresDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.GruposFornecedoresVO;
import br.com.syscondosind.vo.TiposFornecedoresVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class TiposFornecedoresViewController implements Initializable {

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
    private JFXTextField txtNomeTipoFornecedor;
    @FXML
    private JFXComboBox<GruposFornecedoresVO> comboGrupoFornecedor;
    @FXML
    private TableView<TiposFornecedoresVO> gradeTipoFornecedor;
    @FXML
    private TableColumn<TiposFornecedoresVO, Integer> colId;
    @FXML
    private TableColumn<TiposFornecedoresVO, String> colTipoFornecedor;
    @FXML
    private TableColumn<TiposFornecedoresVO, String> colGrupo;

    private Integer codigoGradeTipoFornecedor;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboGrupos();
        renderizaGrade();
        getGrade();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoTipoFornecedor"));
        colTipoFornecedor.setCellValueFactory(new PropertyValueFactory<>("tipoFornecedor"));
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nomeGrupo"));
    }

    private void getGrade() {
        try {
            TiposFornecedoresDAO dao = new TiposFornecedoresDAO();

            List<TiposFornecedoresVO> list = dao.findAll();

            gradeTipoFornecedor.getItems().clear();
            gradeTipoFornecedor.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar grade tipos fornecedores.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldGrade() {

        if (gradeTipoFornecedor.getSelectionModel().getSelectedItem() != null) {

            codigoGradeTipoFornecedor = gradeTipoFornecedor.getSelectionModel().getSelectedItem().getCodigoTipoFornecedor();

            try {
                TiposFornecedoresDAO dao = new TiposFornecedoresDAO();

                List<TiposFornecedoresVO> list = dao.findTipoFornecedor(codigoGradeTipoFornecedor);

                list.stream().forEach((lista) -> {
                    txtNomeTipoFornecedor.setText(lista.getTipoFornecedor());
                    comboGrupoFornecedor.getSelectionModel().select(0);
                    findFieldGrupoFornecedor(lista.getCodigoGrupoFornecedor());
                });
            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao retornar dados no clique da grade.\n" + e, Alert.AlertType.ERROR);
            }
        }
    }
    
    private void findFieldGrupoFornecedor(int codigoGrupo){
        try {
            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();
            
            List<GruposFornecedoresVO> list = dao.findGrupo(codigoGrupo);
            
            list.stream().forEach((lista) ->{
                comboGrupoFornecedor.getSelectionModel().select(lista);
            });
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro carregar o combo grupo de fornecedores.\n"+e, Alert.AlertType.ERROR);
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
        txtNomeTipoFornecedor.requestFocus();
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {

        if (txtNomeTipoFornecedor.getText().isEmpty()) {
            Funcoes.messageAlert("O campo tipo forneceor é obrigatório", Alert.AlertType.WARNING);
            txtNomeTipoFornecedor.requestFocus();
            return;
        }
        if (comboGrupoFornecedor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Um grupo deve ser informado.", Alert.AlertType.WARNING);
            comboGrupoFornecedor.requestFocus();
            return;
        }

        try {

            TiposFornecedoresDAO dao = new TiposFornecedoresDAO();

            Integer codigoGrupoFornecedor = comboGrupoFornecedor.getSelectionModel().getSelectedItem().getCodigoGrupoFornecedor();
            String nomeTipoForn = txtNomeTipoFornecedor.getText().trim().toUpperCase();

            TiposFornecedoresVO oTiposForn = new TiposFornecedoresVO(codigoGrupoFornecedor, nomeTipoForn);

            dao.save(oTiposForn);

            getGrade();

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeTipoFornecedor.getText().isEmpty()) {
            Funcoes.messageAlert("O campo tipo forneceor é obrigatório", Alert.AlertType.WARNING);
            txtNomeTipoFornecedor.requestFocus();
            return;
        }
        if (comboGrupoFornecedor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Um grupo deve ser informado.", Alert.AlertType.WARNING);
            comboGrupoFornecedor.requestFocus();
            return;
        }

        try {

            TiposFornecedoresDAO dao = new TiposFornecedoresDAO();

            Integer codigoGrupoFornecedor = comboGrupoFornecedor.getSelectionModel().getSelectedItem().getCodigoGrupoFornecedor();
            String nomeTipoForn = txtNomeTipoFornecedor.getText().trim().toUpperCase();

            TiposFornecedoresVO oTiposForn = new TiposFornecedoresVO(codigoGradeTipoFornecedor, codigoGrupoFornecedor, nomeTipoForn);

            dao.update(oTiposForn);

            getGrade();

            Funcoes.messageAlert("Registro atualizado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException e) {
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
                
                TiposFornecedoresDAO dao = new TiposFornecedoresDAO();
                
                TiposFornecedoresVO oTipoForn = new TiposFornecedoresVO(codigoGradeTipoFornecedor);
                
                dao.delete(oTipoForn);
                
                getGrade();
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao deletar registro. \n" + e, Alert.AlertType.ERROR);
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
    private void cadastrarGrupoFornOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GruposFornecedoresView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo

        carregaComboGrupos();
    }

    @FXML
    private void gradeTipoFornOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldGrade();
    }

    private void carregaComboGrupos() {
        try {
            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

            List<GruposFornecedoresVO> list = dao.findAll();

            comboGrupoFornecedor.getItems().clear();
            comboGrupoFornecedor.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar o combo grupo de fornecdores.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void limparCampos() {
        txtNomeTipoFornecedor.setText("");
        comboGrupoFornecedor.getSelectionModel().select(-1);
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
