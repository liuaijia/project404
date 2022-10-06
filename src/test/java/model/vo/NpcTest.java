package model.vo;

import frame.GameFrame;
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

import static model.manager.MoveTypeEnum.DOWN;
import static model.manager.MoveTypeEnum.TOP;
import static org.junit.jupiter.api.Assertions.*;

class NpcTest {
    Npc npcExample;
    @BeforeEach
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        int i = 1;
        int j = 2;
        int npcNum = 2;//第几个npc，2为npcA，3为npcB,4为npcC
        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
        int w = MapSquare.PIXEL_X;//控制npc显示与一个方格大小一致
        int h = MapSquare.PIXEL_Y;
        List <String> data  = gameInfoMap.get("npcA");
        List<ImageIcon> imageList =
                new ArrayList<>(ElementLoader.getElementLoader().getNpcImageList(data.get(0)));
        int imgW = Integer.parseInt(data.get(3));
        int imgH = Integer.parseInt(data.get(4));
        npcExample = new Npc(x, y, w, h, imgW, imgH, imageList, npcNum);
        npcExample.setNpcNum(2);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void bubbleCrashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int tx = npcExample.getX();
        int ty = npcExample.getY();
        Method method = npcExample.getClass().getDeclaredMethod("bubbleCrashDetection", int.class, int.class, List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(npcExample, tx,ty,ElementManager.getManager().getElementList("bubble"));
        assertEquals(true, result);
    }
    @Test
    void crashDetection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int tx = npcExample.getX();
        int ty = npcExample.getY();
        Method method = npcExample.getClass().getDeclaredMethod("crashDetection", int.class, int.class, List.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(npcExample, tx,ty,ElementManager.getManager().getElementList("obstacle"));
        assertEquals(true, result);
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
        npcExample.moveType=DOWN;
        npcExample.move();
        assertEquals(128, npcExample.getX());
        assertEquals(128, npcExample.getY());
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
}