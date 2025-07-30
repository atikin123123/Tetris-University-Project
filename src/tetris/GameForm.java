package tetris;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

public class GameForm extends JFrame{
    private int x = 1024;
    private int y = 768;

    private GameArea ga;
    private GameThread gt;

    private JLabel scoreDisplay;
    private JLabel levelDisplay;

    private JButton btnMainMenu;


    public GameForm() {

        ga = new GameArea(10);

        scoreDisplay = new JLabel("Score: 0");
        levelDisplay = new JLabel("Level: 1");

        btnMainMenu = new JButton("Main Menu");

        GFGUI.initGUI(x, y, this, ga, scoreDisplay, levelDisplay, btnMainMenu);

        setLocationRelativeTo(null);

        initControls();
    }

    public void updateScore(int score) {
        scoreDisplay.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelDisplay.setText("Level: " + level);
    }


    public void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("SPACE"), "space");
        im.put(KeyStroke.getKeyStroke("released ALT"), "alt");

        am.put("alt", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.dropBlock();
            }
        });

        am.put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.rotateBlock();
            }
        });

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockLeft();
            }
        });
        
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockUp();
            }
        });
        
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ga.moveBlockDown();
            }
        });
    }

    public void startGame() {
        ga.initBackgroundArray();
        gt = new GameThread(ga, this);
        gt.start();
    }

    public void stopGame() {
        gt.interrupt();
    }

    public static void main(String[] args) {
            
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }
}
