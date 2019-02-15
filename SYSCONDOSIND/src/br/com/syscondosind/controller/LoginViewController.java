package br.com.syscondosind.controller;

import br.com.syscondosind.dao.UserSystemDAO;
import br.com.syscondosind.enumerics.StatusEnum;
import br.com.syscondosind.funcoes.Funcoes;
import br.com.syscondosind.persistence.DAOException;
import br.com.syscondosind.vo.UserSystemVO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class LoginViewController implements Initializable {

    @FXML
    private TextField txtUserId;
    @FXML
    private PasswordField txtPassowrd;

    private String hashPassword;
    private String User;
    private String nomePessoa;
    private String status;
    @FXML
    private Text btnConfiguracoes;
    @FXML
    private JFXButton btnSair;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginOnAction(ActionEvent event) throws IOException {
        if (txtUserId.getText().isEmpty()) {
            Funcoes.messageAlert("Informe um login para acessar o sistema.", Alert.AlertType.WARNING);
            txtUserId.requestFocus();
            return;
        }
        if (txtPassowrd.getText().isEmpty()) {
            Funcoes.messageAlert("Informe uma senha para acessar o sistema.", Alert.AlertType.WARNING);
            txtPassowrd.requestFocus();
            return;
        }

        carregaDados(txtUserId.getText().trim().toUpperCase());

        try {
            if (User == null) {
                Funcoes.messageAlert("Atenção! \n"
                        + "O usuário ou a senha não conferem, verifique e tente novamente.", Alert.AlertType.WARNING);
            } else if (status.equals(StatusEnum.INATIVO.getStatus())) {
                Funcoes.messageAlert("O usuário está desativado.\n"
                        + "Por favor entre em contato com o administrador do sistema.", Alert.AlertType.INFORMATION);
            } else {
                if (hashPassword.equals(Funcoes.criptografiaSHA(txtPassowrd.getText().trim().toUpperCase()))
                        && User.equals(txtUserId.getText().trim().toUpperCase())) {

                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/DesktopView.fxml"));

                    Parent parent = loader.load();
                    Scene scene = new Scene(parent, Color.TRANSPARENT);

                    primaryStage.setScene(scene);
                    primaryStage.initModality(Modality.APPLICATION_MODAL);
                    primaryStage.initStyle(StageStyle.DECORATED);
                    primaryStage.setResizable(true);
                    primaryStage.setMaximized(true);
                    primaryStage.show();

                    fecharTelaLogin();

                } else {
                    Funcoes.messageAlert("Atenção! \n"
                            + "O usuário ou a senha não conferem, verifique e tente novamente.", Alert.AlertType.WARNING);
                }
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Funcoes.messageAlert("Erro ao tentar acessar o sistema.\n"
                    + "Por favor, entre em contato com o administrador do sistema", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void configuracoesMouseOnClicked(MouseEvent event) {
        Stage stage = (Stage) btnConfiguracoes.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sairDoSistemaMouseOnAclicked(MouseEvent event) {
        System.exit(0);
    }

    private void carregaDados(String UserId) {

        User = null;
        hashPassword = null;
        nomePessoa = null;
        status = null;

        try {
            UserSystemDAO dao = new UserSystemDAO();

            List<UserSystemVO> list = dao.findUserLogin(UserId);

            list.stream().forEach((lista) -> {
                User = lista.getUser();
                hashPassword = lista.getPassword();
                nomePessoa = lista.getNomePessoa();
                status = lista.getStatus();
            });

//            System.out.println("User: " + User);
//            System.out.println("senha: " + hashPassword);
//            System.out.println("Pessoa: " + nomePessoa);
        } catch (DAOException e) {
        }
    }

    private void fecharTelaLogin() {

        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();

    }

}
