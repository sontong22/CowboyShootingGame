
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

        String gameStarted = "false";

        while (gameStarted.equalsIgnoreCase("false")) {
            outputToServer.println(GET_START_GAME);
            outputToServer.flush();
            
            try {
                gameStarted = inputFromServer.readLine();                
            } catch (IOException ex) {
                System.err.println("Error in getStartGame: IOException!");
                ex.printStackTrace();
            }
                        
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
        int opponentId = 0;
        try{
            opponentId = Integer.parseInt(inputFromServer.readLine());
            System.err.println("getOpponentId: "+opponentId);
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return opponentId;
    }
    
    
    
    public void sendMove(int type, int x, int y){
        outputToServer.println(SEND_MOVE);      
        outputToServer.println(type);
        outputToServer.println(x);
        outputToServer.println(y);        
        outputToServer.flush();
    }
    
    public int getMoveCount(){
        outputToServer.println(GET_MOVE_COUNT);
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
    
    public Movement getMove(int n){
        outputToServer.println(GET_MOVE);
        outputToServer.println(n);
        outputToServer.flush();
        int type = -1;
        int playerId = -1;
        int x = -1;
        int y = -1;
        
        try{
            type = Integer.parseInt(inputFromServer.readLine());
            playerId = Integer.parseInt(inputFromServer.readLine());
            x = Integer.parseInt(inputFromServer.readLine());
            y = Integer.parseInt(inputFromServer.readLine());            
        } catch(IOException ex){
            System.err.println("Error in geCowboyMove!");
            ex.printStackTrace();
        }
        
        Movement move = new Movement(type, playerId, x, y);
        
        System.out.println("getCowboyMove: " + move);
        
        return move;
    }    
}
