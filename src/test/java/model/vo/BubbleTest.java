package model.vo;

import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BubbleTest {

    private Bubble bubbleExample;

    @BeforeEach
    void setUp() throws IOException {
        System.out.println("@BeforeEach，测试开始");

        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();

        int i = 1;
        int j = 2;
//        int imgW = Integer.parseInt(list.get(1));
//        int imgH = Integer.parseInt(list.get(2));
        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
        int w = MapSquare.PIXEL_X;
        int h = MapSquare.PIXEL_Y;
        Map<String, ImageIcon> imageMap =
                ElementLoader.getElementLoader().getImageMap();//获取资源加载器的图片字典
        List<String> data = new ArrayList<>();
        List<String> list = ElementLoader.getElementLoader().getGameInfoMap().get("bubble");
        int imgW = Integer.parseInt(list.get(1));
        int imgH = Integer.parseInt(list.get(2));
        int playernum = 1;
        int power=1;
        data.add("bubble");
        System.out.println(imageMap);
//        bubbleExample=new Bubble(x,y,w,h,imageMap.get(data.get(0)),imgW,imgH,playernum,power);
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    void createBubble() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        int x = bubbleExample.getX();
//        int y = bubbleExample.getY();
//        int playerNum=1;
//        int power=1;
//        Method method = bubbleExample.getClass().getDeclaredMethod("createBubble", int.class, int.class, List.class, int.class, int.class);
//        method.setAccessible(true);
//        Bubble result = (Bubble) method.invoke(bubbleExample, x,y, ElementManager.getManager().getElementList("bubble"), playerNum, power);
//        assertEquals(true,result);
    }

//    @Test
//    void showElement() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void updateImage() {
//    }
//
//    @Test
//    void move() {
//    }
//
//    @Test
//    void destroy() {
//    }
//
//    @Test
//    void getImg() {
//    }
//
//    @Test
//    void setImg() {
//    }
//
//    @Test
//    void getMoveX() {
//    }
//
//    @Test
//    void setMoveX() {
//    }
//
//    @Test
//    void getPlayerNum() {
//    }
//
//    @Test
//    void getPower() {
//    }
}