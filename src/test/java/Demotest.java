import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Demotest {

    @Test
    public void test1(){
        assertEquals(4,add(2,2));
    }

    public int add(int a, int b){
        if (a<0){
            return a;
        }else {
            return a+b;
        }

    }
}
