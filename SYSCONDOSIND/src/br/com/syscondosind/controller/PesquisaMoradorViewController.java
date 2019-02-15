package br.com.syscondosind.controller;

import br.com.syscondosind.dao.MoradoresDAO;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.MoradoresVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class PesquisaMoradorViewController implements Initializable {

    @FXML
    private JFXButton btnCadastraMorador;
    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXTextField txtPesquisaMorador;
    @FXML
    private TableView<MoradoresVO> gradePesquisaMorador;
    @FXML
    private TableColumn<MoradoresVO, Integer> colId;
    @FXML
    private TableColumn<MoradoresVO, String> colNomeMorador;
    @FXML
    private TableColumn<MoradoresVO, Integer> colIdPessoa;

    private Integer codigoMorador;
    public Integer codigoPessoa;
    public String nomePessoaMorador;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renderizaGrade();
        getGrade();

    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigoMorador"));
        colIdPessoa.setCellValueFactory(new PropertyValueFactory<>("codigoPessoa"));
        colNomeMorador.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
    }

    private void getGrade() {
        try {
            String nomePessoa;
            if(txtPesquisaMorador.getText().isEmpty()){
                nomePessoa = null;
            }else{
                nomePessoa = txtPesquisaMorador.getText().trim().toUpperCase();
            }
            
            
            MoradoresDAO dao = new MoradoresDAO();

            List<MoradoresVO> list = dao.findAll(nomePessoa);

            gradePesquisaMorador.getItems().clear();
            gradePesquisaMorador.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao carregar grade moradores.\n" + e, Alert.AlertType.ERROR);
        }
    }

    private void getFieldPesquisa() {

        codigoMorador = gradePesquisaMorador.getSelectionModel().getSelectedItem().getCodigoMorador();
        

        if (codigoMorador > 0) {
            try {
                MoradoresDAO dao = new MoradoresDAO();

                List<MoradoresVO> lista = dao.findMorador(codigoMorador);

                lista.stream().forEach((lista1) -> {
                    txtPesquisaMorador.setText(lista1.getNomePessoa());
                    codigoPessoa = lista1.getCodigoPessoa();
                    nomePessoaMorador = lista1.getNomePessoa();
                });
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void cadastraMoradorOnAction(ActionEvent event) {
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pesquisaMoradorOnKeyReleased(KeyEvent event) {
        getGrade();
    }

    @FXML
    private void gradePesquisaMoradorOnMouseClicked(MouseEvent event) {
        getFieldPesquisa();
    }

}
