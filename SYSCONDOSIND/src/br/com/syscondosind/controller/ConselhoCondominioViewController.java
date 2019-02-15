package br.com.syscondosind.controller;

import br.com.syscondosind.dao.ConselhoCondominioDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.ConselhoCondominioVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
public class ConselhoCondominioViewController implements Initializable {

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
    private JFXTextField txtNomePessoaConselho;
    @FXML
    private JFXComboBox<String> comboCargo;
    @FXML
    private JFXDatePicker dateInicio;
    @FXML
    private JFXDatePicker dateTermino;
    @FXML
    private TextArea textObservacao;
    @FXML
    private JFXComboBox<String> comboFiltro;
    @FXML
    private TableView<ConselhoCondominioVO> gradeConselho;
    @FXML
    private TableColumn<ConselhoCondominioVO, Integer> colId;
    @FXML
    private TableColumn<ConselhoCondominioVO, String> colNomePessoa;
    @FXML
    private TableColumn<ConselhoCondominioVO, String> colCargo;
    @FXML
    private TableColumn<ConselhoCondominioVO, String> colDataIni;
    @FXML
    private TableColumn<ConselhoCondominioVO, String> colDataFim;

    private Integer recebeCodigoPessoa;
    private Integer codigoGradeConselho;
    private Integer codigoPessoaGrade;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboCargoConselho();
        carregaComboFiltro();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        renderizaGrade();
        getGrade();
    }

    private void renderizaGrade() {
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoConselhoCond"));
        colNomePessoa.setCellValueFactory(new PropertyValueFactory<>("nomePessoaConselho"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDataIni.setCellValueFactory(new PropertyValueFactory<>("dataIniFormat"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFimFormat"));
    }

    private void getGrade() {
        try {
            ConselhoCondominioDAO dao = new ConselhoCondominioDAO();

            List<ConselhoCondominioVO> lista = dao.findAll();

            gradeConselho.getItems().clear();
            gradeConselho.getItems().addAll(lista);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar a grade.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldConselho() {

        if (gradeConselho.getSelectionModel().getSelectedItem() != null) {

            codigoGradeConselho = gradeConselho.getSelectionModel().getSelectedItem().getCodigoConselhoCond();
            codigoPessoaGrade = gradeConselho.getSelectionModel().getSelectedItem().getCodigoPessoa();

            try {
                ConselhoCondominioDAO dao = new ConselhoCondominioDAO();

                List<ConselhoCondominioVO> lista = dao.findConselheiro(codigoGradeConselho);

                lista.stream().forEach((list) -> {
                    txtNomePessoaConselho.setText(list.getNomePessoaConselho());
                    comboCargo.getSelectionModel().select(list.getCargo());
                    dateInicio.setValue(list.getDataIniciomandato());
                    dateTermino.setValue(list.getDataterminoMandato());
                    textObservacao.setText(list.getObservacao());
                });

            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao retornar dados no clique da grade.\n" + e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        limpar();
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
        txtNomePessoaConselho.setText(controller.nomePessoaMorador);//aqui resgata os dados do segundo controller.

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
        if (txtNomePessoaConselho.getText().isEmpty()) {
            Funcoes.messageAlert("Um morador deve ser informado no sistema.", Alert.AlertType.WARNING);
            return;
        }
        if (comboCargo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe o cargo.", Alert.AlertType.WARNING);
            comboCargo.requestFocus();
            return;
        }
        if (dateInicio.getValue() == null) {
            Funcoes.messageAlert("O campo Data Início é obrigatório.", Alert.AlertType.WARNING);
            dateInicio.requestFocus();
            return;
        }

        if (dateTermino.getValue() != null) {
            int verificaData = Funcoes.comparaDatas(dateInicio.getValue(), dateTermino.getValue());

            if (verificaData < 0) {
                String dataIni = Funcoes.formataLocalDateBR(dateInicio.getValue());
                String dataFim = Funcoes.formataLocalDateBR(dateTermino.getValue());
                Funcoes.messageAlert("A data de início do mandato não pode ser maior que a data de término do mandato.\n"
                        + "Data início: " + dataIni + "\n"
                        + "Data término: " + dataFim, Alert.AlertType.WARNING);
                return;
            }
        }

        try {

            ConselhoCondominioDAO dao = new ConselhoCondominioDAO();

            String cargo = comboCargo.getSelectionModel().getSelectedItem();
            LocalDate dataIni = dateInicio.getValue();
            LocalDate dataFim = dateTermino.getValue();
            String observacao = textObservacao.getText().trim();

            ConselhoCondominioVO oConselho = new ConselhoCondominioVO(recebeCodigoPessoa,
                    cargo,
                    dataIni,
                    dataFim,
                    observacao
            );

            dao.save(oConselho);
            
            getGrade();

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomePessoaConselho.getText().isEmpty()) {
            Funcoes.messageAlert("Um morador deve ser informado no sistema.", Alert.AlertType.WARNING);
            return;
        }
        if (comboCargo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe o cargo.", Alert.AlertType.WARNING);
            comboCargo.requestFocus();
            return;
        }
        if (dateInicio.getValue() == null) {
            Funcoes.messageAlert("O campo Data Início é obrigatório.", Alert.AlertType.WARNING);
            dateInicio.requestFocus();
            return;
        }

        if (dateTermino.getValue() != null) {
            int verificaData = Funcoes.comparaDatas(dateInicio.getValue(), dateTermino.getValue());

            if (verificaData < 0) {
                String dataIni = Funcoes.formataLocalDateBR(dateInicio.getValue());
                String dataFim = Funcoes.formataLocalDateBR(dateTermino.getValue());
                Funcoes.messageAlert("A data de início do mandato não pode ser maior que a data de término do mandato.\n"
                        + "Data início: " + dataIni + "\n"
                        + "Data término: " + dataFim, Alert.AlertType.WARNING);
                return;
            }
        }

        try {

            ConselhoCondominioDAO dao = new ConselhoCondominioDAO();

            String cargo = comboCargo.getSelectionModel().getSelectedItem();
            LocalDate dataIni = dateInicio.getValue();
            LocalDate dataFim = dateTermino.getValue();
            String observacao = textObservacao.getText().trim();

            ConselhoCondominioVO oConselho = new ConselhoCondominioVO(codigoGradeConselho, 
                    codigoPessoaGrade, 
                    cargo, 
                    dataIni, 
                    dataFim, 
                    observacao
            );

            dao.update(oConselho);
            
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
                ConselhoCondominioDAO dao = new ConselhoCondominioDAO();
                
                ConselhoCondominioVO oConselho = new ConselhoCondominioVO(codigoGradeConselho);
                
                dao.delete(oConselho);
                
                getGrade();
                
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
        limpar();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void gradeConselhoOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldConselho();
    }

    private void limpar() {
        txtNomePessoaConselho.setText("");
        comboCargo.getSelectionModel().select(-1);
        dateInicio.setValue(null);
        dateTermino.setValue(null);
        textObservacao.setText("");
        comboFiltro.getSelectionModel().select(-1);
    }

    private void carregaComboCargoConselho() {

        comboCargo.getItems().addAll("Síndico", "SubSíndico", "Conselheiro", "Conselho Fiscal");
    }

    private void carregaComboFiltro() {
        comboFiltro.getItems().addAll("ATIVO", "INATIVO");
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

                comboFiltro.setDisable(desativa);
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

                comboFiltro.setDisable(desativa);
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

                comboFiltro.setDisable(ativa);
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

                comboFiltro.setDisable(ativa);
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

                comboFiltro.setDisable(ativa);
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

                comboFiltro.setDisable(ativa);
                break;
        }
    }

    @FXML
    private void comboFiltroOnAction(ActionEvent event) {
        if (comboFiltro.getSelectionModel().getSelectedItem() != null) {
            try {

                String filtro = comboFiltro.getSelectionModel().getSelectedItem();

                ConselhoCondominioDAO dao = new ConselhoCondominioDAO();

                List<ConselhoCondominioVO> lista = dao.findAll();

                List<ConselhoCondominioVO> filter = lista.stream().filter(list -> list.getStatusFiltro().equals(filtro)).collect(Collectors.toList());

                gradeConselho.getItems().clear();
                gradeConselho.getItems().addAll(filter);

            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao gerar filtro na grade.\n" + e, Alert.AlertType.ERROR);
            }
        } else {
            getGrade();
        }
    }

}
