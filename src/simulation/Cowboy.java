
package simulation;

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
    public boolean isDead;
    private Circle c;
    private Lock lock;
    
    public Cowboy(int id, int x, int y) {
        playerId = id;        
        this.x = x;
        this.y = y;
        isDead = false;
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
                           
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }
    
    public Movement getCowboyPosition(){
        Movement currentPosition = new Movement(1, playerId, x, y);
        return currentPosition;
    }

       

    public Shape getShape() {
        c = new Circle(x, y, RADIUS);
        c.setFill(Color.ORANGE);
        return c;
    }
    
    public void updateShape() {
        c.setCenterX(x);
        c.setCenterY(y);
    }

}
