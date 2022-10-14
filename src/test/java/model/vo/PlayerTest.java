package model.vo;
import main.GameController;
import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import static model.manager.MoveTypeEnum.*;

import model.manager.MoveTypeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
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

    Player player1,player2;
    Npc npc1,npc2,npc3;
    List<?> playerList;
    @BeforeEach
    //PIXEL_X=64,PIXEL_Y=64
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        GameController.setTwoPlayer(true);
        ElementManager.getManager().loadMap();
        playerList = ElementManager.getManager().getElementList("player");
        player1 = (Player) playerList.get(0);
        player2 = (Player) playerList.get(1);
//        System.out.println(ElementManager.getManager().getMap());
//        int i = 1;
//        int j = 1;
//        //System.out.println(GameMap.getBiasX()); 0
//        //System.out.println(GameMap.getBiasX()); 0
//        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
//        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
//        int w = MapSquare.PIXEL_X;
//        int h = MapSquare.PIXEL_Y;
//        int playernum = 0;
//        Map<String, ImageIcon> imageMap =
//                ElementLoader.getElementLoader().getImageMap();//获取资源加载器的图片字典
//        List<String> data = new ArrayList<>();
//        data.add("PlayerA");
////        System.out.println(x);
////        System.out.println(y);
//        playerExample = new Player(x, y, w, h, imageMap.get(data.get(0)),playernum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void move() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<SuperElement> bubbleList = ElementManager.getManager().getElementList("bubble");
        bubbleList.add(Bubble.createBubble(120, 130, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        bubbleList.add(Bubble.createBubble(120, 706, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        MoveTypeEnum[] moveType = new MoveTypeEnum[] {TOP, LEFT, RIGHT, DOWN, STOP};
        player1 = (Player) playerList.get(0);
        int i = 4;
        int[] expectX = new int[]{120, 120, 124, 120, 120};
        int[] expectY = new int[]{66, 66, 66, 66, 66};
        player1.moveType = moveType[i];
        player1.move();
        assertEquals(expectX[i],player1.getX());
        assertEquals(expectY[i],player1.getY() );
    }


    @Test
    void updateImage() {
        playerExample.moveType =  LEFT;
        playerExample.setMoveX(23);
        playerExample.updateImage();
        assertEquals(0, playerExample.getMoveX());
        assertEquals(1, playerExample.getMoveY());
    }
    @Test
    //player检测碰撞
    void crashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        player1.setX(185);
        player1.setY(66);
        Method method = player1.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
        method.setAccessible(true);
        //上下左右四个方向移动
        int [] moveX = new int [] {0, 0, -4, 4};
        int [] moveY = new int [] {-4, 4, 0, 0};
        //预期值
        boolean [] expect = new boolean[]  {false, false, true, false};
        for(int i = 0;i < 4;i++){
            int tx = player1.getX() + moveX[i];
            int ty = player1.getY() + moveY[i];
            boolean result1 = (boolean) method.invoke(player1, tx,ty,ElementManager.getManager().getElementList("obstacle"));
            boolean result2 = (boolean) method.invoke(player1, tx,ty,ElementManager.getManager().getElementList("fragility"));
            assertEquals(expect[i], result1 && result2);
        }
    }
    @Test
    void bubbleCrashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<SuperElement> bubbleList = ElementManager.getManager().getElementList("bubble");
        bubbleList.add(Bubble.createBubble(120, 130, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        bubbleList.add(Bubble.createBubble(120, 706, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        int tx1 = player1.getX();
        int ty1 = player1.getY() + 4;
        Method method1 = player1.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method1.setAccessible(true);
        boolean result1 = (boolean) method1.invoke(player1, tx1,ty1,ElementManager.getManager().getElementList("bubble"));
        assertEquals(false, result1);
        int tx2 = player2.getX();
        int ty2 = player2.getY() - 4;
        Method method2 = player2.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method2.setAccessible(true);
        boolean result2 = (boolean) method2.invoke(player2, tx2,ty2,ElementManager.getManager().getElementList("bubble"));
        assertEquals(true, result2);
    }
    @Test
    void addBubble() {
        int a = playerExample.bubbleNum;
        playerExample.addBubble();
        assertEquals(a, playerExample.bubbleNum);
    }


    @Test
    void getImg() throws NoSuchFieldException, IllegalAccessException {
        System.out.println(playerExample.getImg());
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