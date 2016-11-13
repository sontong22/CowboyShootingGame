/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshootingclient;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author macbookair
 */
public class SpaceShootingClient extends Application {
    
    private Gateway gateway;
    private int playerID = 0;
    
    @Override
    public void start(Stage primaryStage) {
        
        // Setup gateway:
        gateway = new Gateway();
        
        // Put up a dialog to get a handle from the user:
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Game");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter your name:");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            playerID = gateway.sendName(name);
            gateway.getStartGame(); 
        });
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Space Shooter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
