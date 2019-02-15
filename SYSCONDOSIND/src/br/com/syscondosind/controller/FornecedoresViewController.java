package br.com.syscondosind.controller;

import br.com.syscondosind.dao.EnderecosEletronicosDAO;
import br.com.syscondosind.dao.FornecedoresDAO;
import br.com.syscondosind.dao.GruposFornecedoresDAO;
import br.com.syscondosind.dao.TelefonesDAO;
import br.com.syscondosind.dao.TiposFornecedoresDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.enumerics.TipoPessoaEnumerics;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.funcoes.TextFieldFormatter;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.EnderecoEletronicosVO;
import br.com.syscondosind.vo.FornecedoresVO;
import br.com.syscondosind.vo.GruposFornecedoresVO;
import br.com.syscondosind.vo.TelefonesVO;
import br.com.syscondosind.vo.TiposFornecedoresVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
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
public class FornecedoresViewController implements Initializable {

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
    private JFXTextField txtNomeFornecedor;
    @FXML
    private JFXComboBox<GruposFornecedoresVO> comboGrupoFornecedores;
    @FXML
    private JFXComboBox<TiposFornecedoresVO> comboTipoFornecedor;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private JFXTextField txtTipoPessoa;
    @FXML
    private JFXTextField txtCPF_CNPJ;
    @FXML
    private JFXTextField txtInscEstadual;
    @FXML
    private JFXTextField txtInscMunicipal;
    @FXML
    private JFXTextField txtNomeRepresentante;
    @FXML
    private JFXTextField txtCustoMedioFrete;
    private JFXTextField txtPrazoPagamento;
    @FXML
    private TextArea textObservacao;
    @FXML
    private TableView<FornecedoresVO> gradeFornecedores;
    @FXML
    private TableColumn<FornecedoresVO, Integer> colIDFornededor;
    @FXML
    private TableColumn<FornecedoresVO, String> colNomeFornecedor;
    @FXML
    private TableColumn<FornecedoresVO, String> colStatusFornecedor;
    @FXML
    private TableColumn<FornecedoresVO, String> colTipoPessoaFornecedor;
    @FXML
    private TableColumn<FornecedoresVO, String> colGrupoFornecedor;
    @FXML
    private TableColumn<FornecedoresVO, String> colTipoFornecedorFornecedor;
    @FXML
    private TableView<EnderecoEletronicosVO> gradeEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, Integer> colEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colTipoEmailEmail;
    @FXML
    private TableColumn<EnderecoEletronicosVO, String> colStatusEmail;
    @FXML
    private TableView<TelefonesVO> gradeTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colDDDTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colNumeroTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colRamalTelefone;
    @FXML
    private TableColumn<TelefonesVO, String> colTipoTelefone;

    private Integer recebeCodigoPessoaFornecedor;
    @FXML
    private JFXComboBox<String> comboPrazoPagamento;

