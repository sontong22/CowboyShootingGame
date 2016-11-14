
package simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import physics.Point;
import physics.Ray;
import physics.Vector;

public class Missile {
    private Circle c;
    private Ray r;
    
    public Missile(int x, int y, int dY){
        Vector v = new Vector(0,dY);
        double speed = v.length();
        
        r = new Ray(new Point(x,y), v, speed);
        
    }
    
     public Ray getRay()
    {
        return r;
    }
    
    public void setRay(Ray r)
    {
        this.r = r;
    }
    
    public void move(double time)
    {
        r = new Ray(r.endPoint(time),r.v,r.speed);
    }
    
    public Shape getShape() {
        c = new Circle(r.origin.x,r.origin.y,4);
        c.setFill(Color.GREEN);
        return c;
    }
    
    public void updateShape() {
        c.setCenterX(r.origin.x);
        c.setCenterY(r.origin.y);
    }
    public String toString(){
        return "Add ten lua thanh cong";
    }
}