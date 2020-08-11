package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class WindowChess {

    JFrame window;
    Container container;
    JPanel titleNamePanel, startButtonPanel, loadGamePanel, quitGamePanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Helvetica", Font.BOLD, 200);
    Font normalFont = new Font("Helvetica", Font.BOLD, 36);
    JButton startButton, loadButton, quitButton;


    public static void main(String[] args) {
        new WindowChess();
    }

    public WindowChess(){

        window = new JFrame("EL CHESS"); // Assigning a new JFrame
        window.setResizable(false);
        window.setSize(800,800); // The dimensions of the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When you click the x button at the top right the window closes
        window.getContentPane().setBackground(Color.white); // setting background color of the window as black
        window.setLayout(null); // so we can use our own custom layout
        window.setVisible(true); // makes the window appear
        container = window.getContentPane();

        // Making the title name appear
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(0, 100, 800, 300);
        titleNamePanel.setBackground(Color.white);
        titleNameLabel = new JLabel("CHESS");
        titleNameLabel.setForeground(Color.black);
        titleNameLabel.setFont(titleFont); // Setting the font
        titleNamePanel.add(titleNameLabel);

        // Adding a New Game button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200,80);
        startButtonPanel.setBackground(Color.white);
        startButton = new JButton("New Game ");
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButtonPanel.add(startButton);

        // Adding a Load Game button
        loadGamePanel = new JPanel();
        loadGamePanel.setBounds(300, 480, 200,80);
        loadGamePanel.setBackground(Color.white);
        loadButton = new JButton("Load Game");
        loadButton.setBackground(Color.white);
        loadButton.setForeground(Color.black);
        loadButton.setFont(normalFont);
        loadGamePanel.add(loadButton);

        // Adding a Quit button
        quitGamePanel = new JPanel();
        quitGamePanel.setBounds(300, 560, 200,80);
        quitGamePanel.setBackground(Color.white);
        quitButton = new JButton("  Quit   ");
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFont(normalFont);
        quitGamePanel.add(quitButton);


        //Adding all panels to the container
        container.add(titleNamePanel);
        container.add(startButtonPanel);
        container.add(loadGamePanel);
        container.add(quitGamePanel);

    }


}
