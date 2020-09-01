package GUI;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class WindowChess {

    JFrame window;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(400,600);

    Container container;
    JPanel titleNamePanel, startButtonPanel, loadGamePanel, howToPlayPanel, quitGamePanel, gamePanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Helvetica", Font.BOLD, 80);
    Font normalFont = new Font("Helvetica", Font.BOLD, 36);
    JButton startButton, loadButton, quitButton, howToPlayButton;

    NewGameHandler onClickNewGame = new NewGameHandler();
    LoadGameHandler onClickLoadGame = new LoadGameHandler();
    HowToPlayHandler onClickHowToPlay = new HowToPlayHandler();
    QuitGameHandler onClickQuitGame = new QuitGameHandler();

    private ImageIcon fileIcon = new ImageIcon("src/Icons/file.png");
    Image image = fileIcon.getImage();
    Image newImg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    ImageIcon finalFileImg = new ImageIcon(newImg);


    public static void main(String[] args) { new WindowChess(); }

    public WindowChess(){

        window = new JFrame("CHESS"); // Assigning a new JFrame
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        window.setSize(OUTER_FRAME_DIMENSION); // The dimensions of the window

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When you click the x button at the top right the window closes
        window.getContentPane().setBackground(Color.white); // setting background color of the window as black
        window.setLayout(null); // so we can use our own custom layout
        window.setVisible(true); // makes the window appear
        window.setLocationRelativeTo(null);
        container = window.getContentPane();

        // Making the title name appear
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(0, 50, 400, 300);
        titleNamePanel.setBackground(Color.white);
        titleNameLabel = new JLabel("CHESS");
        titleNameLabel.setForeground(Color.black);
        titleNameLabel.setFont(titleFont); // Setting the font
        titleNamePanel.add(titleNameLabel);

        // Adding a New Game button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(100, 200, 200,80);
        startButtonPanel.setBackground(Color.white);
        startButton = new JButton("New Game ");
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.addActionListener(onClickNewGame);
        startButtonPanel.add(startButton);

        // Adding a Load Game button
        loadGamePanel = new JPanel();
        loadGamePanel.setBounds(100, 280, 200,80);
        loadGamePanel.setBackground(Color.white);
        loadButton = new JButton("Load Game");
        loadButton.setBackground(Color.white);
        loadButton.setForeground(Color.black);
        loadButton.setFont(normalFont);
        loadButton.addActionListener(onClickLoadGame);
        loadGamePanel.add(loadButton);

        // Adding a How To Play button
        howToPlayPanel = new JPanel();
        howToPlayPanel.setBounds(100, 360, 200,80);
        howToPlayPanel.setBackground(Color.white);
        howToPlayButton = new JButton("Information");
        howToPlayButton.setBackground(Color.white);
        howToPlayButton.setForeground(Color.black);
        howToPlayButton.setFont(normalFont);
        howToPlayButton.addActionListener(onClickHowToPlay);
        howToPlayPanel.add(howToPlayButton);

        // Adding a Quit button
        quitGamePanel = new JPanel();
        quitGamePanel.setBounds(100, 440, 200,80);
        quitGamePanel.setBackground(Color.white);
        quitButton = new JButton("Quit Game");
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFont(normalFont);
        quitButton.addActionListener(onClickQuitGame);
        quitGamePanel.add(quitButton);

        //Adding all panels to the container
        container.add(loadGamePanel);
        container.add(titleNamePanel);
        container.add(startButtonPanel);

        container.add(howToPlayPanel);
        container.add(quitGamePanel);
        window.setVisible(true);
    }


    public static class NewGameHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new Window("", false);
        }
    }

    public class LoadGameHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            File tmpDir = new File("src/SavedGames");

            Object[] options = tmpDir.list();
            if(options.length != 0) {
                String s = (String) JOptionPane.showInputDialog(
                        window,
                        "",
                        "Pick a file, any file.",
                        JOptionPane.PLAIN_MESSAGE,
                        finalFileImg,
                        options, 0);

                if (s != null && s.trim().length() > 0) {
                    new Window(s.replaceAll(".txt", ""), true);
                }
            }else {
                JOptionPane.showMessageDialog(window, "We looked, couldn't find any files.", "Maybe turn it off and on?", 2);
            }
        }

    }

    public static class HowToPlayHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();
            String url = "https://www.wikihow.com/Play-Chess";

            if(os.indexOf("win") >= 0){ // Windows
                try {

                        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }else if(os.indexOf("mac") >= 0){ // Mac
                try {
                        rt.exec("open " + url);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }else if(os.indexOf("nix") >=0 || os.indexOf("nux") >=0){ // Linux
                String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "links", "lynx" };

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    if(i == 0)
                        cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                    else
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // If the first didn't work, try the next browser and so on

                try {
                    rt.exec(new String[] { "sh", "-c", cmd.toString() });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static class QuitGameHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}
