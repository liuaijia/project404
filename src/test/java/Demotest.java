import org.junit.Test;

public class Demotest {

    @Test
    public void test1(){

        assert add(1, 2)==3;
    }

    public int add(int a, int b){
        if (a<0){
            return a;
        }else {
            return a+b;
        }

    }
}
