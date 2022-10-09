import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class Demotest {

    @Test
    public void test1(){
        assertEquals(4,add(2,2));
    }

    @Test
    public void testNet() throws IOException {
        // 实例化OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //实例化RequestBody对象
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "dsfs")
                .add("num","1")
                .add("grade","10")
                .build();
        // 实例化Request对象
        Request request = new Request.Builder()
                .url("http://localhost:8002/upload")
                .post(requestBody)
                .build();
        try {
            // Response对象存储的就是获取的数据
            Response response = client.newCall(request).execute();
            String data = Objects.requireNonNull(response.body()).string();
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public int add(int a, int b){
        if (a<0){
            return a;
        }else {
            return a+b;
        }

    }
}
