package GalacticTBA;
import java.awt.Color;
import java.awt.Graphics;

public class Saturn extends HeavenlyBody {

     public Saturn(HeavenlyBody p, int d, double r, double s, Color c) {
        super(p, d, r, s, c);
     }

     public void draw(Graphics g) {
        super.draw(g);
        g.fillOval(x + (int) (-0.5*diameter), y + (int) (+0.35*diameter),
                   2*diameter, (int) (0.3*diameter) );
    }
}