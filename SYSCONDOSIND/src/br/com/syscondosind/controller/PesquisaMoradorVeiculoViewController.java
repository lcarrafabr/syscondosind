package br.com.syscondosind.controller;

import br.com.syscondosind.dao.PesquisaMoradorVeiculoDAO;
import br.com.syscondosind.enumerics.ControlButtonsEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.vo.PesquisaMoradorVeiculoVO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class PesquisaMoradorVeiculoViewController implements Initializable {

    @FXML
    private JFXButton btnSair;
    @FXML
    private JFXTextField txtNomeMorador;
    @FXML
    private TableView<PesquisaMoradorVeiculoVO> gradePesquisaMorador;
    @FXML
    private TableColumn<PesquisaMoradorVeiculoVO, Integer> colId;
    @FXML
    private TableColumn<PesquisaMoradorVeiculoVO, String> colNome;
    @FXML
    private TableColumn<PesquisaMoradorVeiculoVO, String> colStatus;
    @FXML
    private TableColumn<PesquisaMoradorVeiculoVO, String> colTorreBloco;
    @FXML
    private TableColumn<PesquisaMoradorVeiculoVO, String> colAptoSetor;

    public int codigoGradePessoa;
    public String nomePessoa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renderizaGrade();
        getgrade();
        txtNomeMorador.requestFocus();
    }

    private void renderizaGrade() {

        colId.setCellValueFactory(new PropertyValueFactory<>("codigo_pessoa"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomePessoa"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTorreBloco.setCellValueFactory(new PropertyValueFactory<>("nomeTorre"));
        colAptoSetor.setCellValueFactory(new PropertyValueFactory<>("aptoSetor"));
    }

    private void getgrade() {

        String pesquisaNome = txtNomeMorador.getText().trim().toUpperCase();
        if (pesquisaNome.isEmpty()) {
            pesquisaNome = null;
        }

        try {
            PesquisaMoradorVeiculoDAO dao = new PesquisaMoradorVeiculoDAO();

            List<PesquisaMoradorVeiculoVO> list = dao.findAll(pesquisaNome);

            gradePesquisaMorador.getItems().clear();
            gradePesquisaMorador.getItems().addAll(list);
        } catch (Exception e) {
            Funcoes.messageAlert("Erro ao atualizar Grade\n" + e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void sairOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void nomeMoradorKeyReleased(KeyEvent event) {
        getgrade();
    }

    @FXML
    private void gradePesquisaMoradorOnClicked(MouseEvent event) throws IOException {
        codigoGradePessoa = gradePesquisaMorador.getSelectionModel().getSelectedItem().getCodigo_pessoa();

        if (codigoGradePessoa > 0) {
            try {
                PesquisaMoradorVeiculoDAO dao = new PesquisaMoradorVeiculoDAO();

                List<PesquisaMoradorVeiculoVO> lista = dao.findAllPessoa(codigoGradePessoa);

                lista.stream().forEach((lista1) -> {
                    txtNomeMorador.setText(lista1.getNomePessoa());
                    nomePessoa = lista1.getNomePessoa();
                });
            } catch (Exception e) {
                Funcoes.messageAlert("Erro ao retornar dados no clique da grade\n" + e, Alert.AlertType.ERROR);
            }
        }

    }

    @FXML
    private void AtualizaMoradoresOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/MoradoresView.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        getgrade();
    }

}
