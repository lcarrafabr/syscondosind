package br.com.syscondosind.controller;

import br.com.syscondosind.dao.SetoresGaragemDAO;
import br.com.syscondosind.dao.VagasGaragemDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusSetorGaragemEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.SetoresGaragemVO;
import br.com.syscondosind.vo.VagasGaragemVO;
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
public class VagasGaragemViewController implements Initializable {
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
    private JFXTextField txtNomeVaga;
    @FXML
    private JFXComboBox<SetoresGaragemVO> comboSetor;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXComboBox<String> comboStatus;
    @FXML
    private TextArea txtAreaObservacao;
    @FXML
    private TableView<VagasGaragemVO> gradeVagas;
    @FXML
    private TableColumn<VagasGaragemVO, Integer> colId;
    @FXML
    private TableColumn<VagasGaragemVO, String> colNomeVaga;
    @FXML
    private TableColumn<VagasGaragemVO, String> colSetor;
    @FXML
    private TableColumn<VagasGaragemVO, String> colCodigo;
    @FXML
    private TableColumn<VagasGaragemVO, String> colStatus;
    @FXML
    private Label lblLiberado;
    @FXML
    private Label lblManutencao;
    @FXML
    private Label lblInterditado;
    @FXML
    private Label lblTotalVagas;
    @FXML
    private TableView<SetoresGaragemVO> gradeSetor;
    @FXML
    private TableColumn<SetoresGaragemVO, String> colSetorNome;
    @FXML
    private TableColumn<SetoresGaragemVO, Integer> colQTDVagas;
    
