package model.manager;

import model.loader.ElementLoader;
import model.vo.SuperElement;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ElementManagerTest {
//    ElementManager elementManagerExample;

    @BeforeEach
    void setUp() throws IOException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        System.out.println("@BeforeEach，测试开始");

        ElementLoader.getElementLoader().readGamePro();
//        ElementManager.getManager().init();
        Map<String,Integer> result=invokeMap("priorityMap");

//        //获取元素管理器单例
//        Field field = ElementManager.getManager().getClass().getDeclaredField("elementManager");
//        field.setAccessible(true);
//        ElementManager elementManagerExample = (ElementManager) field.get(ElementManager.getManager());
//        //运行初始注入数据的私有方法
//        Method method = elementManagerExample.getClass().getDeclaredMethod("initMap");
//        method.setAccessible(true);
//        method.invoke(elementManagerExample);
//        method = elementManagerExample.getClass().getDeclaredMethod("initPriorityMap");
//        method.setAccessible(true);
//        method.invoke(elementManagerExample);

    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    void getMapKeyComparator() throws NoSuchFieldException, IllegalAccessException {
        //有效范围内分别进行测试：
        Assert.assertEquals(1,ElementManager.getManager().getMapKeyComparator().compare("player","explode"));
        Assert.assertEquals(-1,ElementManager.getManager().getMapKeyComparator().compare("floor","explode"));
        Assert.assertEquals(0,ElementManager.getManager().getMapKeyComparator().compare("magicBox","magicBox"));
    }

    private Map<String,Integer> invokeMap(String invoke)  throws NoSuchFieldException, IllegalAccessException{
        Field field = ElementManager.getManager().getClass().getDeclaredField(invoke);
        field.setAccessible(true);
        Map<String,Integer> result = (Map<String,Integer>) field.get(ElementManager.getManager());
        return result;
    }
}