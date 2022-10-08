package model.vo;

import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleExplodeTest {
    BubbleExplode bubbleExplodeExample;
    @BeforeEach
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        int x = 100;
        int y = 200;
        int power = 2;
        int playerNum = 0;
        bubbleExplodeExample = new BubbleExplode(x, y, MapSquare.PIXEL_X, MapSquare.PIXEL_Y, power,playerNum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createExplode() {
    }

    @Test
    void showElement() {
    }

    @Test
    void move() {
    }

    @Test
    void destroy() {
    }

    @Test
    void crash() {
        List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
        assertEquals(true, bubbleExplodeExample.crash(playerList.get(0)));
    }
    @Test
    void getMoveStep() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int mapI = GameMap.getIJ(bubbleExplodeExample.getX(), bubbleExplodeExample.getY()).get(0);
        int mapJ = GameMap.getIJ(bubbleExplodeExample.getX(), bubbleExplodeExample.getY()).get(1);
        String direction ="down";
        Method method = bubbleExplodeExample.getClass().getDeclaredMethod("getMoveStep", int.class, int.class, String.class);
        method.setAccessible(true);
        int result = (int) method.invoke(bubbleExplodeExample, mapI,mapJ,direction);
        assertEquals(0, result);
    }
    @Test
    void setMoveStep() {
        assertEquals(0, bubbleExplodeExample.getUp());
        assertEquals(0, bubbleExplodeExample.getDown());
        assertEquals(0, bubbleExplodeExample.getLeft());
        assertEquals(0, bubbleExplodeExample.getRight());
    }

    @Test
    void getUp() throws NoSuchFieldException, IllegalAccessException {
        Field field = bubbleExplodeExample.getClass().getDeclaredField("up");
        field.setAccessible(true);
        int result = (int) field.get(bubbleExplodeExample);
        assertEquals(result, bubbleExplodeExample.getUp());
    }

    @Test
    void SetUp() {
        bubbleExplodeExample.setUp(1);
        assertEquals(1, bubbleExplodeExample.getUp());
    }

    @Test
    void getDown() throws NoSuchFieldException, IllegalAccessException {
        Field field = bubbleExplodeExample.getClass().getDeclaredField("down");
        field.setAccessible(true);
        int result = (int) field.get(bubbleExplodeExample);
        assertEquals(result, bubbleExplodeExample.getDown());
    }

    @Test
    void setDown() {
        bubbleExplodeExample.setDown(2);
        assertEquals(2, bubbleExplodeExample.getDown());
    }

    @Test
    void getLeft() throws NoSuchFieldException, IllegalAccessException {
        Field field = bubbleExplodeExample.getClass().getDeclaredField("left");
        field.setAccessible(true);
        int result = (int) field.get(bubbleExplodeExample);
        assertEquals(result, bubbleExplodeExample.getLeft());
    }

    @Test
    void setLeft() {
        bubbleExplodeExample.setLeft(3);
        assertEquals(3, bubbleExplodeExample.getLeft());
    }

    @Test
    void getRight() throws NoSuchFieldException, IllegalAccessException {
        Field field = bubbleExplodeExample.getClass().getDeclaredField("right");
        field.setAccessible(true);
        int result = (int) field.get(bubbleExplodeExample);
        assertEquals(result, bubbleExplodeExample.getRight());
    }

    @Test
    void setRight() {
        bubbleExplodeExample.setRight(4);
        assertEquals(4, bubbleExplodeExample.getRight());
    }

    @Test
    void getPlayerNum() throws IllegalAccessException, NoSuchFieldException {
        Field field = bubbleExplodeExample.getClass().getDeclaredField("playerNum");
        field.setAccessible(true);
        int result = (int) field.get(bubbleExplodeExample);
        assertEquals(result, bubbleExplodeExample.getPlayerNum());
    }

    @Test
    void setPlayerNum() {
        bubbleExplodeExample.setPlayerNum(1);
        assertEquals(1, bubbleExplodeExample.getPlayerNum());
    }
}