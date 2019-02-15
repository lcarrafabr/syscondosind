package br.com.syscondosind.controller;

import br.com.syscondosind.dao.EnderecosEletronicosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.EnderecoEletronicosVO;
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
public class EnderecosEletronicosViewController implements Initializable {

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
    private JFXTextField txtNomePessoa;
    @FXML
    private JFXComboBox<String> comboTipoEmail;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPaginaWEB;
    @FXML
    private TableView<EnderecoEletronicosVO> gradeEnderecoEletronico;
    @FXML
    private TableColumn<EnderecoEletronicosVO, Integer> colId;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colEnderecoEletronico;
    @FXML
    private JFXButton btnSair;

    private String recebeNomePessoa;
    private Integer recebeCodigoPessoa;
    private Integer codigoGradeEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colTipoEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colStatus;

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
        carregaComboTipoEmail();
    }

    public void carregaVariaveisPrinciáis(int codigoPessoa, String nomePessoa) {

        recebeCodigoPessoa = codigoPessoa;
        recebeNomePessoa = nomePessoa;
        txtNomePessoa.setText(recebeNomePessoa);

        /*O renderiza grade e o getGrade devem ficar dentro desse metodo
         Se colocar no Initialize não ira carregar a grade*/
        renderizaGrade();
        getGradeEmail();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoEnderecoEletronico"));
        colEnderecoEletronico.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTipoEmail.setCellValueFactory(new PropertyValueFactory<>("tipoEmail"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getGradeEmail() {

        try {

            EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();

            List<EnderecoEletronicosVO> list = dao.findEmailPessoa(recebeCodigoPessoa);

            gradeEnderecoEletronico.getItems().clear();
            gradeEnderecoEletronico.getItems().addAll(list);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar a grade endereços eletrônicos.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldEnderecoEmail() {

        codigoGradeEmail = gradeEnderecoEletronico.getSelectionModel().getSelectedItem().getCodigoEnderecoEletronico();

        try {

            EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();

            List<EnderecoEletronicosVO> lista = dao.findEmailPessoaGetField(recebeCodigoPessoa, codigoGradeEmail);

            lista.stream().forEach((lista1) -> {

                comboTipoEmail.getSelectionModel().select(0);
                comboTipoEmail.getSelectionModel().select(lista1.getTipoEmail());
                System.out.println("Tipo Email: " + lista1.getTipoEmail());
                comboStatus.getSelectionModel().select(0);
                comboStatus.getSelectionModel().select(lista1.getStatus());
                txtEmail.setText(lista1.getEmail());
                txtPaginaWEB.setText(lista1.getPaginaWeb());
            });

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao retornar registro do clique na grade.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGradeEmail();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limpaCampos();
        controlMenuButton(ControlButtonsEnum.NOVO);
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomePessoa.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome Pessoa deve ser preenchida automaticamente\n"
                    + "Em casso de erro entre em contato com o suporte técnico.", Alert.AlertType.ERROR);
            txtNomePessoa.requestFocus();
            return;
        }
        if (comboTipoEmail.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de e-mail.", Alert.AlertType.WARNING);
            comboTipoEmail.requestFocus();
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um e-mail.", Alert.AlertType.WARNING);
            txtEmail.requestFocus();
            return;
        }

        try {

            EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();

            String tipoEmail = comboTipoEmail.getSelectionModel().getSelectedItem();
            String status = comboStatus.getSelectionModel().getSelectedItem();
            String email = txtEmail.getText().trim().toLowerCase();
            String paginaWEB = txtPaginaWEB.getText().trim().toLowerCase();

            EnderecoEletronicosVO oEnd = new EnderecoEletronicosVO(0,
                    recebeCodigoPessoa,
                    email,
                    paginaWEB,
                    tipoEmail,
                    status
            );

            dao.save(oEnd);
            getGradeEmail();

            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Registro salvo com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomePessoa.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome Pessoa deve ser preenchida automaticamente\n"
                    + "Em casso de erro entre em contato com o suporte técnico.", Alert.AlertType.ERROR);
            txtNomePessoa.requestFocus();
            return;
        }
        if (comboTipoEmail.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de e-mail.", Alert.AlertType.WARNING);
            comboTipoEmail.requestFocus();
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um e-mail.", Alert.AlertType.WARNING);
            txtEmail.requestFocus();
            return;
        }

        try {

            EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();

            String tipoEmail = comboTipoEmail.getSelectionModel().getSelectedItem();
            String status = comboStatus.getSelectionModel().getSelectedItem();
            String email = txtEmail.getText().trim().toLowerCase();
            String paginaWEB = txtPaginaWEB.getText().trim().toLowerCase();

            EnderecoEletronicosVO oEnd = new EnderecoEletronicosVO(codigoGradeEmail,
                    recebeCodigoPessoa,
                    email,
                    paginaWEB,
                    tipoEmail,
                    status
            );

            dao.update(oEnd);
            getGradeEmail();

            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao atualizar registro\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem do sistema");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir este registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            try {
                
                EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();
                
                EnderecoEletronicosVO oEnd = new EnderecoEletronicosVO(codigoGradeEmail, recebeCodigoPessoa, null, null, null, null);
                
                dao.delete(oEnd);
                getGradeEmail();
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                Funcoes.messageAlert("Erro\n"+e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        limpaCampos();
        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    private void carregaComboStatus() {

        for (StatusEnum s : StatusEnum.values()) {
            comboStatus.getItems().addAll(s.getStatus());
        }
    }

    private void limpaCampos() {
        comboTipoEmail.getSelectionModel().select(-1);
        comboStatus.getSelectionModel().select(-1);
        txtEmail.setText("");
        txtPaginaWEB.setText("");
    }

    private void carregaComboTipoEmail() {

        comboTipoEmail.getItems().addAll("CONTATO", "SUPORTE");
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

    @FXML
    private void gradeEmailOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldEnderecoEmail();
    }

}
