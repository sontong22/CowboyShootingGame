/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshootingclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author macbookair
 */
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
            
        }
    }

    // Start the chat by sending in the user's handle.
    public int sendName(String name) {
        outputToServer.println(SEND_NAME);
        outputToServer.println(name);
        outputToServer.flush();
        
        int playerID = 0;
        try {
            playerID = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            
        }
        
        return playerID;
    }
    
    public void getStartGame() {
        outputToServer.println(GET_START_GAME);
        outputToServer.flush();
        
        boolean gameStarted = false;

        try {
            while (true) {
                gameStarted = Boolean.parseBoolean(inputFromServer.readLine());
                System.out.println ("Waiting for opponent...");
                if (gameStarted == true) {
                    System.out.println ("Start game");
                    break;
                }
            }   
        } catch (IOException ex) {
            
        }
    }

//    // Send a new comment to the server.
//    public void sendComment(String comment) {
//        outputToServer.println(SEND_COMMENT);
//        outputToServer.println(comment);
//        outputToServer.flush();
//    }
//
//    // Ask the server to send us a count of how many comments are
//    // currently in the transcript.
//    public int getCommentCount() {
//        outputToServer.println(GET_COMMENT_COUNT);
//        outputToServer.flush();
//        int count = 0;
//        try {
//            count = Integer.parseInt(inputFromServer.readLine());
//        } catch (IOException ex) {
//            Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
//        }
//        return count;
//    }
//
//    // Fetch comment n of the transcript from the server.
//    public String getComment(int n) {
//        outputToServer.println(GET_COMMENT);
//        outputToServer.println(n);
//        outputToServer.flush();
//        String comment = "";
//        try {
//            comment = inputFromServer.readLine();
//        } catch (IOException ex) {
//            Platform.runLater(() -> textArea.appendText("Error in getComment: " + ex.toString() + "\n"));
//        }
//        return comment;
//    }
//    
//    // Get number of rooms available on the server.
//    public int getNumberOfRooms() {
//        outputToServer.println(GET_NUMBER_OF_ROOMS);
//        outputToServer.flush();
//        int noOfRooms = 0;
//        try {
//            noOfRooms = Integer.parseInt(inputFromServer.readLine());
//        } catch (IOException ex) {
//            Platform.runLater(() -> textArea.appendText("Error in getNumberOfRooms: " + ex.toString() + "\n"));
//        }
//        return noOfRooms;
//    }
//    
//    // Get room available on the server.
//    public String getRoom(int n) {
//        outputToServer.println(GET_ROOM);
//        outputToServer.println(n);
//        outputToServer.flush();
//        String roomName = "";
//        try {
//            roomName = inputFromServer.readLine();
//        } catch (IOException ex) {
//            Platform.runLater(() -> textArea.appendText("Error in getRoom: " + ex.toString() + "\n"));
//        }
//        return roomName;
//    }
//    
//    // Choose a room:
//    public void requestRoom(String roomName) {
//        outputToServer.println(ROOM_REQUEST);
//        outputToServer.println(roomName);
//        outputToServer.flush();
//    }
    
}
