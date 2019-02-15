package br.com.syscondosind.controller;

import br.com.syscondosind.dao.GaragemMoradorDAO;
import br.com.syscondosind.dao.VagasGaragemDAO;
import br.com.syscondosind.dao.VeiculosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.GaragemMoradorVO;
import br.com.syscondosind.vo.VagasGaragemVO;
import br.com.syscondosind.vo.VeiculosVO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class GaragemMoradorViewController implements Initializable {

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
    private JFXTextField txtNomeMorador;
    @FXML
    private JFXComboBox<VeiculosVO> comboVeiculo;
    @FXML
    private JFXComboBox<VagasGaragemVO> comboVaga;
    @FXML
    private JFXComboBox<String> comboTipoVaga;
    @FXML
    private TextArea txtAreaObservacao;
    @FXML
    private Label lblQTDVagasDisponiveis;
    @FXML
    private Label lblQTDVagasOcupadas;
    @FXML
    private Label lblQTDPessoasSemVaga;
    @FXML
    private Label lblMediaVeiculosPessoa;
    @FXML
    private Label PercentualVagasDisponiveis;
    @FXML
    private Label percentualVagasOcupadas;
    @FXML
    private TableView<GaragemMoradorVO> gradeVagaMorador;
    @FXML
    private TableColumn<GaragemMoradorVO, Integer> colId;
    @FXML
    private TableColumn<GaragemMoradorVO, String> colNomePessoa;
    @FXML
    private TableColumn<GaragemMoradorVO, String> colVeiculo;
    @FXML
    private TableColumn<GaragemMoradorVO, String> colVaga;
    @FXML
    private TableColumn<GaragemMoradorVO, String> colSetor;
    @FXML
    private TableColumn<GaragemMoradorVO, String> colTipoVaga;

    private Integer recebeCodigoPessoa;

    private Integer codigoGradeGaragemMorador;
    private Integer codigoGradePessoa;

    private String percentVagasOcupadas;
    private String percentVagasDisponiveis;
    @FXML
    private Label lblTotalvagas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboTipoVaga();
        carregaComboVagas();
        renderizaGrade();
        getgrade();
        getInformacoesgerais();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoGaragemMorador"));
        colNomePessoa.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
        colVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculoMorador"));
        colVaga.setCellValueFactory(new PropertyValueFactory<>("nomevaga"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        colTipoVaga.setCellValueFactory(new PropertyValueFactory<>("tipoVaga"));
    }

    private void getgrade() {

        try {

            GaragemMoradorDAO dao = new GaragemMoradorDAO();

            List<GaragemMoradorVO> list = dao.findAll();

            gradeVagaMorador.getItems().clear();
            gradeVagaMorador.getItems().addAll(list);

        } catch (Exception e) {
            Funcoes.messageAlert("erro ao carregar grade.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldGrade() {

        if (gradeVagaMorador.getSelectionModel().getSelectedItem() != null) {

            codigoGradeGaragemMorador = gradeVagaMorador.getSelectionModel().getSelectedItem().getCodigoGaragemMorador();
            codigoGradePessoa = gradeVagaMorador.getSelectionModel().getSelectedItem().getCodigoPessoa();

            if (codigoGradeGaragemMorador > 0) {

                try {

                    GaragemMoradorDAO dao = new GaragemMoradorDAO();

                    List<GaragemMoradorVO> lista = dao.findGaragemMorador(codigoGradeGaragemMorador);

                    lista.stream().forEach((lista1) -> {
                        txtNomeMorador.setText(lista1.getNomePessoa());
                        comboTipoVaga.getSelectionModel().select(lista1.getTipoVaga());
                        comboVeiculo.getSelectionModel().select(0);
                        getFindVeiculo(lista1.getCodigoVeiculoMorador());
                        comboVaga.getSelectionModel().select(0);
                        getFindVagaMorador(lista1.getCodigoVagaGaragem());
                    });

                } catch (Exception e) {
                }
            }
        }
    }

    private void getFindVeiculo(int codigoVeiculo) {

        try {
            VeiculosDAO dao = new VeiculosDAO();

            comboVeiculo.getItems().clear();

            List<VeiculosVO> list = dao.findAllVeiculo(codigoVeiculo);

            list.stream().forEach((lista1) -> {
                comboVeiculo.getSelectionModel().select(0);
                comboVeiculo.getItems().addAll(lista1);
                comboVeiculo.getSelectionModel().select(lista1);
            });
        } catch (Exception e) {
        }
    }

    private void getFindVagaMorador(int codigoVaga) {

        try {
            VagasGaragemDAO dao = new VagasGaragemDAO();

            List<VagasGaragemVO> list = dao.findVaga(codigoVaga);

            comboVaga.getItems().clear();
            list.stream().forEach((lista1) -> {
                comboVaga.getItems().addAll(lista1);
                comboVaga.getSelectionModel().select(lista1);

            });
        } catch (Exception e) {
        }

    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getgrade();
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
        txtNomeMorador.setText(controller.nomePessoaMorador);//aqui resgata os dados do segundo controller.

        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if (recebeCodigoPessoa != null) {
            controlMenuButton(ControlButtonsEnum.NOVO);
            carregaComboVeiculo();
        }
    }

    @FXML
    private void editarEditarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeMorador.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um morador", Alert.AlertType.WARNING);
            return;
        }
        if (comboVeiculo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atribua um veículo ao morador ou informação sem veículo.", Alert.AlertType.WARNING);
            comboVeiculo.requestFocus();
            return;
        }
        if (comboVaga.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atribua uma vaga ao morador.", Alert.AlertType.WARNING);
            comboVaga.requestFocus();
            return;
        }
        if (comboTipoVaga.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Selecione o tipo de vaga.", Alert.AlertType.WARNING);
            comboTipoVaga.requestFocus();
            return;
        }

        try {
            GaragemMoradorDAO dao = new GaragemMoradorDAO();

            Integer codigoVeiculo = comboVeiculo.getSelectionModel().getSelectedItem().getCodigoVeiculo();
            Integer codigoVaga = comboVaga.getSelectionModel().getSelectedItem().getCodigoVagaGaragem();
            String tipoVaga = comboTipoVaga.getSelectionModel().getSelectedItem();
            String observacao = txtAreaObservacao.getText().trim();

            GaragemMoradorVO oGaragem = new GaragemMoradorVO(0, recebeCodigoPessoa, codigoVeiculo, codigoVaga, tipoVaga, observacao);

            dao.save(oGaragem);

            carregaComboVagas();

            getgrade();

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.SALVAR);

            getInformacoesgerais();

        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeMorador.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um morador", Alert.AlertType.WARNING);
            return;
        }
        if (comboVeiculo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atribua um veículo ao morador ou informação sem veículo.", Alert.AlertType.WARNING);
            comboVeiculo.requestFocus();
            return;
        }
        if (comboVaga.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Atribua uma vaga ao morador.", Alert.AlertType.WARNING);
            comboVaga.requestFocus();
            return;
        }
        if (comboTipoVaga.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Selecione o tipo de vaga.", Alert.AlertType.WARNING);
            comboTipoVaga.requestFocus();
            return;
        }

        try {
            GaragemMoradorDAO dao = new GaragemMoradorDAO();

            Integer codigoVeiculo = comboVeiculo.getSelectionModel().getSelectedItem().getCodigoVeiculo();
            Integer codigoVaga = comboVaga.getSelectionModel().getSelectedItem().getCodigoVagaGaragem();
            String tipoVaga = comboTipoVaga.getSelectionModel().getSelectedItem();
            String observacao = txtAreaObservacao.getText().trim();

            GaragemMoradorVO oGaragem = new GaragemMoradorVO(codigoGradeGaragemMorador, codigoGradePessoa, codigoVeiculo, codigoVaga, tipoVaga, observacao);

            dao.update(oGaragem);

            getgrade();

            Funcoes.messageAlert("Registro atualizado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.ALTERAR);

            carregaComboVagas();
            getInformacoesgerais();

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

            if (result.get() == ButtonType.OK) {

                GaragemMoradorDAO dao = new GaragemMoradorDAO();

                GaragemMoradorVO oGaragem = new GaragemMoradorVO(codigoGradeGaragemMorador, 0, 0, 0, null, null);

                dao.delete(oGaragem);

                controlMenuButton(ControlButtonsEnum.EXCLUIR);

                getgrade();
                carregaComboVagas();
                getInformacoesgerais();

                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
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
    private void pesquisaVeiculoOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/VeiculosView.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        carregaComboVeiculo();
    }

    @FXML
    private void pesquisaVagaOnAction(ActionEvent event) {
    }

    @FXML
    private void gradeVagaMoradorOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldGrade();
        carregaComboVagas();
    }

    private void carregaComboTipoVaga() {

        comboTipoVaga.getItems().addAll("INTEGRAL", "DIURNO", "NOTURNO");
    }

    private void carregaComboVeiculo() {
        try {
            VeiculosDAO dao = new VeiculosDAO();
            List<VeiculosVO> list = dao.findAllVeiculoPessoa(recebeCodigoPessoa);

            comboVeiculo.getItems().clear();
            comboVeiculo.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar o combo veículos.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboVagas() {
        try {
            VagasGaragemDAO dao = new VagasGaragemDAO();

            List<VagasGaragemVO> list = dao.findVagasDisponiveis();

            if (btnEditar.isDisabled()) {
                comboVaga.getItems().clear();
            }
            comboVaga.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao atualizar combo Vagas.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void limparCampos() {
        txtNomeMorador.setText("");
        comboVeiculo.getItems().clear();
        comboVaga.getItems().clear();
        carregaComboVagas();
        comboVaga.getSelectionModel().select(-1);
        comboTipoVaga.getSelectionModel().select(-1);
    }

    private void getInformacoesgerais() {

        try {

            VagasGaragemDAO dao = new VagasGaragemDAO();

            List<VagasGaragemVO> list = dao.findResultadosGeraisGaragem();

            list.stream().forEach((lista) -> {
                lblQTDVagasDisponiveis.setText(Integer.toString(lista.getQtdVagaDisponivel()));
                lblQTDVagasOcupadas.setText(Integer.toString(lista.getQtdvagaOcupada()));
                lblQTDPessoasSemVaga.setText(Integer.toString(lista.getQtdPessoasSemVaga()));

                percentVagasDisponiveis = Funcoes.calculaPercentual(lista.getQtdVagasTotal(), lista.getQtdVagaDisponivel());
                percentVagasOcupadas = Funcoes.calculaPercentual(lista.getQtdVagasTotal(), lista.getQtdvagaOcupada());
                lblMediaVeiculosPessoa.setText(Funcoes.formtaDouble2Decimal(lista.getMediaCarrosMorador()));
                lblTotalvagas.setText(Integer.toString(lista.getQtdVagasTotal()));

                PercentualVagasDisponiveis.setText(percentVagasDisponiveis.concat("%"));
                percentualVagasOcupadas.setText(percentVagasOcupadas.concat("%"));

            });

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao preencher quadro de informações gerais.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private int calcula(int valor1, int valor2) {

        int resultado = (valor1 / valor2) * 100;

        return resultado;
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
