import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GamePanel extends JPanel {

    //instance fields

    //wavelengths
    private int wavelength1;
    private int wavelength2;

    //position of the dots
    private Point dotposition1;
    private Point dotposition2;

    //distance between dots and the horizontal line
    private int linePosition;

    //the initial offset of the line, can be used for animation purposes int he future
    private int initOffset1;
    private int initOffset2;

    private int fps;

    private Timer timer;

    private ActionListener action;

    //--------------------

    //constructor
    public GamePanel(int w, int h) {

        dotposition1 = new Point(200, 400);
        dotposition2 = new Point(400, 400);

        linePosition = 300;

        wavelength1 = 80;
        wavelength2 = 130;

        initOffset1 = 0;
        initOffset2 = 0;

        fps = 60;

        //setting up the actionListener
        action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(initOffset1 < wavelength1){
                    initOffset1++;
                }else{
                    initOffset1 = 0;
                }//end if else


                if(initOffset2 < wavelength2){
                    initOffset2++;
                }else{
                    initOffset2 = 0;
                }//end if else

                repaint();

            }//end actionperformed
        };//end actionListner

        //setting up timer
        timer = new Timer(1000/fps, action);

        timer.start();

    }//end GamePanel

    //--------------------

    //methods
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setStroke(new BasicStroke(2));

        //first circle (blue)
        g2.setColor(new Color(66, 134, 244));
        g2.fillOval((int)dotposition1.getX() - 5, (int)dotposition1.getY() - 5, 10, 10);
        drawWaves(wavelength1, dotposition1, g2, 40, initOffset1);
        g2.setColor(new Color(145, 187, 255));
        drawWaves(wavelength1, dotposition1, g2, 40, initOffset1 - wavelength1/2);

        //second circle (red)
        g2.setColor(new Color(255, 96, 96));
        g2.fillOval((int)dotposition2.getX() - 5, (int)dotposition2.getY() - 5, 10, 10);
        drawWaves(wavelength2, dotposition2, g2, 40, initOffset2);
        g2.setColor(new Color(255, 160, 160));
        drawWaves(wavelength2, dotposition2, g2, 40, initOffset2 - wavelength2/2);

        //drawing various lines, comment out if you don't want
//        g2.setColor(new Color(10, 10, 10));
//        g2.drawLine(300, 0, 300, 600);

        //this erases the portion above the line, comment out if you do not want
        g2.setColor(UIManager.getColor("Panel.background"));
        g2.fillRect(0, 0, 600, 100);

        //draws the line
        g2.setColor(new Color(10, 10, 10));
        g2.drawLine(0, (int)(dotposition1.getY() - linePosition), 600, (int)(dotposition1.getY() - linePosition));

        drawStats(g2);

    }//end paintComponent

    //--------------------

    public void drawWaves(int wavelength, Point startpoint, Graphics2D g2, int numWaves, int offset){

        int modwave = offset;

        for (int i = 0; i < numWaves; i++) {

            g2.drawOval((int)startpoint.getX() - modwave/2, (int)startpoint.getY() - modwave/2, modwave, modwave);
            modwave += wavelength;

        }//end for

    }//end drawWaves

    //--------------------

    public void drawStats(Graphics2D g2){

        g2.setFont(new Font("Arial", Font.BOLD, 12));

        g2.drawString("Blue Wavelength: " + wavelength1 + "px", 10, 20);
        g2.drawString("Red Wavelength: " + wavelength2 + "px", 10, 40);
        g2.drawString("Velocity/FPS: " + fps + "px/sec", 10, 60);

    }//end drawStats

}//end class
