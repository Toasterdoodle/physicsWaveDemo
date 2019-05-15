import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GamePanel extends JPanel {

    //instance fields

    private double frequency1;
    private double frequency2;

    private JSlider fpsSlider;
    private JSlider wavelength1Slider;
    private JSlider wavelength2Slider;

    private ChangeListener fpsChanger;
    private ChangeListener wavelength1Changer;
    private ChangeListener wavelength2Changer;

    private JButton rephaseButton;
    private ActionListener rephase;

    //wavelengths
    private int wavelength1;
    private int wavelength2;

    //position of the dots
    private Point dotposition1;
    private Point dotposition2;

    //distance between dots and the horizontal line, calculated using dotposition1
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

        setLayout(null);

        dotposition1 = new Point(200, 400);
        dotposition2 = new Point(400, 400);

        linePosition = 250;

        wavelength1 = 80;
        wavelength2 = 80;

        initOffset1 = 0;
        initOffset2 = 0;

        fps = 60;

        initRephaseButtons();

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

        //reliant upon fpsSlider and timer being complete
        initSliders();

        //adds component
        add(fpsSlider);
        add(wavelength1Slider);
        add(wavelength2Slider);
        add(rephaseButton);

        frequency1 = (double)fps/wavelength1;
        frequency2 = (double)fps/wavelength1;

        //WARNING: should be the last line
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
        g2.fillRect(0, 0, 600, (int)dotposition1.getY() - linePosition);

        //draws the line
        g2.setColor(new Color(10, 10, 10));
        g2.drawLine(0, (int)(dotposition1.getY() - linePosition), 600, (int)(dotposition1.getY() - linePosition));

        drawStats(g2);

    }//end paintComponent

    //--------------------

    public void drawWaves(int wavelength, Point startpoint, Graphics2D g2, int numWaves, int offset){
        //desc:
        //wavelength: wavelength of the wave
        //startpoint: origin point of the waves
        //g2: g2
        //numWaves: number of waves to draw, this method only draws a certain amount of waves
        //just draw enough to fill up the screen
        //offset: how much you want the shift to be,
        //mainly designed so you can draw in "trough" waves at half the wavelength of the normal wave

        int modwave = offset;

        for (int i = 0; i < numWaves; i++) {

            g2.drawOval((int)startpoint.getX() - modwave/2, (int)startpoint.getY() - modwave/2, modwave, modwave);
            modwave += wavelength;

        }//end for

    }//end drawWaves

    //--------------------

    public void drawStats(Graphics2D g2){

        g2.setFont(new Font("Arial", Font.BOLD, 12));

        DecimalFormat df = new DecimalFormat("#.####");

        g2.drawString("Blue Wavelength: " + wavelength1 + "px", 35 + 10 + 180, 70);
        g2.drawString("Red Wavelength: " + wavelength2 + "px", 35 + 10 + 180 + 10 + 180, 70);
        g2.drawString("Velocity/FPS: " + fps + "px/sec", 35, 70);
        g2.drawString("Frequency: " + df.format(frequency1) + "waves/sec", 30 + 10 + 180, 90);
        g2.drawString("Frequency: " + df.format(frequency2) + "waves/sec", 30 + 10 + 180 + 10 + 180, 90);

    }//end drawStats

    //--------------------

    public void initSliders(){

        //creating slider for FPS
        fpsSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 60);

        //Turn on labels at major tick marks.
        fpsSlider.setMajorTickSpacing(10);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setMinorTickSpacing(5);
        fpsSlider.setPaintLabels(true);
        fpsSlider.setSnapToTicks(true);

        fpsChanger = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                fps = fpsSlider.getValue();
                timer.setDelay(1000/fps);
                frequency1 = (double)fps/wavelength1;
                frequency2 = (double)fps/wavelength2;

            }//end stateChanged
        };//end changeListener

        //reliant upon fpsChanger and fpsSlider being complete
        fpsSlider.addChangeListener(fpsChanger);

        fpsSlider.setBounds(10, 10, 180, 40);

        //--------------------

        //creating slider for wavelength1
        wavelength1Slider = new JSlider(JSlider.HORIZONTAL, 60, 200, 80);

        wavelength1Slider.setMajorTickSpacing(20);
        wavelength1Slider.setPaintTicks(true);
        wavelength1Slider.setPaintLabels(true);
        wavelength1Slider.setSnapToTicks(true);

        wavelength1Changer = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                wavelength1 = wavelength1Slider.getValue();
                frequency1 = (double)fps/wavelength1;

            }//end stateChanged
        };//end changeListener

        wavelength1Slider.addChangeListener(wavelength1Changer);

        wavelength1Slider.setBounds(10 + 180 + 10, 10, 180, 40);

        //--------------------

        //creating the slider for wavelength2
        wavelength2Slider = new JSlider(JSlider.HORIZONTAL, 60, 200, 80);

        wavelength2Slider.setMajorTickSpacing(20);
        wavelength2Slider.setPaintTicks(true);
        wavelength2Slider.setPaintLabels(true);
        wavelength2Slider.setSnapToTicks(true);

        wavelength2Changer = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                wavelength2 = wavelength2Slider.getValue();
                frequency2 = (double)fps/wavelength2;

            }//end stateChanged
        };//end changeListener

        wavelength2Slider.addChangeListener(wavelength2Changer);

        wavelength2Slider.setBounds(10 + 180 + 10 + 180 + 10, 10, 180, 40);

    }//end initSliders

    //--------------------

    public void initRephaseButtons(){

        //setting up rephase action
        rephase = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                initOffset2 = 0;
                initOffset1 = 0;

            }//end actionPerformed
        };//end actionListener

        rephaseButton = new JButton("Rephase");
        rephaseButton.addActionListener(rephase);
        rephaseButton.setBounds(300 - 40, 100, 80, 40);

    }//end initRephaseButtons

}//end class
