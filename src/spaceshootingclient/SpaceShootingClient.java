
package spaceshootingclient;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import simulation.Simulation;

public class SpaceShootingClient extends Application implements interaction.InteractionConstants{    
    
    private Gateway gateway;
    private int playerID = -1;
    private int opponentId = -1;
    
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
            opponentId = gateway.getOpponentId();
            gateway.getStartGame();
        });
        
        physicsdemo.GamePane root = new physicsdemo.GamePane();
        Simulation sim = new Simulation(SIMULATION_WIDTH, SIMULATION_HEIGHT, playerID, opponentId);
        root.setShapes(sim.setUpShapes());
        
        Scene scene = new Scene(root, SIMULATION_WIDTH, SIMULATION_HEIGHT);
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
//                case DOWN:
//                    sim.moveCowboy(0, 9);
//                    break;
//                case UP:
//                    sim.moveCowboy(0, -9);
//                    break;
//                case LEFT:
//                    sim.moveCowboy(-9, 0);
//                    break;
//                case RIGHT:
//                    sim.moveCowboy(9, 0);
//                    break;
//                case SPACE:
//                    sim.shootMissileUp();
//                    break;
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


//class MovementCheck implements Runnable, interaction.InteractionConstants {
//    private Gateway gateway; // Gateway to the server    
//    private int N; // How many comments we have read
//    
//    /** Construct a thread */
//    public MovementCheck(Gateway gateway,TextArea textArea) {
//      this.gateway = gateway;
//      this.textArea = textArea;
//      this.N = 0;
//    }
//
//    //Run a thread
//    public void run() {
//        while (true) {
//            if (gateway.getCommentCount() > N) {
//                String newComment = gateway.getComment(N);
//                Platform.runLater(() -> textArea.appendText(newComment + "\n"));
//                N++;
//            } else {
//                try {
//                    Thread.sleep(250);
//                } catch (InterruptedException ex) {
//                }
//            }
//        }
//    }
//  }