
package spaceshootingclient;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import simulation.Simulation;

public class SpaceShootingClient extends Application {
    
    
    private Gateway gateway;
    private int playerID = -1;
    
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
        
        physicsdemo.GamePane root = new physicsdemo.GamePane();
        Simulation sim = new Simulation(300, 250, 2, 2);
        root.setShapes(sim.setUpShapes());
        
        Scene scene = new Scene(root, 300, 250);
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    sim.movePlayer(0, 9);
                    break;
                case UP:
                    sim.movePlayer(0, -9);
                    break;
                case LEFT:
                    sim.movePlayer(-9, 0);
                    break;
                case RIGHT:
                    sim.movePlayer(9, 0);
                    break;
                case SPACE:
                    sim.shootMissileUp();
                    break;
            }
        });
        root.requestFocus(); 

        primaryStage.setTitle("Game Physics");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();

        // This is the main animation thread
        new Thread(() -> {
            while (true) {
               
                Platform.runLater(()->sim.updateShapes());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {

                }
            }
        }).start();
    }

    
    public static void main(String[] args) {
        launch(args);
    }    
}
