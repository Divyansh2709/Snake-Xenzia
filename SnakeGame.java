import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardwidth;
    int boardheight;
    int tilesize = 25;

    Tile snakehead;
    ArrayList<Tile> snakebody;

    Tile food;

    Random random;

    Timer gameloop;

    int velocityX = 1;
    int velocityY = 0;

    boolean gameover = false;

    SnakeApp app;

    String username;

    int score = 0;

    public SnakeGame(int boardwidth, int boardheight, SnakeApp app,
                     String username, int delay) {

        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        this.app = app;
        this.username = username;

        setPreferredSize(new Dimension(boardwidth, boardheight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        random = new Random();

        int startX = (boardwidth / tilesize) / 2;
        int startY = (boardheight / tilesize) / 2;

        snakehead = new Tile(startX, startY);

        snakebody = new ArrayList<>();

        food = new Tile(0, 0);

        placeFood();

        gameloop = new Timer(delay, this);
        gameloop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        g.setColor(Color.red);
        g.fillRect(food.x * tilesize, food.y * tilesize, tilesize, tilesize);

        g.setColor(Color.green);

        g.fillRect(snakehead.x * tilesize, snakehead.y * tilesize, tilesize, tilesize);

        for (Tile part : snakebody) {

            g.fillRect(part.x * tilesize, part.y * tilesize, tilesize, tilesize);

        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("User: " + username, 10, 20);
        g.drawString("Score: " + score, 10, 40);

        if (gameover) {

            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("GAME OVER", 200, boardheight / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Press ENTER to return to menu", 150, boardheight / 2 + 40);

        }
    }

    public void placeFood() {

        int maxX = (boardwidth / tilesize) - 2;
        int maxY = (boardheight / tilesize) - 2;

        boolean valid = false;

        while (!valid) {

            int newX = random.nextInt(maxX - 1) + 1;
            int newY = random.nextInt(maxY - 1) + 1;

            boolean onSnake = false;

            if (snakehead.x == newX && snakehead.y == newY)
                onSnake = true;

            for (Tile part : snakebody) {

                if (part.x == newX && part.y == newY) {
                    onSnake = true;
                    break;
                }

            }

            if (!onSnake) {

                food.x = newX;
                food.y = newY;
                valid = true;

            }
        }
    }

    public boolean collision(Tile a, Tile b) {

        return a.x == b.x && a.y == b.y;

    }

    public void move() {

        if (collision(snakehead, food)) {

            snakebody.add(new Tile(food.x, food.y));

            score += 10;

            placeFood();
        }

        for (int i = snakebody.size() - 1; i >= 0; i--) {

            Tile part = snakebody.get(i);

            if (i == 0) {

                part.x = snakehead.x;
                part.y = snakehead.y;

            } else {

                Tile prev = snakebody.get(i - 1);

                part.x = prev.x;
                part.y = prev.y;
            }
        }

        snakehead.x += velocityX;
        snakehead.y += velocityY;

        if (snakehead.x < 0 || snakehead.x * tilesize >= boardwidth ||
                snakehead.y < 0 || snakehead.y * tilesize >= boardheight) {

            gameover = true;

            gameloop.stop();

            app.getScoreManager().updateHighScore(score);

        }

        for (Tile part : snakebody) {

            if (collision(snakehead, part)) {

                gameover = true;

                gameloop.stop();
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!gameover) {

            move();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gameover && e.getKeyCode() == KeyEvent.VK_ENTER) {

            app.showWelcome();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {

            velocityX = 0;
            velocityY = -1;

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {

            velocityX = 0;
            velocityY = 1;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {

            velocityX = -1;
            velocityY = 0;

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {

            velocityX = 1;
            velocityY = 0;

        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}