package tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import helper_classes.CustomFontLoader;
import helper_classes.OnClickEventHelper;
import helper_classes.RoundedBorder;

public class StartupForm extends JFrame {

    private static String initialLevel;

    public StartupForm() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 728);
        this.setResizable(false);
        this.setTitle("Tetris");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#eeeeee"));
        
        JButton btnStartGame = new JButton("Start Game");
        btnStartGame.setBounds(412, 284, 200, 40);
        btnStartGame.setBackground(Color.decode("#ffffff"));
        btnStartGame.setForeground(Color.decode("#1b1b1b"));
        btnStartGame.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        btnStartGame.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        btnStartGame.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(btnStartGame, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
        panel.add(btnStartGame);
        
        JButton btnLeaderboard = new JButton("Leaderboard");
        btnLeaderboard.setBounds(412, 344, 200, 40);
        btnLeaderboard.setBackground(Color.decode("#ffffff"));
        btnLeaderboard.setForeground(Color.decode("#1b1b1b"));
        btnLeaderboard.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        btnLeaderboard.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        btnLeaderboard.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(btnLeaderboard, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
        panel.add(btnLeaderboard);
        
        JButton btnQuit = new JButton("Quit");
        btnQuit.setBounds(412, 404, 200, 40);
        btnQuit.setBackground(Color.decode("#ffffff"));
        btnQuit.setForeground(Color.decode("#1b1b1b"));
        btnQuit.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        btnQuit.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        btnQuit.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(btnQuit, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
        panel.add(btnQuit);
        
        this.add(panel);

        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialLevel = JOptionPane.showInputDialog("Please enter level \nyou want to start from (1-18)");
                Tetris.start();
            }
        });

        btnLeaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tetris.closeStartupForm();
                Tetris.showLeaderboard();
            }
        });

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
    }


    public static String getInitialLevel() {
        return initialLevel;
    }
    
    public static void main(String[] args) {
            
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartupForm().setVisible(true);
            }
        });
    }
}
