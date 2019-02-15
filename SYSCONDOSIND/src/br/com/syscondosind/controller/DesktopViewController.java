package br.com.syscondosind.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luciano Carrafa Benfica
 */
public class DesktopViewController implements Initializable {

    @FXML
    private AnchorPane desktopPane;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void cadastrosOnAction(ActionEvent event) throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/view/DesktopCadastrosView.fxml"));
            desktopPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DesktopViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void garavemVeiculoOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/view/DescktopGaragemVeiculoView.fxml"));
            desktopPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DesktopViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void financeiroOnAction(ActionEvent event) {
    }

    @FXML
    private void forcenedorPedidoOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/view/DesktopControlePedidosView.fxml"));
            desktopPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DesktopViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
