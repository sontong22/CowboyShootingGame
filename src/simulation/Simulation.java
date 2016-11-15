package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.shape.Shape;
import physics.Ray;
import spaceshootingclient.Movement;

public class Simulation {
    private Box outer;
    private Cowboy cowboyDown;
    private Cowboy cowboyUp;
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
        
        lock = new ReentrantLock();
    }
    
//    public void evolve(double time)
//    {
//        lock.lock();
//        Ray newLoc = cowboyDown.bounceRay(ball.getRay(), time);
//        if(newLoc != null)
//            ball.setRay(newLoc);
//        else {
//            newLoc = outer.bounceRay(ball.getRay(), time);
//            if(newLoc != null)
//                ball.setRay(newLoc);
//            else
//                ball.move(time);
//        } 
//        
//        lock.unlock();
//    }
    
    public void evolve(double time)
    {
        lock.lock();
        cowboyDown.evolve(time);
        cowboyUp.evolve(time);
        lock.unlock();
    }    
    
    
    
    
    
    public void moveCowboy(Movement move)
    {
        lock.lock();
        if (move.playerId == cowboyDown.playerId) {
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
        if(id != cowboyDown.playerId)
            return cowboyDown.getCowboyPosition();
        else
            return cowboyUp.getCowboyPosition();
    }
    
    public void shootMissileUp(){
        lock.lock();
        cowboyDown.shootUp();         
        lock.unlock();
        
    }
    
    public void shootMissileDown(){
        lock.lock();
        cowboyDown.shootDown();
        lock.unlock();
    }        
    
    public List<Shape> setUpShapes()
    {         
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(outer.getShape());
        shapes.add(cowboyDown.getShape());
        shapes.add(cowboyUp.getShape());
       
        int numOfMissile = cowboyDown.getMissileList().size();
        for (int i = 0; i < numOfMissile; i++) 
            shapes.add(cowboyDown.getMissileList().get(i).getShape());        

        numOfMissile = cowboyUp.getMissileList().size();
        for (int i = 0; i < numOfMissile; i++) 
            shapes.add(cowboyUp.getMissileList().get(i).getShape());        

        return shapes;
    }
    
    public void updateShapes()
    {
        lock.lock();
        cowboyDown.updateShape();
        cowboyUp.updateShape();
        lock.unlock();
    }
}
