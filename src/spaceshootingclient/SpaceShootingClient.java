
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
        
        physicsdemo.GamePane root = new physicsdemo.GamePane();
        Simulation sim = new Simulation(SIMULATION_WIDTH, SIMULATION_HEIGHT, playerID, opponentId);
        root.setShapes(sim.setUpShapes());
        
        Scene scene = new Scene(root, SIMULATION_WIDTH, SIMULATION_HEIGHT);
        
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
//                case DOWN:
//                    Movement moveDown = new Movement(playerID,0,9);
//                    sim.moveCowboy(moveDown);
//                    int xD = sim.getCowboyPosition(playerID).x;
//                    int yD = sim.getCowboyPosition(playerID).y;
//                    gateway.sendCowboyMove(xD, yD);
//                    System.out.println("sendCowboyMove:"+playerID+" "+xD+" "+yD);
//                    break;
//                case UP:
//                    Movement moveUp = new Movement(playerID,0,-9);
//                    sim.moveCowboy(moveUp);
//                    int xU = sim.getCowboyPosition(playerID).x;
//                    int yU = sim.getCowboyPosition(playerID).y;
//                    gateway.sendCowboyMove(xU, yU);
//                    System.out.println("sendCowboyMove:"+playerID+" "+xU+" "+yU);                    
//                    break;
                case LEFT:
                    Movement moveLeft = new Movement(playerID,-9,0);
//                    sim.moveCowboy(moveLeft);
//                    int xL = sim.getCowboyPosition(playerID).x;
//                    int yL = sim.getCowboyPosition(playerID).y;
                                        
                    int xL =  - 9;
                    int yL = 0;
                    
                    gateway.sendCowboyMove(xL, yL);
                    System.out.println("sendCowboyMove:" + playerID + " " + xL + " " + yL);
                    break;
                case RIGHT:
                    Movement moveRight = new Movement(playerID,9,0);
//                    sim.moveCowboy(moveRight);
//                    int xR = sim.getCowboyPosition(playerID).x;
//                    int yR = sim.getCowboyPosition(playerID).y;
                    
                    int xR = 9;
                    int yR = 0;
                    
                    gateway.sendCowboyMove(xR, yR);
                    System.out.println("sendCowboyMove:" + playerID + " " + xR + " " + yR);

                    break;
//                case SPACE:
//                    sim.shootMissileUp();
//                    break;
            }
        });
        root.requestFocus(); 

        primaryStage.setTitle("Game Physics " + playerID);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();

//        // This is the main animation thread
//        new Thread(() -> {
//            while (true) {
//        
//                Platform.runLater(()->sim.updateShapes());
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException ex) {
//
//                }
//            }
//        }).start();
        
        new Thread(new CowboyMovementCheck(gateway, sim)).start();
    }

    
    public static void main(String[] args) {
        launch(args);
    }    
}


class CowboyMovementCheck implements Runnable, interaction.InteractionConstants {
    private Gateway gateway;
    private int N; 
    private Simulation sim;
     
    public CowboyMovementCheck(Gateway gateway, Simulation sim) {
      this.gateway = gateway;
      this.sim = sim;
      this.N = 0;
    }

    //Run a thread
    public void run() {
        while (true) {
            if (gateway.getCowboyMoveCount()> N) {
                Movement newMove = gateway.getCowBoyMove(N);
                sim.moveCowboy(newMove);
                    
                Platform.runLater(()->sim.updateShapes());
                
                N++;
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
  }