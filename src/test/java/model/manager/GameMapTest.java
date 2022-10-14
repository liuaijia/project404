package model.manager;

import main.GameController;
import model.loader.ElementLoader;
import model.vo.SuperElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

class GameMapTest {

    private GameMap gameMapExample;
    ElementManager elementManagerExample;

    @BeforeEach
    void setUp() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        System.out.println("@BeforeEach，测试开始");

        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();

        //初始化gameMapExample
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        List<String> windowSize = gameInfoMap.get("windowSize");
        gameMapExample = new GameMap(Integer.parseInt(windowSize.get(0)),Integer.parseInt(windowSize.get(1)));

    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    void testSquareType(){
        //有效
        assertEquals(GameMap.SquareType.OBSTACLE,GameMap.SquareType.valueOf('0'));
        assertEquals(GameMap.SquareType.FLOOR,GameMap.SquareType.valueOf('1'));
        assertEquals(GameMap.SquareType.FRAGILITY,GameMap.SquareType.valueOf('2'));
        assertEquals(GameMap.SquareType.ITEM,GameMap.SquareType.valueOf('3'));
        assertEquals(GameMap.SquareType.PLAYER_1,GameMap.SquareType.valueOf('6'));
        assertEquals(GameMap.SquareType.PLAYER_2,GameMap.SquareType.valueOf('7'));
        assertEquals(GameMap.SquareType.NPC,GameMap.SquareType.valueOf('8'));
        assertEquals(GameMap.SquareType.BUBBLE,GameMap.SquareType.valueOf('9'));
        //无效
        assertEquals(null,GameMap.SquareType.valueOf('4'));
    }

    @Test
    void createMap1andFloorandSquareandInitPlayer() {
        //测试单玩家
        //Map:
        gameMapExample.createMap("stage1Map");
        assertEquals(14,GameMap.getMapRows());
        assertEquals(17,GameMap.getMapCols());
        assertEquals(56,GameMap.getBiasX());
        assertEquals(2,GameMap.getBiasY());
        //Floor:
        assertEquals(238,ElementManager.getManager().getElementList("floor").size());
        //Square:
        assertEquals(3, GameController.getNpcNum());
        //initPlayer1:
        assertEquals(true,!ElementManager.getManager().getMap().get("player").isEmpty());
    }

    @Test
    void createMap2andFloorandSquareandInitPlayer() {
        //测试双玩家
        GameController.setTwoPlayer(true);
        //Map:
        gameMapExample.createMap("stage2Map");
        assertEquals(13, GameMap.getMapRows());
        assertEquals(17, GameMap.getMapCols());
        assertEquals(56, GameMap.getBiasX());
        assertEquals(34, GameMap.getBiasY());
        //Floor:
        assertEquals(221, ElementManager.getManager().getElementList("floor").size());
        //Square:
        assertEquals(3, GameController.getNpcNum());
        //initPlayer2:
        assertEquals(true,!ElementManager.getManager().getMap().get("player").isEmpty());
    }

    @Test
    void createMap3andFloorandSquare() {
        //Map:
        gameMapExample.createMap("stage3Map");
        assertEquals(12,GameMap.getMapRows());
        assertEquals(18,GameMap.getMapCols());
        assertEquals(24,GameMap.getBiasX());
        assertEquals(66,GameMap.getBiasY());
        //Floor:
        assertEquals(216, ElementManager.getManager().getElementList("floor").size());
        //Square:
        assertEquals(3, GameController.getNpcNum());
    }

    @Test
    void getBlockSquareType() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取地图，以stage1Map为例
        gameMapExample.createMap("stage2Map");
        //测试双玩家
        GameController.setTwoPlayer(true);
        List<List<String>> mapList = GameMap.getMapList();
        for(int i=0;i<GameMap.getMapRows();i++){
            List<String> row_i=mapList.get(i);
            for(int j=0;j<GameMap.getMapCols();j++){
                if(row_i.get(j).charAt(0)=='0') {//如果当前map[i,j]的方块类型为OBSTACLE('0')，则断言value和调用原方法
                    assertEquals(GameMap.SquareType.OBSTACLE,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='1') {
                    assertEquals(GameMap.SquareType.FLOOR,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='2') {
                    assertEquals(GameMap.SquareType.FRAGILITY,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='3') {
                    assertEquals(GameMap.SquareType.ITEM,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='6') {
                    assertEquals(GameMap.SquareType.PLAYER_1,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='7') {
                    assertEquals(GameMap.SquareType.PLAYER_2,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='8') {
                    assertEquals(GameMap.SquareType.NPC,gameMapExample.getBlockSquareType(i,j));
                }else if(row_i.get(j).charAt(0)=='9') {
                    assertEquals(GameMap.SquareType.BUBBLE,gameMapExample.getBlockSquareType(i,j));
                }
            }
        }
    }

    @Test
    void blockIsObstacle() {
        //获取地图，以stage1Map为例
        gameMapExample.createMap("stage1Map");
        assertEquals(true,gameMapExample.blockIsObstacle(0,0));
        assertEquals(false,gameMapExample.blockIsObstacle(1,2));
    }

    @Test
    void blockIsWalkable() {
        //获取地图，以stage1Map为例
        gameMapExample.createMap("stage1Map");
        assertEquals(false,gameMapExample.blockIsWalkable(0,0));
        assertEquals(false,gameMapExample.blockIsWalkable(1,3));
        assertEquals(true,gameMapExample.blockIsWalkable(1,2));
    }

    @Test
    void outOfBoundary() {
        //获取地图，以stage1Map为例
        gameMapExample.createMap("stage1Map");
        assertEquals(true,gameMapExample.outOfBoundary(-1,-1));
        assertEquals(false,gameMapExample.outOfBoundary(1,1));
    }

//    @Test
//    void getIJ() {
//    }
//
//    @Test
//    void getXY() {
//    }
//
//    @Test
//    void testGetXY() {
//    }
//
//    @Test
//    void clearMapOther() {
//    }
//
//    @Test
//    void clearMapALL() {
//
//
//
//        System.out.println(ElementManager.getManager().getMap());
//    }

    private Map<String,Integer> invokeMap(String invoke)  throws NoSuchFieldException, IllegalAccessException{
        Field field = ElementManager.getManager().getClass().getDeclaredField(invoke);
        field.setAccessible(true);
        Map<String,Integer> result = (Map<String,Integer>) field.get(ElementManager.getManager());
        return result;
    }

}