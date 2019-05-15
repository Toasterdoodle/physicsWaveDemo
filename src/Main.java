import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

//        //setting look and feel
//        try{
//
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//
//        }catch(Exception e){
//
//            System.out.println("There's something wrong with your look and feel lmao");
//
//        }//end trycatch

        //===========Jframe==========
        JFrame frame = new JFrame("Wave Demo");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        int width = 600;
        int height = 600;
        frame.setPreferredSize(new Dimension(width, height + 22));

        //============JPanel===========
        JPanel panel = new GamePanel(width, height);
        panel.setFocusable(true);
        panel.grabFocus();

        //============JFrame============
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }//end psvm

}//end class