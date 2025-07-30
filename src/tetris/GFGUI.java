package tetris;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import helper_classes.CustomFontLoader;
import helper_classes.OnClickEventHelper;
import helper_classes.RoundedBorder;

public class GFGUI {

    public static void initGUI(int x, int y, GameForm gf, GameArea ga, JLabel levelDisplay, JLabel scoreDisplay, JButton btnMainMenu ) {

        gf.setTitle("Tetris");
        gf.setSize(x, y);
        gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gf.setBackground(Color.decode("#f4c064"));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        gf.add(leftPanel, BorderLayout.CENTER);
        leftPanel.add(ga, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // Меняем на BorderLayout для точного позиционирования
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Создаем панель для меток с FlowLayout
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Важно!

        levelDisplay.setFont(new Font("Arial", Font.ITALIC, 20));
        levelDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);

        scoreDisplay.setFont(new Font("Arial", Font.ITALIC, 20));
        scoreDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel info = new JLabel();
        info.setFont(new Font("Arial", Font.ITALIC, 20));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.setText("<html>" +
             "<br>" +
             "Управление:<br>" +
             "← → ↓ - перемещение<br>" +
             "Space - поворот<br>" +
             "Alt   - мгновенное падение" +
             "</html>");


        rightPanel.add(btnMainMenu, BorderLayout.SOUTH);
        btnMainMenu.setBounds(10, 10, 100, 50); // Позиция и размеры
        btnMainMenu.setBackground(Color.decode("#ffffff"));
        btnMainMenu.setForeground(Color.decode("#1b1b1b"));
        btnMainMenu.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        btnMainMenu.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        btnMainMenu.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(btnMainMenu, Color.decode("#c2c2c2"), Color.decode("#ffffff"));
        btnMainMenu.setFocusable(false);

        btnMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Tetris.stopGame();
                Tetris.closeGameForm();
                Tetris.showStartupForm();
            }
        });

        // Добавляем метки с отступами
        labelsPanel.add(levelDisplay);
        labelsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Отступ между метками
        labelsPanel.add(scoreDisplay);
        labelsPanel.add(info);

        // Помещаем панель с метками в верхнюю правую часть
        rightPanel.add(labelsPanel, BorderLayout.NORTH);
        rightPanel.add(Box.createVerticalGlue(), BorderLayout.CENTER); // Прижимаем к верху


        // Разделяем окно на левую и правую части
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, 
            ga,
            rightPanel
        );
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(0); 
        splitPane.setEnabled(false);
        
        gf.add(splitPane, BorderLayout.CENTER);
    }
}