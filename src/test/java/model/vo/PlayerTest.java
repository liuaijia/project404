package model.vo;
import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import static model.manager.MoveTypeEnum.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player playerExample;



    @BeforeEach
    //PIXEL_X=64,PIXEL_Y=64
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        int i = 1;
        int j = 2;
        //System.out.println(GameMap.getBiasX()); 0
        //System.out.println(GameMap.getBiasX()); 0
        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
        int w = MapSquare.PIXEL_X;
        int h = MapSquare.PIXEL_Y;
        int playernum = 1;
        Map<String, ImageIcon> imageMap =
                ElementLoader.getElementLoader().getImageMap();//获取资源加载器的图片字典
        List<String> data = new ArrayList<>();
        data.add("player2");
        playerExample = new Player(x, y, w, h, imageMap.get(data.get(0)),playernum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void move() {
        playerExample.moveType=DOWN;
        playerExample.move();
        assertEquals(128, playerExample.getX());
        assertEquals(128, playerExample.getY());
    }


    @Test
    void updateImage() throws IllegalAccessException, NoSuchFieldException {
        playerExample.moveType =  LEFT;
        playerExample.setMoveX(23);
        playerExample.updateImage();
        assertEquals(0, playerExample.getMoveX());
        assertEquals(1, playerExample.getMoveY());
    }
    @Test
    void bubbleCrashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int tx = playerExample.getX();
        int ty = playerExample.getY();
        Method method = playerExample.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(playerExample, tx,ty,ElementManager.getManager().getElementList("bubble"));
        assertEquals(true, result);

    }
    @Test
    void crashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int tx = playerExample.getX();
        int ty = playerExample.getY();
        Method method = playerExample.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(playerExample, tx,ty,ElementManager.getManager().getElementList("obstacle"));
        assertEquals(true, result);

    }
    @Test
    void addBubble() {
        int a = playerExample.bubbleNum;
        playerExample.addBubble();
        assertEquals(a, playerExample.bubbleNum);
    }


    @Test
    void getImg() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("img");
        field.setAccessible(true);
        ImageIcon result = (ImageIcon) field.get(playerExample);
        assertEquals(result, playerExample.getImg());
    }

    @Test
    void setImg()  {
         ImageIcon icon = new ImageIcon("../main/resources/img/player/player1,png");
         playerExample.setImg(icon);
         assertEquals(icon,playerExample.getImg());
    }

    @Test
    void getMoveX() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("moveX");
        field.setAccessible(true);
        int result = (int) field.get(playerExample);
        assertEquals(result, playerExample.getMoveX());
    }

    @Test
    void setMoveX() {
        playerExample.setMoveX(1);
        assertEquals(1, playerExample.getMoveX());
    }

    @Test
    void getMoveY() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("moveY");
        field.setAccessible(true);
        int result = (int) field.get(playerExample);
        assertEquals(result, playerExample.getMoveY());
    }

    @Test
    void setMoveY() {
        playerExample.setMoveY(1);
        assertEquals(1, playerExample.getMoveY());
    }

    @Test
    void isAttack() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("attack");
        field.setAccessible(true);
        boolean result = (boolean) field.get(playerExample);
        assertEquals(result, playerExample.isAttack());
    }

    @Test
    void setAttack() {
        playerExample.setAttack(true);
        assertEquals(true, playerExample.isAttack());
    }

    @Test
    void isKeepAttack() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("keepAttack");
        field.setAccessible(true);
        boolean result = (boolean) field.get(playerExample);
        assertEquals(result, playerExample.isKeepAttack());
    }

    @Test
    void setKeepAttack() {
        playerExample.setKeepAttack(true);
        assertEquals(true, playerExample.isKeepAttack());
    }

    @Test
    void getPlayerNum() throws NoSuchFieldException, IllegalAccessException {
        Field field = playerExample.getClass().getDeclaredField("playerNum");
        field.setAccessible(true);
        int result = (int) field.get(playerExample);
        assertEquals(result, playerExample.getPlayerNum());
    }

    @Test
    void isDead() throws IllegalAccessException, NoSuchFieldException {
        Field field = playerExample.getClass().getSuperclass().getDeclaredField("dead");//访问父类中的protect dead
        field.setAccessible(true);
        boolean result = (boolean) field.get(playerExample);
        assertEquals(result, playerExample.isDead());
    }

    @Test
    void setDead() {
        playerExample.setDead(true);
        assertEquals(true, playerExample.isDead());
    }

}