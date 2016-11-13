
package game;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Cowboy {
    private static final int  RADIUS = 10;
    public int x;
    public int y;
    boolean isDead;
    private ArrayList<Missile> missileList;
    private Circle c;

    // Set outward to true if you want a box with outward pointed normals
    public Cowboy(int x, int y) {
        missileList = new ArrayList<>();
        this.x = x;
        this.y = y;
        isDead=false;
    }

    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public void shootUp() {
        missileList.add(new Missile(this.x, this.y,10));
        //pass len server
    }
    public void shootDown() {
        missileList.add(new Missile(this.x, this.y,-10));
        //pass len server
    }
    
    public ArrayList<Missile> getMissileList(){
        return missileList;
    }

    public Shape getShape() {
        c = new Circle(x, y, RADIUS);
        c.setFill(Color.ORANGE);
        return c;
    }
    
    public int  getRadius(){
        return RADIUS;
    }

    public void updateShape() {
        c.setCenterX(x);
        c.setCenterY(y);
        
        int n = missileList.size();
        for(int i = 0; i < n; i++){
            missileList.get(i).updateShape();
        }
        
    }

}
