package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.RowSorter.SortKey; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import helper_classes.CustomFontLoader;
import helper_classes.OnClickEventHelper;
import helper_classes.RoundedBorder;

public class LeaderboardForm extends JFrame {

    DefaultTableModel model;
    JTable table;

    private TableRowSorter<TableModel> sorter;

    private String leaderboardFile = "leaderboard";

    public LeaderboardForm() {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        this.setSize(1024, 768);

        JButton backButton = new JButton("Main Menu");
        backButton.setBounds(10, 10, 100, 30); // Позиция и размеры
        backButton.setBackground(Color.decode("#ffffff"));
        backButton.setForeground(Color.decode("#1b1b1b"));
        backButton.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        backButton.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        backButton.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(backButton, Color.decode("#c2c2c2"), Color.decode("#ffffff"));

        JButton clearButton = new JButton("Clear Leaderboard");
        clearButton.setBounds(362, 620, 300, 40); // Центрированная под таблицей
        clearButton.setBackground(Color.decode("#ffffff"));
        clearButton.setForeground(Color.decode("#1b1b1b"));
        clearButton.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
        clearButton.setBorder(new RoundedBorder(4, Color.decode("#626262"), 1));
        clearButton.setFocusPainted(false);
        OnClickEventHelper.setOnClickColor(clearButton, Color.decode("#c2c2c2"), Color.decode("#ffffff"));

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                saveLeaderboard();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Tetris.closeLeaderboard();
                Tetris.showStartupForm();
            }
        });
        
        // Создаем модель таблицы с заголовками
        String[] columns = {"Name", "Score"};
        Object[][] data = {};
        
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        

        // Настраиваем таблицу
        table.setEnabled(false);
        table.setRowHeight(30); // Высота строк
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Размещаем таблицу в центре с прокруткой
        JScrollPane scrollPane = new JScrollPane(table);
        
        this.setLayout(null); 
        
        this.add(backButton);
        this.add(clearButton);
        scrollPane.setBounds(100, 100, 800, 500); 
        this.add(scrollPane);

        setLocationRelativeTo(null);

        initTableData();

        initTableSorter();
    }
    
    public void addPlayer(String playerName, int score) {
        model.addRow(new Object[] {playerName, score});

        sorter.sort();

        saveLeaderboard();

        this.setVisible(true);
    }

    public void initTableData() {
        Vector ci = new Vector<String>();
        ci.add("Player");
        ci.add("Score");

        model = (DefaultTableModel) table.getModel();

        try {
            FileInputStream fs = new FileInputStream("leaderboard");
            ObjectInputStream os = new ObjectInputStream(fs);

            model.setDataVector( (Vector<Vector>) os.readObject(), ci);

            os.close();
            fs.close();
        }catch(Exception e) {}
    }

    public void saveLeaderboard() {
        
        try {
            FileOutputStream fs = new FileOutputStream(leaderboardFile);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(model.getDataVector());

            os.close();
            fs.close();
        } catch(Exception e) {}
    }

    public void clearLeaderBoard() {
        try {
            FileOutputStream fs = new FileOutputStream(leaderboardFile);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject("");

            os.close();
            fs.close();
        } catch(Exception e) {}
    }

    public void initTableSorter() {
        sorter = new TableRowSorter<>(model);
        sorter.setComparator(1, Comparator.naturalOrder());
        table.setRowSorter(sorter);

        ArrayList<SortKey> keys = new ArrayList<>();
        keys.add( new SortKey(1, SortOrder.DESCENDING));

        sorter.setSortKeys(keys);
    }
}
