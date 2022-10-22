import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Visual extends Application {
        private static final int WIDTH = 800;
        private static final int HEIGHT = WIDTH;
        private static final int ROWS = 15;
        private static final int COLUMNS = ROWS;
        private static final int SQUARE_SIZE = WIDTH / ROWS;
        private static final String[] FOODS_IMAGE = new String[]{"/img/monetka.png"};
        private static final String[] STONE_IMAGE = new String[]{"/img/stone.png"};
        private static final String[] STONE2_IMAGE = new String[]{"/img/stone2.png"};
        private static final String[] STONE3_IMAGE = new String[]{"/img/stone3.png"};
        private static final String[] STONE4_IMAGE = new String[]{"/img/stone4.png"};
        private static final String[] STONE5_IMAGE = new String[]{"/img/stone5.png"};

        private static final int RIGHT = 0;
        private static final int LEFT = 1;
        private static final int UP = 2;
        private static final int DOWN = 3;

        private GraphicsContext gc;
        private List<Point> playerBody = new ArrayList();
        private Point playerHead;
        private Image foodImage;
        private int foodX;
        private int foodY;
        private Image stoneImage;
        private Image stoneImage2;
        private Image stoneImage3;
        private Image stoneImage4;
        private Image stoneImage5;
        private int stoneX;
        private int stoneY;
        private int stoneX2;
        private int stoneY2;
        private int stoneX3;
        private int stoneY3;
        private int stoneX4;
        private int stoneY4;
        private int stoneX5;
        private int stoneY5;
        private boolean gameOver;
        private boolean gameWin;
        private int currentDirection;
        private int score = 0;

        @Override
        public void start(Stage primaryStage) throws Exception {
                primaryStage.setTitle("ИГРА");
                Group root = new Group();
                Canvas canvas = new Canvas(WIDTH, HEIGHT);
                root.getChildren().add(canvas);
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
                gc = canvas.getGraphicsContext2D();

                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                                KeyCode code = event.getCode();
                                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                                        if (currentDirection != LEFT) {
                                                currentDirection = RIGHT;
                                        }
                                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                                        if (currentDirection != RIGHT) {
                                                currentDirection = LEFT;
                                        }
                                } else if (code == KeyCode.UP || code == KeyCode.W) {
                                        if (currentDirection != DOWN) {
                                                currentDirection = UP;
                                        }
                                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                                        if (currentDirection != UP) {
                                                currentDirection = DOWN;
                                        }
                                }
                        }
                });



                playerBody.add(new Point(5, ROWS / 2));

                playerHead = playerBody.get(0);
                generateFood();
                generateStone();
                generateStone2();
                generateStone3();
                generateStone4();
                generateStone5();

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
        }

        private void run(GraphicsContext gc) {
                if (gameOver) {
                        gc.setFill(Color.RED);
                        gc.setFont(new Font("Digital-7", 70));
                        gc.fillText("Конец игры", WIDTH / 3.7, HEIGHT / 2);
                        return;
                }
                drawBackground(gc);
                drawFood(gc);
                drawStone(gc);
                drawStone2(gc);
                drawStone3(gc);
                drawStone4(gc);
                drawStone5(gc);

                drawPlayer(gc);
                drawScore();

                if (gameWin) {
                        gc.setFill(Color.GREEN);
                        gc.setFont(new Font("Digital-7", 70));
                        gc.fillText("Ура, победа, ура!!!", WIDTH / 7.45, HEIGHT / 2);
                        return;
                }



                switch (currentDirection) {
                        case RIGHT:
                                moveRight();
                                break;
                        case LEFT:
                                moveLeft();
                                break;
                        case UP:
                                moveUp();
                                break;
                        case DOWN:
                                moveDown();
                                break;
                }

                gameOver();
                gameWin();
                eatFood();
        }

        private void drawBackground(GraphicsContext gc) {
                for (int i = 0; i < ROWS; i++) {
                        for (int j = 0; j < COLUMNS; j++) {
                                if ((i + j) % 2 == 0) {
                                        gc.setFill(Color.web("E0EBCC"));
                                } else {
                                        gc.setFill(Color.web("B5BF9F"));
                                }
                                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                        }
                }
        }

        private void generateFood() {
                start:
                while (true) {
                        foodX = (int) (Math.random() * ROWS);
                        foodY = (int) (Math.random() * COLUMNS);

                        for (Point player : playerBody) {
                                if (player.getX() == foodX && player.getY() == foodY) {
                                        continue start;
                                }
                        }
                        foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
                        break;
                }
        }

        private void drawFood(GraphicsContext gc) {
                gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

        //попытка сделать генерацию препятствий   |  короче препятствия работают но появляются в единственном виде рандомно по карте так что исправь эту хуйню

        private void generateStone() {
                for (int i = 1; i < 10; i++) {
                        start:
                        while (true) {
                                stoneX = (int) (Math.random() * ROWS);
                                stoneY = (int) (Math.random() * COLUMNS);


                                for (Point player : playerBody) {
                                        if (player.getX() == stoneX && player.getY() == stoneY) {
                                                gameOver();
                                        }
                                }
                                stoneImage = new Image(STONE_IMAGE[(int) (Math.random() * STONE_IMAGE.length)]);
                                break;
                        }
                }

        }

        private void drawStone(GraphicsContext gc) {
                for (int i = 1; i < 10; i++) {
                        gc.drawImage(stoneImage, stoneX * SQUARE_SIZE, stoneY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
        }

        private void generateStone2() {
                for (int i = 1; i < 10; i++) {
                        start:
                        while (true) {
                                stoneX2 = (int) (Math.random() * ROWS);
                                stoneY2 = (int) (Math.random() * COLUMNS);


                                for (Point player : playerBody) {
                                        if (player.getX() == stoneX2 && player.getY() == stoneY2) {
                                                gameOver();
                                        }
                                }
                                stoneImage2 = new Image(STONE2_IMAGE[(int) (Math.random() * STONE_IMAGE.length)]);
                                break;
                        }
                }

        }

        private void drawStone2(GraphicsContext gc) {
                for (int i = 1; i < 10; i++) {
                        gc.drawImage(stoneImage2, stoneX2 * SQUARE_SIZE, stoneY2 * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
        }

        private void generateStone3() {
                for (int i = 1; i < 10; i++) {
                        start:
                        while (true) {
                                stoneX3 = (int) (Math.random() * ROWS);
                                stoneY3 = (int) (Math.random() * COLUMNS);


                                for (Point player : playerBody) {
                                        if (player.getX() == stoneX3 && player.getY() == stoneY3) {
                                                gameOver();
                                        }
                                }
                                stoneImage3 = new Image(STONE3_IMAGE[(int) (Math.random() * STONE_IMAGE.length)]);
                                break;
                        }
                }

        }

        private void drawStone3(GraphicsContext gc) {
                for (int i = 1; i < 10; i++) {
                        gc.drawImage(stoneImage3, stoneX3 * SQUARE_SIZE, stoneY3 * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
        }
        private void generateStone4() {
                for (int i = 1; i < 10; i++) {
                        start:
                        while (true) {
                                stoneX4 = (int) (Math.random() * ROWS);
                                stoneY4 = (int) (Math.random() * COLUMNS);


                                for (Point player : playerBody) {
                                        if (player.getX() == stoneX4 && player.getY() == stoneY4) {
                                                gameOver();
                                        }
                                }
                                stoneImage4 = new Image(STONE4_IMAGE[(int) (Math.random() * STONE_IMAGE.length)]);
                                break;
                        }
                }

        }

        private void drawStone4(GraphicsContext gc) {
                for (int i = 1; i < 10; i++) {
                        gc.drawImage(stoneImage4, stoneX4 * SQUARE_SIZE, stoneY4 * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
        }
        private void generateStone5() {
                for (int i = 1; i < 10; i++) {
                        start:
                        while (true) {
                                stoneX5 = (int) (Math.random() * ROWS);
                                stoneY5 = (int) (Math.random() * COLUMNS);


                                for (Point player : playerBody) {
                                        if (player.getX() == stoneX5 && player.getY() == stoneY5) {
                                                gameOver();
                                        }
                                }
                                stoneImage5 = new Image(STONE5_IMAGE[(int) (Math.random() * STONE_IMAGE.length)]);
                                break;
                        }
                }

        }

        private void drawStone5(GraphicsContext gc) {
                for (int i = 1; i < 10; i++) {
                        gc.drawImage(stoneImage5, stoneX5 * SQUARE_SIZE, stoneY5 * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
        }





        private void drawPlayer(GraphicsContext gc) {
                gc.setFill(Color.web("4674E9"));
                gc.fillRoundRect(playerHead.getX() * SQUARE_SIZE, playerHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

                for (int i = 1; i < playerBody.size(); i++) {
                        gc.fillRoundRect(playerBody.get(i).getX() * SQUARE_SIZE, playerBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                                SQUARE_SIZE - 1, 20, 20);
                }
        }

        private void moveRight() {
                playerHead.x++;
        }

        private void moveLeft() {
                playerHead.x--;
        }

        private void moveUp() {
                playerHead.y--;
        }

        private void moveDown() {
                playerHead.y++;
        }

        public void gameOver() {
                if (playerHead.x < 0 || playerHead.y < 0 || playerHead.x * SQUARE_SIZE >= WIDTH || playerHead.y * SQUARE_SIZE >= HEIGHT) {
                        gameOver = true;
                }


                if (playerHead.x == stoneX && playerHead.y == stoneY) {
                        gameOver = true;
                }
                if (playerHead.x == stoneX2 && playerHead.y == stoneY2) {
                        gameOver = true;
                }
                if (playerHead.x == stoneX3 && playerHead.y == stoneY3) {
                        gameOver = true;
                }
                if (playerHead.x == stoneX4 && playerHead.y == stoneY4) {
                        gameOver = true;
                }
                if (playerHead.x == stoneX5 && playerHead.y == stoneY5) {
                        gameOver = true;
                }



        }

        public void gameWin() {
                if (score == 10) {
                        gameWin = true;
                }
        }

        private void eatFood() {
                if (playerHead.getX() == foodX && playerHead.getY() == foodY) {
                        playerBody.add(new Point(-1, -1));
                        generateFood();
                        score += 1;
                }
        }

        private void drawScore() {
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Digital-7", 35));
                gc.fillText("Счет: " + score, 1, 35);
        }


}
