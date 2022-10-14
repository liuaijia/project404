package model.vo;

import frame.GameFrame;
import main.GameController;
import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import model.manager.MoveTypeEnum;
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

import static model.manager.MoveTypeEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class NpcTest {
    Npc npcExample;
    Npc npc1,npc2,npc3;
    @BeforeEach
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        GameController.setTwoPlayer(true);
        ElementManager.getManager().loadMap();
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        List<?> npcList = ElementManager.getManager().getElementList("npc");
        npc1 = (Npc) npcList.get(0); // 右上角
        npc2 = (Npc) npcList.get(1); // 左下角
//        System.out.println(npc1.getX());
//        System.out.println(npc2.getX());
//        System.out.println(npc1.getY());
//        System.out.println(npc2.getY());
        //npc3 = (Npc) npcList.get(2); // 右下角
//        System.out.println(npc3.getX());
//        System.out.println(npc3.getY());
//        int i = 1;
//        int j = 2;
//        int npcNum = 2;//第几个npc，2为npcA，3为npcB,4为npcC
//        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
//        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
//        int w = MapSquare.PIXEL_X;//控制npc显示与一个方格大小一致
//        int h = MapSquare.PIXEL_Y;
//        List <String> data  = gameInfoMap.get("npcA");
//        List<ImageIcon> imageList =
//                new ArrayList<>(ElementLoader.getElementLoader().getNpcImageList(data.get(0)));
//        int imgW = Integer.parseInt(data.get(3));
//        int imgH = Integer.parseInt(data.get(4));
//        npcExample = new Npc(x, y, w, h, imgW, imgH, imageList, npcNum);
//        npcExample.setNpcNum(2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void autoAttack() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int bubuleNum = npcExample.bubbleNum;
        Method method = npcExample.getClass().getDeclaredMethod("autoAttack");
        method.setAccessible(true);
        method.invoke(npcExample);
        assertEquals(bubuleNum, npcExample.bubbleNum);
    }
    @Test
    //未解决
    void judgeForward() {
        MoveTypeEnum m = TOP;
        assertEquals(true, npcExample.judgeForward(m));
    }
    @Test
    //未解决
    void judgeStop() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GameMap gameMap = ElementManager.getManager().getGameMap();
        List<Integer> loc = GameMap.getIJ(npcExample.getX(), npcExample.getY());
        Method method = npcExample.getClass().getDeclaredMethod("judgeStop", List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(npcExample,loc);
        assertEquals(true, result);
    }
    @Test
    void move() throws NoSuchMethodException {
        List<SuperElement> bubbleList = ElementManager.getManager().getElementList("bubble");
        bubbleList.add(Bubble.createBubble(120, 130, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        bubbleList.add(Bubble.createBubble(120, 706, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        MoveTypeEnum[] moveType = new MoveTypeEnum[] {TOP, LEFT, RIGHT, DOWN};
        int i = 0;
        int[] expectX = new int[]{120, 120, 124, 120};
        int[] expectY = new int[]{770, 770, 770, 770};
        npc2.moveType = moveType[i];
        npc2.move();
        assertEquals(expectX[i],npc2.getX());
        assertEquals(expectY[i],npc2.getY() );
    }
    @Test
    void crash1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //上下左右四个方向移动
        int [] moveX = new int [] {0, 0, -4, 4};
        int [] moveY = new int [] {-4, 4, 0, 0};
        //预期值
        boolean [] expect = new boolean[]  {false, true, true, false};
        for(int i = 0;i < 4;i++){
            Method method = npc1.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
            method.setAccessible(true);
            int tx = npc1.getX() + moveX[i];
            int ty = npc1.getY() + moveY[i];
            boolean result1 = (boolean) method.invoke(npc1, tx,ty,ElementManager.getManager().getElementList("obstacle"));
            boolean result2 = (boolean) method.invoke(npc1, tx,ty,ElementManager.getManager().getElementList("fragility"));
            assertEquals(expect[i], result1 && result2);
        }
    }
    @Test
    void crash2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //上下左右四个方向移动
        int [] moveX = new int [] {0, 0, -4, 4};
        int [] moveY = new int [] {-4, 4, 0, 0};
        //预期值
        boolean [] expect = new boolean[]  {true, false, false, true};
        for(int i = 0;i < 4;i++) {
            Method method = npc2.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
            method.setAccessible(true);
            int tx = npc2.getX() + moveX[i];
            int ty = npc2.getY() + moveY[i];
            boolean result1 = (boolean) method.invoke(npc2, tx, ty, ElementManager.getManager().getElementList("obstacle"));
            boolean result2 = (boolean) method.invoke(npc2, tx, ty, ElementManager.getManager().getElementList("fragility"));
            assertEquals(expect[i], result1 && result2);
        }
    }
    @Test
    void crash3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        npc3.setX(1016);
        npc3.setY(706);
        //上下左右四个方向移动
        int [] moveX = new int [] {0, 0, -4, 4};
        int [] moveY = new int [] {-4, 4, 0, 0};
        //预期值
        boolean [] expect = new boolean[]  {false, true, false, false};
        for(int i = 0;i < 4;i++) {
            Method method = npc3.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
            method.setAccessible(true);
            int tx = npc3.getX() + moveX[i];
            int ty = npc3.getY() + moveY[i];
            boolean result1 = (boolean) method.invoke(npc3, tx, ty, ElementManager.getManager().getElementList("obstacle"));
            boolean result2 = (boolean) method.invoke(npc3, tx, ty, ElementManager.getManager().getElementList("fragility"));
            assertEquals(expect[i], result1 && result2);
        }
    }
    @Test
    void bubbleCrashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<SuperElement> bubbleList = ElementManager.getManager().getElementList("bubble");
        bubbleList.add(Bubble.createBubble(120, 128, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        bubbleList.add(Bubble.createBubble(120, 706, ElementLoader.getElementLoader().getGameInfoMap().get("bubble"),2,1));
        int tx1 = npc1.getX();
        int ty1 = npc1.getY() + 4;
        int tx2 = npc2.getX();
        int ty2 = npc2.getY() - 4;
        Method method1 = npc1.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method1.setAccessible(true);
        boolean result1 = (boolean) method1.invoke(npc1, tx1,ty1,ElementManager.getManager().getElementList("bubble"));
        assertEquals(true, result1);
        Method method2 = npc2.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method2.setAccessible(true);
        boolean result2 = (boolean) method2.invoke(npc2, tx2,ty2,ElementManager.getManager().getElementList("bubble"));
        assertEquals(false, result2);
    }
    @Test
    void addBubble() { //未解决
        int a = npcExample.bubbleNum;
        npcExample.addBubble();
        assertEquals(a, npcExample.bubbleNum);
    }

    @Test
    void getMoveX() throws NoSuchFieldException, IllegalAccessException {
        Field field = npcExample.getClass().getDeclaredField("moveX");
        field.setAccessible(true);
        int result = (int) field.get(npcExample);
        assertEquals(result, npcExample.getMoveX());
    }

    @Test
    void setMoveX() {
        npcExample.setMoveX(1);
        assertEquals(1, npcExample.getMoveX());
    }

    @Test
    void getNpcNum() throws NoSuchFieldException, IllegalAccessException {
        Field field = npcExample.getClass().getDeclaredField("npcNum");
        field.setAccessible(true);
        int result = (int) field.get(npcExample);
        assertEquals(result, npcExample.getNpcNum());
    }

    @Test
    void setNpcNum() {
        npcExample.setNpcNum(3);
        assertEquals(3, npcExample.getNpcNum());
    }

    @Test
    void getImgW() throws NoSuchFieldException, IllegalAccessException {
        Field field = npcExample.getClass().getDeclaredField("imgW");
        field.setAccessible(true);
        int result = (int) field.get(npcExample);
        assertEquals(result, npcExample.getImgW());
    }

    @Test
    void setImgW() {
        npcExample.setImgW(111);
        assertEquals(111, npcExample.getImgW());
    }

    @Test
    void getImgH() throws NoSuchFieldException, IllegalAccessException {
        Field field = npcExample.getClass().getDeclaredField("imgH");
        field.setAccessible(true);
        int result = (int) field.get(npcExample);
        assertEquals(result, npcExample.getImgH());
    }

    @Test
    void setImgH() {
        npcExample.setImgH(111);
        assertEquals(111, npcExample.getImgH());
    }

    @Test
    void test(){

    }
}