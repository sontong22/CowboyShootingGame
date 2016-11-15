
package simulation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import physics.LineSegment;
import physics.Point;
import physics.Ray;
import spaceshootingclient.Movement;

public class Cowboy {
    public static final int RADIUS = 10;
    public int playerId;
    public int x;
    public int y;
    boolean isDead;
    private ArrayList<Missile> missileList;    
    private Circle c;
    private Lock lock;
    
    public Cowboy(int id, int x, int y) {
        playerId = id;
        missileList = new ArrayList<>();
        this.x = x;
        this.y = y;
        isDead=false;
        lock = new ReentrantLock();
    }
    
    public boolean hitByMissile(Ray in,double time)
    {        
        Point intersection = null;

        LineSegment missleLine = in.toSegment(time);
        
        Point downLeft = new Point(x-RADIUS, y+RADIUS);
        Point downRight = new Point(x+RADIUS, y+RADIUS);
        Point upLeft = new Point(x-RADIUS, y-RADIUS);
        Point upRight = new Point(x+RADIUS, y-RADIUS);
        
        LineSegment belowSegment = new LineSegment(downLeft, downRight);
        LineSegment upperSegment = new LineSegment(upLeft, upRight);
        
        intersection = belowSegment.intersection(missleLine);
        if (intersection != null) {
            return true;
        }
        
        intersection = upperSegment.intersection(missleLine);
        if(intersection != null)
            return true;

        return false;
    }
    
    
    
    
    public void evolve(double time)
    {
        lock.lock();
        int n = missileList.size();
        for(int i = 0; i < n; i++)
            missileList.get(i).move(time);
        lock.unlock();
    }

    
    

    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }
    
    public Movement getCowboyPosition(){
        Movement currentPosition = new Movement(playerId, x, y);
        return currentPosition;
    }

    public void shootUp() {
        missileList.add(new Missile(this.x, this.y,5));
    }
    
    public void shootDown() {
        missileList.add(new Missile(this.x, this.y,-5));
    }
    
    public ArrayList<Missile> getMissileList(){
        return missileList;
    }

    public Shape getShape() {
        c = new Circle(x, y, RADIUS);
        c.setFill(Color.ORANGE);
        return c;
    }
    
    public void updateShape() {
        c.setCenterX(x);
        c.setCenterY(y);

        int n = missileList.size();
        for (int i = 0; i < n; i++) {
            missileList.get(i).updateShape();
        }

    }

}
