package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame
{

    private Container contents;

    //Components:
    private JButton[][] tiles = new JButton[8][8];

    //Colors:
    private Color colorBlack = Color.black;

    // Current position:
    private int row = 7;
    private int col = 1;

    // Images
    //private ImageIcon dKing = new ImageIcon("wKing.png");
    private ImageIcon wPawn = new ImageIcon("src/Icons/wP.png");
    private ImageIcon wRook = new ImageIcon("src/Icons/wR.png");
    private ImageIcon wKnight = new ImageIcon("src/Icons/wN.png");
    private ImageIcon wBishop = new ImageIcon("src/Icons/wB.png");
    private ImageIcon wQueen = new ImageIcon("src/Icons/wQ.png");
    private ImageIcon wKing = new ImageIcon("src/Icons/wK.png");

    private ImageIcon dPawn = new ImageIcon("src/Icons/dP.png");
    private ImageIcon dRook = new ImageIcon("src/Icons/dR.png");
    private ImageIcon dKnight = new ImageIcon("src/Icons/dN.png");
    private ImageIcon dBishop = new ImageIcon("src/Icons/dB.png");
    private ImageIcon dQueen = new ImageIcon("src/Icons/dQ.png");
    private ImageIcon dKing = new ImageIcon("src/Icons/dK.png");



    public Window(){
        super("El Chess");

        contents = getContentPane();
        contents.setLayout(new GridLayout(8,8));

        //Create event handlers:
        ButtonHandler buttonHandler = new ButtonHandler();

        // Create and add board components:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(((i+j) % 2 != 0) ? Color.gray : Color.white);
                contents.add(tiles[i][j]);
                tiles[i][j].addActionListener(buttonHandler);
            }
        }
        // Where we set the Icon of each button
        tiles[0][0].setIcon(dRook);
        tiles[0][1].setIcon(dKnight);
        tiles[0][2].setIcon(dBishop);
        tiles[0][3].setIcon(dQueen);
        tiles[0][4].setIcon(dKing);
        tiles[0][5].setIcon(dBishop);
        tiles[0][6].setIcon(dKnight);
        tiles[0][7].setIcon(dRook);

        tiles[1][0].setIcon(dPawn);
        tiles[1][1].setIcon(dPawn);
        tiles[1][2].setIcon(dPawn);
        tiles[1][3].setIcon(dPawn);
        tiles[1][4].setIcon(dPawn);
        tiles[1][5].setIcon(dPawn);
        tiles[1][6].setIcon(dPawn);
        tiles[1][7].setIcon(dPawn);

        tiles[7][0].setIcon(wRook);
        tiles[7][1].setIcon(wKnight);
        tiles[7][2].setIcon(wBishop);
        tiles[7][3].setIcon(wQueen);
        tiles[7][4].setIcon(wKing);
        tiles[7][5].setIcon(wBishop);
        tiles[7][6].setIcon(wKnight);
        tiles[7][7].setIcon(wRook);

        tiles[6][0].setIcon(wPawn);
        tiles[6][1].setIcon(wPawn);
        tiles[6][2].setIcon(wPawn);
        tiles[6][3].setIcon(wPawn);
        tiles[6][4].setIcon(wPawn);
        tiles[6][5].setIcon(wPawn);
        tiles[6][6].setIcon(wPawn);
        tiles[6][7].setIcon(wPawn);



        // Size and display window
        setSize(800, 800);
        setResizable(false);
        setLocationRelativeTo(null); // Centers window
        setVisible(true);
    }


    private boolean isValidMove(int i, int j){
        return false;
    }

    private void processClick(int i, int j){
        if(!isValidMove(i, j)){
            return;
        }
        //tiles[i][j].setIcon(dKing);
        row = i;
        col = j;
    }


    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    if(source == tiles[i][j])
                    {
                        processClick(i,j);
                        return;
                    }
                }
            }
        }
    }



}
