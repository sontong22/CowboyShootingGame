
package spaceshootingclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Gateway implements interaction.InteractionConstants {
    
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;

    // Establish the connection to the server.
    public Gateway() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {           
            System.err.println("Error in gatewayConstructor!");
            ex.printStackTrace();
        }
    }

    // Start the chat by sending in the user's handle.
    public int sendName(String name) {
        outputToServer.println(SEND_NAME);
        outputToServer.println(name);
        outputToServer.flush();
        
        int playerID = -1;
        try {
            playerID = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            System.err.println("Error in sendName!");
            ex.printStackTrace();
        }
        System.out.println("playerId: "+playerID);
        
        return playerID;
    }

    // A loop that ends after the server has found an opponent for the player
    public void getStartGame() {

        boolean gameStarted = false;

        while (gameStarted == false) {
            outputToServer.println(GET_START_GAME);
            outputToServer.flush();

            try {
                gameStarted = Boolean.parseBoolean(inputFromServer.readLine());
                System.out.println("Game State: " + gameStarted);
            } catch (IOException ex) {
                System.err.println("Error in getStartGame: IOException!");
                ex.printStackTrace();
            }
            System.out.println("Waiting for opponent...");
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.err.println("Error in getStartGame: InterruptedException!");
                ex.printStackTrace();
            }
        }
        
        System.out.println("Start Game");
    }

}
