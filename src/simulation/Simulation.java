package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.shape.Shape;

public class Simulation {
    private Box outer;
    private Cowboy cowboy;
    private Lock lock;
    private ArrayList<Shape> newShapes;
    
    public Simulation(int width,int height,int dX,int dY)
    {
        newShapes = new ArrayList<>();
        outer = new Box(0,0,width,height,false);
        cowboy = new Cowboy(width/2, height-20);
        lock = new ReentrantLock();
    }
    
//    public void evolve(double time)
//    {
//        lock.lock();
//        Ray newLoc = cowboy.bounceRay(ball.getRay(), time);
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
    
    public void movePlayer(int deltaX,int deltaY)
    {
        lock.lock();
        int dX = deltaX;
        int dY = deltaY;
        if(cowboy.x + deltaX < 0)
          dX = -cowboy.x;
        if(cowboy.x + cowboy.getRadius() + deltaX > outer.width)
          dX = outer.width - cowboy.getRadius() - cowboy.x;
       
        if(cowboy.y + deltaY < 0)
           dY = -cowboy.y;
        if(cowboy.y + cowboy.getRadius() + deltaY > outer.height)
           dY = outer.height - cowboy.getRadius() - cowboy.y;
        
        cowboy.move(dX,dY);
       
        lock.unlock();
    }
    
    public void shootMissileUp(){
        lock.lock();
        cowboy.shootUp(); 
        int indexMissile = cowboy.getMissileList().size() - 1;
        //cowboy.getMissileList().get(indexMissile).updateShape();
        newShapes.add(cowboy.getMissileList().get(indexMissile).getShape());
       
        lock.unlock();
        System.out.print(cowboy.getMissileList().get(indexMissile));
    }
    
    public void shootMissileDown(){
        cowboy.shootDown();
    }
    
    public List<Shape> setUpShapes()
    {
        
        newShapes.add(outer.getShape());
        newShapes.add(cowboy.getShape());
       
//        
//        int numOfMissile = cowboy.getMissileList().size();
//        for(int i = 0; i < numOfMissile; i++){
//            newShapes.add(cowboy.getMissileList().get(i).getShape());
//        }
//                
        return newShapes;
    }
    
    public void updateShapes()
    {
        cowboy.updateShape();
    }
}
