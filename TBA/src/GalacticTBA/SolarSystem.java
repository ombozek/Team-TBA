package GalacticTBA;
// Animated solar system

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class SolarSystem extends Applet {
    int sleepTime = 100,
        pauseTime = 2000,
        appletXSize = 900,
        appletYSize = 600;
    Button pause = new Button("Pause"),
           showOrbits = new Button("Show/Remove orbit traces");

    HeavenlyBody God = new HeavenlyBody(appletXSize/2, appletYSize/2, 40),
                 Sun = new HeavenlyBody(God, 50, 0.0, 0.0, Color.yellow),
                 Mercury = new HeavenlyBody(Sun, 5, 80.0, 0.5, Color.darkGray),
                 Venus = new HeavenlyBody(Sun, 7, 110.0, 0.4, Color.green),
                 Earth = new HeavenlyBody(Sun, 8, 150.0, 0.3, Color.blue),
                 Moon = new HeavenlyBody(Earth, 3, 25.0, 0.8, Color.black),
                 Mars = new HeavenlyBody(Sun, 8, 190.0, 0.24, Color.red),
                 Jupiter = new HeavenlyBody(Sun, 24, 240.0, 0.12, Color.pink),
                 JMoon1 = new HeavenlyBody(Jupiter, 4, 34.0, 0.7, Color.black),
                 JMoon2 = new HeavenlyBody(Jupiter, 5, 36.0, 0.2, Color.black),
                 JMoon2Moon = new HeavenlyBody(JMoon2, 3, 9.0, 0.1, Color.red),
                 Saturn = new Saturn(Sun, 21, 280.0, 0.08, Color.orange),
                 Uranus = new HeavenlyBody(Sun, 10, 320.0, 0.04, Color.green),
                 Neptune = new HeavenlyBody(Sun, 10, 350.0, 0.02, Color.blue),
                 Pluto = new HeavenlyBody(Sun, 6, 370.0, 0.01, Color.gray);

    public void init() {
        add(pause);                 // add Pause button
        add(showOrbits);            // add Show/Remove orbit traces button
    }

    public void paint(Graphics g) {
        Sun.move();
        Sun.draw(g);

        try {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e) {
            showStatus(e.toString());
        }
        repaint();
    }

    public boolean action(Event event, Object o) {
        if (event.target == pause)
            try {
                Thread.sleep(pauseTime);
            }
            catch (InterruptedException e) {
                showStatus(e.toString());
            }
        else if (event.target == showOrbits)
                HeavenlyBody.showOrbit = !HeavenlyBody.showOrbit;
        return true;
    }
}