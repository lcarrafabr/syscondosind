package br.com.syscondosind.controller;

import br.com.syscondosind.dao.TorreBlocoDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.SimNaoEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.TorresVo;
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
public class TorresViewController implements Initializable {

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
    private JFXTextField txtNomeTorre;
    @FXML
    private JFXComboBox<String> comboPossuiElevador;
    @FXML
    private TableView<TorresVo> gradeTorre;
    @FXML
    private TableColumn<TorresVo, Integer> colId;
    @FXML
    private TableColumn<TorresVo, String> colNomeTorre;
    @FXML
    private TableColumn<TorresVo, String> colPossuiElevador;

    private Integer codigoGradeTorre;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboElevador();
        renderizaGrade();
        getGrade();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoTorre"));
        colNomeTorre.setCellValueFactory(new PropertyValueFactory<>("nome_torre"));
        colPossuiElevador.setCellValueFactory(new PropertyValueFactory<>("possuiElevador"));

    }

    private void getGrade() {

        try {
            TorreBlocoDAO dao = new TorreBlocoDAO();

            List<TorresVo> list = dao.findAll();

            gradeTorre.getItems().clear();
            gradeTorre.getItems().addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao carregar a grade.", Alert.AlertType.ERROR);
        }
    }

    private void getFieldTorre() {
        btnEditar.setDisable(false);
        codigoGradeTorre = gradeTorre.getSelectionModel().getSelectedItem().getCodigoTorre();

        if (codigoGradeTorre > 0) {
            try {

                TorreBlocoDAO dao = new TorreBlocoDAO();

                List<TorresVo> lista = dao.findTorreBloco(codigoGradeTorre);

                lista.stream().forEach((lista1) -> {

                    txtNomeTorre.setText(lista1.getNome_torre());
                    comboPossuiElevador.getSelectionModel().select(lista1.getPossuiElevador());

                });

            } catch (Exception e) {
                e.printStackTrace();
                Funcoes.messageAlert("Erro ao encontrar registro na grade", Alert.AlertType.ERROR);
            }
        }

    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limparCampos();
        txtNomeTorre.requestFocus();
        controlMenuButton(ControlButtonsEnum.NOVO);

    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) throws DAOException {
        if (txtNomeTorre.getText().isEmpty()) {
            Funcoes.messageAlert("Atenção\n"
                    + "Informe um nome para a torre", Alert.AlertType.WARNING);
            return;
        }
        if (comboPossuiElevador.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atenção\n"
                    + "Informe se a Torre/Bloco possui elevador", Alert.AlertType.WARNING);
            return;
        }

        String nomeTorre = txtNomeTorre.getText().trim().toUpperCase();
        String possuiElevador = comboPossuiElevador.getSelectionModel().getSelectedItem();

        TorresVo oTorre = new TorresVo(nomeTorre, possuiElevador);

        TorreBlocoDAO dao = new TorreBlocoDAO();
        dao.save(oTorre);

        getGrade();
        controlMenuButton(ControlButtonsEnum.SALVAR);
        
        Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void salvarMaisOnAction(ActionEvent event) throws DAOException {

        salvarOnAction(event);
        novoOnAction(event);

    }

    @FXML
    private void alterarOnAction(ActionEvent event) throws DAOException {
        if (txtNomeTorre.getText().isEmpty()) {
            Funcoes.messageAlert("Atenção\n"
                    + "Informe um nome para a torre", Alert.AlertType.WARNING);
            return;
        }
        if (comboPossuiElevador.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atenção\n"
                    + "Informe se a Torre/Bloco possui elevador", Alert.AlertType.WARNING);
            return;
        }

        String nomeTorre = txtNomeTorre.getText().trim().toUpperCase();
        String possuiElevador = comboPossuiElevador.getSelectionModel().getSelectedItem();

        TorresVo oTorre = new TorresVo(nomeTorre, possuiElevador);
        oTorre.setCodigoTorre(codigoGradeTorre);

        TorreBlocoDAO dao = new TorreBlocoDAO();
        dao.update(oTorre);

        getGrade();
        controlMenuButton(ControlButtonsEnum.ALTERAR);
        
        Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);
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

                TorresVo oTorre = new TorresVo(null, null);

                oTorre.setCodigoTorre(codigoGradeTorre);

                TorreBlocoDAO dao = new TorreBlocoDAO();
                dao.delete(oTorre);

                Funcoes.messageAlert("Registro deletado com sucesso!", Alert.AlertType.INFORMATION);

                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }

        } catch (DAOException e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao deletar registro\n" + e.getMessage(), Alert.AlertType.ERROR);
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
    private void gradeTorreOnMouseClicked(MouseEvent event) {
        getFieldTorre();
    }

    private void carregaComboElevador() {

        for (SimNaoEnum o : SimNaoEnum.values()) {
            comboPossuiElevador.getItems().addAll(o.getOpcao());
        }
    }

    private void limparCampos() {
        txtNomeTorre.setText("");
        comboPossuiElevador.getSelectionModel().select(-1);
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
