package simulation;

import physics.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Box {
    private ArrayList<LineSegment> walls;
    private Rectangle r;
    public int x;
    public int y;
    public int width;
    public int height;
    
    // Set outward to true if you want a box with outward pointed normals
    public Box(int x,int y,int width,int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        walls = new ArrayList<>();

        walls.add(new LineSegment(new Point(x, y), new Point(x + width, y)));
        walls.add(new LineSegment(new Point(x + width, y + height), new Point(x, y + height)));
    }
    
    public boolean missileOutOfBound(Ray in,double time)
    {
        // For each of the walls, check to see if the Ray intersects the wall
        Point intersection = null;
        for(int n = 0;n < walls.size();n++)
        {
            LineSegment seg = in.toSegment(time);
            intersection = walls.get(n).intersection(seg);
            if(intersection != null)
            {
                return true;
            }
        }
        return false;
    }
    
    public Shape getShape()
    {
        r = new Rectangle(x, y, width, height);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        return r;
    }    
}
