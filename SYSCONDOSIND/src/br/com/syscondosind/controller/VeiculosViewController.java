package br.com.syscondosind.controller;

import br.com.syscondosind.dao.VeiculosDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.funcoes.TextFieldFormatter;
import br.com.syscondosind.vo.VeiculosVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
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
public class VeiculosViewController implements Initializable {
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
    private JFXTextField txtMarca;
    @FXML
    private JFXTextField txtModelo;
    @FXML
    private JFXTextField txtPlaca;
    @FXML
    private JFXColorPicker colorCor;
    @FXML
    private JFXTextField txtAno;
    @FXML
    private TableView<VeiculosVO> gradeVeiculos;
    @FXML
    private TableColumn<VeiculosVO, Integer> colId;
    @FXML
    private TableColumn<VeiculosVO, String> colMorador;
    @FXML
    private TableColumn<VeiculosVO, String> colMarca;
    @FXML
    private TableColumn<VeiculosVO, String> colModelo;
    @FXML
    private TableColumn<VeiculosVO, String> colPlaca;
    @FXML
    private TableColumn<VeiculosVO, String> colAno;
    
    private int recebeCodigoPessoa;
    private int codigoGradeVeiculo;
    private int codigoPessoaUpdate;// usado para pegar o codigo da pessoa ao clicar na grade e usar no metodo update

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        renderizaGrade();
        getGrade();
    }    
    
    private void renderizaGrade(){
        
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoVeiculo"));
        colMorador.setCellValueFactory(new PropertyValueFactory<>("nomeMorador"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("Ano"));
    }
    
    private void getGrade(){
        try {
            VeiculosDAO dao = new VeiculosDAO();
            
            List<VeiculosVO> list = dao.findAll();
            
            gradeVeiculos.getItems().clear();
            gradeVeiculos.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar a grade.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void getFieldVeiculo(){
        
        codigoGradeVeiculo = gradeVeiculos.getSelectionModel().getSelectedItem().getCodigoVeiculo();
        
        if(codigoGradeVeiculo > 0){
            try {
                
                VeiculosDAO dao = new VeiculosDAO();
                
                List<VeiculosVO> lista = dao.findAllVeiculo(codigoGradeVeiculo);
                
                lista.stream().forEach((lista1)->{
                    txtMorador.setText(lista1.getNomeMorador());
                    txtMarca.setText(lista1.getMarca());
                    txtModelo.setText(lista1.getModelo());
                    txtPlaca.setText(lista1.getPlaca());
                    txtAno.setText(lista1.getAno());
                    colorCor.setValue(Color.web(lista1.getCor()));
                    codigoPessoaUpdate = lista1.getCodigoPessoa();
                });
                
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao retornar registros no clique da grade.\n"+e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) throws IOException {
        limpaCampos();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/pesquisaMoradorVeiculoView.fxml"));

        Parent parent = loader.load();

        /*Este código é usado para receber dados de outro controller
         Ou seja receber dados da segunda janela para a primeira*/
        PesquisaMoradorVeiculoViewController controller = loader.getController();
//------------------------------------------------------------------     

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        recebeCodigoPessoa = controller.codigoGradePessoa;
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
    private void salvarOnAction(ActionEvent event) {
        if(txtMorador.getText().isEmpty()){
            Funcoes.messageAlert("Selecione um morador clicando no botão novo.\n"
                    + "Observação: O morador deve estar cadastrado e ter veículo.", Alert.AlertType.WARNING);
            return;
        }
        if(txtMarca.getText().isEmpty()){
            Funcoes.messageAlert("Informe a marca do veículo.", Alert.AlertType.WARNING);
            txtMarca.requestFocus();
            return;
        }
        if(txtModelo.getText().isEmpty()){
            Funcoes.messageAlert("Informe o modelo do veículo.", Alert.AlertType.WARNING);
            txtModelo.requestFocus();
            return;
        }
        if(txtPlaca.getText().isEmpty()){
            Funcoes.messageAlert("Informe a placa do veículo.", Alert.AlertType.WARNING);
            txtPlaca.requestFocus();
            return;
        }
        if(txtAno.getText().isEmpty()){
            Funcoes.messageAlert("Informe o ano do veículo.", Alert.AlertType.WARNING);
        }
        
        try {
            
            
            String marca = txtMarca.getText().trim().toUpperCase();
            String modelo = txtModelo.getText().trim().toUpperCase();
            String placa = txtPlaca.getText().trim().toUpperCase();
            String ano = txtAno.getText().trim();
            String cor = colorCor.getValue().toString();
            
            VeiculosDAO dao = new VeiculosDAO();
            VeiculosVO oVeiculo = new VeiculosVO(0, recebeCodigoPessoa, marca, modelo, placa, ano, cor);
            
            dao.save(oVeiculo);
            getGrade()
                    ;
            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            controlMenuButton(ControlButtonsEnum.SALVAR);
            
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if(txtMorador.getText().isEmpty()){
            Funcoes.messageAlert("Selecione um morador clicando no botão novo.\n"
                    + "Observação: O morador deve estar cadastrado e ter veículo.", Alert.AlertType.WARNING);
            return;
        }
        if(txtMarca.getText().isEmpty()){
            Funcoes.messageAlert("Informe a marca do veículo.", Alert.AlertType.WARNING);
            txtMarca.requestFocus();
            return;
        }
        if(txtModelo.getText().isEmpty()){
            Funcoes.messageAlert("Informe o modelo do veículo.", Alert.AlertType.WARNING);
            txtModelo.requestFocus();
            return;
        }
        if(txtPlaca.getText().isEmpty()){
            Funcoes.messageAlert("Informe a placa do veículo.", Alert.AlertType.WARNING);
            txtPlaca.requestFocus();
            return;
        }
        if(txtAno.getText().isEmpty()){
            Funcoes.messageAlert("Informe o ano do veículo.", Alert.AlertType.WARNING);
        }
        
        try {
            
            
            String marca = txtMarca.getText().trim().toUpperCase();
            String modelo = txtModelo.getText().trim().toUpperCase();
            String placa = txtPlaca.getText().trim().toUpperCase();
            String ano = txtAno.getText().trim();
            String cor = colorCor.getValue().toString();
            
            VeiculosDAO dao = new VeiculosDAO();
            VeiculosVO oVeiculo = new VeiculosVO(codigoGradeVeiculo, codigoPessoaUpdate, marca, modelo, placa, ano, cor);
            
            dao.update(oVeiculo);
            getGrade();
            
            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            controlMenuButton(ControlButtonsEnum.SALVAR);
            
        } catch (Exception e) {
            e.printStackTrace();
            Funcoes.messageAlert("Erro ao atualizar registro\n"+e, Alert.AlertType.ERROR);
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
                
                VeiculosDAO dao = new VeiculosDAO();
                
                VeiculosVO oVeiculo = new VeiculosVO(codigoGradeVeiculo, 0, null, null, null, null, null);
                
                dao.delete(oVeiculo);
                
                getGrade();
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao deletar registro\n"+e, Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void cancelarOnAction(ActionEvent event) {
    }

    @FXML
    private void limparOnAction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage)btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void gradeVeiculosMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldVeiculo();
    }
    
    private void limpaCampos(){
        txtMorador.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtPlaca.setText("");
        txtAno.setText("");
        
        recebeCodigoPessoa = 0;
        colorCor.setValue(Color.WHITE);
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
    private void anoKeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtAno);
        tff.formatter();
    }
    
}
