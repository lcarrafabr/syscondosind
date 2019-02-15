package br.com.syscondosind.controller;

import br.com.syscondosind.dao.UserSystemDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.UserSystemVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
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
public class UsersSystemViewController implements Initializable {

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
    private JFXTextField txtMorador;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXPasswordField txtRePassword;
    @FXML
    private Label lblDataUltimaAlteracao;
    @FXML
    private JFXComboBox<String> comboNivelAcesso;
    @FXML
    private JFXToggleButton btnStatus;
    @FXML
    private TableView<UserSystemVO> gradeUserSystem;
    @FXML
    private TableColumn<UserSystemVO, Integer> colId;
    @FXML
    private TableColumn<UserSystemVO, String> colMorador;
    @FXML
    private TableColumn<UserSystemVO, String> colUsuario;
    @FXML
    private TableColumn<UserSystemVO, String> colTipoAcesso;
    @FXML
    private TableColumn<UserSystemVO, String> colStatus;

    private Integer recebeCodigoPessoa;
    private String status = StatusEnum.INATIVO.getStatus();
    private Integer codigoGradePessoa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboStatus();
        renderizaGrade();
        getGradeUser();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoPessoa"));
        colMorador.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("user"));
        colTipoAcesso.setCellValueFactory(new PropertyValueFactory<>("nivelAcesso"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getGradeUser() {
        try {
            UserSystemDAO dao = new UserSystemDAO();

            List<UserSystemVO> list = dao.findAll();

            gradeUserSystem.getItems().clear();
            gradeUserSystem.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar a grade Usuários.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldUser() {

        if (gradeUserSystem.getSelectionModel().getSelectedItem() != null) {

            codigoGradePessoa = gradeUserSystem.getSelectionModel().getSelectedItem().getCodigoPessoa();

            try {

                UserSystemDAO dao = new UserSystemDAO();

                List<UserSystemVO> list = dao.findUser(codigoGradePessoa);

                list.stream().forEach((lista) -> {
                    txtMorador.setText(lista.getNomePessoa());
                    txtUsuario.setText(lista.getUser());
                    comboNivelAcesso.getSelectionModel().select(lista.getNivelAcesso());
                    lblDataUltimaAlteracao.setText("Data da última alteração: " + Funcoes.formataLocalDateBR(lista.getDataUltimaAlteracao()));
                    ativaBotaoStatus(lista.getStatus());
                    txtPassword.setText("");
                    txtRePassword.setText("");
                });
            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao retornar consulta no clique da grade.\n" + e, Alert.AlertType.ERROR);
            }

        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGradeUser();
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        limparCampos();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/PesquisaMoradorView.fxml"));

        Parent parent = loader.load();

        /*Este código é usado para receber dados de outro controller
         Ou seja receber dados da segunda janela para a primeira*/
        PesquisaMoradorViewController controller = loader.getController();
//------------------------------------------------------------------     

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        recebeCodigoPessoa = controller.codigoPessoa;
        txtMorador.setText(controller.nomePessoaMorador);//aqui resgata os dados do segundo controller.

        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if (recebeCodigoPessoa != null) {
            controlMenuButton(ControlButtonsEnum.NOVO);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtMorador.getText().isEmpty()) {
            Funcoes.messageAlert("Um morador ativo deve ser informado.", Alert.AlertType.WARNING);
            return;
        }
        if (txtUsuario.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Usuário é obrigatório.", Alert.AlertType.WARNING);
            txtUsuario.requestFocus();
            return;
        }
        if (txtPassword.getText().isEmpty()) {
            Funcoes.messageAlert("Informe uma senha.", Alert.AlertType.WARNING);
            txtPassword.requestFocus();
            return;
        }
        if (txtRePassword.getText().isEmpty()) {
            Funcoes.messageAlert("Por favor repetir a senha.", Alert.AlertType.WARNING);
            txtRePassword.requestFocus();
            return;
        }
        if (comboNivelAcesso.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("escolha um tipo de acesso.", Alert.AlertType.WARNING);
            comboNivelAcesso.requestFocus();
            return;
        }
        if (!txtPassword.getText().trim().toUpperCase().equals(txtRePassword.getText().trim().toUpperCase())) {
            Funcoes.messageAlert("As senhas não conferem, por favor verifique e tente novamente.", Alert.AlertType.WARNING);
            txtPassword.requestFocus();
            return;
        }
        try {

            String usuario = txtUsuario.getText().trim().toUpperCase();
            String password = txtPassword.getText().trim().toUpperCase();
            String nivelAcesso = comboNivelAcesso.getSelectionModel().getSelectedItem();
            LocalDate dataUltimaAlteracao = Funcoes.getNow();

            UserSystemVO oUser = new UserSystemVO(recebeCodigoPessoa,
                    usuario,
                    password,
                    nivelAcesso,
                    dataUltimaAlteracao,
                    status
            );

            UserSystemDAO dao = new UserSystemDAO();

            dao.save(oUser);

            getGradeUser();

            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Usuário " + usuario + " cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (DAOException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Funcoes.messageAlert("Erro ao cadastrar usuário\n" + e, Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtMorador.getText().isEmpty()) {
            Funcoes.messageAlert("Um morador ativo deve ser informado.", Alert.AlertType.WARNING);
            return;
        }
        if (txtUsuario.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Usuário é obrigatório.", Alert.AlertType.WARNING);
            txtUsuario.requestFocus();
            return;
        }
        if (txtPassword.getText().isEmpty()) {
            Funcoes.messageAlert("Informe uma senha.", Alert.AlertType.WARNING);
            txtPassword.requestFocus();
            return;
        }
        if (txtRePassword.getText().isEmpty()) {
            Funcoes.messageAlert("Por favor repetir a senha.", Alert.AlertType.WARNING);
            txtRePassword.requestFocus();
            return;
        }
        if (comboNivelAcesso.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("escolha um tipo de acesso.", Alert.AlertType.WARNING);
            comboNivelAcesso.requestFocus();
            return;
        }
        if (!txtPassword.getText().trim().toUpperCase().equals(txtRePassword.getText().trim().toUpperCase())) {
            Funcoes.messageAlert("As senhas não conferem, por favor verifique e tente novamente.", Alert.AlertType.WARNING);
            txtPassword.requestFocus();
            return;
        }
        try {

            String usuario = txtUsuario.getText().trim().toUpperCase();
            String password = txtPassword.getText().trim().toUpperCase();
            String nivelAcesso = comboNivelAcesso.getSelectionModel().getSelectedItem();
            LocalDate dataUltimaAlteracao = Funcoes.getNow();

            UserSystemVO oUser = new UserSystemVO(codigoGradePessoa,
                    usuario,
                    password,
                    nivelAcesso,
                    dataUltimaAlteracao,
                    status
            );
            
            UserSystemDAO dao = new UserSystemDAO();

            dao.update(oUser);

            getGradeUser();

            controlMenuButton(ControlButtonsEnum.ALTERAR);

            Funcoes.messageAlert("Usuário " + usuario + " atualizado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (DAOException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Funcoes.messageAlert("Erro ao atualizar usuário\n" + e, Alert.AlertType.ERROR);
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
                UserSystemDAO dao = new UserSystemDAO();

                UserSystemVO oUser = new UserSystemVO(codigoGradePessoa);

                dao.delete(oUser);
                
                getGradeUser();
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao deletar registro.\n"+e, Alert.AlertType.ERROR);
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
    private void pesquisaMoradorOnAction(ActionEvent event) {
    }

    @FXML
    private void gradeUserSystemMouseOnClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldUser();
    }

    private void limparCampos() {
        txtMorador.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
        comboNivelAcesso.getSelectionModel().select(-1);
        btnStatus.setSelected(false);
        lblDataUltimaAlteracao.setText("Data da última alteração:__/__/____");
    }

    private void carregaComboStatus() {

        comboNivelAcesso.getItems().addAll("ADMINISTRADOR", "SUPERVISOR", "ZELADOR", "PORTARIA");
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

    private void ativaBotaoStatus(String status) {

        if (status.equals(StatusEnum.ATIVO.getStatus())) {
            btnStatus.setSelected(true);
        } else {
            btnStatus.setSelected(false);
        }

    }

    @FXML
    private void statusOnAction(ActionEvent event) {

        if (btnStatus.isSelected()) {
            status = StatusEnum.ATIVO.getStatus();
        } else {
            status = StatusEnum.INATIVO.getStatus();
        }
    }

}
