package br.com.syscondosind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Luciano Carrafa Benfica
 */
public class Start extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/DesktopView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/LoginView.fxml"));
        Parent parent = loader.load();
//        Scene scene = new Scene(parent, Color.TRANSPARENT);
        Scene scene = new Scene(parent, Color.BLUE);

//        primaryStage.setOpacity(1.0);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(true);
        primaryStage.setMaximized(false);
        primaryStage.show();

    }

}
