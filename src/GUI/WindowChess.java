package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WindowChess {

    JFrame window;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(800,800);

    Container container;
    JPanel titleNamePanel, startButtonPanel, loadGamePanel, quitGamePanel, gamePanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Helvetica", Font.BOLD, 200);
    Font normalFont = new Font("Helvetica", Font.BOLD, 36);
    JButton startButton, loadButton, quitButton;

    NewGameHandler onClickNewGame = new NewGameHandler();
    LoadGameHandler onClickLoadGame = new LoadGameHandler();
    QuitGameHandler onClickQuitGame = new QuitGameHandler();



    public static void main(String[] args) {
        new WindowChess();
    }

    public WindowChess(){

        window = new JFrame("EL CHESS"); // Assigning a new JFrame
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        window.setSize(OUTER_FRAME_DIMENSION); // The dimensions of the window

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
        startButton.addActionListener(onClickNewGame);
        startButtonPanel.add(startButton);

        // Adding a Load Game button
        loadGamePanel = new JPanel();
        loadGamePanel.setBounds(300, 480, 200,80);
        loadGamePanel.setBackground(Color.white);
        loadButton = new JButton("Load Game");
        loadButton.setBackground(Color.white);
        loadButton.setForeground(Color.black);
        loadButton.setFont(normalFont);
        loadButton.addActionListener(onClickLoadGame);
        loadGamePanel.add(loadButton);

        // Adding a Quit button
        quitGamePanel = new JPanel();
        quitGamePanel.setBounds(300, 560, 200,80);
        quitGamePanel.setBackground(Color.white);
        quitButton = new JButton("Quit Game");
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFont(normalFont);
        quitButton.addActionListener(onClickQuitGame);
        quitGamePanel.add(quitButton);


        //Adding all panels to the container
        container.add(titleNamePanel);
        container.add(startButtonPanel);
        container.add(loadGamePanel);
        container.add(quitGamePanel);
        window.setVisible(true);

    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFinalMenu());
        return  menuBar;
    }

    private JMenu createFinalMenu(){
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem titleScreen = new JMenuItem("Title Screen");
        titleScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.setVisible(false);

                titleNamePanel.setVisible(true);
                startButtonPanel.setVisible(true);
                loadGamePanel.setVisible(true);
                quitGamePanel.setVisible(true);

                window.setVisible(true);
            }
        });



        fileMenu.add(titleScreen);
        return fileMenu;
    }

    // New Game Screen
    public void createGameScreen(){
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        loadGamePanel.setVisible(false);
        quitGamePanel.setVisible(false);

        // Menu Bar
        final JMenuBar menuBar = createMenuBar();
        this.window.setJMenuBar(menuBar);

        gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, 800, 800);
        gamePanel.setBackground(Color.BLUE);
        container.add(gamePanel);
        gamePanel.setVisible(true);
        window.setVisible(true);
    }



    public class NewGameHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            new Window();
            //createGameScreen();
        }

    }

    public class LoadGameHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {

        }

    }

    public class QuitGameHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }


}
