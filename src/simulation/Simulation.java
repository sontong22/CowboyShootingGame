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
    private Lock lock;
    private ArrayList<Shape> newShapes;
    
    public Simulation(int width, int height, int playerId, int opponentId) {
        newShapes = new ArrayList<>();
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
//        lock.unlock();
//    }
    
//    public void moveCowboyDown(int deltaX,int deltaY)
//    {
//        lock.lock();
//        int dX = deltaX;
//        int dY = deltaY;
//        if(cowboyDown.x + cowboyDown.RADIUS + deltaX < 0)
//          dX = -cowboyDown.x;
//        if(cowboyDown.x + cowboyDown.RADIUS + deltaX > outer.width)
//          dX = outer.width - cowboyDown.RADIUS - cowboyDown.x;
//       
//        if(cowboyDown.y + cowboyDown.RADIUS + deltaY < 0)
//           dY = -cowboyDown.y;
//        if(cowboyDown.y + cowboyDown.RADIUS + deltaY > outer.height)
//           dY = outer.height - cowboyDown.RADIUS - cowboyDown.y;
//        
//        cowboyDown.move(dX,dY);
//       
//        lock.unlock();
//    }
    
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
    
    
    
//    public void moveCowboyTo(Movement move)
//    {
//        lock.lock();
//        if (move.playerId == cowboyDown.playerId) {
//            int dX = move.x;
//            int dY = move.y;
//            if (cowboyUp.x + cowboyUp.RADIUS + move.x < 0) {
//                dX = -cowboyUp.x;
//            }
//            if (cowboyUp.x + cowboyUp.RADIUS + move.x > outer.width) {
//                dX = outer.width - cowboyUp.RADIUS - cowboyUp.x;
//            }
//
//            if (cowboyUp.y + cowboyUp.RADIUS + move.y < 0) {
//                dY = -cowboyUp.y;
//            }
//            if (cowboyUp.y + cowboyUp.RADIUS + move.y > outer.height) {
//                dY = outer.height - cowboyUp.RADIUS - cowboyUp.y;
//            }
//            
//            cowboyUp.move(dX, dY);
//        } else {
//            int dX = move.x;
//            int dY = move.y;
//            if (cowboyDown.x + cowboyDown.RADIUS + move.x < 0) {
//                dX = -cowboyDown.x;
//            }
//            if (cowboyDown.x + cowboyDown.RADIUS + move.x > outer.width) {
//                dX = outer.width - cowboyDown.RADIUS - cowboyDown.x;
//            }
//
//            if (cowboyDown.y + cowboyDown.RADIUS + move.y < 0) {
//                dY = -cowboyDown.y;
//            }
//            if (cowboyDown.y + cowboyDown.RADIUS + move.y > outer.height) {
//                dY = outer.height - cowboyDown.RADIUS - cowboyDown.y;
//            }
//
//            cowboyDown.move(dX, dY);
//        }
//        lock.unlock();
//    }
    
    
    
    
    
    
    public Movement getCowboyPosition(int id){
        if(id != cowboyDown.playerId)
            return cowboyDown.getCowboyPosition();
        else
            return cowboyUp.getCowboyPosition();
    }
    
//    public void shootMissileUp(){
//        lock.lock();
//        cowboyDown.shootUp(); 
//        int indexMissile = cowboyDown.getMissileList().size() - 1;
//        //cowboy.getMissileList().get(indexMissile).updateShape();
//        newShapes.add(cowboyDown.getMissileList().get(indexMissile).getShape());
//       
//        lock.unlock();
//        System.out.print(cowboyDown.getMissileList().get(indexMissile));
//    }
//    
//    public void shootMissileDown(){
//        cowboyDown.shootDown();
//    }        
    
    public List<Shape> setUpShapes()
    {        
        newShapes.add(outer.getShape());
        newShapes.add(cowboyDown.getShape());
        newShapes.add(cowboyUp.getShape());
     
        return newShapes;
    }
    
    public void updateShapes()
    {
        lock.lock();
        cowboyDown.updateShape();
        cowboyUp.updateShape();
        lock.unlock();
    }
}
