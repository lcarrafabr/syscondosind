package br.com.syscondosind.controller;

import br.com.syscondosind.dao.ApartamentosDAO;
import br.com.syscondosind.dao.EnderecosEletronicosDAO;
import br.com.syscondosind.dao.MoradoresDAO;
import br.com.syscondosind.dao.PessoasDAO;
import br.com.syscondosind.dao.TelefonesDAO;
import br.com.syscondosind.dao.TorreBlocoDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.SimNaoEnum;
import br.com.syscondosind.enumerics.TipoPessoaEnumerics;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.ApartamentosVO;
import br.com.syscondosind.vo.EnderecoEletronicosVO;
import br.com.syscondosind.vo.MoradoresVO;
import br.com.syscondosind.vo.PessoasVO;
import br.com.syscondosind.vo.TelefonesVO;
import br.com.syscondosind.vo.TorresVo;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
public class MoradoresViewController implements Initializable {

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
    private JFXTextField txtMorador;
    @FXML
    private JFXComboBox<TorresVo> comboTorreBloco;
    @FXML
    private JFXComboBox<ApartamentosVO> comboAptoSetor;
    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtRG;
    @FXML
    private JFXComboBox<String> comboPossuiVeiculo;
    @FXML
    private JFXComboBox<String> comboTipoMorador;
    @FXML
    private JFXDatePicker dateDataNascimento;
    @FXML
    private JFXComboBox<PessoasVO> comboProprietario;
    @FXML
    private TableView<MoradoresVO> gradeMorador;
    @FXML
    private TableColumn<MoradoresVO, Integer> collMoradorId;
    @FXML
    private TableColumn<MoradoresVO, String> colMoradorNome;
    @FXML
    private TableColumn<MoradoresVO, String> colMoradorTorreBloco;
    @FXML
    private TableColumn<MoradoresVO, String> colMoradorAptoSetor;
    @FXML
    private TableColumn<MoradoresVO, String> colTipoMorador;
    @FXML
    private TableView<TelefonesVO> gradeTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colTelefoneNumero;
    @FXML
    private TableColumn<TelefonesVO, String> colTelefoneRamal;
    @FXML
    private TableColumn<TelefonesVO, String> colTelefoneTipoTel;
    @FXML
    private TableView<EnderecoEletronicosVO> gradeEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colTipoEmail;
    @FXML
    private JFXButton btnRegistraTelefone;
    @FXML
    private JFXButton btnRegistraEmails;

    public Integer recebeCodigoPessoa;
    @FXML
    private TextField txtConsultaMorador;

