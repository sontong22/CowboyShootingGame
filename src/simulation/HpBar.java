
package simulation;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class HpBar{
    public static final int PIXELS_PER_VALUE = 40;
    public static final int WIDTH = PIXELS_PER_VALUE * 5;
    public static final int HEIGHT = 15;        
    public int playerId;
    public int x;
    public int y;
    public int hp;    
    private Rectangle border;
    private Rectangle hpBar;    
    
    
    public HpBar(int id, int x, int y) {        
        playerId = id;        
        this.x = x;
        this.y = y;
        this.hp = 5;                
                                
        border = new Rectangle(this.x, this.y , WIDTH, HEIGHT);
        border.setFill(Color.WHITE);
        border.setStroke(Color.BLACK); 
        setHp(5);
    }
    
    public void setHp(int x){
        if(x < 0 || x > 5){
            throw new IllegalArgumentException("Hp out of range");
        } else {
            hp = x;
            drawHpBar();
        }                  
    }
    
    public void drawHpBar(){                
        hpBar = new Rectangle(x, y, PIXELS_PER_VALUE*hp, HEIGHT);
        hpBar.setFill(Color.GREEN);
        hpBar.setStroke(Color.BLACK);
               
    }

    public List<Shape> getShape() {
        
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(border);
        shapes.add(hpBar);      
        
        return shapes;
    }
    
    public void updateShape() {
        setHp(hp);
    }
}

