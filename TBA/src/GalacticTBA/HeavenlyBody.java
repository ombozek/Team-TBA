package GalacticTBA;
import java.awt.Color;
import java.awt.Graphics;

public class HeavenlyBody {
    private HeavenlyBody parent;    // parent body of which this is a satellite
    int x, y,                       // location of this body
        diameter,                   // diameter of this body
        numSatellites = 0;          // number of satellites
    final static int MAXSATELLITES = 10;

    private double orbitRadius,     // radius of its orbit
                   stepSize;        // amount of radians incremented per time step
    private double orbitPosition;   // orbit position in radians
    private Color color;
    private HeavenlyBody satellite[] = new HeavenlyBody [MAXSATELLITES]; // its satellites
    static boolean showOrbit = false;

    public HeavenlyBody(HeavenlyBody p, int d, double r, double s, Color c) {
        p.addSatellite(this); // insert this onto parent's satellite list
        parent = p;
        diameter = d;
        orbitRadius = r;
        stepSize = s;
        color = c;
    }

    public HeavenlyBody(int X, int Y, int D) { // invoked only on God
        x = X;
        y = Y;
        diameter = D;
    }

    public boolean addSatellite(HeavenlyBody p) {
        if (numSatellites < MAXSATELLITES) {
            satellite[numSatellites++] = p;
            return true;
        }
        else
            return false;
    }

    public void move() {
        orbitPosition += stepSize;
        if (orbitPosition > 2 * Math.PI)
            orbitPosition -= 2 * Math.PI;
        x = parent.x  + parent.diameter/2 - diameter/2 + (int) (orbitRadius * Math.cos(orbitPosition));
        y = parent.y  + parent.diameter/2 - diameter/2 + (int) (orbitRadius * Math.sin(orbitPosition));
        for (int i = 0; i < numSatellites; i++)
            satellite[i].move();
    }

     public void draw(Graphics g) {

        if (showOrbit) {
            g.setColor(Color.black);
            g.drawOval(parent.x - (int) orbitRadius + parent.diameter/2,
                       parent.y - (int) orbitRadius + parent.diameter/2,
                       2 * (int) orbitRadius,
                       2 * (int)orbitRadius);
        }
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
        for (int i = 0; i < numSatellites; i++)
            satellite[i].draw(g);
    }
}