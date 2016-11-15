package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.shape.Shape;
import spaceshootingclient.Movement;

public class Simulation {
    private Box outer;
    private Cowboy cowboyDown;
    private Cowboy cowboyUp;    
    private ArrayList<Missile> missileList;    
    private Lock lock;
    
    public Simulation(int width, int height, int playerId, int opponentId) {
        outer = new Box(0, 0, width, height, false);
        
        if (playerId < opponentId) {
            cowboyDown = new Cowboy(playerId, width / 2, height - 20);
            cowboyUp = new Cowboy(opponentId, width / 2, 20);
        } else {            
            cowboyUp = new Cowboy(playerId, width / 2, 20);
            cowboyDown = new Cowboy(opponentId, width / 2, height - 20);
        }
        missileList = new ArrayList<>();
        lock = new ReentrantLock();
    }
    
    // Evolve the simulation through one round, advancing simulation 
    // time by time units.
    public void evolve(double time)
    {
        lock.lock();
        if (!missileList.isEmpty()) {
            int n = missileList.size();
            for (int i = 0; i < n; i++) {
//                boolean isDisappeared = cowboyDown.hitByMissile(missileList.get(i).getRay(), time);
//                if (isDisappeared == true) {
//                    missileList.remove(i);
//                } else {
//                    isDisappeared = outer.missileOutOfBound(missileList.get(i).getRay(), time);
//                    if (isDisappeared == true) {
//                        missileList.remove(i);
//                    } else {
//                        missileList.get(i).move(time);
//                    }
//                    missileList.get(i).move(time);
//                }
                missileList.get(i).move(time);
            }
        }
        lock.unlock();
    } 
    
    public void shootMissile(Movement move) {
        lock.lock();
        
        if(move.playerId == cowboyDown.playerId)
            missileList.add(new Missile(move.x, move.y, -5));
        else
            missileList.add(new Missile(move.x, move.y, 5));
        
        lock.unlock();
    }    
    
    // Move cowboy by the indicated amount          
    public void moveCowboy(Movement move)
    {
        lock.lock();
        if (move.playerId == cowboyUp.playerId) {
            int dX = move.x;
            int dY = move.y;
            if (cowboyUp.x + cowboyUp.RADIUS + move.x < 0) {
                dX = -cowboyUp.x;
            }
            if (cowboyUp.x + cowboyUp.RADIUS + move.x > outer.width) {
                dX = outer.width - cowboyUp.RADIUS - cowboyUp.x;
            }

            if (cowboyUp.y + cowboyUp.RADIUS + move.y < 0) {
                dY = -cowboyUp.y;
            }
            if (cowboyUp.y + cowboyUp.RADIUS + move.y > outer.height) {
                dY = outer.height - cowboyUp.RADIUS - cowboyUp.y;
            }
            
            cowboyUp.move(dX, dY);
        } else {
            int dX = move.x;
            int dY = move.y;
            if (cowboyDown.x + cowboyDown.RADIUS + move.x < 0) {
                dX = -cowboyDown.x;
            }
            if (cowboyDown.x + cowboyDown.RADIUS + move.x > outer.width) {
                dX = outer.width - cowboyDown.RADIUS - cowboyDown.x;
            }

            if (cowboyDown.y + cowboyDown.RADIUS + move.y < 0) {
                dY = -cowboyDown.y;
            }
            if (cowboyDown.y + cowboyDown.RADIUS + move.y > outer.height) {
                dY = outer.height - cowboyDown.RADIUS - cowboyDown.y;
            }

            cowboyDown.move(dX, dY);
        }
        lock.unlock();
    }
        
    public Movement getCowboyPosition(int id){
        if(id == cowboyDown.playerId)
            return cowboyDown.getCowboyPosition();
        else
            return cowboyUp.getCowboyPosition();
    }    
    
     // Set up the shapes to be displayed in the GUI
    public List<Shape> setUpShapes()
    {         
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(outer.getShape());
        shapes.add(cowboyDown.getShape());
        shapes.add(cowboyUp.getShape());
        
        if (!missileList.isEmpty()) {
            int n = missileList.size();
            for (int i = 0; i < n; i++) {
                shapes.add(missileList.get(i).getShape());
            }
        }
        return shapes;
    }
    
    // Update the GUI shapes by moving things to their correct positions
    public void updateShapes()
    {
        lock.lock();
        cowboyDown.updateShape();
        cowboyUp.updateShape();
        
        if (missileList.isEmpty()) {
            int n = missileList.size();
            for (int i = 0; i < n; i++) {
                missileList.get(i).updateShape();
            }
        }
        lock.unlock();
    }
}
