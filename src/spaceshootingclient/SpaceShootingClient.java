
package spaceshootingclient;

import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import simulation.Simulation;

public class SpaceShootingClient extends Application implements interaction.InteractionConstants{    
    
    private Gateway gateway;
    private int playerID = -1;
    private int opponentId = -1;
    private int cowboyMoveCount = 0;
    private int missileMoveCount = 0;
    
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
            opponentId = gateway.getOpponentId();
        });
        
        GamePane root = new GamePane();
        Simulation sim = new Simulation(500, 420, playerID, opponentId);
        root.setShapes(sim.setUpShapes());
        
        Scene scene = new Scene(root, 500, 420);
        
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    gateway.sendMove(1, 0, 5);
                    break;
                case UP:
                    gateway.sendMove(1, 0, -5);
                    break;
                case LEFT:                     
                    gateway.sendMove(1, -5, 0);
                    break;
                case RIGHT:                   
                    gateway.sendMove(1, 5, 0);
                    break;
                case SPACE:
                    int x = sim.getCowboyPosition(playerID).x;
                    int y = sim.getCowboyPosition(playerID).y;
                    gateway.sendMove(2, x, y);
                    System.out.println("sendMove: " + 2 + " " + x + " " + y);
                    break;
            }
        });
        root.requestFocus(); 

        primaryStage.setTitle("Cowboy Wars " + playerID);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();

        // Main animation thread
        new Thread(new CowboyMovementCheck(gateway, sim, root)).start();
    }

    
    public static void main(String[] args) {
        launch(args);
    }    
}


class CowboyMovementCheck implements Runnable, interaction.InteractionConstants {
    private Gateway gateway;    
    private Simulation sim;
    private GamePane root;
    private int N; 
     
    public CowboyMovementCheck(Gateway gateway, Simulation sim, GamePane root) {
      this.gateway = gateway;
      this.sim = sim;
      this.root = root;
      this.N = 0;
    }

    //Run a thread
    public void run() {
        while (sim.getWhoWon() == 0) {
            sim.evolve(1.0);
            Platform.runLater(() -> {
                sim.updateShapes();
                root.setShapes(sim.setUpShapes());
            }
            );
            
            if (gateway.getMoveCount()> N) {
                Movement newMove = gateway.getMove(N);
                
                if(newMove.type == 1)
                    sim.moveCowboy(newMove);
                else
                    sim.shootMissile(newMove);
                                                                    
                Platform.runLater( ()->{
                    sim.updateShapes();
                    root.setShapes(sim.setUpShapes());}
                );
                
                N++;
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        }
        if(sim.getWhoWon() == 1){
            JOptionPane.showMessageDialog(null, "GAME OVER. YOU WON!");            
            System.err.println("YOU WON!");
            System.exit(0);
        } else{
            JOptionPane.showMessageDialog(null, "GAME OVER. YOU LOSE!");
            System.err.println("YOU LOSE!");
            System.exit(0);
        }
    }
  }