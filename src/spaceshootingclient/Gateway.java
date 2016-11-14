
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
    
    public int getOpponentId(){
        outputToServer.println(GET_OPPONENT_ID);
        outputToServer.flush();
        int opponentId = -1;
        try{
            opponentId = Integer.parseInt(inputFromServer.readLine());
        } catch(IOException ex){
            System.err.println("Error in getOpponentId");
            ex.printStackTrace();            
        }
        return opponentId;
    }
    
    
    
    
    public void sendCowboyMove(Movement move){
        outputToServer.println(SEND_COWBOY_MOVE);        
        outputToServer.println(move);
        outputToServer.flush();
    }
    
    public int getCowboyMoveCount(){
        outputToServer.println(GET_COWBOY_MOVE_COUNT);
        outputToServer.flush();
        int count = 0;
        try{
            count = Integer.parseInt(inputFromServer.readLine());
        } catch(IOException ex){
            System.err.println("Error in getCowboyMoveCount");
            ex.printStackTrace();            
        }
        return count;
    }
    
    public Movement getCowBoyMove(int n){
        outputToServer.println(GET_COWBOY_MOVE);
        outputToServer.flush();
        int playerId = -1;
        int x = -1;
        int y = -1;
        
        try{
            playerId = Integer.parseInt(inputFromServer.readLine());
            x = Integer.parseInt(inputFromServer.readLine());
            y = Integer.parseInt(inputFromServer.readLine());            
        } catch(IOException ex){
            System.err.println("Error in geCowboyMove!");
            ex.printStackTrace();
        }
        
        Movement move = new Movement(playerId, x, y);
        return move;
    }
    
    public void sendMissileMove(Movement move){        
        outputToServer.println(SEND_MISSILE_MOVE);        
        outputToServer.println(move);
        outputToServer.flush();
    }
    
    public int getMissileMoveCount(){        
        outputToServer.println(GET_MISSILE_MOVE_COUNT);
        outputToServer.flush();
        int count = 0;
        try{
            count = Integer.parseInt(inputFromServer.readLine());
        } catch(IOException ex){
            System.err.println("Error in getCowboyMoveCount");
            ex.printStackTrace();            
        }
        return count;
    }
    
    public Movement getMissileMove(int n){       
        outputToServer.println(GET_MISSILE_MOVE);
        outputToServer.flush();
        int playerId = -1;
        int x = -1;
        int y = -1;
        
        try{
            playerId = Integer.parseInt(inputFromServer.readLine());
            x = Integer.parseInt(inputFromServer.readLine());
            y = Integer.parseInt(inputFromServer.readLine());            
        } catch(IOException ex){
            System.err.println("Error in geCowboyMove!");
            ex.printStackTrace();
        }
        
        Movement move = new Movement(playerId, x, y);
        return move;
    }
}
