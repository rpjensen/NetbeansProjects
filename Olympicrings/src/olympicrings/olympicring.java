package olympicrings;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JApplet;
/**
 *
 * @author Ryan Jensen
 */
public class olympicring extends JApplet {

    /**
     * @param args the command line arguments
     */
    @Override
    public void paint (Graphics draw){
        draw.setColor(Color.BLUE);
        draw.drawOval(5, 5, 100, 100);//x,y(topleft),width,height
        draw.setColor(Color.YELLOW);
        draw.drawOval(65, 55, 100, 100);//x,y(topleft),width,height
        draw.setColor(Color.BLACK);
        draw.drawOval(110, 5, 100, 100);//x,y(topleft),width,height
        draw.setColor(Color.GREEN);
        draw.drawOval(170, 55, 100, 100);//x,y(topleft),width,height
        draw.setColor(Color.RED);
        draw.drawOval(215, 5, 100, 100);//x,y(topleft),width,height
        
    }//main
}//public class
