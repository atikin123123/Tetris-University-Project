package tetris;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tetrisBlocks.*;

public class GameArea extends JPanel{

    private int gridRows;
    private int gridColumns;
    private int gridCellSize;
    private Color[][] background;

    private TetrisBlock block;

    private TetrisBlock[] blocks = {new IShape(),
                                    new JShape(),
                                    new LShape(),
                                    new OShape(),
                                    new SShape(),
                                    new TShape(),
                                    new ZShape()};

    public GameArea(int columns) {
        this.setSize(new Dimension(500, 700));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        gridColumns = columns;
        gridCellSize = this.getBounds().width / gridColumns;
        gridRows = this.getBounds().height / gridCellSize;
    }

    public void initBackgroundArray() {
        background = new Color[gridRows][gridColumns];
    }

    public void spawnBlock() {
        Random r = new Random();

        block = blocks[ r.nextInt(blocks.length) ];
        block.spawn(gridColumns);
    }

    public boolean checkBottom() {
        if (block.getBottomEdge() == gridRows) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int col = 0; col < w; col++) {
            for (int row = h - 1; row >= 0; row--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;
                    if (y < 0) break;
                    if (background[y][x] != null) return false;    
                    break;            
                }
            }
        }

        return true;
    }

    private boolean checkLeft() {
        if (block.getLeftEdge() == 0) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null) return false;    
                    break;            
                }
            }
        }
        
        return true;
    }

    private boolean checkRight() {
        if (block.getRightEdge() == gridColumns) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = w - 1; col >= 0; col--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() + 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null) return false;    
                    break;            
                }
            }
        }

        return true;
    }

    public boolean isBlockOutOfBounds() {
        if (block.getY() < 0) {
            block = null;
            return true;
        } else return false;
    }

    public boolean moveBlockDown() {
        if (!checkBottom()) {
            return false;
        }
        block.moveDown();
        repaint();  

        return true;
        }

    public void moveBlockRight() {
        if (block == null) return;
        if (checkRight()) {
            block.moveRight();
            repaint();
        }
    }

    public void moveBlockLeft() {
        if (block == null) return;
        if (checkLeft()) {
            block.moveLeft();
            repaint();
        }
    }

    public void moveBlockUp() {
        if (block == null) return;
        block.moveUp();
        repaint();
    }

    public void dropBlock() {
        if (block == null) return;
        while (checkBottom()) {
            block.moveDown();
        }
        repaint();
    }

    public void rotateBlock() {
        if (block == null) return;
        // if (block.getY() < 0) return;

        // Сохраняем исходное состояние
        int oldX = block.getX();
        int oldY = block.getY();
        int[][] oldShape = block.getShape(); 

        block.rotate();

        if (block.getLeftEdge() < 0) block.setX(0);
        if (block.getRightEdge() >= gridColumns) block.setX(gridColumns - block.getWidth());
        if (block.getBottomEdge() >= gridRows) block.setY( gridRows - block.getHeight());

        // Проверяем коллизии с другими фигурами
        boolean collision = false;
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getShape()[row][col] != 0 &&
                    background[row + block.getY()][col + block.getX()] != null) {
                    collision = true;
                    break;
                }
            }
            if (collision) break;
        }
    
        // Если есть коллизия - возвращаем исходное состояние
        if (collision) {
            block.setX(oldX);
            block.setY(oldY);
            block.setShape(oldShape); // Предполагая, что есть метод setShape()
        }
    
        repaint();
    }

    public void moveBlockToBackground() {

        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    background[row + yPos][col + xPos] = color;           
                }
            }
        }
    }

    public int checkLines() {
        boolean lineFilled;
        int clearedLines = 0;

        for (int row = gridRows - 1; row >= 0; row--) {
            lineFilled = true;

            for (int col = 0; col < gridColumns; col++ ) {
                if (background[row][col] == null) {
                    lineFilled = false;
                    break;
                }
            }

            if (lineFilled) {
                GameThread.setCounter( GameThread.getCounter() + 1 );

                clearedLines++;

                clearLine(row);
                shiftLineDown(row);
                clearLine(0);

                row++;

                repaint();
            }
        }
        return clearedLines * 100;
    }

    public void clearLine(int row) {
        for (int i = 0; i < gridColumns; i++) {
            background[row][i] = null;
        }
    }

    public void shiftLineDown(int row) {
        for (int r = row; r > 0; r--) {
            for (int col = 0; col < gridColumns; col++) {
                background[r][col] = background[r - 1][col];
            }
        }
    }

    private void drawBlock(Graphics g) {
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getShape()[row][col] == 1) {

                    int x = (block.getX() + col) * gridCellSize;
                    int y = (block.getY() + row) * gridCellSize;

                    drawGridSquare(g, block.getColor(), x, y);
                }
            }
        }
    }

    private void drawGrid(Graphics g) {
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {

            int x = col * gridCellSize;
            int y = row * gridCellSize;

            g.setColor(Color.GRAY);
            g.drawRect(x, y, gridCellSize, gridCellSize);

            }
        }
    }

    private void drawBackground(Graphics g) {
        Color color;

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                color = background[row][col];

                if (color != null) {
                    int x = col * gridCellSize;
                    int y = row * gridCellSize;

                    drawGridSquare(g, color, x, y);
                }
            }
        }
    }

    private void drawGridSquare(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawBackground(g);
        drawBlock(g);
    }
}