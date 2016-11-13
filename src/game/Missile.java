
package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import physics.Point;
import physics.Ray;
import physics.Vector;

public class Missile {
    int x;
    int y;
    private Circle c;
    private Ray r;
    boolean collides;
    
    public Missile(int x, int y, int dY){
        Vector v = new Vector(0,dY);
        double speed = v.length();
        
        r = new Ray(new Point(x,y), v, speed);
        
        collides = false;
    }
    public boolean collides(Cowboy player){
        return collides;
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
        c = new Circle(x, y, 5);
        c.setFill(Color.BLACK);
        return c;
    }
    
    public void updateShape() {
        c.setCenterX(x);
        c.setCenterY(y);
    }
    public String toString(){
        return "Add ten lua thanh cong";
    }
}