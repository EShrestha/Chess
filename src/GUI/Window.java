package GUI;

import javax.swing.*;
import java.awt.*;

public class Window {

    private final JFrame gameFrame;
    private static Dimension WINDOW_DIMENSION = new Dimension(800,800);

    public Window(){
        this.gameFrame = new JFrame("El Chess");

        this.gameFrame.setSize(WINDOW_DIMENSION);
        this.gameFrame.setVisible(true);
    }

}
