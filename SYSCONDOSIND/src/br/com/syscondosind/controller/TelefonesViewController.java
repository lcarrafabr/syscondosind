package br.com.syscondosind.controller;

import br.com.syscondosind.dao.OperadorasDAO;
import br.com.syscondosind.dao.TelefonesDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.OperadorasVO;
import br.com.syscondosind.vo.TelefonesVO;
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
public class TelefonesViewController implements Initializable {

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
    private JFXTextField txtNomePessoa;
    @FXML
    private JFXComboBox<String> comboTipoTelefone;
    @FXML
    private JFXTextField txtDDD;
    @FXML
    private JFXTextField txtNumeroTelefone;
    @FXML
    private JFXTextField txtRamal;
    @FXML
    private TableView<TelefonesVO> gradeTelefone;
    @FXML
    private TableColumn<TelefonesVO, Integer> colId;
    @FXML
    private TableColumn<TelefonesVO, String> colDDD;
    @FXML
    private TableColumn<TelefonesVO, String> colNumeroTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colRamal;
    @FXML
    private TableColumn<TelefonesVO, String> colTipoTelefone;

    private String recebeNomePessoa;
    private Integer recebeCodigoPessoa;
    @FXML
    private JFXComboBox<OperadorasVO> comboOperadora;
    private Integer codigoGradeTelefone;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carrecaComboTipoTelefone();
        carregaComboOperadora();

    }

    public void carregaVariaveisPrinciáis(int codigoPessoa, String nomePessoa) {

        recebeCodigoPessoa = codigoPessoa;
        recebeNomePessoa = nomePessoa;
        txtNomePessoa.setText(recebeNomePessoa);

        /*O renderiza grade e o getGrade devem ficar dentro desse metodo
         Se colocar no Initialize não ira carregar a grade*/
        renderizaGrade();
        getGradeTelefone();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoTelefone"));
        colDDD.setCellValueFactory(new PropertyValueFactory<>("ddd"));
        colNumeroTelefone.setCellValueFactory(new PropertyValueFactory<>("numeroTelefone"));
        colRamal.setCellValueFactory(new PropertyValueFactory<>("ramal"));
        colTipoTelefone.setCellValueFactory(new PropertyValueFactory<>("tipoTelefone"));
    }

    private void getGradeTelefone() {
        try {
//            System.out.println("recebe codigo: " + recebeCodigoPessoa);
            TelefonesDAO dao = new TelefonesDAO();

            List<TelefonesVO> list = dao.findTelefones(recebeCodigoPessoa);

            gradeTelefone.getItems().clear();
            gradeTelefone.getItems().addAll(list);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao atualizar grade.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldTelefone() {

        codigoGradeTelefone = gradeTelefone.getSelectionModel().getSelectedItem().getCodigoTelefone();

        try {

            TelefonesDAO dao = new TelefonesDAO();

            List<TelefonesVO> lista = dao.findTelefonesPesoa(recebeCodigoPessoa, codigoGradeTelefone);

            lista.stream().forEach((lista1) -> {
                comboTipoTelefone.getSelectionModel().select(lista1.getTipoTelefone());
                txtDDD.setText(lista1.getDdd());
                txtNumeroTelefone.setText(lista1.getNumeroTelefone());
                txtRamal.setText(lista1.getRamal());
                findOperadora(lista1.getCodigoOperadora());
            });

        } catch (Exception e) {
        }
    }

    private void findOperadora(int codigoOperadora) {
        try {
            OperadorasDAO dao = new OperadorasDAO();

            List<OperadorasVO> lista = dao.findOperadora(codigoOperadora);

            lista.stream().forEach((lista1) -> {
                comboOperadora.getSelectionModel().select(0);
                comboOperadora.getSelectionModel().select(lista1);
            });

        } catch (Exception e) {
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGradeTelefone();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        limpaCampos();
        comboTipoTelefone.requestFocus();
        controlMenuButton(ControlButtonsEnum.NOVO);
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (comboTipoTelefone.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de telefone.", Alert.AlertType.WARNING);
            comboTipoTelefone.requestFocus();
            return;
        }
        if (comboOperadora.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe uma operadora de telefonia.", Alert.AlertType.WARNING);
            comboOperadora.requestFocus();
            return;
        }
        try {

            Integer codigoOperadora = comboOperadora.getSelectionModel().getSelectedItem().getCodigoOperadora();
            String ddd = txtDDD.getText().trim();
            String numeroTelefone = txtNumeroTelefone.getText().trim().replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
            String ramal = txtRamal.getText().trim().toUpperCase();
            String tipoTelefone = comboTipoTelefone.getSelectionModel().getSelectedItem();

            TelefonesVO oTel = new TelefonesVO(0, recebeCodigoPessoa, codigoOperadora, ddd, numeroTelefone, ramal, tipoTelefone);

            TelefonesDAO dao = new TelefonesDAO();

            dao.save(oTel);
            getGradeTelefone();

            controlMenuButton(ControlButtonsEnum.SALVAR);

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (comboTipoTelefone.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de telefone.", Alert.AlertType.WARNING);
            comboTipoTelefone.requestFocus();
            return;
        }
        if (comboOperadora.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe uma operadora de telefonia.", Alert.AlertType.WARNING);
            comboOperadora.requestFocus();
            return;
        }
        try {

            Integer codigoOperadora = comboOperadora.getSelectionModel().getSelectedItem().getCodigoOperadora();
            String ddd = txtDDD.getText().trim();
            String numeroTelefone = txtNumeroTelefone.getText().trim().replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
            String ramal = txtRamal.getText().trim().toUpperCase();
            String tipoTelefone = comboTipoTelefone.getSelectionModel().getSelectedItem();

            TelefonesVO oTel = new TelefonesVO(codigoGradeTelefone,
                    recebeCodigoPessoa,
                    codigoOperadora,
                    ddd,
                    numeroTelefone,
                    ramal,
                    tipoTelefone
            );

            TelefonesDAO dao = new TelefonesDAO();

            dao.update(oTel);
            getGradeTelefone();

            controlMenuButton(ControlButtonsEnum.ALTERAR);

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void excluirOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensagem do sistema");
            alert.setHeaderText(null);
            alert.setContentText("Deseja realmente excluir este registro?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    
                    TelefonesDAO dao = new TelefonesDAO();
                    
                    TelefonesVO oTel = new TelefonesVO(codigoGradeTelefone, recebeCodigoPessoa, 0, null, null, null, null);
                    
                    dao.delete(oTel);
                    getGradeTelefone();
                    
                    controlMenuButton(ControlButtonsEnum.CANCELAR);
                    
                    Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                    
                } catch (Exception e) {
                    Funcoes.messageAlert("Erro ao deletar registro\n"+e, Alert.AlertType.ERROR);
                }
            }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
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

    @FXML
    private void gradeTelefoneOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldTelefone();
    }

    private void limpaCampos() {

        comboTipoTelefone.getSelectionModel().select(-1);
        txtDDD.setText("");
        txtNumeroTelefone.setText("");
        txtRamal.setText("");

    }

    private void carrecaComboTipoTelefone() {

        comboTipoTelefone.getItems().addAll("CELULAR", "RESIDENCIAL", "COMERCIAL", "RAMAL CONDOMÍNIO");

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
    private void comboTipoTelefoneOnAction(ActionEvent event) {

        if ("RAMAL CONDOMÍNIO".equals(comboTipoTelefone.getSelectionModel().getSelectedItem())) {

            txtNumeroTelefone.setDisable(true);
            txtDDD.setDisable(true);

            txtNumeroTelefone.setText("");
            txtDDD.setText("");
        } else {
            txtNumeroTelefone.setDisable(false);
            txtDDD.setDisable(false);
        }
    }

    @FXML
    private void cadastraOperadoraOnAction(ActionEvent event) {
    }

    private void carregaComboOperadora() {

        try {
            OperadorasDAO dao = new OperadorasDAO();

            List<OperadorasVO> list = dao.findAll();

            comboOperadora.getItems().clear();
            comboOperadora.getItems().addAll(list);

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar combo operadoras.\n", Alert.AlertType.ERROR);
        }

    }

}
