
package simulation;

import java.util.ArrayList;
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
    public int hp;
    private Circle c;
    ArrayList<LineSegment> walls;
    boolean isDown;
    
    public Cowboy(int id, int x, int y, boolean down) {
        walls = new ArrayList<>();
        playerId = id;        
        this.x = x;
        this.y = y;
        hp = 5;        
        isDown = down;
        
        Point downLeft = new Point(x-RADIUS, y+RADIUS);
        Point downRight = new Point(x+RADIUS, y+RADIUS);
        Point upLeft = new Point(x-RADIUS, y-RADIUS);
        Point upRight = new Point(x+RADIUS, y-RADIUS);
        
        if (isDown) {
            walls.add(new LineSegment(upRight, upLeft));
        } else {
            walls.add(new LineSegment(downLeft, downRight));            
        }
    }
    
    public boolean hitByMissile(Ray in,double time)
    {        
        // For each of the walls, check to see if the Ray intersects the wall
        Point intersection = null;
        for(int n = 0;n < walls.size();n++)
        {
            LineSegment seg = in.toSegment(time);
            intersection = walls.get(n).intersection(seg);
            if(intersection != null)
            {
                hp--;
                return true;
            }
        }
        return false;
    }
    
    public boolean getIsDead(){
        if(hp <= 0)
            return true;
        else
            return false;
    }
            
                           
    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        
        
        Point downLeft = new Point(x-RADIUS, y+RADIUS);
        Point downRight = new Point(x+RADIUS, y+RADIUS);
        Point upLeft = new Point(x-RADIUS, y-RADIUS);
        Point upRight = new Point(x+RADIUS, y-RADIUS);
        
        int n = walls.size();
        for(int i = 0; i < n; i++)
            walls.remove(i);
        
        if (isDown) {
            walls.add(new LineSegment(upRight, upLeft));
        } else {
            walls.add(new LineSegment(downLeft, downRight));            
        }
    }
    
    public Movement getCowboyPosition(){
        Movement currentPosition = new Movement(1, playerId, x, y);
        return currentPosition;
    }

    public boolean contains(Point p) {
        if (p.x >= x && p.x <= x + RADIUS && p.y >= y && p.y <= y + RADIUS) {
            return true;
        }
        return false;
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