    private Integer codigoGradeFornecedor;
    private Integer codigoGradePessoa;
    @FXML
    private JFXButton btnCadastraEmail;
    @FXML
    private JFXButton btnCadastraTelefone;
    private Integer codigoPessoaUpdate; //usado para receber o codigo da pessoa ao clicar na grade.

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboGrupoFornecedor();
        carregacomboStatus();
        carregaPrazoPagamento();
        renderizaGradeFornecedor();
        getgradeFornecedor();
        renderizaGradeEmail();
        renderizaGradeTelefone();
    }

    private void renderizaGradeFornecedor() {

        colIDFornededor.setCellValueFactory(new PropertyValueFactory<>("codigoFornecedor"));
        colNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
        colStatusFornecedor.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTipoPessoaFornecedor.setCellValueFactory(new PropertyValueFactory<>("tipoPessoa"));
        colGrupoFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeGrupoFornecedor"));
        colTipoFornecedorFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeTipoFornecedor"));
    }

    private void getgradeFornecedor() {

        try {

            FornecedoresDAO dao = new FornecedoresDAO();

            List<FornecedoresVO> list = dao.findAll();

            gradeFornecedores.getItems().clear();
            gradeFornecedores.getItems().addAll(list);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar a grade Fornecedores.", Alert.AlertType.ERROR);
            System.out.println("Erro.\n" + e);
        }
    }

    private void getFieldFornecedor() {

        if (gradeFornecedores.getSelectionModel().getSelectedItem() != null) {

            codigoGradeFornecedor = gradeFornecedores.getSelectionModel().getSelectedItem().getCodigoFornecedor();
            codigoGradePessoa = gradeFornecedores.getSelectionModel().getSelectedItem().getCodigoPessoaFornecedor();

            try {

                FornecedoresDAO dao = new FornecedoresDAO();

                List<FornecedoresVO> lista = dao.findAllFornecedor(codigoGradeFornecedor);
                
                lista.stream().forEach((list) ->{
                    codigoPessoaUpdate = list.getCodigoPessoaFornecedor();
                    txtNomeFornecedor.setText(list.getNomeFornecedor());
                    comboGrupoFornecedores.getSelectionModel().select(0);
                    findGrupoFornecedores(list.getCodigoGrupoFornecedor());
                    comboTipoFornecedor.getSelectionModel().select(0);
                    findTiposFornecedores(list.getCodigoTipoFornecedor());
                    comboStatus.getSelectionModel().select(list.getStatus());
                    txtTipoPessoa.setText(list.getTipoPessoa());
                    txtCPF_CNPJ.setText(list.getCpf_cnpj());
                    txtInscEstadual.setText(list.getInscEstadual());
                    txtInscMunicipal.setText(list.getInscMunicipal());
                    txtNomeRepresentante.setText(list.getRepresentante());
                    comboPrazoPagamento.getSelectionModel().select(list.getPrazoPagamento());
                    txtCustoMedioFrete.setText(Double.toString(list.getCustoMediofrete()));
                    textObservacao.setText(list.getObservacao());
                    
                    getGradeEmail();
                    getGradeTelefone();
                });

            } catch (DAOException e) {
                Funcoes.messageAlert("Erro ao carregar campos ao clicar na grade.\n"+e, Alert.AlertType.ERROR);
                System.err.println("Erro\n"+e);
            }
        }
    }
    
    private void findGrupoFornecedores(int codigoGrupo){
        try {
            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();
            
            List<GruposFornecedoresVO> list = dao.findGrupo(codigoGrupo);
            
            list.stream().forEach((lista) ->{
                comboGrupoFornecedores.getSelectionModel().select(lista);
            });
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro carregar o combo grupo de fornecedores.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void findTiposFornecedores(int codigoTipoFornecedor){
        
        try {
            TiposFornecedoresDAO dao = new TiposFornecedoresDAO();
            
            List<TiposFornecedoresVO> list = dao.findTipoFornecedor(codigoTipoFornecedor);
            
            list.stream().forEach((lista)->{
                comboTipoFornecedor.getSelectionModel().select(lista);
            });
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro carregar o combo tipos de fornecedores.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void renderizaGradeEmail() {
        
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTipoEmailEmail.setCellValueFactory(new PropertyValueFactory<>("tipoEmail"));
        colStatusEmail.setCellValueFactory(new PropertyValueFactory<>("status"));
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
    
    private void renderizaGradeTelefone() {
        
        colDDDTelefone.setCellValueFactory(new PropertyValueFactory<>("ddd"));
        colNumeroTelefone.setCellValueFactory(new PropertyValueFactory<>("numeroTelefone"));
        colRamalTelefone.setCellValueFactory(new PropertyValueFactory<>("ramal"));
        colTipoTelefone.setCellValueFactory(new PropertyValueFactory<>("tipoTelefone"));
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

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getgradeFornecedor();
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

        /*Controler.pessoaFisica é usado para setar um tipo de pessoa.*/
//        controller.pessoaFisica();
        /*COntroller.carregaComboTipoPessoa deixa a escolha do usuário decidir*/
        controller.carregaComboTipoPessoa();

//------------------------------------------------------------------     
        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        recebeCodigoPessoaFornecedor = controller.codigo_pessoa;
        txtNomeFornecedor.setText(controller.nomePessoa);//aqui resgata os dados do segundo controller.
        txtTipoPessoa.setText(controller.pegaTipoPesoa);

        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if (recebeCodigoPessoaFornecedor > 0) {
            controlMenuButton(ControlButtonsEnum.NOVO);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeFornecedor.getText().isEmpty()) {
            Funcoes.messageAlert("É obrigatório selecionar um fornecedor.", Alert.AlertType.WARNING);
            return;
        }
        if (comboGrupoFornecedores.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Infome um grupo de fornecedor.", Alert.AlertType.WARNING);
            comboGrupoFornecedores.requestFocus();
            return;
        }
        if (comboTipoFornecedor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um tipo de fornecedor.", Alert.AlertType.WARNING);
            comboTipoFornecedor.requestFocus();
            return;
        }
        if (comboStatus.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Selecione um Status para o fornecedor.", Alert.AlertType.WARNING);
            comboStatus.requestFocus();
            return;
        }
        if (txtCPF_CNPJ.getText().isEmpty()) {

            if (txtTipoPessoa.getText().equals(TipoPessoaEnumerics.FISICA.getTipoPessoa())) {
                Funcoes.messageAlert("Informe o CPF do fornecedor.", Alert.AlertType.WARNING);
            } else {
                Funcoes.messageAlert("Informe o CNPJ do fornecedor", Alert.AlertType.WARNING);
            }

            txtCPF_CNPJ.requestFocus();
            return;
        }
        if (comboPrazoPagamento.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um prazo de pagamento.", Alert.AlertType.WARNING);
            comboPrazoPagamento.requestFocus();
            return;
        }
        if (txtCustoMedioFrete.getText().isEmpty()) {
            Funcoes.messageAlert("Informe o custo médio de frete.", Alert.AlertType.WARNING);
            txtCustoMedioFrete.requestFocus();
            return;
        }

        try {
            FornecedoresDAO dao = new FornecedoresDAO();

            Integer codigoPessoaFornecedor = recebeCodigoPessoaFornecedor;
            Integer codigoTipoFornecedor = comboTipoFornecedor.getSelectionModel().getSelectedItem().getCodigoTipoFornecedor();
            String status = comboStatus.getSelectionModel().getSelectedItem();
            String tipoPessoa = txtTipoPessoa.getText();
            String cpf_cnpj = txtCPF_CNPJ.getText().trim();
            String inscEstadual = txtInscEstadual.getText().trim();
            String inscMunicipal = txtInscMunicipal.getText().trim();
            String nomeRepresentante = txtNomeRepresentante.getText().trim().toUpperCase();
            String prazoPagamento = comboPrazoPagamento.getSelectionModel().getSelectedItem();
            double custoMedioFrete = Double.parseDouble(txtCustoMedioFrete.getText().trim());
            String observacao = textObservacao.getText().trim();

            FornecedoresVO oForn = new FornecedoresVO(codigoPessoaFornecedor,
                    codigoTipoFornecedor,
                    status,
                    tipoPessoa,
                    cpf_cnpj,
                    inscEstadual,
                    inscMunicipal,
                    nomeRepresentante,
                    prazoPagamento,
                    custoMedioFrete,
                    observacao
            );

            dao.save(oForn);
            
            getgradeFornecedor();

            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException | NumberFormatException e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n" + e, Alert.AlertType.ERROR);
            System.out.println("Erro\n" + e);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeFornecedor.getText().isEmpty()) {
            Funcoes.messageAlert("É obrigatório selecionar um fornecedor.", Alert.AlertType.WARNING);
            return;
        }
        if (comboGrupoFornecedores.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Infome um grupo de fornecedor.", Alert.AlertType.WARNING);
            comboGrupoFornecedores.requestFocus();
            return;
        }
        if (comboTipoFornecedor.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um tipo de fornecedor.", Alert.AlertType.WARNING);
            comboTipoFornecedor.requestFocus();
            return;
        }
        if (comboStatus.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Selecione um Status para o fornecedor.", Alert.AlertType.WARNING);
            comboStatus.requestFocus();
            return;
        }
        if (txtCPF_CNPJ.getText().isEmpty()) {

            if (txtTipoPessoa.getText().equals(TipoPessoaEnumerics.FISICA.getTipoPessoa())) {
                Funcoes.messageAlert("Informe o CPF do fornecedor.", Alert.AlertType.WARNING);
            } else {
                Funcoes.messageAlert("Informe o CNPJ do fornecedor", Alert.AlertType.WARNING);
            }

            txtCPF_CNPJ.requestFocus();
            return;
        }
        if (comboPrazoPagamento.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Informe um prazo de pagamento.", Alert.AlertType.WARNING);
            comboPrazoPagamento.requestFocus();
            return;
        }
        if (txtCustoMedioFrete.getText().isEmpty()) {
            Funcoes.messageAlert("Informe o custo médio de frete.", Alert.AlertType.WARNING);
            txtCustoMedioFrete.requestFocus();
            return;
        }

        try {
            FornecedoresDAO dao = new FornecedoresDAO();

            Integer codigoPessoaFornecedor = recebeCodigoPessoaFornecedor;
            Integer codigoTipoFornecedor = comboTipoFornecedor.getSelectionModel().getSelectedItem().getCodigoTipoFornecedor();
            String status = comboStatus.getSelectionModel().getSelectedItem();
            String tipoPessoa = txtTipoPessoa.getText();
            String cpf_cnpj = txtCPF_CNPJ.getText().trim();
            String inscEstadual = txtInscEstadual.getText().trim();
            String inscMunicipal = txtInscMunicipal.getText().trim();
            String nomeRepresentante = txtNomeRepresentante.getText().trim().toUpperCase();
            String prazoPagamento = comboPrazoPagamento.getSelectionModel().getSelectedItem();
            double custoMedioFrete = Double.parseDouble(txtCustoMedioFrete.getText().trim());
            String observacao = textObservacao.getText().trim();

            FornecedoresVO oForn = new FornecedoresVO(codigoGradeFornecedor, 
                    codigoGradePessoa, 
                    codigoTipoFornecedor, 
                    status, 
                    tipoPessoa, 
                    cpf_cnpj, 
                    inscEstadual, 
                    inscMunicipal, 
                    nomeRepresentante,
                    prazoPagamento, 
                    custoMedioFrete, 
                    observacao
            );

            dao.update(oForn);
            
            getgradeFornecedor();

            Funcoes.messageAlert("Registro atualizado com sucesso.", Alert.AlertType.INFORMATION);

            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException | NumberFormatException e) {
            Funcoes.messageAlert("Erro ao atualizar registro.\n" + e, Alert.AlertType.ERROR);
            System.out.println("Erro\n" + e);
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
                
                FornecedoresDAO dao = new FornecedoresDAO();
                
                FornecedoresVO oForn = new FornecedoresVO(codigoGradeFornecedor);
                
                dao.delete(oForn);
                
                getgradeFornecedor();
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao deletar registro.\n"+e, Alert.AlertType.ERROR);
            System.err.println("Erro.\n"+e);
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.CANCELAR);
    }

    @FXML
    private void limparOnaction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void buscaFornecedorOnAction(ActionEvent event) {
    }

    @FXML
    private void buscaGrupoFornecedor(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GruposFornecedoresView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        carregaComboGrupoFornecedor();

    }

    @FXML
    private void gradeFornecedoresMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        btnCadastraEmail.setDisable(false);
        btnCadastraTelefone.setDisable(false);
        getFieldFornecedor();
    }

    private void carregaComboGrupoFornecedor() {
        try {
            GruposFornecedoresDAO dao = new GruposFornecedoresDAO();

            List<GruposFornecedoresVO> lista = dao.findAll();

            comboGrupoFornecedores.getItems().clear();
            comboGrupoFornecedores.getItems().addAll(lista);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar o combo Grupo de fornecedores.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void carregaComboTipoFornecedor(Integer codigoGrupoForn) {
        try {
            TiposFornecedoresDAO dao = new TiposFornecedoresDAO();

            List<TiposFornecedoresVO> lista = dao.findAll();

            List<TiposFornecedoresVO> filter = lista.stream().filter(list -> list.getCodigoGrupoFornecedor().equals(codigoGrupoForn)).collect(Collectors.toList());

            comboTipoFornecedor.getItems().clear();
            comboTipoFornecedor.getItems().addAll(filter);
        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao carregar o combo Tipos de fornecedores.\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void grupoFornecedorOnAction(ActionEvent event) {

        if (comboGrupoFornecedores.getSelectionModel().getSelectedItem() != null) {

            Integer codigoGrupoForn = comboGrupoFornecedores.getSelectionModel().getSelectedItem().getCodigoGrupoFornecedor();

            carregaComboTipoFornecedor(codigoGrupoForn);
        } else {
            comboTipoFornecedor.getItems().clear();
        }
    }

    private void carregacomboStatus() {

        for (StatusEnum s : StatusEnum.values()) {

            comboStatus.getItems().addAll(s.getStatus());
        }
    }

    private void carregaPrazoPagamento() {

        comboPrazoPagamento.getItems().addAll("Á VISTA", "30 DIAS", "60 DIAS", "90 DIAS", "120 DIAS", "MAIS QUE 120 DIAS");
    }

    private void limpaCampos() {
        txtNomeFornecedor.setText("");
        comboGrupoFornecedores.getSelectionModel().select(-1);
        comboStatus.getSelectionModel().select(-1);
        txtTipoPessoa.setText("");
        txtCPF_CNPJ.setText("");
        txtInscEstadual.setText("");
        txtInscMunicipal.setText("");
        txtNomeRepresentante.setText("");
        comboPrazoPagamento.getSelectionModel().select(-1);
        txtCustoMedioFrete.setText("");
        
        btnCadastraEmail.setDisable(true);
        btnCadastraTelefone.setDisable(true);
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
    private void cpf_cnpjOnKeyReleased(KeyEvent event) {

        if (txtTipoPessoa.getText().equals(TipoPessoaEnumerics.FISICA.getTipoPessoa())) {
            TextFieldFormatter tff = new TextFieldFormatter();
            //123.456.789-01
            tff.setMask("###.###.###-##");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(txtCPF_CNPJ);
            tff.formatter();

        } else {
            TextFieldFormatter tff = new TextFieldFormatter();
            //14.218.835/0001-27
            tff.setMask("##.###.###/####-##");
            tff.setCaracteresValidos("0123456789");
            tff.setTf(txtCPF_CNPJ);
            tff.formatter();
        }
    }

    @FXML
    private void custoFreteOnKeyReleased(KeyEvent event) {
        try {
            /*\\d{0,7}  quantidade de digitos antes do ponto*/
 /*[\\.] somente será aceito ponto simples */
 /*\\d{0,2}) quantidade de digitos após o ponto simples */
            txtCustoMedioFrete.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                    txtCustoMedioFrete.setText(oldValue);
                }
            });
        } catch (Exception e) {
            Funcoes.messageAlert("Erro.\n" + e, Alert.AlertType.ERROR);
            txtCustoMedioFrete.setText("");
        }
    }

    @FXML
    private void cadastraEmailOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/EnderecosEletronicosView.fxml"));

        Parent parent = loader.load();

        /*Esse codigo envia dados para um metodo no controller telefones
         enviando o codigo da pessoa e o nome para serem usados no form.*/
        EnderecosEletronicosViewController controller = loader.getController();
        controller.carregaVariaveisPrinciáis(codigoPessoaUpdate, txtNomeFornecedor.getText().trim().toUpperCase());

        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        
        getGradeEmail();
    }

    @FXML
    private void cadastraTelefoneOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/TelefonesView.fxml"));

        Parent parent = loader.load();

        /*Esse codigo envia dados para um metodo no controller telefones
         enviando o codigo da pessoa e o nome para serem usados no form.*/
        TelefonesViewController controller = loader.getController();
        controller.carregaVariaveisPrinciáis(codigoPessoaUpdate, txtNomeFornecedor.getText().trim().toUpperCase());

        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        
        getGradeTelefone();
    }

    @FXML
    private void buscaTipoFornecedor(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/TiposFornecedoresView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        
        
        Integer codigoFornecedor = comboGrupoFornecedores.getSelectionModel().getSelectedItem().getCodigoGrupoFornecedor();
        
        carregaComboTipoFornecedor(codigoFornecedor);
    }

}
