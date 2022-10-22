import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class generatePoint {

    private static final String[] FOODS_IMAGE = new String[]{"/img/ic_orange.png", "/img/ic_apple.png", "/img/ic_cherry.png",
            "/img/ic_berry.png", "/img/ic_coconut_.png", "/img/ic_peach.png", "/img/ic_watermelon.png", "/img/ic_orange.png",
            "/img/ic_pomegranate.png"};
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;

    public static void generatePoint(){

        List<Point> snakeBody = new ArrayList();
        Image foodImage;
            start:
            while (true) {
                int foodX = (int) (Math.random() * ROWS);
                int foodY = (int) (Math.random() * COLUMNS);

                for (Point snake : snakeBody) {
                    if (snake.getX() == foodX && snake.getY() == foodY) {
                        continue start;
                    }
                }
                foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
                break;
            }
        }
    }