    private Integer codigoGradeVaga;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboStatus();
        carregaComboSetores();
        renderizaGradeVagasSetor();
        getGradeQTDVagasSetor();
        renderizaGradevagas();
        getGradeVaga();
        getResultadosPorStatus();
    }    
    
    
    private void renderizaGradevagas(){
        
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoVagaGaragem"));
        colNomeVaga.setCellValueFactory(new PropertyValueFactory<>("nomeVaga"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void getGradeVaga(){
        try {
            VagasGaragemDAO dao = new VagasGaragemDAO();
            
            List<VagasGaragemVO> lista = dao.findAll();
            
            gradeVagas.getItems().clear();
            gradeVagas.getItems().addAll(lista);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar a grade vagas.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void getFieldVaga(){
        
        codigoGradeVaga = gradeVagas.getSelectionModel().getSelectedItem().getCodigoVagaGaragem();
        
        if(codigoGradeVaga > 0){
            try {
                VagasGaragemDAO dao = new VagasGaragemDAO();
                
                List<VagasGaragemVO> list = dao.findVaga(codigoGradeVaga);
                
                list.stream().forEach((lista)-> {
                    txtNomeVaga.setText(lista.getNomeVaga());
                    txtCodigo.setText(lista.getCodigo());
                    comboStatus.getSelectionModel().select(lista.getStatus());
                    txtAreaObservacao.setText(lista.getObservacao());
                    getFieldComboSetor(lista.getCodigoSetor());
                });
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao retornar os resultados no clique da grade.\n"+e, Alert.AlertType.ERROR);
            }
        }
    }
    
    private void getFieldComboSetor(int codigoSetor){
        
        try {
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            List<SetoresGaragemVO> list = dao.findAllSetor(codigoSetor);
            
            list.stream().forEach((lista)->{
                comboSetor.getSelectionModel().select(0);
                comboSetor.getSelectionModel().select(lista);
            });
        } catch (Exception e) {
            Funcoes.messageAlert("Erro selecionar o item do combo Setor.\n"+e, Alert.AlertType.ERROR);
        }
        
    }
    
    private void renderizaGradeVagasSetor(){
        
        colSetorNome.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        colQTDVagas.setCellValueFactory(new PropertyValueFactory<>("qtdVagas"));
    }
    
    private void getGradeQTDVagasSetor(){
        try {
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            List<SetoresGaragemVO> list = dao.findQTDVagasSetor();
            
            gradeSetor.getItems().clear();
            gradeSetor.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar grade vagas por setor.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void getResultadosPorStatus(){
        try {
            VagasGaragemDAO dao = new VagasGaragemDAO();
            
            List<VagasGaragemVO> list = dao.findResultadosPorStatus();
            
            list.stream().forEach((lista)-> {
                lblTotalVagas.setText(Integer.toString(lista.getQtdTotal()));
                lblLiberado.setText(Integer.toString(lista.getQtdLiberado()));
                lblManutencao.setText(Integer.toString(lista.getQtdManutencao()));
                lblInterditado.setText(Integer.toString(lista.getQtdInterditado()));
            });
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao trazer os resultados por Status\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGradeQTDVagasSetor();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.NOVO);
        limpaCampos();
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if(txtNomeVaga.getText().isEmpty()){
            Funcoes.messageAlert("O campo Identificação da vaga é obrigatório.", Alert.AlertType.WARNING);
            txtNomeVaga.requestFocus();
            return;
        }
        if(comboSetor.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe o setor onde se encontra a vaga.", Alert.AlertType.WARNING);
            comboSetor.requestFocus();
            return;
        }
        if(comboStatus.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe o status da vaga.", Alert.AlertType.WARNING);
            comboStatus.requestFocus();
            return;
        }
        try {
            
            VagasGaragemVO oVaga = new VagasGaragemVO();
            
            oVaga.setNomeVaga(txtNomeVaga.getText().trim().toUpperCase());
            oVaga.setCodigoSetor(comboSetor.getSelectionModel().getSelectedItem().getCodigoSetorGaragem());
            oVaga.setCodigo(txtCodigo.getText().trim().toUpperCase());
            oVaga.setStatus(comboStatus.getSelectionModel().getSelectedItem());
            oVaga.setObservacao(txtAreaObservacao.getText().trim());
            
            VagasGaragemDAO dao = new VagasGaragemDAO();
            
            dao.save(oVaga);
            
            getGradeVaga();
            getGradeQTDVagasSetor();
            getResultadosPorStatus();
            
            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.SALVAR);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if(txtNomeVaga.getText().isEmpty()){
            Funcoes.messageAlert("O campo Identificação da vaga é obrigatório.", Alert.AlertType.WARNING);
            txtNomeVaga.requestFocus();
            return;
        }
        if(comboSetor.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe o setor onde se encontra a vaga.", Alert.AlertType.WARNING);
            comboSetor.requestFocus();
            return;
        }
        if(comboStatus.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe o status da vaga.", Alert.AlertType.WARNING);
            comboStatus.requestFocus();
            return;
        }
        try {
            
            VagasGaragemVO oVaga = new VagasGaragemVO();
            
            oVaga.setCodigoVagaGaragem(codigoGradeVaga);
            oVaga.setNomeVaga(txtNomeVaga.getText().trim().toUpperCase());
            oVaga.setCodigoSetor(comboSetor.getSelectionModel().getSelectedItem().getCodigoSetorGaragem());
            oVaga.setCodigo(txtCodigo.getText().trim().toUpperCase());
            oVaga.setStatus(comboStatus.getSelectionModel().getSelectedItem());
            oVaga.setObservacao(txtAreaObservacao.getText().trim());
            
            VagasGaragemDAO dao = new VagasGaragemDAO();
            
            dao.update(oVaga);
            
            getGradeVaga();
            getGradeQTDVagasSetor();
            getResultadosPorStatus();
            
            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.ALTERAR);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar.\n"+e, Alert.AlertType.ERROR);
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
                VagasGaragemDAO dao = new VagasGaragemDAO();
                
                VagasGaragemVO oVaga = new VagasGaragemVO();
                
                oVaga.setCodigoVagaGaragem(codigoGradeVaga);
                
                dao.delete(oVaga);
                
                getGradeVaga();
                getGradeQTDVagasSetor();
                getResultadosPorStatus();
                
                Funcoes.messageAlert("Registro deletado com sucesso.", Alert.AlertType.INFORMATION);
                
                controlMenuButton(ControlButtonsEnum.EXCLUIR);
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao deletar registro.\n"+e, Alert.AlertType.ERROR);
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
        Stage stage = (Stage)btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cadastraSetorOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/SetoresGaragemView.fxml"));

        Parent parent = loader.load();
        Scene scene = new Scene(parent, Color.TRANSPARENT);
        
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
        
        carregaComboSetores();
        getGradeQTDVagasSetor();
    }

    @FXML
    private void gradeVagasMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getFieldVaga();
    }
    
    private void carregaComboStatus(){
        for (StatusSetorGaragemEnum s : StatusSetorGaragemEnum.values()) {
            comboStatus.getItems().addAll(s.getStatusSetorGaragem());            
        }
    }
    
    private void carregaComboSetores(){
        try {
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            List<SetoresGaragemVO> list = dao.findAll();
            
            comboSetor.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar combo Setor.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void limpaCampos(){
        txtNomeVaga.setText("");
        comboSetor.getSelectionModel().select(-1);
        txtCodigo.setText("");
        comboStatus.getSelectionModel().select(-1);
        txtAreaObservacao.setText("");
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
