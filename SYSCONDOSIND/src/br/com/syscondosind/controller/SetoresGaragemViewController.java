package br.com.syscondosind.controller;

import br.com.syscondosind.dao.SetoresGaragemDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.enumerics.StatusSetorGaragemEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.SetoresGaragemVO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class SetoresGaragemViewController implements Initializable {
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
    private JFXTextField txtNomeSetor;
    @FXML
    private JFXComboBox<String> comboStatusSetor;
    @FXML
    private JFXTextField txtCodigoSetor;
    @FXML
    private Label lblLiberado;
    @FXML
    private Label lblManutencao;
    @FXML
    private Label lblInterditado;
    @FXML
    private TableView<SetoresGaragemVO> gradeSetores;
    @FXML
    private TableColumn<SetoresGaragemVO, Integer> colId;
    @FXML
    private TableColumn<SetoresGaragemVO, String> colSetor;
    @FXML
    private TableColumn<SetoresGaragemVO, String> colStatus;
    @FXML
    private TableColumn<SetoresGaragemVO, String> colCodigoSetor;
    @FXML
    private Label lblTotalSetores;
    @FXML
    private TextArea txtAreaObservacao;
    
    private int codigoGradeSetor;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlMenuButton(ControlButtonsEnum.ABERTURA);
        carregaComboStatus();
        renderizaGrade();
        getGrade();
        resultadosTela();
    }    
    
    private void renderizaGrade(){
        
        colId.setCellValueFactory(new PropertyValueFactory<>("codigoSetorGaragem"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCodigoSetor.setCellValueFactory(new PropertyValueFactory<>("codigoSetor"));
    }
    
    private void getGrade(){
        
        try {
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            List<SetoresGaragemVO> lista = dao.findAll();
            
            gradeSetores.getItems().clear();
            gradeSetores.getItems().addAll(lista);            
            
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar grade Setores.\n"+e, Alert.AlertType.ERROR);
        }
    }
    
    private void getField(){
        
        codigoGradeSetor = gradeSetores.getSelectionModel().getSelectedItem().getCodigoSetorGaragem();
        
        if(codigoGradeSetor > 0){
            try {
                SetoresGaragemDAO dao = new SetoresGaragemDAO();
                
                List<SetoresGaragemVO> lista = dao.findAllSetor(codigoGradeSetor);
                
                lista.stream().forEach((lista1)-> {
                    txtNomeSetor.setText(lista1.getNomeSetor());
                    comboStatusSetor.getSelectionModel().select(lista1.getStatus());
                    txtCodigoSetor.setText(lista1.getCodigoSetor());
                    txtAreaObservacao.setText(lista1.getObservacao());
                });
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao receber dados do clique na grade.\n"+e, Alert.AlertType.ERROR);
            }
        }
    }
    
    private void resultadosTela(){
        
        try {
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            List<SetoresGaragemVO> list = dao.findAllResultados();
            
            list.stream().forEach((lista)-> {
                lblTotalSetores.setText(Integer.toString(lista.getQtdTotal()));
                lblLiberado.setText(Integer.toString(lista.getQtdLiberado()));
                lblManutencao.setText(Integer.toString(lista.getQtdManutencao()));
                lblInterditado.setText(Integer.toString(lista.getQtdInterditado()));
            });
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao preencher os resultados dos setores.\n"+e, Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void buscarOnAction(ActionEvent event) {
        getGrade();
    }

    @FXML
    private void novoOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.NOVO);
        limpaCampos();
        txtNomeSetor.requestFocus();
    }

    @FXML
    private void editarOnAction(ActionEvent event) {
        controlMenuButton(ControlButtonsEnum.EDITAR);
    }

    @FXML
    private void salvarOnAction(ActionEvent event) {
        if(txtNomeSetor.getText().isEmpty()){
            Funcoes.messageAlert("Informe o nome do setor.", Alert.AlertType.WARNING);
            txtNomeSetor.requestFocus();
            return;
        }
        if(comboStatusSetor.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe um Status para o setor.", Alert.AlertType.WARNING);
            comboStatusSetor.requestFocus();
            return;
        }
        try {
            
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            SetoresGaragemVO oSetor = new SetoresGaragemVO();
            
            oSetor.setNomeSetor(txtNomeSetor.getText().trim().toUpperCase());
            oSetor.setStatus(comboStatusSetor.getSelectionModel().getSelectedItem());
            oSetor.setCodigoSetor(txtCodigoSetor.getText().trim().toUpperCase());
            oSetor.setObservacao(txtAreaObservacao.getText().trim());
            
            dao.save(oSetor);
            getGrade();
            resultadosTela();
            
            Funcoes.messageAlert("Registro cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.SALVAR);
            
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao cadastrar\n"+e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void alterarOnAction(ActionEvent event) {
        if(txtNomeSetor.getText().isEmpty()){
            Funcoes.messageAlert("Informe o nome do setor.", Alert.AlertType.WARNING);
            txtNomeSetor.requestFocus();
            return;
        }
        if(comboStatusSetor.getSelectionModel().getSelectedIndex() == -1){
            Funcoes.messageAlert("Informe um Status para o setor.", Alert.AlertType.WARNING);
            comboStatusSetor.requestFocus();
            return;
        }
        try {
            
            SetoresGaragemDAO dao = new SetoresGaragemDAO();
            
            SetoresGaragemVO oSetor = new SetoresGaragemVO();
            
            oSetor.setCodigoSetorGaragem(codigoGradeSetor);
            oSetor.setNomeSetor(txtNomeSetor.getText().trim().toUpperCase());
            oSetor.setStatus(comboStatusSetor.getSelectionModel().getSelectedItem());
            oSetor.setCodigoSetor(txtCodigoSetor.getText().trim().toUpperCase());
            oSetor.setObservacao(txtAreaObservacao.getText().trim());
            
            dao.update(oSetor);
            getGrade();
            resultadosTela();
            
            Funcoes.messageAlert("Registro atualizado com sucesso.", Alert.AlertType.INFORMATION);
            
            controlMenuButton(ControlButtonsEnum.SALVAR);
            
        } catch (Exception e) {
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
                SetoresGaragemDAO dao = new SetoresGaragemDAO();
                SetoresGaragemVO oSetor = new SetoresGaragemVO();
                
                oSetor.setCodigoSetorGaragem(codigoGradeSetor);
                
                dao.delete(oSetor);
                getGrade();
                resultadosTela();
                
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
    private void gradeSetoresMouseClicked(MouseEvent event) {
        btnEditar.setDisable(false);
        getField();
    }
    
    private void limpaCampos(){
        txtNomeSetor.setText("");
        txtCodigoSetor.setText("");
        txtAreaObservacao.setText("");
        comboStatusSetor.getSelectionModel().select(-1);
    }
    
    private void carregaComboStatus(){
        
        try {
            for (StatusSetorGaragemEnum s : StatusSetorGaragemEnum.values()) {
                comboStatusSetor.getItems().addAll(s.getStatusSetorGaragem());
            }
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar o combo Status\n"+e, Alert.AlertType.ERROR);
        }
        
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
