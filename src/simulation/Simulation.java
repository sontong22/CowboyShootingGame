package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import spaceshootingclient.Movement;

public class Simulation {
    private Box outer;
    private Cowboy cowboyDown;
    private Cowboy cowboyUp;    
    private ArrayList<Missile> missileList;    
    private Lock lock;
    
    public Simulation(int width, int height, int playerId, int opponentId) {
        outer = new Box(0, 0, width, height);
        
        if (playerId < opponentId) {
            cowboyDown = new Cowboy(playerId, width / 2, height - 20, true);
            cowboyUp = new Cowboy(opponentId, width / 2, 20, false);
        } else {            
            cowboyUp = new Cowboy(playerId, width / 2, 20, false);
            cowboyDown = new Cowboy(opponentId, width / 2, height - 20, true);
        }
        missileList = new ArrayList<>();
        lock = new ReentrantLock();
    }    
    
    public void evolve(double time)
    {
        lock.lock();
        if (!missileList.isEmpty()) {
            //int n = missileList.size();
            for (int i = 0; i < missileList.size(); i++) {
                boolean hitDown = cowboyDown.hitByMissile(missileList.get(i).getRay(), time);
                boolean hitUp = cowboyUp.hitByMissile(missileList.get(i).getRay(), time);
                if (hitDown == true || hitUp == true) {
                    missileList.remove(i);
                } else {
                    boolean isDisappeared = outer.missileOutOfBound(missileList.get(i).getRay(), time);
                    if (isDisappeared == true) {
                        missileList.remove(i);
                    } else {
                        missileList.get(i).move(time);
                    }
                }
            }
        }
        lock.unlock();
    } 
                
    
    public void shootMissile(Movement move) {
        lock.lock();
        
        if(move.playerId == cowboyDown.playerId)
            missileList.add(new Missile(move.x, move.y - Cowboy.RADIUS - 5, -5));
        else
            missileList.add(new Missile(move.x, move.y, 5));
        
        lock.unlock();
    }    
           
    // Move cowboy by the indicated amount          
    public void moveCowboy(Movement move)
    {
        lock.lock();
        Cowboy cowboy;
        if (move.playerId == cowboyUp.playerId) {
            cowboy = cowboyUp;
        } else {
            cowboy = cowboyDown;
        }

        int dX = move.x;
        int dY = move.y;
        if (cowboy.x + Cowboy.RADIUS + move.x < 0) {
            dX = -cowboy.x;
        }
        if (cowboy.x + Cowboy.RADIUS + move.x > outer.width) {
            dX = outer.width - Cowboy.RADIUS - cowboy.x;
        }

        if (cowboy.y + Cowboy.RADIUS + move.y < 0) {
            dY = -cowboy.y;
        }
        if (cowboy.y + Cowboy.RADIUS + move.y > outer.height) {
            dY = outer.height - Cowboy.RADIUS - cowboy.y;
        }

        cowboy.move(dX, dY);

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
