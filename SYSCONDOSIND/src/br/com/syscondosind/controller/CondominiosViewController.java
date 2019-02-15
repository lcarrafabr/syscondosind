/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.controller;

import br.com.syscondosind.dao.CondominioDAO;
import br.com.syscondosind.dao.DestinacaoDAO;
import br.com.syscondosind.dao.TiposCondominiosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.EstadosUfEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.funcoes.TextFieldFormatter;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.viacep.ViaCEP;
import br.com.syscondosind.viacep.ViaCEPException;
import br.com.syscondosind.vo.CondominiosVO;
import br.com.syscondosind.vo.DestinacaoVO;
import br.com.syscondosind.vo.TipoCondominioVO;
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
public class CondominiosViewController implements Initializable {

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
    private JFXTextField txtNomeCondominio;
    @FXML
    private JFXButton btnCadastraPessoaCondominio;
    @FXML
    private JFXComboBox<DestinacaoVO> comboDestinacao;
    @FXML
    private JFXButton btnCadastraDestinacao;
    @FXML
    private JFXComboBox<TipoCondominioVO> comboTipoCondominio;
    @FXML
    private JFXButton btnCadastraTipoCondominio;
    @FXML
    private JFXTextField txtCNPJ;
    @FXML
    private JFXTextField txtInscricaoEstadual;
    @FXML
    private JFXTextField txtCEP;
    @FXML
    private JFXButton btnConsultaCEP;
    @FXML
    private JFXTextField txtLogradouro;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXComboBox<String> comboUF;
    @FXML
    private JFXTextField txtEstado;
    @FXML
    private JFXTextField txtCOmplemento;
    @FXML
    private TableView<CondominiosVO> gradeCondominio;
    @FXML
    private TableColumn<CondominiosVO, Integer> colCodigoPessoaCondominio;
    @FXML
    private TableColumn<CondominiosVO, String> colNomeCondominio;
    @FXML
    private TableColumn<CondominiosVO, String> colCNPJ;