    private Integer codigoGradeMorador;
    private Integer codigoPessoaUpdate; //usado para receber o codigo da pessoa ao clicar na grade.
    @FXML
    private TableColumn<TelefonesVO, String> colTelefoneDDD;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colEmailStatus;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboPossuiveiculo();
        carregaComboTipoMorador();
        carregaComboTorreBloco();
        carregaComboApto();
        carregaComboMoradores();
        renderizaGradeMorador();
        getGradeMorador();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        
        renderizaGradeTelefone();
        renderizaGradeEmail();
    }

    private void renderizaGradeMorador() {
        collMoradorId.setCellValueFactory(new PropertyValueFactory<>("codigoMorador"));
        colMoradorNome.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
        colMoradorTorreBloco.setCellValueFactory(new PropertyValueFactory<>("Torre"));
        colMoradorAptoSetor.setCellValueFactory(new PropertyValueFactory<>("Apto"));
        colTipoMorador.setCellValueFactory(new PropertyValueFactory<>("tipomorador"));
    }

    private void getGradeMorador() {

        String pesquisaMorador = txtConsultaMorador.getText().trim().toUpperCase();
        if (pesquisaMorador.isEmpty()) {
            pesquisaMorador = null;
        }

        try {

            MoradoresDAO dao = new MoradoresDAO();

            List<MoradoresVO> list = dao.findAll(pesquisaMorador);

            gradeMorador.getItems().clear();
            gradeMorador.getItems().addAll(list);

        } catch (DAOException e) {
//            e.printStackTrace();
            Funcoes.messageAlert("Erro ao gerar grade Moradores\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldMorador() {

        codigoGradeMorador = gradeMorador.getSelectionModel().getSelectedItem().getCodigoMorador();

        if (codigoGradeMorador > 0) {
            try {

                MoradoresDAO dao = new MoradoresDAO();

                List<MoradoresVO> list = dao.findMorador(codigoGradeMorador);

                list.stream().forEach((lista) -> {

                    codigoPessoaUpdate = lista.getCodigoPessoa();
                    txtMorador.setText(lista.getNomePessoa());
                    comboTorreBloco.getSelectionModel().select(-1);
                    getFindTorre(lista.getCodigoTorre());
                    comboAptoSetor.getSelectionModel().select(-1);
                    getFindApto(lista.getCodigoApto());
                    txtCPF.setText(lista.getCpf());
                    txtRG.setText(lista.getRg());
                    comboPossuiVeiculo.getSelectionModel().select(lista.getPossuiVeiculo());
                    comboTipoMorador.getSelectionModel().select(lista.getTipomorador());
                    dateDataNascimento.setValue(lista.getDatanascimento());
                    getFieldProprietario(lista.getCodigoPessoaproprietario());
                    
                    getGradeTelefone();
                    getGradeEmail();
                });

            } catch (DAOException e) {
                System.err.println(e);
                Funcoes.messageAlert("Erro ao carregar dados ao clicar na grade\n" + e, Alert.AlertType.ERROR);
            }
        }
    }

    private void getFieldProprietario(int codigoProprietario) {
        try {
            PessoasDAO dao = new PessoasDAO();

            List<PessoasVO> lista = dao.findPerson(codigoProprietario);

            lista.stream().forEach((lista1) -> {

                comboProprietario.getSelectionModel().select(0);
                comboProprietario.getSelectionModel().select(lista1);

            });
        } catch (DAOException e) {
        }
    }

    private void getFindTorre(int codigoTorre) {

        TorreBlocoDAO dao = new TorreBlocoDAO();

        try {

            List<TorresVo> lista = dao.findTorreBloco(codigoTorre);

            lista.stream().forEach((lista1) -> {
                comboTorreBloco.getSelectionModel().select(0);
                comboTorreBloco.getSelectionModel().select(lista1);
            });

        } catch (DAOException e) {
        }
    }

    private void getFindApto(int codigoApto) {

        try {
            ApartamentosDAO dao = new ApartamentosDAO();

            List<ApartamentosVO> lista = dao.findApartamento(codigoApto);

            lista.stream().forEach((lista1) -> {
                comboAptoSetor.getSelectionModel().select(0);
                comboAptoSetor.getSelectionModel().select(lista1);
            });

        } catch (DAOException e) {
        }
    }
    
    private void renderizaGradeTelefone() {
        
        colTelefoneDDD.setCellValueFactory(new PropertyValueFactory<>("ddd"));
        colTelefoneNumero.setCellValueFactory(new PropertyValueFactory<>("numeroTelefone"));
        colTelefoneRamal.setCellValueFactory(new PropertyValueFactory<>("ramal"));
        colTelefoneTipoTel.setCellValueFactory(new PropertyValueFactory<>("tipoTelefone"));
    }
    
    private void getGradeTelefone() {
        try {
//            System.out.println("recebe codigo: " + recebeCodigoPessoa);
            TelefonesDAO dao = new TelefonesDAO();

            List<TelefonesVO> list = dao.findTelefones(codigoPessoaUpdate);

            gradeTelefone.getItems().clear();
            gradeTelefone.getItems().addAll(list);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao atualizar grade.\n" + e, Alert.AlertType.ERROR);
        }
    }
    
    private void renderizaGradeEmail() {
        
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTipoEmail.setCellValueFactory(new PropertyValueFactory<>("tipoEmail"));
        colEmailStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void getGradeEmail() {

        try {

            EnderecosEletronicosDAO dao = new EnderecosEletronicosDAO();

            List<EnderecoEletronicosVO> list = dao.findEmailPessoa(codigoPessoaUpdate);

            gradeEmail.getItems().clear();
            gradeEmail.getItems().addAll(list);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar a grade endereços eletrônicos.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGradeMorador();
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        limpaCampos();
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
        txtMorador.setText(controller.nomePessoa);//aqui resgata os dados do segundo controller.

        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if (recebeCodigoPessoa > 0) {
            controlMenuButton(ControlButtonsEnum.NOVO);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) throws DAOException {
        if (txtMorador.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome morador é obrigatório..", Alert.AlertType.WARNING);
            txtMorador.requestFocus();
            return;
        }
        if (comboTorreBloco.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe uma torre ou bloco", Alert.AlertType.WARNING);
            comboTorreBloco.requestFocus();
            return;
        }
        if (comboAptoSetor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um Apto ou Setor", Alert.AlertType.WARNING);
            comboAptoSetor.requestFocus();
            return;
        }
        if (comboPossuiVeiculo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe se o morador possui um veículo.", Alert.AlertType.WARNING);
            comboPossuiVeiculo.requestFocus();
            return;
        }
        if (comboTipoMorador.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe o tipo morador.", Alert.AlertType.WARNING);
            comboTipoMorador.requestFocus();
            return;
        }
        if (dateDataNascimento.getValue() == null) {
            Funcoes.messageAlert("O cmapo data nascimento é obrigatório.", Alert.AlertType.WARNING);
            dateDataNascimento.requestFocus();
            return;
        }
        if (comboProprietario.getSelectionModel().getSelectedIndex() == -1
                && !"PROPRIETÁRIO".equals(comboTipoMorador.getSelectionModel().getSelectedItem())) {

            Funcoes.messageAlert("Informe o proprietário do Apto/Setor", Alert.AlertType.WARNING);
            comboProprietario.requestFocus();
            return;

        }

        try {

            Integer codigoPessoa = recebeCodigoPessoa;
            Integer codigoTorreBlobo = comboTorreBloco.getSelectionModel().getSelectedItem().getCodigoTorre();
            Integer codigoApartamento = comboAptoSetor.getSelectionModel().getSelectedItem().getCodigoApartamento();
            String cpf = txtCPF.getText().trim().replaceAll("\\.", "").replaceAll("-", "");
            String rg = txtRG.getText().trim();
            String possuiVeiculo = comboPossuiVeiculo.getSelectionModel().getSelectedItem();
            String tipoMorador = comboTipoMorador.getSelectionModel().getSelectedItem();
            LocalDate dataNascimento = dateDataNascimento.getValue();
            Integer codigoProprietario = 0;
            if (comboProprietario.getSelectionModel().getSelectedIndex() == -1) {
                codigoProprietario = recebeCodigoPessoa;
            } else {
                codigoProprietario = comboProprietario.getSelectionModel().getSelectedItem().getCodigoPessoa();
            }

            MoradoresVO oMorador = new MoradoresVO(0, codigoPessoa,
                    codigoTorreBlobo,
                    codigoApartamento,
                    cpf,
                    rg,
                    possuiVeiculo,
                    tipoMorador,
                    dataNascimento,
                    codigoProprietario);

            MoradoresDAO dao = new MoradoresDAO();
            dao.save(oMorador);

            getGradeMorador();

            Funcoes.messageAlert("Dados cadastrados com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar\n" + e, Alert.AlertType.ERROR);
            throw new DAOException("Erro ao cadastrar\n" + e, e);
        }
    }

    @FXML
    private void salvarMaisOnAction(ActionEvent event) throws DAOException, IOException {
        salvarOnAction(event);
        novoOnAction(event);

    }

    @FXML
    private void alterarOnAction(ActionEvent event) throws DAOException {
        if (txtMorador.getText().isEmpty()) {
            Funcoes.messageAlert("O campo Nome morador é obrigatório..", Alert.AlertType.WARNING);
            txtMorador.requestFocus();
            return;
        }
        if (comboTorreBloco.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe uma torre ou bloco", Alert.AlertType.WARNING);
            comboTorreBloco.requestFocus();
            return;
        }
        if (comboAptoSetor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um Apto ou Setor", Alert.AlertType.WARNING);
            comboAptoSetor.requestFocus();
            return;
        }
        if (comboPossuiVeiculo.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe se o morador possui um veículo.", Alert.AlertType.WARNING);
            comboPossuiVeiculo.requestFocus();
            return;
        }
        if (comboTipoMorador.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe o tipo morador.", Alert.AlertType.WARNING);
            comboTipoMorador.requestFocus();
            return;
        }
        if (dateDataNascimento.getValue() == null) {
            Funcoes.messageAlert("O cmapo data nascimento é obrigatório.", Alert.AlertType.WARNING);
            dateDataNascimento.requestFocus();
            return;
        }
        if (comboProprietario.getSelectionModel().getSelectedIndex() == -1
                && !"PROPRIETÁRIO".equals(comboTipoMorador.getSelectionModel().getSelectedItem())) {

            Funcoes.messageAlert("Informe o proprietário do Apto/Setor", Alert.AlertType.WARNING);
            comboProprietario.requestFocus();
            return;

        }

        try {

            Integer codigoTorreBlobo = comboTorreBloco.getSelectionModel().getSelectedItem().getCodigoTorre();
            Integer codigoApartamento = comboAptoSetor.getSelectionModel().getSelectedItem().getCodigoApartamento();
            String cpf = txtCPF.getText().trim().replaceAll("\\.", "").replaceAll("-", "");
            String rg = txtRG.getText().trim();
            String possuiVeiculo = comboPossuiVeiculo.getSelectionModel().getSelectedItem();
            String tipoMorador = comboTipoMorador.getSelectionModel().getSelectedItem();
            LocalDate dataNascimento = dateDataNascimento.getValue();
            Integer codigoProprietario = 0;
            if (comboProprietario.getSelectionModel().getSelectedIndex() == -1) {
                codigoProprietario = recebeCodigoPessoa;
            } else {
                codigoProprietario = comboProprietario.getSelectionModel().getSelectedItem().getCodigoPessoa();
            }

            MoradoresVO oMorador = new MoradoresVO(codigoGradeMorador,
                    codigoPessoaUpdate,
                    codigoTorreBlobo,
                    codigoApartamento,
                    cpf,
                    rg,
                    possuiVeiculo,
                    tipoMorador,
                    dataNascimento,
                    codigoProprietario);

            MoradoresDAO dao = new MoradoresDAO();
            dao.update(oMorador);

            getGradeMorador();

            Funcoes.messageAlert("Dados alterados com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar\n" + e, Alert.AlertType.ERROR);
            throw new DAOException("Erro ao cadastrar\n" + e, e);
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

            if (result.get() == ButtonType.OK) {

                MoradoresDAO dao = new MoradoresDAO();

                MoradoresVO oMorador = new MoradoresVO(codigoGradeMorador, 0, 0, 0, null, null, null, null, null, 0);

                dao.delete(oMorador);

                getGradeMorador();

                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);

                controlMenuButton(ControlButtonsEnum.EXCLUIR);

            }
        } catch (DAOException e) {
            throw new DAOException("Erro ao deletar registro\n" + e, e);
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
    private void gradeMoradorOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        btnRegistraTelefone.setDisable(false);
        btnRegistraEmails.setDisable(false);
        getFieldMorador();
    }

    @FXML
    private void registraTelefoneOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/TelefonesView.fxml"));

        Parent parent = loader.load();

        /*Esse codigo envia dados para um metodo no controller telefones
         enviando o codigo da pessoa e o nome para serem usados no form.*/
        TelefonesViewController controller = loader.getController();
        controller.carregaVariaveisPrinciáis(codigoPessoaUpdate, txtMorador.getText().trim().toUpperCase());

        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        
        getGradeTelefone();
    }

    @FXML
    private void registraEmailOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/EnderecosEletronicosView.fxml"));

        Parent parent = loader.load();

        /*Esse codigo envia dados para um metodo no controller telefones
         enviando o codigo da pessoa e o nome para serem usados no form.*/
        EnderecosEletronicosViewController controller = loader.getController();
        controller.carregaVariaveisPrinciáis(codigoPessoaUpdate, txtMorador.getText().trim().toUpperCase());

        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        
        getGradeEmail();
    }

    private void carregaComboPossuiveiculo() {

        for (SimNaoEnum s : SimNaoEnum.values()) {
            comboPossuiVeiculo.getItems().add(s.getOpcao());
        }
    }

    private void carregaComboTipoMorador() {
        comboTipoMorador.getItems().addAll("PROPRIETÁRIO", "INQUILINO", "DEPENDENTE");
    }

    private void carregaComboTorreBloco() {
        try {
            TorreBlocoDAO dao = new TorreBlocoDAO();
            List<TorresVo> list = dao.findAll();

            comboTorreBloco.getItems().clear();
            comboTorreBloco.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar combo Torre/Bloco\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboApto() {
        try {
            ApartamentosDAO dao = new ApartamentosDAO();

            List<ApartamentosVO> list = dao.findAll();

            comboAptoSetor.getItems().clear();
            comboAptoSetor.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar combo Apto/setor\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboMoradores() {
        try {
            PessoasDAO dao = new PessoasDAO();
            List<PessoasVO> list = dao.find(null, TipoPessoaEnumerics.FISICA.getTipoPessoa());

            comboProprietario.getItems().clear();
            comboProprietario.getItems().addAll(list);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar combo Proprietários\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void limpaCampos() {
        txtMorador.setText("");
        comboTorreBloco.getSelectionModel().select(-1);
        comboAptoSetor.getSelectionModel().select(-1);
        txtCPF.setText("");
        txtRG.setText("");
        comboPossuiVeiculo.getSelectionModel().select(-1);
        dateDataNascimento.setValue(null);
        comboProprietario.getSelectionModel().select(-1);
        txtConsultaMorador.setText("");

        btnRegistraTelefone.setDisable(true);
        btnRegistraEmails.setDisable(true);
    }

    @FXML
    private void consultaMoradorKeyReleased(KeyEvent event) {
        getGradeMorador();
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

                btnRegistraTelefone.setDisable(true);
                btnRegistraEmails.setDisable(true);
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

                btnRegistraTelefone.setDisable(true);
                btnRegistraEmails.setDisable(true);
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

                btnRegistraTelefone.setDisable(true);
                btnRegistraEmails.setDisable(true);
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

                btnRegistraTelefone.setDisable(true);
                btnRegistraEmails.setDisable(true);
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

                btnRegistraTelefone.setDisable(true);
                btnRegistraEmails.setDisable(true);
                break;
        }
    }

    @FXML
    private void cadastraTorreOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/TorresView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        carregaComboTorreBloco();
    }

    @FXML
    private void cadastraAptoOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/ApartamentosView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        carregaComboApto();
    }

}
