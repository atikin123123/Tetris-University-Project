import javax.swing.*;
import java.awt.Color;
import helper_classes.*;

public class WindowBuilder {
  public static void main(String[] args) {

     JFrame frame = new JFrame("My Awesome Window");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(985, 531);
     JPanel panel = new JPanel();
     panel.setLayout(null);
     panel.setBackground(Color.decode("#eeeeee"));

     JLabel gameName = new JLabel("Tetris");
     gameName.setBounds(323, -7, 604, 152);
     gameName.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 100));
     gameName.setForeground(Color.decode("#1b1b1b"));
     panel.add(gameName);

     JButton btnStartGame = new JButton("Start Game");
     btnStartGame.setBounds(368, 143, 106, 30);
     btnStartGame.setBackground(Color.decode("#ffffff"));
     btnStartGame.setForeground(Color.decode("#1b1b1b"));
     btnStartGame.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
     btnStartGame.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
     btnStartGame.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(btnStartGame, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
     panel.add(btnStartGame);

     JButton btnLeaderboard = new JButton("Leaderboard");
     btnLeaderboard.setBounds(371, 205, 106, 30);
     btnLeaderboard.setBackground(Color.decode("#ffffff"));
     btnLeaderboard.setForeground(Color.decode("#1b1b1b"));
     btnLeaderboard.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
     btnLeaderboard.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
     btnLeaderboard.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(btnLeaderboard, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
     panel.add(btnLeaderboard);

     JButton btnOptions = new JButton("Options");
     btnOptions.setBounds(375, 257, 106, 30);
     btnOptions.setBackground(Color.decode("#ffffff"));
     btnOptions.setForeground(Color.decode("#1b1b1b"));
     btnOptions.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
     btnOptions.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
     btnOptions.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(btnOptions, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
     panel.add(btnOptions);

     JButton btnQuit = new JButton("Quit");
     btnQuit.setBounds(373, 308, 106, 30);
     btnQuit.setBackground(Color.decode("#ffffff"));
     btnQuit.setForeground(Color.decode("#1b1b1b"));
     btnQuit.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
     btnQuit.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
     btnQuit.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(btnQuit, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
     panel.add(btnQuit);

     frame.add(panel);
     frame.setVisible(true);

  }
}