    public int recebeCodigoPessoaCondominio;
    public int codigoCondominioGrade; // recebe o codigo da pessoa ao clicar na grade.

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboDestinacao();
        carregaComboTipoCondominio();
        carregaComboUF();
        renderizaGrade();
        getGrade();
        controlMenuButton(ControlButtonsEnum.ABERTURA);
    }

    private void renderizaGrade() {

        colCodigoPessoaCondominio.setCellValueFactory(new PropertyValueFactory<>("codigoPessoaCondominio"));
        colNomeCondominio.setCellValueFactory(new PropertyValueFactory<>("nomePessoaCondominio"));
        colCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));

    }

    private void getGrade() {

        try {

            CondominioDAO dao = new CondominioDAO();

            List<CondominiosVO> list = dao.findAll();

            gradeCondominio.getItems().clear();
            gradeCondominio.getItems().addAll(list);

        } catch (DAOException e) {
            System.err.println("Erro\n" + e);
            Funcoes.messageAlert("Erro ao carregar grade.\n" + e, Alert.AlertType.ERROR);
        }

    }

    private void getFieldCondominio() {

        try {
            CondominioDAO dao = new CondominioDAO();

            codigoCondominioGrade = gradeCondominio.getSelectionModel().getSelectedItem().getCodigoPessoaCondominio();

            List<CondominiosVO> lista = dao.findCondominio(codigoCondominioGrade);

            lista.stream().forEach((lista1) -> {
                txtNomeCondominio.setText(lista1.getNomePessoaCondominio());
                findDestinacao(lista1.getCodigoDestinacao());
                findTipoCondominio(lista1.getCodigoTipoCondominio());
                txtCNPJ.setText(lista1.getCnpj());
                txtInscricaoEstadual.setText(lista1.getInscricaoMunicipal());
                txtCEP.setText(lista1.getCep());
                txtLogradouro.setText(lista1.getLogradouro());
                txtNumero.setText(lista1.getNumero());
                txtBairro.setText(lista1.getBairro());
                txtCidade.setText(lista1.getCidade());
                comboUF.getSelectionModel().select(lista1.getEstadoSigla());
                txtCOmplemento.setText(lista1.getComplemento());
            });

        } catch (DAOException e) {
            System.err.println("Erro\n" + e.getMessage());
            Funcoes.messageAlert("Erro ao carregar dados do condomínio", Alert.AlertType.ERROR);
        }

    }

    private void carregaComboDestinacao() {

        try {
            DestinacaoDAO dao = new DestinacaoDAO();
            List<DestinacaoVO> list = dao.findAll();

            comboDestinacao.getItems().clear();
            comboDestinacao.getItems().addAll(list);
        } catch (DAOException e) {
        }
    }

    private void findDestinacao(int codigoDestinacao) {

        try {
            DestinacaoDAO dao = new DestinacaoDAO();
            List<DestinacaoVO> list = dao.findDestin(codigoDestinacao);

            list.stream().forEach((lista1) -> {

                comboDestinacao.getSelectionModel().select(0);
                comboDestinacao.getSelectionModel().select(lista1);
            });
        } catch (DAOException e) {
        }
    }

    private void carregaComboTipoCondominio() {

        try {
            TiposCondominiosDAO dao = new TiposCondominiosDAO();
            List<TipoCondominioVO> list = dao.findAll();

            comboTipoCondominio.getItems().clear();
            comboTipoCondominio.getItems().addAll(list);

        } catch (DAOException e) {
        }
    }

    private void findTipoCondominio(int codigoTipoCondominio) {

        try {

            TiposCondominiosDAO dao = new TiposCondominiosDAO();

            List<TipoCondominioVO> lista = dao.findTipoCondominio(codigoTipoCondominio);

            lista.stream().forEach((lista1) -> {

                comboTipoCondominio.getSelectionModel().select(0);
                comboTipoCondominio.getSelectionModel().select(lista1);
            });

        } catch (DAOException e) {
            System.err.println("Erro\n" + e);
            Funcoes.messageAlert("Erro ao localizar registro.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        
        limparCampos();
        
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/PessoasFX.fxml"));

        Parent parent = loader.load();

        /*Este código é usado para receber dados de outro controller
         Ou seja receber dados da segunda janela para a primeira*/
        PessoasFXController controller = loader.getController();
        controller.pessoaJuridica();
//------------------------------------------------------------------     

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        recebeCodigoPessoaCondominio = controller.codigo_pessoa;
        txtNomeCondominio.setText(controller.nomePessoa);//aqui resgata os dados do segundo controller.
        
        /*verifica se foi selecionado uma pessoa e ativa ou não o botão salvar*/
        if(recebeCodigoPessoaCondominio > 0){
            controlMenuButton(ControlButtonsEnum.NOVO);
        }
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if (txtNomeCondominio.getText().isEmpty()) {
            Funcoes.messageAlert("O campo nome condominio é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (comboDestinacao.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha uma destinação", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoCondominio.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de condomínio.", Alert.AlertType.WARNING);
            return;
        }
        if (txtCNPJ.getText().isEmpty()) {
            Funcoes.messageAlert("O campo CNPJ é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (txtInscricaoEstadual.getText().isEmpty()) {
            Funcoes.messageAlert("O campo inscrição municipal é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (txtCEP.getText().isEmpty()) {
            Funcoes.messageAlert("O CEP é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (txtLogradouro.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um logradouro", Alert.AlertType.WARNING);
            return;
        }
        if (txtNumero.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um número", Alert.AlertType.WARNING);
            return;
        }
        if (txtBairro.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um bairro", Alert.AlertType.WARNING);
            return;
        }
        if (txtCidade.getText().isEmpty()) {
            Funcoes.messageAlert("Informe uma cidade", Alert.AlertType.WARNING);
            return;
        }
        if (comboUF.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um estado", Alert.AlertType.WARNING);
            return;
        }

        try {
            //08461530
            CondominiosVO oCond = new CondominiosVO();
            oCond.setCodigoPessoaCondominio(recebeCodigoPessoaCondominio);
            oCond.setCodigoDestinacao(comboDestinacao.getSelectionModel().getSelectedItem().getCodigoDestinacaoCond());
            oCond.setCodigoTipoCondominio(comboTipoCondominio.getSelectionModel().getSelectedItem().getCodigoTipoCondominio());
            oCond.setCnpj(txtCNPJ.getText().trim());
            oCond.setInscricaoMunicipal(txtInscricaoEstadual.getText().trim());
            oCond.setCep(txtCEP.getText().trim());
            oCond.setLogradouro(txtLogradouro.getText().trim().toUpperCase());
            oCond.setNumero(txtNumero.getText().trim().toUpperCase());
            oCond.setBairro(txtBairro.getText().trim().toUpperCase());
            oCond.setCidade(txtCidade.getText().trim().toUpperCase());
            oCond.setEstadoSigla(comboUF.getSelectionModel().getSelectedItem());
            oCond.setComplemento(txtCOmplemento.getText().trim().toUpperCase());

            CondominioDAO dao = new CondominioDAO();

            dao.save(oCond);
            Funcoes.messageAlert("Cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
            getGrade();
            controlMenuButton(ControlButtonsEnum.SALVAR);

        } catch (DAOException e) {
            Funcoes.messageAlert("Erro ao cadastrar\n"+e, Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if (txtNomeCondominio.getText().isEmpty()) {
            Funcoes.messageAlert("O campo nome condominio é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (comboDestinacao.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha uma destinação", Alert.AlertType.WARNING);
            return;
        }
        if (comboTipoCondominio.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um tipo de condomínio.", Alert.AlertType.WARNING);
            return;
        }
        if (txtCNPJ.getText().isEmpty()) {
            Funcoes.messageAlert("O campo CNPJ é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (txtInscricaoEstadual.getText().isEmpty()) {
            Funcoes.messageAlert("O campo inscrição municipal é obrigatório.", Alert.AlertType.WARNING);
            return;
        }
        if (txtCEP.getText().isEmpty()) {
            Funcoes.messageAlert("O CEP é obrigatório", Alert.AlertType.WARNING);
            return;
        }
        if (txtLogradouro.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um logradouro", Alert.AlertType.WARNING);
            return;
        }
        if (txtNumero.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um número", Alert.AlertType.WARNING);
            return;
        }
        if (txtBairro.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um bairro", Alert.AlertType.WARNING);
            return;
        }
        if (txtCidade.getText().isEmpty()) {
            Funcoes.messageAlert("Informe uma cidade", Alert.AlertType.WARNING);
            return;
        }
        if (comboUF.getSelectionModel().getSelectedIndex() == -1) {
            Funcoes.messageAlert("Escolha um estado", Alert.AlertType.WARNING);
            return;
        }

        try {

            CondominiosVO oCond = new CondominiosVO();

            oCond.setCodigoPessoaCondominio(codigoCondominioGrade);
            oCond.setCodigoDestinacao(comboDestinacao.getSelectionModel().getSelectedItem().getCodigoDestinacaoCond());
            oCond.setCodigoTipoCondominio(comboTipoCondominio.getSelectionModel().getSelectedItem().getCodigoTipoCondominio());
            oCond.setCnpj(txtCNPJ.getText().trim());
            oCond.setInscricaoMunicipal(txtInscricaoEstadual.getText().trim());
            oCond.setCep(txtCEP.getText().trim());
            oCond.setLogradouro(txtLogradouro.getText().trim().toUpperCase());
            oCond.setNumero(txtNumero.getText().trim().toUpperCase());
            oCond.setBairro(txtBairro.getText().trim().toUpperCase());
            oCond.setCidade(txtCidade.getText().trim().toUpperCase());
            oCond.setEstadoSigla(comboUF.getSelectionModel().getSelectedItem());
            oCond.setComplemento(txtCOmplemento.getText().trim().toUpperCase());

            CondominioDAO dao = new CondominioDAO();
            dao.getAlterar(oCond);

            Funcoes.messageAlert("Registro alterado com sucesso.", Alert.AlertType.INFORMATION);

            getGrade();
            controlMenuButton(ControlButtonsEnum.ALTERAR);

        } catch (DAOException e) {
            System.err.println("Erro\n" + e);
            Funcoes.messageAlert("Erro ao alterar registro", Alert.AlertType.ERROR);
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

                CondominiosVO oCond = new CondominiosVO();

                oCond.setCodigoPessoaCondominio(codigoCondominioGrade);

                CondominioDAO dao = new CondominioDAO();
                dao.setDelete(oCond);

                Funcoes.messageAlert("Registro deletado com sucesso!", Alert.AlertType.INFORMATION);

                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            }

        } catch (DAOException e) {
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
        controlMenuButton(ControlButtonsEnum.ABERTURA);
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cadastraPessoaCondominioOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    private void cadastraDestinacaoOnAction(ActionEvent event) throws IOException {

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/DestinacaoView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        carregaComboDestinacao();

    }

    @FXML
    private void cadastraTipoCondominioOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/TipoCondominioView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait(); // usar showAndWait para poder atualizar o combo
        carregaComboTipoCondominio();
    }

    @FXML
    private void consultaCepOnAction(ActionEvent event) {
        if (txtCEP.getText().isEmpty()) {
            Funcoes.messageAlert("O campo CEP é obrigatório para realizar a consulta", Alert.AlertType.WARNING);
            return;
        }
        buscaCep();
    }

    private void buscaCep() {

        ViaCEP viaCEP = new ViaCEP();

        try {
            //08461530
            viaCEP.buscar(txtCEP.getText().trim().replaceAll("\\.", "").replaceAll("-", ""));

            txtLogradouro.setText(viaCEP.getLogradouro());
            txtBairro.setText(viaCEP.getBairro());
            txtCidade.setText(viaCEP.getLocalidade());
            comboUF.getSelectionModel().select(viaCEP.getUf());
            EstadosUfEnum estado = EstadosUfEnum.getByCodigo(viaCEP.getUf());
            txtEstado.setText(estado.getEstado());
            System.out.println("Estado" + estado.getEstado());

        } catch (ViaCEPException e) {
            Funcoes.messageAlert("Erro ao consultar CEP\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void carregaComboUF() {

        for (EstadosUfEnum s : EstadosUfEnum.values()) {
            comboUF.getItems().addAll(s.toString());
        }

    }

    @FXML
    private void cepKeyReleased(KeyEvent event) {

        TextFieldFormatter tff = new TextFieldFormatter();

        tff.setMask("##.###-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCEP);
        tff.formatter();
    }

    @FXML
    private void cnpjKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        //14.218.835/0001-27
        tff.setMask("##.###.###/####-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCNPJ);
        tff.formatter();
    }

    @FXML
    private void inscricaoMunicipalKeyReleades(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###############");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtInscricaoEstadual);
        tff.formatter();
    }

    @FXML
    private void gradeCondominioOnMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldCondominio();
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
    
    private void limparCampos(){
        txtNomeCondominio.setText("");
        comboDestinacao.getSelectionModel().select(-1);
        comboTipoCondominio.getSelectionModel().select(-1);
        txtCNPJ.setText("");
        txtInscricaoEstadual.setText("");
        txtCEP.setText("");
        txtLogradouro.setText("");
        txtNumero.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        comboUF.getSelectionModel().select(-1);
        txtEstado.setText("");
        txtCOmplemento.setText("");
    }

}
